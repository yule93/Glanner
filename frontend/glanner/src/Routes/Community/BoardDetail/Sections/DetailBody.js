import { Avatar, CardActions, CardContent, CardHeader, CardMedia, Divider, IconButton } from "@mui/material"
import { grey } from "@mui/material/colors"
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import { makeStyles } from "@mui/styles";
import { boardStyles } from "../../Board.styles";
import MoreBtn from "../../../../Components/MoreBtn";
import moment from "moment";


const useStyles = makeStyles(boardStyles);

export const DetailBody = ({post, addLike }) => {
  const classes = useStyles();
  console.log(post)
  return (
    <>    
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: grey[500] }} aria-label="recipe">              
          </Avatar>
        }
        action={
          <MoreBtn editData={post} type='body' />
        }
        title={post.writer}
        subheader={moment(post.date).format('YYYY.MM.DD HH:mm:ss')}
        className={classes.dateText}
      />
      <Divider />
      {/* {post.attachment && <CardMedia
        component="img"
        height="194"
        image={post.attachment}
        alt="image"
      />} */}
      <CardContent>
        <h1 className={classes.title}>
          {post.title}
        </h1>
        <p className={classes.content}>
          {post.content}
        </p>
      </CardContent>
      
      <CardActions disableSpacing sx={{display: 'flex', justifyContent: 'space-between'}}>
        <span className={classes.botText}>조회수 {post.count}{post.like ? <>{`, 좋아요 ${post.like}`}</>: null } </span>
        <div>
          <IconButton onClick={addLike}>
            <FavoriteIcon />
          </IconButton>
          <IconButton aria-label="share">
            <ShareIcon />
          </IconButton>
        </div>          
      </CardActions>
    </>
  )
}