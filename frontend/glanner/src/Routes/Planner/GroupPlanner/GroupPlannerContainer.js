import React from "react";

import Helmet from "react-helmet";

import GroupPlannerPresenter from "./GroupPlannerPresenter";
import axios from "axios";
import { useParams } from 'react-router-dom';
import { useEffect } from "react";
import Moment from "moment";
import jwt_decode from "jwt-decode";

export default function GroupPlannerContainer() {
  const [eventList, setEventList] = React.useState(null);
  const [usedId, setUserId] = React.useState("test2@naver.com");
  const { id } = useParams();
  const [glannerInfo, setGlannerInfo] = React.useState({});
  const [latestBoard, setLatestBoard] = React.useState([]);
  const [latestPlan, setlatestPlan] = React.useState([]);
  const [authData, setAuthData] = React.useState('');

  useEffect(() => {
    axios(`/api/glanner/${id}`)
      .then(res => {
        setGlannerInfo(res.data)
      })
      .catch(err => console.log(err))
    axios(`/api/glanner-board/${id}/0/3`)
      .then(res => {
        setLatestBoard(res.data)
      })
      .catch(err => console.log(err))
    axios(`/api/glanner/${id}/${Moment().format('YYYY-MM-DD')}`)
      .then(res => {
        setlatestPlan(res.data)
      })
      .catch(err => console.log(err))

    const token = localStorage.getItem('token');
    const decoded = jwt_decode(token);
    setAuthData(decoded)

  }, [id])
  // console.log(eventList);

  // 멤버 삭제
  const deleteMember = (user, userId) => {
    console.log(user, userId)
    const ok = window.confirm(`${user}님을 글래너에서 삭제하겠습니까?`)
    if (ok) {
      axios(`/api/glanner/user/${id}/${userId}`, {method: 'DELETE'})
      .then(res => {
        alert(`삭제되었습니다.`)
        axios(`/api/glanner/${id}`)
          .then(res => {
            setGlannerInfo(res.data)
          })
          .catch(err => console.log(err))
      })
      .catch(err => {
        alert('삭제할 수 없습니다.')
        console.log(err)
      })
    }
  }
  return (
    <>
      <Helmet>
        <title>Glanner | 그룹 플래너</title>
      </Helmet>
      <GroupPlannerPresenter
        groupPlannerId={id}
        numOfMember={glannerInfo.numOfMember}
        hostEmail={glannerInfo.hostEmail}
        membersInfos={glannerInfo.membersInfos}
        latestBoard={latestBoard}
        latestPlan={latestPlan}
        groupBoardId={glannerInfo.groupBoardId}
        deleteMember={deleteMember}
        authData={authData}
      />
    </>
  );
}
