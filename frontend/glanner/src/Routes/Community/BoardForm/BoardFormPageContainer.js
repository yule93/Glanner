import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { BoardFormPagePresenter } from "./BoardFormPagePresenter"

export const BoardFormPageContainer = () => {
  const navigate = useNavigate();
  const { state } = useLocation();
  const [data, setData] = useState({
    title: "",
    content: "",
    date: "",
    writer: "테스트 유저",
    count: 0,
    like: 0,
    comments: [],
    attachment: ""
  })

  const [titleError, setTitleError] = useState(false);
  const [contentError, setContentError] = useState(false);
  const [attachment, setAttachment] = useState("");
  
  // 글 수정인 경우, 기존 데이터를 입력창에 채워주기
  useEffect(() => {
    if (state) {
      setData(state)
    }       
  }, [])

  const handleSubmit = (e) => {
    e.preventDefault();

    setTitleError(false)
    setContentError(false)
    if (data.title === '') {
      setTitleError(true)
    }

    if (data.content === '') {
      setContentError(true)
    }
    // 기존 글을 수정하는 로직
    if (state) {
      const today = new Date(new Date().getTime()).toISOString()
      axios({
        url: `http://localhost:8000/boardList/${state.id}`,
        method: 'PUT',
        data: {
          title: data.title,
          content: data.content,
          date: today,
          // date: `${dateData.slice(0, 10)} ${dateData.slice(11, 19)}`,
          writer: data.writer,
          count: data.count,
          like: data.like,
          comments: data.comments,
          attachment: data.attachment
        }
      })
        .then(res => {
          alert('수정 성공!')
          console.log(res.data)
          navigate(`/board/${res.data.id}`)
        })
        .catch(err => alert('수정 실패!'))    
    
    } else {
      // 새로운 글 작성하는 로직
      const today = new Date(new Date().getTime()).toISOString()
      axios({
        url: 'http://localhost:8000/boardList',
        method: 'POST',
        data: {
          title: data.title,
          content: data.content,
          date: today,      
          writer: data.writer,
          count: data.count,
          like: data.like,
          comments: data.comments,
          attachment: data.attachment
        }
      })
        .then(res => {
          alert('작성 성공!')
          console.log(res.data)
          navigate(`/board/${res.data.id}`)
        })
        .catch(err => alert('작성 실패!'))
    }
  };
   // 첨부한 이미지 파일을 string으로 저장하기, 
   const onFileChange = (event) => {
    const {target: {files}} = event;
    const theFile = files[0];        // input 태그의 file은 한개
    const reader = new FileReader(); // FileReader 객체는 파일(이미지 등)의 내용을 읽어서 문자열로 저장해줌.
    
    reader.onloadend = (finishedEvent) => {  // 읽기 동작이 끝났을 때 onloadend 이벤트 발생
      const {currentTarget: {result}} = finishedEvent;
      setAttachment(result);
    };
    if (theFile) {
      reader.readAsDataURL(theFile);
    };    
  };

  const handle = (e) => {
    const newData = {...data}
    newData[e.target.id] = e.target.value
    setData(newData)
  };

  const deleteFile = () => {
    setAttachment("")
  }
  return (
    <BoardFormPagePresenter
      handleSubmit={handleSubmit}
      handle={handle}
      onFileChange={onFileChange}
      state={state}
      data={data}
      attachment={attachment}
      deleteFile={deleteFile}
    />
  )
  
};