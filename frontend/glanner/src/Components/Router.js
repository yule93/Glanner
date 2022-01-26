import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainPage from "../Routes/MainPage";
import Header from "./Header";
import Paper from "@mui/material/Paper";

export default () => (
  <Paper variant="outlined" sx={{width: "100%"}} elevation={3}>
    <Header userName="글래너" />
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />}
        />
        <Route path="/home" />
      </Routes>
    </Router>
  </Paper>
);
