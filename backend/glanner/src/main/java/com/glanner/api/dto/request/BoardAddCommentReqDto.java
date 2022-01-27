package com.glanner.api.dto.request;

import com.glanner.core.domain.board.Board;
import com.glanner.core.domain.board.Comment;
import com.glanner.core.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardAddCommentReqDto {
    private Long boardId;
    private Long parentId;
    private String content;

    @Builder
    public BoardAddCommentReqDto(Long boardId, Long parentId, String content) {
        this.boardId = boardId;
        this.parentId = parentId;
        this.content = content;
    }

    public Comment toEntity(User user, Board board, Comment parent){
        return Comment.builder()
                .content(content)
                .user(user)
                .board(board)
                .parent(parent)
                .build();
    }
}
