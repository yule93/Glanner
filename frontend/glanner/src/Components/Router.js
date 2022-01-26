import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainPage from "../Routes/MainPage";
import Header from "./Header";
import Paper from "@mui/material/Paper";
import { BoardForm } from "../Routes/Community/BoardForm";
import { BoardDetail } from "../Routes/Community/BoardDetail";

export default () => (
  <Paper variant="outlined" sx={{width: "1200px"}} elevation={3}>
    <Header userName="글래너" />
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />}
        />
        <Route path="/home" />
        <Route path="/board-form"  element={<BoardForm />} />
        <Route path="/board-detail" element={<BoardDetail />} />
      </Routes>
    </Router>
  </Paper>
);