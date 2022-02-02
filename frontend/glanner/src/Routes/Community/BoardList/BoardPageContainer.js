import React, { useEffect, useState } from "react";
import BoardPagePresenter from "./BoardPagePresenter";
import Helmet from "react-helmet";
import axios from "axios";

export default function BoardPageContainer () { 
   
    const [loading, setLoading] = useState(true);
    const [boardList, setBoardList] = useState([]);
    const [latestNoticeList, setLatestNoticeList] = useState([]);
    
    // 임시 테스트용 API, 
    // `json-server --watch data/db.json --port 8000` 으로 서버를 켜야 데이터 받아올 수 있음
    // 백엔드 REST API 구축되면 지울 예정
    useEffect(() => {
      axios('http://localhost:8000/boardList', {method: 'GET'})
      .then(res => setBoardList(res.data))
      .catch(err => console.log(err))
      axios('http://localhost:8000/LatestNoticeList', {method: 'GET'})
        .then(res => {
          setLatestNoticeList(res.data) 
          setLoading(false)
        })
        .catch(err => console.log(err))
    }, [])
        
    return (
      <>
        <Helmet>
          <title>Glanner | 게시판</title>
        </Helmet>
        <BoardPagePresenter
          loading={loading}
          boardList={boardList}
          latestNoticeList={latestNoticeList}
        />
      </>
    );
  }

