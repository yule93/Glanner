import React from "react";
import styled from "styled-components";
import Grid from "@mui/material/Grid";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell } from "@fortawesome/free-solid-svg-icons";
import { ReactComponent as CalendarIcon } from "../assets/calendar-check-solid.svg";
import { ReactComponent as CircleUser } from "../assets/circle-user-solid.svg";

const HeaderContainer = styled.div`
  word-break: break-all;
  height: 100px;
  max-width: 100%;
  overflow: hidden;
  border-bottom: 1px solid #f6f6f6;
  font-size: 16px;
  align-content: center;
  color: 5f5f5f;
`;

const NotificationContainer = styled.div`
  height: 100px;
  float: right;
  margin: 0 25px;
`;

export default function Header({ userName }) {
  return (
    <HeaderContainer>
      <Grid container spacing={3}>
        <Grid item xs={8} sx={{ textAlign: "left", fontSize: "30px", display:'inline-block'}}>
          <div style={{marginLeft: "20px"}}>{userName}님의 플래너</div>
        </Grid>
        <Grid item xs={4}>
          <NotificationContainer>
            <CalendarIcon
              style={{
                fontSize: 30 + "px",
                marginRight: 5 + "px",
                color: "#5F5F5F",
              }}
            />
            오늘의 일정
            <FontAwesomeIcon
              icon={faBell}
              className="bell"
              style={{
                fontSize: 30 + "px",
                marginLeft: 5 + "px",
                marginRight: 5 + "px",
                color: "#5F5F5F",
              }}
            />
            알림함 {" | "}
            <CircleUser
              style={{
                fontSize: 40 + "px",
                color: "#5F5F5F",
                backgroundColor: "#F2D0D9",
                borderRadius: "50%"
              }}
            />
          </NotificationContainer>
        </Grid>
      </Grid>
    </HeaderContainer>
  );
}
