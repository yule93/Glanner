import React, { useState } from 'react';
import { Card, CardHeader, CardContent, CardActions, Avatar, IconButton, Typography, Divider, Box, Button, TextField } from '@mui/material';
import { grey } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import { makeStyles } from '@mui/styles';
import MoreBtn from '../../Components/MoreBtn';
import { boardStyles } from './Board.styles';

const useStyles = makeStyles(boardStyles)

export const BoardDetail = () => {
  const classes = useStyles()
  const [open, setOpen] = useState(false);
  
  // const [title, setTitle] = useState("");
  // const [content, setContent] = useState("");
  // const [image, setImage] = useState("");

  const [like, setLike] = useState(0);
  const [commentLike, setCommentLike] = useState(0);
  const [nestedCommentLike, setNestedCommentLike] = useState(0);

  const openCommentForm = () => {
    if (open) {
      setOpen(false)
    } else {
      setOpen(true)
    }
  };

  // useEffect(() => {
  //   setTitle("테스트 제목")
  //   setContent('테스트 내용입니다. 테스트입니다')    
  // }, [])
  return (
      <Card className={classes.card}>
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: grey[500] }} aria-label="recipe">              
            </Avatar>
          }
          action={
            <MoreBtn />
          }
          title="닉네임이에요"
          subheader="2022.01.18 17:23"
          className={classes.dateText}
        />
        <Divider />
        {/* {image && <CardMedia
          component="img"
          height="194"
          image={image}
          alt="image"
        />} */}
        <CardContent>
          <h1 className={classes.title}>
            Lizard
          </h1>
          <p className={classes.content}>
            This impressive paella is a perfect party dish and a fun meal to cook
            together with your guests. Add 1 cup of frozen peas along with the mussels,
            if you like.
          </p>
        </CardContent>
        
        <CardActions disableSpacing sx={{display: 'flex', justifyContent: 'space-between'}}>
          <span className={classes.botText}>조회수 20{like ? <>{`, 좋아요 ${like}`}</>: null } </span>
          <div>
            <IconButton onClick={() => setLike(like + 1)}>
              <FavoriteIcon />
            </IconButton>
            <IconButton aria-label="share">
              <ShareIcon />
            </IconButton>
          </div>          
        </CardActions>

        <Divider />


        <Typography component="div" variant="h5" sx={{ padding: 3}}>댓글 2</Typography>
        {/* 댓글 작성창 */}
        <form>
          <TextField className={classes.field} fullWidth multiline={true} rows={3} placeholder='댓글을 달아주세요.' sx={{ml: 5, width: "95%"}}/>
          <Button type="submit" className={classes.btn} sx={{ml: 110, my: 1}}>작성</Button>
        </form>

        {/* 댓글 박스 */}
        <Box className={classes.comments}>
          <CardHeader
            avatar={
              <Avatar sx={{ bgcolor: grey[500] }} aria-label="recipe">              
              </Avatar>
            }
            action={
              <MoreBtn />
            }
            title="닉네임이에요"
            subheader="2022.01.18 17:23"
            className={classes.commentDateText}
          />         
          <CardContent>
            <p className={classes.commentContent}>
              This impressive paella is a perfect party dish and a fun meal to cook
              together with your guests. Add 1 cup of frozen peas along with the mussels,
              if you like.
            </p>
          </CardContent>
          <CardActions disableSpacing sx={{display: 'flex', justifyContent: 'space-between'}}>
            <span>
              <Button className={classes.botText} onClick={openCommentForm} component="span">답글쓰기</Button>
              <Button className={classes.botText} onClick={() => setCommentLike(commentLike + 1)} component="span"> 좋아요 {commentLike? <>{commentLike}</> : null}</Button>
            </span>            
          </CardActions>

          {/* 대댓글 작성창 */}
          {open &&
          <form>
            <TextField className={classes.field} fullWidth multiline={true} rows={3} placeholder='답글을 달아주세요.' sx={{mt: 2}}/>
            <Button className={classes.btn} sx={{ml: 105, my: 1}}>작성</Button>
          </form>
          }

          <Divider />

          {/* 대댓글 박스*/}
          <Box className={classes.nestedComment}>
            <CardHeader
              avatar={
                <>
                  <span 
                  className={classes.replyIcon}
                  style={{ position: 'absolute', left: '145px'}}
                  >ㄴ</span>
                  <Avatar sx={{ bgcolor: grey[500] }} aria-label="recipe">              
                  </Avatar>
                </>
              }
              action={
                <MoreBtn />
              }
              title="닉네임이에요"
              subheader="2022.01.18 17:23"
              className={classes.commentDateText}
            />
            <CardContent>
              <p className={classes.commentContent}>
                This impressive paella is a perfect party dish and a fun meal to cook
                together with your guests. Add 1 cup of frozen peas along with the mussels,
                if you like.
              </p>
            </CardContent>
            <CardActions disableSpacing sx={{display: 'flex', justifyContent: 'space-between'}}>
              <span>                
                <Button className={classes.botText} onClick={() => setNestedCommentLike(nestedCommentLike + 1)} component="span"> 좋아요 {nestedCommentLike? <>{nestedCommentLike}</> : null}</Button>
              </span>            
            </CardActions>
          </Box>
        </Box>
      </Card>
  );
}