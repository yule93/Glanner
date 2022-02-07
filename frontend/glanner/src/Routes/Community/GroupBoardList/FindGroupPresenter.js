import React from "react";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import InputAdornment from "@mui/material/InputAdornment";
import FormControl from "@mui/material/FormControl";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { ReactComponent as CircleUser } from "../../../assets/circle-user-solid.svg";
import { ReactComponent as UserFriend } from "../../../assets/user-friends.svg";
import "./FindGroup.css";
import {
  Button,
  Paper,
  styled as St,
  Grid,
  Typography,
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

export default function FindGroupPresenter({ 
  type, 
  loading, 
  boardList, 
  latestNoticeList,
  groupboardList,
}) {
  console.log(groupboardList);
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
        <Box className={classes.textField} sx={{ textAlign: "left", minHeight: "100%" }}>
          <Grid container rowSpacing={5} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
          {groupboardList.map(({ title, writer, date, list, full, present,tag }) => (
            <Grid item xs={6}>
              <Paper sx={{ p: 2, margin: 'auto', maxWidth: 500, flexGrow: 1 }}>
                <Grid container spacing={2}>
                <Grid item xs={12} sm container>
                  <Grid item xs container direction="column" spacing={2}>
                    <Grid item xs>
                      <Typography className='title' gutterBottom variant="subtitle1" component="div">
                        {title}
                      </Typography>
                      <Typography className = 'membernum' variant="body2" gutterBottom>
                      <UserFriend
                        style={{
                          color: "#5F5F5F",
                        }}
                      /> {present} / {full}
                      </Typography>
                      <Typography className='boardpost' variant="body2" color="text.secondary">
                        {list}
                      </Typography>
                      <Typography className='boardpost' variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                    </Grid>
                    <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        {tag}
                      </Typography>
                      </Grid>
                      <Grid item>
                      
                      <Typography className='nickName' sx={{ cursor: 'pointer' }} variant="body2">
                      <CircleUser
                        style={{
                          fontSize: 15 + "px",
                          color: "#5F5F5F",
                          backgroundColor: "#F2D0D9",
                          borderRadius: "50%",
                        }}
                      />{writer}
                      </Typography>
                    </Grid>
                  </Grid>
                  <Grid item>
                    <Typography variant="subtitle1" component="div">
                      {getTime(date)}
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
              </Paper>
          </Grid>
          ))}
          {/* <Grid item xs={6}>
          <Paper sx={{ p: 2, margin: 'auto', maxWidth: 500, flexGrow: 1 }}>
                <Grid container spacing={2}>
                <Grid item xs={12} sm container>
                  <Grid item xs container direction="column" spacing={2}>
                    <Grid item xs>
                      <Typography gutterBottom variant="subtitle1" component="div">
                        알고리즘 스터디
                      </Typography>
                      <Typography variant="body2" gutterBottom>
                        3/4
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                    </Grid>
                    <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        공부 알고리즘
                      </Typography>
                      </Grid>
                      <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        닉네임
                      </Typography>
                    </Grid>
                  </Grid>
                  <Grid item>
                    <Typography variant="subtitle1" component="div">
                      날짜
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Paper>
          </Grid>

          <Grid item xs={6}>
          <Paper sx={{ p: 2, margin: 'auto', maxWidth: 500, flexGrow: 1 }}>
                <Grid container spacing={2}>
                <Grid item xs={12} sm container>
                  <Grid item xs container direction="column" spacing={2}>
                    <Grid item xs>
                      <Typography gutterBottom variant="subtitle1" component="div">
                        알고리즘 스터디
                      </Typography>
                      <Typography variant="body2" gutterBottom>
                        3/4
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                    </Grid>
                    <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        공부 알고리즘
                      </Typography>
                      </Grid>
                      <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        닉네임
                      </Typography>
                    </Grid>
                  </Grid>
                  <Grid item>
                    <Typography variant="subtitle1" component="div">
                      날짜
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Paper>
            </Grid>
            
            <Grid item xs={6}>
            <Paper sx={{ p: 2, margin: 'auto', maxWidth: 500, flexGrow: 1 }}>
                <Grid container spacing={2}>
                <Grid item xs={12} sm container>
                  <Grid item xs container direction="column" spacing={2}>
                    <Grid item xs>
                      <Typography gutterBottom variant="subtitle1" component="div">
                        알고리즘 스터디
                      </Typography>
                      <Typography variant="body2" gutterBottom>
                        3/4
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                      <Typography variant="body2" color="text.secondary">
                        알고리즘 스터디 하실 분들 구합니다
                      </Typography>
                    </Grid>
                    <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        공부 알고리즘
                      </Typography>
                      </Grid>
                      <Grid item>
                      <Typography sx={{ cursor: 'pointer' }} variant="body2">
                        닉네임
                      </Typography>
                    </Grid>
                  </Grid>
                  <Grid item>
                    <Typography variant="subtitle1" component="div">
                      날짜
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
            </Paper>
          </Grid> */}
        </Grid>
              
        
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
