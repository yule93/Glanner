import { DeleteForever } from "@material-ui/icons";
import { Box, Button, Divider, Paper, TextField, Grid, Typography, Avatar } from "@mui/material";
import { makeStyles } from "@mui/styles";
import React, { useState } from "react";

const useStyles = makeStyles({
  field: {
    marginTop: 20,
    // marginBottom: 20,
    // display: 'block',
    // flexGrow: 1,
    background: '#FFFFFF',
    border: '1px solid #E5E5E5',
    boxSizing: 'border-box',
    boxShadow: '0px 2px 6px rgba(0, 0, 0, 0.25)',
    borderRadius: '10px'    
  },
  smallInput: {
    '& .MuiOutlinedInput-root': {
      height: 40,      
    }
  },
  label: {
    paddingTop: 20,    
    marginRight: 20,
    textAlign: 'end',
    fontFamily: 'Noto Sans CJK KR',
    fontStyle: 'normal',
    fontWeight: 600,
    fontSize: '18px',
    lineHeight: '44px',
    // textAlign: 'right',
    color: '#5F5F5F'
  },
  btn: {
    background: 'rgba(149, 149, 149, 0.1)',
    border: '2px solid #959595',
    boxSsizing: 'border-box',
    borderRadius: '10px',
    fontFamily: 'Noto Sans CJK KR',
    fontStyle: 'normal',
    fontWeight: 'normal',
    fontSize: '14px',
    lineHeight: '16px',    
    /* identical to box height */
    width: '100px',
    height: '35px',
    display: 'flex',
    alignItems: 'center',
    color: '#959595',
    '&:hover': {
      backgroundColor: '#F6F6F6'
    }
  },
  attBtn: {
    background: '#FFFFFF',
    border: '1px solid #7B8C8C',
    boxSizing: 'border-box',
    boxShadow: '0px 2px 6px rgba(0, 0, 0, 0.25)',
    borderRadius: '10px',
    color: "#7B8C8C",
    position: "relative",
    display: "inline-block",
    width: '110px',
    // float: 'right'
  },
  attBtnText: {
    fontFamily: 'Noto Sans CJK KR',
    fontStyle: 'normal',
    fontWeight: 700,
    fontSize: '16px',
    lineHeight: '28px',
    textAlign: 'center',
    color: '#7B8C8C'
  }
});

export const BoardForm = () => {
  const classes = useStyles();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [titleError, setTitleError] = useState(false);
  const [contentError, setContentError] = useState(false);
  const [attachment, setAttachment] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    setTitleError(false)
    setContentError(false)
    if (title == '') {
      setTitleError(true)
    }

    if (content == '') {
      setContentError(true)
    }

    if (title && content) {
      fetch('http://localhost:8000/articles', {
        method: 'POST',
        headers: {"Content-type": "application/json"},
        body: JSON.stringify({title, content, attachment})
      }).then(() => {
        alert('작성 완료!')
      })
        .catch(err => {
          alert('작성 실패!')
        })
      setTitle("")
      setContent("")
      setAttachment("")
    }
  }
   // 첨부한 이미지 파일을 string으로 저장하기, 
   const onFileChange = (event) => {
    console.log(attachment, '1')
    const {target: {files}} = event;
    const theFile = files[0];        // input 태그의 file은 한개
    const reader = new FileReader(); // FileReader 객체는 파일(이미지 등)의 내용을 읽어서 문자열로 저장해줌.
    
    reader.onloadend = (finishedEvent) => {  // 읽기 동작이 끝났을 때 onloadend 이벤트 발생
      const {currentTarget: {result}} = finishedEvent;
      setAttachment(result);
    };
    if (theFile) {
      reader.readAsDataURL(theFile);
    };
    
  }
  return (
    <Paper style={{ margin: '100px 300px', padding: "20px, 5px", width: '1000px'}}>
      <form noValidate onSubmit={handleSubmit} style={{ padding: '40px 40px'}}>
        <Grid container>
          <Grid item xs={2}>
            <Typography className={classes.label}>제목</Typography>
          </Grid>
          <Grid item xs={10}>
            <TextField
              onChange={(e) => {
                if (title) {
                  setTitleError(false)
                }
                setTitle(e.target.value)}} 
              // label="제목"
              fullWidth
              required
              error={titleError}
              className={[classes.smallInput, classes.field].join(' ')}
              sx={{flexGrow: 2}}
            />
          </Grid>
        </Grid>
        <Grid container>
          <Grid item xs={2}>
            <Typography className={classes.label}>내용</Typography>
          </Grid>
          <Grid item xs={10}>
            <TextField 
              onChange={(e) => {
                if (content) {
                  setContentError(false)
                }
                setContent(e.target.value)
              }} 
              // label="내용"
              fullWidth
              required
              multiline={true}
              rows={15}
              error={contentError}
              className={classes.field}
            />
          </Grid>
        </Grid>
        <Grid container >
          <Grid item xs={2}>
            <Typography className={classes.label}>파일 첨부</Typography>
          </Grid>
          <Grid item xs={8}>            
            <TextField value={attachment} className={[classes.smallInput, classes.field].join(' ')} disabled fullWidth />
          </Grid>
          {attachment &&
            <Grid item xs={0.4} sx={{ mt: 3}}>                        
              <DeleteForever onClick={() => setAttachment("")} fontSize="large" />
            </Grid>
          }
            <Grid item xs={1}>
              <input
                  style={{ display: 'none' }}
                  id="upload-photo"
                  name="upload-photo"
                  type="file"
                  onChange={onFileChange}
                />            
              <label htmlFor="upload-photo" >  
                <Box sx={{ mt: 2.5, 
                      ml: 1.3 }}>                                 
                  <Button component="span" className={[classes.attBtn, classes.attBtnText].join(' ')}>                  
                    첨부하기
                  </Button>
                </Box>              
              </label>
            
            {/* {attachment && (
                <Avatar
                  variant="square"
                  src={attachment}
                  style={{backgroundImage: attachment }}
                  alt=""
                />)
              } */}              
          </Grid>
        </Grid>
        <Divider sx={{ mt: 2}} />
        <Grid container sx={{mt: 2, justifyContent: 'space-between'}}>
          {/* <Grid item xs={2}/> */}
          <Grid item>
            <Button className={classes.btn} variant="contained">목록으로</Button>
          </Grid>
          <Grid item sx={{ ml: 1}}>
            <Button className={classes.btn} type="submit" variant="contained">작성하기</Button>
          </Grid>
        </Grid>
      </form>
    </Paper>
  )
};