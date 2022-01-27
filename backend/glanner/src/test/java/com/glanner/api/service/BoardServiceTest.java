package com.glanner.api.service;

import com.glanner.api.dto.request.*;
import com.glanner.api.queryrepository.UserQueryRepository;
import com.glanner.core.domain.board.*;
import com.glanner.core.domain.user.Schedule;
import com.glanner.core.domain.user.User;
import com.glanner.core.domain.user.UserRoleStatus;
import com.glanner.core.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private FreeBoardRepository freeBoardRepository;

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void createUser(){
        User user = User.builder()
                .phoneNumber("010-6575-2938")
                .email("cherish8513@naver.com")
                .name("JeongJooHeon")
                .interests("#난그게재밌더라강식당다시보기#")
                .password("1234")
                .role(UserRoleStatus.ROLE_USER)
                .build();

        Schedule schedule = Schedule.builder()
                .build();
        user.changeSchedule(schedule);
        userRepository.save(user);
    }

    @Test
    public void testSaveFreeBoard() throws Exception{
        //given
        User user = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("제목")
                .content("내용")
                .build();


        //when
        FreeBoard freeBoard = boardSaveReqDto.toFreeBoardEntity(user);
        FreeBoard savedFreeBoard = boardRepository.save(freeBoard);

        //then
        assertThat(savedFreeBoard.getTitle()).isEqualTo("제목");
        assertThat(savedFreeBoard.getContent()).isEqualTo("내용");
        assertThat(savedFreeBoard.getUser()).isEqualTo(user);
    }

    @Test
    public void testSaveNoticeBoard() throws Exception{
        //given
        User user = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("제목")
                .content("내용")
                .build();

        List<MultipartFile> files = new ArrayList<>();
        files.add(new MockMultipartFile("files", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes()));
        files.add(new MockMultipartFile("files", "imagefile.jpeg", "image/jpeg", "<<jpeg data>>".getBytes()));

        //when
        NoticeBoard noticeBoard = boardSaveReqDto.toNoticeBoardEntity(user);

        if(!files.isEmpty()){
            String realPath = "uploads/";
            String date = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = realPath + File.separator + date;

            File folder = new File(saveFolder);

            if(!folder.exists()) folder.mkdir();
            for(MultipartFile file: files){
                String originalFileName = file.getOriginalFilename();
                FileInfo fileInfo = null;
                if(!originalFileName.isEmpty()){
                    String saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    fileInfo = FileInfo.builder()
                            .saveFolder(date)
                            .originFile(originalFileName)
                            .saveFile(saveFileName)
                            .board(noticeBoard)
                            .build();
                    try {
                        file.transferTo(new File(folder, saveFileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                noticeBoard.addFile(fileInfo);
            }
        }

        NoticeBoard savedNoticeBoard = boardRepository.save(noticeBoard);

        //then
        assertThat(savedNoticeBoard.getTitle()).isEqualTo("제목");
        assertThat(savedNoticeBoard.getContent()).isEqualTo("내용");
        assertThat(savedNoticeBoard.getUser()).isEqualTo(user);
    }

    @Test
    public void testUpdateBoard() throws Exception{
        //given
        User user = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("제목")
                .content("내용")
                .build();

        FreeBoard freeBoard = boardSaveReqDto.toFreeBoardEntity(user);
        freeBoardRepository.save(freeBoard);

        Board board = getBoard(boardRepository.findByTitleLike("%제목%"));
        BoardUpdateReqDto boardUpdateReqDto = BoardUpdateReqDto.builder()
                .title("제목 수정")
                .content("내용 수정")
                .fileUrls(null)
                .build();

        //when
        Board savedBoard = getBoard(boardRepository.findById(board.getId()));
        savedBoard.changeBoard(boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent(), boardUpdateReqDto.getFileUrls());
        Board updatedBoard = boardRepository.save(savedBoard);

        //then
        assertThat(updatedBoard.getTitle()).isEqualTo("제목 수정");
        assertThat(updatedBoard.getContent()).isEqualTo("내용 수정");
        assertThat(updatedBoard.getUser()).isEqualTo(savedBoard.getUser());
    }

    @Test
    public void testDeleteBoard() throws Exception{
        //given
        User user = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        FreeBoard savedFreeBoard = createFreeBoard();
        Comment comment = Comment.builder()
                .user(user)
                .content("Hi")
                .board(savedFreeBoard)
                .parent(null).build();

        Comment comment1 = Comment.builder()
                .user(user)
                .content("Hi")
                .board(savedFreeBoard)
                .parent(comment).build();

        savedFreeBoard.addComment(comment);
        savedFreeBoard.addComment(comment1);

        savedFreeBoard = boardRepository.save(savedFreeBoard);

        //when
        Board board = getBoard(boardRepository.findById(savedFreeBoard.getId()));
        boardRepository.delete(board);

        //then
        assertThat(boardRepository.count()).isEqualTo(0);
        assertThat(commentRepository.count()).isEqualTo(0);
    }

    @Test
    public void testAddComment() throws Exception{
        //given
        User user = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        FreeBoard savedFreeBoard = createFreeBoard();
        BoardAddCommentReqDto boardAddCommentReqDto1 = BoardAddCommentReqDto.builder()
                .content("댓글이용")
                .parentId(null)
                .boardId(savedFreeBoard.getId())
                .build();

        //when
        Board board = boardRepository.findById(boardAddCommentReqDto1.getBoardId())
                .orElseThrow(()->new IllegalStateException("없는 게시물입니다."));
        Comment parent1 = (boardAddCommentReqDto1.getParentId() == null)?
                null:getComment(commentRepository.findById(boardAddCommentReqDto1.getParentId()));
        Comment comment1 = boardAddCommentReqDto1.toEntity(user, board, parent1);
        board.addComment(comment1);
        commentRepository.save(comment1);
        boardRepository.save(board);

        // given: 대댓글
        BoardAddCommentReqDto boardAddCommentReqDto2 = BoardAddCommentReqDto.builder()
                .content("대댓글이용")
                .parentId(comment1.getId())
                .boardId(savedFreeBoard.getId())
                .build();

        // when: 대댓글
        Comment parent2 = (boardAddCommentReqDto2.getParentId() == null)?
                null:getComment(commentRepository.findById(boardAddCommentReqDto2.getParentId()));
        Comment comment2 = boardAddCommentReqDto2.toEntity(user, board, parent2);
        board.addComment(comment2);

        Board savedBoard = boardRepository.save(board);

        //then
        assertThat(savedBoard.getComments().size()).isEqualTo(2);
        assertThat(savedBoard.getComments().get(0).getContent()).isEqualTo("댓글이용");
        assertThat(savedBoard.getComments().get(1).getContent()).isEqualTo("대댓글이용");
        assertThat(savedBoard.getComments().get(1).getParent()).isEqualTo(savedBoard.getComments().get(0));

    }

    @Test
    public void testEditComment() throws Exception{
        //given
        FreeBoard savedFreeBoard = createFreeBoard();
        Comment comment = Comment.builder()
                .content("내용내용")
                .parent(null)
                .board(savedFreeBoard).build();
        savedFreeBoard.addComment(comment);
        savedFreeBoard = freeBoardRepository.save(savedFreeBoard);

        BoardUpdateCommentReqDto reqDto = new BoardUpdateCommentReqDto("내용수정수정");

        //when
        Comment savedComment = getComment(commentRepository.findById(savedFreeBoard.getComments().get(0).getId()));
        savedComment.changeContent(reqDto.getContent());
        commentRepository.save(comment);

        //then
        assertThat(savedComment.getContent()).isEqualTo("내용수정수정");
        assertThat(savedComment.getBoard().getComments().get(0).getContent()).isEqualTo("내용수정수정");
    }

    @Test
    public void testDeleteComment() throws Exception{
        //given
        FreeBoard savedFreeBoard = createFreeBoard();
        Comment comment = Comment.builder()
                .content("내용내용")
                .parent(null)
                .board(savedFreeBoard).build();
        savedFreeBoard.addComment(comment);
        savedFreeBoard = freeBoardRepository.save(savedFreeBoard);

        //when
        Comment savedComment = getComment(commentRepository.findById(savedFreeBoard.getComments().get(0).getId()));
        Board board = savedComment.getBoard();
        board.getComments().remove(savedComment);
        commentRepository.delete(savedComment);

        //then
        FreeBoard updatedFreeBoard = (FreeBoard) getBoard(boardRepository.findById(savedFreeBoard.getId()));
        assertThat(updatedFreeBoard.getComments().size()).isEqualTo(0);
        assertThat(commentRepository.count()).isEqualTo(0);
    }

    @Test
    public void testUpdateCount() throws Exception{
        // given
        FreeBoard savedFreeBoard = createFreeBoard();

        BoardCountReqDto boardCountReqDto1 = BoardCountReqDto.builder()
                .countType("LIKE").build();
        BoardCountReqDto boardCountReqDto2 = BoardCountReqDto.builder()
                .countType("COUNT").build();

        // when
        Board board = getBoard(boardRepository.findById(savedFreeBoard.getId()));
        board.updateCount();
        Board updatedBoard = boardRepository.save(board);

        FreeBoard freeBoard = (FreeBoard) getBoard(boardRepository.findById(savedFreeBoard.getId()));
        freeBoard.updateCount(boardCountReqDto1.getCountType());
        FreeBoard updatedFreeBoard = boardRepository.save(freeBoard);

        // then
        assertThat(updatedBoard.getCount()).isEqualTo(1);
        assertThat(updatedFreeBoard.getLikeCount()).isEqualTo(1);
    }

    public FreeBoard createFreeBoard(){
        User user = getUser(userQueryRepository.findByEmail("cherish8513@naver.com"));
        FreeBoard freeBoard = FreeBoard.builder()
                .title("제목이에요")
                .content("내용입니다")
                .likeCount(0)
                .disLikeCount(0)
                .count(0)
                .user(user)
                .build();
        return boardRepository.save(freeBoard);
    }

    public Board getBoard(Optional<Board> board){
        return board.orElseThrow(()->new IllegalStateException("존재하지 않는 게시물입니다."));
    }

    public Comment getComment(Optional<Comment> comment){
        return comment.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 댓글입니다.")
        );
    }
    public User getUser(Optional<User> user){
        return user.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
}