import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { BoardDetailPagePresenter } from "./BoardDetailPagePresenter"

export const BoardDetailPageContainer = () => {
  const { id } = useParams();
  const [loading, isLoading] = useState(true);
  const [post, setPost] = useState({});
  const [comments, setComments] = useState([]);
  const [content, setContent] = useState("");

  // json-server를 이용한 CRUD 임시 API
  // 상세페이지는 크게 본문(post)과 댓글(comments) 섹션으로 나누어져 있음. 서버에 총 2번의 요청을 보낸 후 각각의 데이터를 받아옴.
  useEffect(() => {    
    
    axios(`http://localhost:8000/boardList/${id}`, {method: 'GET'})
     .then(res => {
       
      axios(`http://localhost:8000/boardList/${id}`, {method: 'PATCH', data: {count: res.data.count + 1}})
      .then(res => {       
        setPost(res.data)
        isLoading(false)
       })
      .catch(err => console.log(err))

       setPost(() => res.data)    
       isLoading(false)
      })
     .catch(err => console.log(err))    
    
    axios(`http://localhost:8000/comments?postId=${id}`, {method: 'GET'})
    .then(res => {
      setComments(() => res.data)
      isLoading(false)
    })
    .catch(err => console.log(err))
    
    
  }, [])

  // 해당 게시글의 좋아요 + 1
  const addLike = () => {
    axios(`http://localhost:8000/boardlist/${id}`, {
      method: 'PATCH',
      data: {like: post.like + 1}
    })
      .then(res => {
        setPost(res.data)
      })
      .catch(err => console.log(err))
  }

  //  해당 게시글에 댓글 && 대댓글 남기기
  // responseTo는 부모 댓글의 id임. responseTo가 -1 이라면 루트 댓글, responseTo가 -1이 아니라 자연수라면 대댓글임
  const addComment = (newCommentData, parentCommentId) => {
    const today = Date();    
    const commentData = {
      content: newCommentData,
      writer: '테스트 유저',
      date: today,
      like: 0,
      postId: post.id,
      responseTo: parentCommentId
    }
    axios(`http://localhost:8000/comments`, {
      method: 'POST',
      data: commentData
    }).then(res => 
      setComments(comments.concat(res.data)) 
      )
      .catch(err => console.log(err.message))
  }  

  // 댓글 && 대댓글 수정하기
  const updateComment = (commentContent, commentData) => {    
    const today = Date();   
    const newCommentData = {
      content: commentContent,
      writer: commentData.writer,
      date: today,
      like: commentData.like,
      postId: post.id,
      responseTo: commentData.responseTo
    }
    axios(`http://localhost:8000/comments/${commentData.id}`, {
      method: 'PUT',
      data: newCommentData
    }).then(res => {
      const newCommentList = comments.map(comment => {
        if (comment.id !== commentData.id) {
          return comment
        } else {
          return res.data
        }
      })
      setComments(newCommentList)
    } 
      )
      .catch(err => console.log(err.message))
  }

  // 댓글 && 대댓글 좋아요 + 1
  const addCommentLike = (_comment) => {
    axios(`http://localhost:8000/comments/${_comment.id}`, {
      method: 'PATCH',
      data: {like: _comment.like + 1}
    })
      .then(res => {
        const newCommentsData = comments.map(comment => {
          if (comment.id === _comment.id) {
            comment.like ++
            return comment
          } else {
            return comment
          }
        })
        setComments(newCommentsData)
      })
      .catch(err => console.log(err))
  }

  return (
    <BoardDetailPagePresenter 
    loading={loading}
    post={post}
    addLike={addLike}
    content={content}
    setContent={setContent}
    comments={comments}
    setComments={setComments}
    addComment={addComment}
    addCommentLike={addCommentLike}
    updateComment={updateComment}
    />
  )
}