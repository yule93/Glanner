import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import MainPage from "../Routes/MainPage";
import Header from "./Header";
import Navigator from "./Navigator";

import Paper from "@mui/material/Paper";
import styled from 'styled-components';
import BoardDetail from "../Routes/Community/BoardDetail";
import BoardList from "../Routes/Community/BoardList";
import BoardForm from "../Routes/Community/BoardForm";

const HeaderDiv = styled.div`
  border-bottom: 2px solid #e5e5e5;
`;

export default () => (
  <Paper elevation={2} sx={{width: "100%", minWidth:"900px", minHeight:"100%"}}>
    <HeaderDiv>
      <Header title="글래너님의 플래너" />
    </HeaderDiv>
    <Router>
      <Navigator
        PaperProps={{
          style: {
            height: 100 + "%",
            backgroundColor: "#f6f6f6",
            padding: "0 20px",
            display: "inline-block",
            border: "0px"
          },
        }}
      />
      <Routes>
        <Route path="*" element={<MainPage />}/>
        <Route path="/" element={<MainPage />}
        />
        <Route path="/community" element={<BoardList />} />
        <Route path="/home" />
        <Route path="/board-form"  element={<BoardForm />} />
        <Route path="/board/:id" element={<BoardDetail />} />

      </Routes>
    </Router>
  </Paper>
);