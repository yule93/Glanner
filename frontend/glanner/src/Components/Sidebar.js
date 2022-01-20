import React from "react";
import styled from "styled-components";
import { FontAwesomeIcon, faSearch } from "@fortawesome/react-fontawesome";
import { faWrench } from "@fortawesome/free-solid-svg-icons";

import logo from '../assets/glannerLogo1.png'

const Sidebar = styled.div`
  width: 350px;
  height: 100vh;
  color: #f6f6f6;
  z-index: 10;
  text-align: left;
  align-items: center;
  padding: 50px;
  border: 1px solid #e5e5e5;
  position: relative;
`;

const SidebarMyPlannerLists = styled.div`
  font-size: 14px;
  color: #959595;
  margin-top: 50px;
`;

const SidebarGroupPlannerLists = styled.div`
  font-size: 14px;
  color: #959595;
  margin-top: 50px;
`;

const GroupPlannerList = styled.div`
  margin-top: 10px;
  padding-left: 10px;
  height: 40px;
  background-color: #ffffff;
  color: #5f5f5f;
  font-size: 16px;
  text-align: left;
  line-height: 40px;
  border-radius: 5px;
  box-shadow: 0.5px 2px 5px 0.5px rgba(130, 130, 130, 0.2);
  border-bottom: 3px solid
    ${(props) => (props.current ? "#3498db" : "transparent")};
  transition: border-bottom 0.5s ease-in-out;
`;

const SettingAndLogout = styled.div`
  position: absolute;
  bottom: 0;
  margin-bottom: 50px;
  color: #909090;
  font-size: 16px;
`;

export default function Example() {
  return (
    <Sidebar>
      <img src={logo} style={{height: 50+"px"}} />
      <SidebarMyPlannerLists>
        <div style={{ marginBottom: 20 + "px" }}>내 플래너</div>
        <GroupPlannerList>글래너님의 플래너</GroupPlannerList>
      </SidebarMyPlannerLists>
      <SidebarGroupPlannerLists>
        <div style={{ marginBottom: 20 + "px" }}>그룹 플래너</div>
        <GroupPlannerList>알고리즘 스터디</GroupPlannerList>
        <GroupPlannerList>스프링 스터디</GroupPlannerList>
        <GroupPlannerList>독서 모임</GroupPlannerList>
      </SidebarGroupPlannerLists>
      <SettingAndLogout>
        <ul>
          <li style={{ marginBottom: 10 + "px" }}>
            <FontAwesomeIcon
              icon={faWrench}
              className="wrench"
              style={{ color: "#909090" }}
            />{" "}
            설정
          </li>
          <li>
            로그아웃
          </li>
        </ul>
      </SettingAndLogout>
    </Sidebar>
  );
}
