import React from "react";
import styled from "styled-components";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import InputAdornment from "@mui/material/InputAdornment";
import FormControl from "@mui/material/FormControl";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Paper,
  styled as St,
  ListItemText,
  ListItem,
  List,
  Grid,
} from "@mui/material";
import { Link } from "react-router-dom";
import { makeStyles } from "@mui/styles";
import "moment/locale/ko";
import { boardStyles } from "../Board.styles";
import getTime from "../helper";

// const latestNoticeList = [
//   {
//     id: 1,
//     title: "공지 1",
//     writer: "관리자",
//     date: "22.01.15",
//   },
// ];
// const boardList = [
//   {
//     id: 1,
//     title: "예시 1",
//     writer: "관리자",
//     date: "22.01.20",
//   },
//   {
//     id: 2,
//     title: "예시 2",
//     writer: "관리자",
//     date: "22.01.20",
//   },
//   {
//     id: 3,
//     title: "예시 3",
//     writer: "관리자",
//     date: "22.01.20",
//   },
//   {
//     id: 4,
//     title:
//       "Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
//     writer: "관리자",
//     date: "22.01.20",
//   },
// ];

const WriteButton = St(Button)({
  height: "30px",
  border: "1px solid #8C7B80",
  color: "#8C7B80",
  borderRadius: "5px",
  "&:hover": {
    color: "#FFFFFF",
    backgroundColor: "#8C7B80",
    borderColor: "#8C7B80",
    boxShadow: "none",
  },
});

const useStyles = makeStyles(boardStyles)

export default function BoardPagePresenter({ 
  type, 
  loading, 
  boardList, 
  latestNoticeList,
}) {
  const classes = useStyles();
  return (
    <>
      {loading && <div>Loading...</div>}
      {(boardList || latestNoticeList) &&
      <>
        <Box
          component="form"
          noValidate
          autoComplete="off"
          sx={{ textAlign: "right", width: "95%", minHeight: "100%", m: 0 }}
        >
          <FormControl sx={{ m: 1, width: "25ch" }} size="small">
            <InputLabel htmlFor="search-board">이름, 제목</InputLabel>
            <OutlinedInput
              id="search-board"
              endAdornment={
                <InputAdornment position="end">
                  <IconButton aria-label="search-button" onClick={""} edge="end">
                    <FontAwesomeIcon icon={faSearch} className="searchIcon" />
                  </IconButton>
                </InputAdornment>
              }
              label="Password"
            />
          </FormControl>
        </Box>
        <Box className={classes.textField} sx={{ textAlign: "center", minHeight: "100%" }}>
          <List
            sx={{
              width: "90%",
              bgcolor: "background.paper",
              margin: "0 auto",
              minHeight: "100%",
              borderTop: "2px solid #e5e5e5",
            }}
          >
            {latestNoticeList.map(({ id, title, writer, date }) => (
              <Paper sx={{ mb: 1, width: "100%", backgroundColor: "#F9F9F9" }} key={id}>
                <ListItem
                  disableGutters
                  sx={{ height: "35px", textAlign: "center", color: "#DB1111" }}
                >
                  <Grid container>
                    <Grid item xs={1}>
                      <ListItemText primary={"[공지]"} />                
                    </Grid>
                    <Grid item xs={8}>
                      <ListItemText sx={{ whiteSpace: 'nowrap', overflow: 'hidden' }}>{title}</ListItemText>                
                    </Grid>
                    <Grid item xs={1}>
                      <ListItemText>{writer}</ListItemText>
                    </Grid>
                    <Grid item xs={2}>
                      <ListItemText>{getTime(date)}</ListItemText>
                    </Grid>
                  </Grid>
                </ListItem>
              </Paper>
            ))}
          </List>
          <List
            sx={{
              width: "90%",
              bgcolor: "background.paper",
              margin: "0 auto",
              borderBottom: "2px solid #E5E5E5",
              minHeight: "100%",
            }}
          >
            {boardList.map(({ id, title, writer, date }) => (
              <Paper sx={{ mb: 1, width: "100%", backgroundColor: "#F9F9F9" }} key={id}>
                <ListItem                
                  disableGutters
                  sx={{ height: "35px", textAlign: "center" }}
                >
                  <Grid container>
                    <Grid item xs={1}>
                      <ListItemText primary={type == "공지" ? "[공지]" : "[자유]"} />
                    </Grid>
                    <Grid item xs={8}>
                      <Link to={`/board/${id}`}>
                        <ListItemText>{title}</ListItemText>
                      </Link>
                    </Grid>
                    <Grid item xs={1}>
                      <ListItemText >{writer}</ListItemText>
                    </Grid>
                    <Grid item xs={2}>
                      <ListItemText>{getTime(date)}</ListItemText>
                    </Grid>
                  </Grid>
                </ListItem>
              </Paper>
            ))}
          </List>
        </Box>
        <div style={{ width: "95%", textAlign: "right", marginTop: "12px" }}>
          <Link to={`/board-form`}>
            <WriteButton WriteButton variant="">글쓰기</WriteButton>
          </Link>
        </div>
      </>}
    </>
  );
}