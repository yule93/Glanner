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
  min-width: 100%;
  overflow: hidden;
  font-size: 16px;
  color: 5f5f5f;
`;

export default function Header({ title }) {
  return (
    <HeaderContainer>
      <Grid container spacing={3} direction="row">
        <Grid item xs={5} sx={{ textAlign: "left", fontSize: "30px" }}>
          <div style={{ marginLeft: "20px", lineHeight: 3 }}>{title}</div>
        </Grid>
        <Grid
          container
          item
          xs={7}
          sx={{ textAlign: "center" }}
          direction="row"
          justifyContent="right"
          alignItems="center"
        >
          <Grid item xs={6} />
          <Grid item xs={2}>
            <CalendarIcon
              style={{
                fontSize: 30 + "px",
                color: "#5F5F5F",
                marginRight: "3px"
              }}
            />
            오늘의 일정
          </Grid>
          <Grid item xs={1}>
            <FontAwesomeIcon
              icon={faBell}
              className="bell"
              style={{
                fontSize: 30 + "px",
                color: "#5F5F5F",
                marginRight: "3px"
              }}
            />
            알림함
          </Grid>
          <Grid item xs={1} sx={{
            //borderLeft: "1.5px solid #B5B5B5",
            width: "40px"
          }}>
            <CircleUser
              style={{
                fontSize: 40 + "px",
                color: "#5F5F5F",
                backgroundColor: "#F2D0D9",
                borderRadius: "50%",
              }}
            />
          </Grid>
        </Grid>
      </Grid>
    </HeaderContainer>
  );
}
