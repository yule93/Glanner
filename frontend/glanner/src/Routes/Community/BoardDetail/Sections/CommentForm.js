import { Button, Grid, TextField } from "@mui/material"
import { makeStyles } from '@mui/styles';
import { boardStyles } from "../../Board.styles";

const useStyles = makeStyles(boardStyles)

export const CommentForm = ({
  addComment,
  comment,
  setOpenForm, 
  content, 
  setContent, 
  updateComment, 
  updateFlag, 
  setUpdateFlag}) => {
  const classes = useStyles();  
  
  // 댓글 && 대댓글 작성/수정 로직
  const onSubmit = (e) => {
    e.preventDefault();    
    let parentCommentId = -1
    if (comment) parentCommentId = comment.id
    if (comment && updateFlag) { // 수정 로직
      updateComment(content, comment)
      setUpdateFlag(false)      
    } else {                     // 작성 로직
      addComment(content, parentCommentId)
    }  
    setContent("")
    if (parentCommentId !== -1) setOpenForm(false)
  }

  return (    
    <form onSubmit={onSubmit}>
      <TextField className={classes.field} value={content} onChange={(e) => setContent(e.target.value)} id="comment" fullWidth multiline={true} rows={3} placeholder='댓글을 달아주세요.' sx={{ml: 5, width: "95%"}}/>
      <Grid container sx={{justifyContent: 'end'}}>
        { comment && 
          <Button type="button"
            onClick={() => {
              console.log('취소')
              setUpdateFlag(false)
              setContent("")
              setOpenForm(false)
            }} 
            className={classes.btn} sx={{my: 1, mx: 1}}>취소</Button>
        }
        <Button type="submit" className={classes.btn} sx={{my: 1}}>작성</Button>
        <Grid item xs={0.2} />
      </Grid>
    </form>  
  )}