import * as React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Box from "@mui/material/Box";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";

import styled from "styled-components";
import {
  faWrench,
  faCircle,
  faAngleRight,
  faSearch,
  faUserFriends,
  faComment,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { ReactComponent as Logout } from "../assets/arrow-right-from-bracket-solid.svg";
import logo from "../assets/glannerLogo1.png";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { addGlanner, onClickPlanner, removeGlanner } from "../redux/planners";
import { deleteGlanner, fetchGlanner } from "../redux/apiCalls";

const GroupPlannerList = styled.div`
  background-color: #ffffff;
  color: #5f5f5f;
  font-size: 16px;
  vertical-align: middle;
  border-radius: 5px;
  box-shadow: 0.5px 2px 5px 0.5px rgba(130, 130, 130, 0.2);
  min-width: 200px;
  &:hover {
    background-color: rgba(255, 255, 255, 0.08);
  }
`;

const myPlanners = ["글래너님의 플래너", "개인 플래너1"]; // useState, useRedux 들어갈 자리
const groupPlanners = [
  "알고리즘 스터디",
  "스프링 스터디",
  "독서 모임",
  "00대 16학번 모임",
]; // useState, useRedux 들어갈 자리
const categories = [
  {
    id: "내 플래너",
    children: [
      // {
      //   id: myPlanners[0],
      //   active: true,
      // },
      // {
      //   id: myPlanners[1],
      //   active: false,
      // },
      // {
      //   id: "개인 플래너2",
      //   active: false,
      // },
      // {
      //   id: "개인 플래너3",
      //   active: false,
      // },
      // {
      //   id: "개인 플래너4",
      //   active: false,
      // },
    ],
  },
  {
    id: "그룹 플래너",
    children:
    [
      // {
      //   id: groupPlanners[0],
      //   active: false,
      // },
      // {
      //   id: groupPlanners[1],
      //   active: false,
      // },
      // {
      //   id: groupPlanners[2],
      //   active: false,
      // },
      // {
      //   id: groupPlanners[3],
      //   active: false,
      // },
      // {
      //   id: "축구",
      //   active: false,
      // },
    ],
  },
];

const boards = [
  {
    id: "게시판",
    children: [
      {
        id: "그룹 찾기",
        icon: (
          <FontAwesomeIcon
            icon={faSearch}
            className="searchIcon"
            style={{ width: 15 + "px" }}
          />
        ),
        type: "group",
      },
      {
        id: "자유 게시판",
        icon: (
          <FontAwesomeIcon
            icon={faUserFriends}
            className="usersIcon"
            style={{ width: 15 + "px" }}
          />
        ),
        type: "free",
      },
      {
        id: "공지사항",
        icon: (
          <FontAwesomeIcon
            icon={faComment}
            className="commentIcon"
            style={{ width: 15 + "px" }}
          />
        ),
        type: "notice",
      },
    ],
  },
];

// const onLogout = () => {
//   sessionStorage.removeItem("token");
//   document.location.href = "";
// };

const settings = [
  {
    id: "",
    children: [
      {
        id: "설정",
        icon: (
          <FontAwesomeIcon
            icon={faWrench}
            className="wrench"
            style={{ width: 15 + "px" }}
          />
        ),
      },
      {
        id: "로그아웃",
        icon: <Logout style={{ width: 15 + "px" }} />,
        // func: onLogout(),
      },
    ],
  },
];

const item = {
  py: 0,
  color: "#5f5f5f",
  "&:hover, &:focus": {
    bgcolor: "rgba(255, 255, 255, 0.08)",
  },
};

const groupItem = {
  py: 0,
  color: "#5f5f5f",
  "&:hover, &:focus": {
    bgcolor: "rgba(255, 255, 255, 0.08)",
  },
};

const settingItem = {
  position: "relative",
  color: "#909090",
  fontSize: "16px",
};

// const onClickPlanner = (e) => {
//   categories.map(({ id, children }) => {
//     children.map(({ id: childId, active }) => {
//       if (e.currentTarget.id == childId) {
//         active = true;
//       } else {
//         active = false;
//       }
//     });
//   });
// };

function Navigator(props) {
  const { ...other } = props;

  const planners = useSelector(state => state.planner.plannerList);
  const dispatch = useDispatch();
  useEffect(() => {
    fetchGlanner(dispatch)
  }, [])
  
  return (
    <Drawer variant="persistent" {...other} open={true} varient="no">
      <List disablePadding sx={{ display: "inline-block" }}>
        <ListItem
          sx={{
            px: "20px",
            mt: "25px",
            color: "#000000",
            fontWeight: "bold",
            fontFamily: "Rozha One",
          }}
        >
          <Link to={``}>
            <img src={logo} style={{ height: 40 + "px" }} />
          </Link>
        </ListItem>
        {planners.map(planner => (
          <Box
            key={planner.name}
            sx={{
              maxHeight: "240px",
            }}
          >
            <ListItem sx={{ pb: 0, px: 2, mt: 2 }}>
              <ListItemText sx={{ color: "#959595", fontSize: "14px" }}>
                {planner.name}
              </ListItemText>
            </ListItem>            
              <Box
                sx={{
                  maxHeight: "200px",
                  overflow: "scroll",
                }}
              >
                {planner.children.map(({ plannerName, plannerId, glannerName, glannerId, active }) =>                   
                  <ListItem key={glannerId} sx={{ pb: 0 }}>
                    <GroupPlannerList>
                      {planner.name === '내 플래너' ? 
                        <Link to={`/daily`}>
                          <ListItemButton
                            selected={active}
                            sx={item}
                            onClick={(e) => dispatch(onClickPlanner(Number(e.currentTarget.id)))}
                            id={plannerId}
                          >
                            <ListItemText>
                              {planner.name === "그룹 플래너" ? (
                                <FontAwesomeIcon
                                  icon={faCircle}
                                  className="circle"
                                  style={{ width: 12 + "px", color: "#ABC3FF" }}
                                />
                              ) : (
                                <FontAwesomeIcon
                                  icon={faCircle}
                                  className="circle"
                                  style={{ width: 12 + "px", color: "#FFABAB" }}
                                />
                              )}
                              {"  "}
                              {plannerName}
                              {active ? (
                                <FontAwesomeIcon
                                  icon={faAngleRight}
                                  className="arrowRight"
                                  style={{
                                    width: 15 + "px",
                                    color: "#959595",
                                    marginLeft: 10 + "px",
                                  }}
                                />
                              ) : (
                                ""
                              )}
                            </ListItemText>
                          </ListItemButton>
                        </Link>
                        :
                        <Link to={`/group/${glannerId}`}>
                          <ListItemButton
                            selected={active}
                            sx={item}
                            onClick={(e) => dispatch(onClickPlanner(Number(e.currentTarget.id)))}
                            id={glannerId}
                          >
                            <ListItemText>
                              {planner.name === "그룹 플래너" ? (
                                <FontAwesomeIcon
                                  icon={faCircle}
                                  className="circle"
                                  style={{ width: 12 + "px", color: "#ABC3FF" }}
                                />
                              ) : (
                                <FontAwesomeIcon
                                  icon={faCircle}
                                  className="circle"
                                  style={{ width: 12 + "px", color: "#FFABAB" }}
                                />
                              )}
                              {"  "}
                              {glannerName}
                              {active ? (
                                <FontAwesomeIcon
                                  icon={faAngleRight}
                                  className="arrowRight"
                                  style={{
                                    width: 15 + "px",
                                    color: "#959595",
                                    marginLeft: 10 + "px",
                                  }}
                                />
                              ) : (
                                ""
                              )}
                            </ListItemText>
                          </ListItemButton>
                        </Link>
                      }
                    </GroupPlannerList>
                  </ListItem>
                )}
              </Box>
          </Box>
        ))}

        {/* ! 게시판 부분 */}
        {boards.map(({ id, children }) => (
          <Box key={id} sx={{ mt: 3 }}>
            <ListItem>
              <ListItemText sx={{ color: "#959595" }}>{id}</ListItemText>
            </ListItem>
            {children.map(({ id: childId, icon, type }) => (
              <ListItem key={childId} sx={{ py: 0, px: "5px" }}>
                {/* 게시판 종류에 따라 링크 나뉘는 분기 부분 */}
                <Link to={`/community/${type}`}>
                  <ListItemButton sx={groupItem}>
                    <ListItemText>
                      {icon}
                      {"  "}
                      {childId}
                    </ListItemText>
                  </ListItemButton>
                </Link>
              </ListItem>
            ))}
          </Box>
        ))}
        <div
          style={{
            minHeight: "60px",
            width: "auto",
            content: "",
          }}
        ></div>
        {/* ! 설정&로그아웃 부분 */}
        {settings.map(({ id, children }) => (
          <Box key={id} sx={settingItem}>
            {children.map(({ id: childId, icon, func }) => (
              <ListItem key={childId} sx={{ p: 0 }}>
                <ListItemButton
                  sx={{ m: 0, height: "30px" }}
                  components="a"
                  //onClick={func}
                >
                  <ListItemText>
                    {icon} {childId}
                  </ListItemText>
                </ListItemButton>
              </ListItem>
            ))}
          </Box>
        ))}
      </List>
    </Drawer>
  );
}

export default Navigator;
