import * as React from 'react';
import {CssBaseline, FormControlLabel, Checkbox, Paper, Box, Grid, Typography} from '@mui/material';
import initialScreen from "./initial_screen.png";
import RadioButtonUncheckedIcon from '@mui/icons-material/RadioButtonUnchecked';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import { SignupPageButton, SignupPageLink, SignupInput, SignupPageLabel, Copyright } from './SignupComponent.styles';

const SignupComponent = () => {  
  
  const handleSubmit = (event) => {
    event.preventDefault();
    console.log()
    const data = new FormData(event.currentTarget);
    // eslint-disable-next-line no-console
    console.log({
      name: data.get('name'),
      email: data.get('email'),
      password: data.get('password'),
      passwordConfirm: data.get('passwordConfirm'),
      phone: data.get('phone'),
    });
  };  
  
  return (
      <Grid container component="main" sx={{ height: '100vh', width: '1920px' }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={false}
          md={8}
          sx={{
            backgroundImage: `url('${initialScreen}')`,
            backgroundRepeat: 'no-repeat',
            backgroundColor: '#8C7B80',
            // backgroundColor: (t) =>
            // t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
            backgroundSize: 'contain',
            backgroundPosition: 'center',
          }}
        />
        <Grid item xs={12} sm={12} md={4} component={Paper} elevation={6} square backgroundColor="#F6F6F6" sx={{height: "100%"}}>
          <Box
            sx={{
              my: 8,
              mr: 8,
              // mx: {
              //   xs: 0,
              //   sm: 0
              // },
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              mt: 25, 
            }}
          >

            <Typography component="h1" variant="h4" color="#6F6F6F" fontFamily="Rozha One">
              Sign Up
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 1, width: '100%'}}>


              {/* 이름 폼 */}
              <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>
                <Grid item xs={4}>
                  <SignupPageLabel htmlFor="name">이름</SignupPageLabel>                  
                </Grid>
                <Grid item xs={6}>
                  <SignupInput
                    required
                    fullWidth                    
                    id="name"
                    name="name"          
                    />  
                </Grid>             
              </Grid>
              

              {/* 이메일 폼 */}
              <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>
                <Grid item xs={4} >
                  <SignupPageLabel htmlFor='email'>이메일</SignupPageLabel>
                </Grid>
                <Grid item xs={6}>
                  <SignupInput
                    required
                    fullWidth
                    id="email"
                    name="email"
                    autoComplete="email"
                    autoFocus              
                    />                
                </Grid>
              </Grid>


              {/* 패스워드 폼 */}
              <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>
                <Grid item xs={4} >
                  <SignupPageLabel htmlFor='password'>비밀번호</SignupPageLabel>
                </Grid>
                <Grid item xs={6}>
                  <SignupInput
                    required
                    fullWidth
                    name="password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                  />
                </Grid>
              </Grid>


              {/* 비밀번호 확인 폼 */}
              <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>
                <Grid item xs={4} >
                  <SignupPageLabel htmlFor='passwordConfirm' >비밀번호 확인</SignupPageLabel>
                </Grid>
                <Grid item xs={6}>
                  <SignupInput
                    required
                    fullWidth
                    name="passwordConfirm"
                    type="password"
                    id="passwordConfirm"
                    autoComplete="current-password"
                  />
                </Grid>
              </Grid>


              {/* 연락처 폼 */}
              <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>

                <Grid item xs={4}>
                  <SignupPageLabel htmlFor='phone'>연락처</SignupPageLabel>
                </Grid>
                <Grid item xs={4.5}>
                  <SignupInput                    
                    required
                    fullWidth
                    id="phone"
                    // placeholder='ex. 010-1234-1234'
                    name="phone"
                    autoComplete="email"
                    autoFocus                   
                    />
                </Grid>

                
                {/* 추후 인증 과정에서 로딩중 버튼 고민 중 */}
                {/* <LoadingButton loading variant="outlined">Submit</LoadingButton> */}  
                <SignupPageButton sx={{ ml: 1.3 }}>
                  인증
                </SignupPageButton>
                  
                
              </Grid>

              
              <Grid container sx={{mt: '2em'}}>

                {/* 서비스 이용약관 동의 버튼 */}
                <Grid item xs={4} />
                <Grid item xs={7}>
                  
                  <FormControlLabel
                    control={
                      <Checkbox 
                        value="consent"                         
                        icon={<RadioButtonUncheckedIcon />}
                        checkedIcon={<RadioButtonCheckedIcon />} 
                        sx={{color: '#6F6F6F', 
                            backgroundColor: '#F6F6F6',                            
                            '&.Mui-checked': {color: '#6F6F6F'}, 
                          }}
                      />
                    }
                    label={
                      <Typography 
                        sx={{ fontFamily: 'Noto Sans CJK KR', fontWeight: 'normal', color: 'rgba(95, 95, 95, 1)'}}
                      >
                      서비스 이용약관에 동의합니다.
                      </Typography>
                    }
                  />
                </Grid>
                
                {/* 회원가입 버튼  */}
                <Grid item xs={4} />
                <Grid item xs={6} sx={{mt: '1em'}}>
                  <SignupPageButton type="submit" sx={{ width: '100%'}}>
                    회원가입
                  </SignupPageButton>
                </Grid>
              </Grid>



              <Grid container sx={{mt: '1em', justifyContent: 'center'}}>

                {/* 로그인 페이지 이동 링크 */}
                <Grid item xs={3} />
                <Grid item xs={3}>
                  <SignupPageLink href="#" variant="body2" sx={{fontWeight: '600'}}>
                    로그인
                  </SignupPageLink>
                </Grid>

                {/* 아이디 찾기, 비밀번호 찾기 링크 */}
                <Grid item>
                  <SignupPageLink href="#" variant="body2" sx={{color: '#959595', m: '0.5em'}}>
                    아이디 찾기                                      
                  </SignupPageLink>
                  <span style={{border: '1px solid #E5E5E5',transform: 'rotate(90deg)'}}></span>
                
                  <SignupPageLink href="#" variant="body2" sx={{color: '#959595', m: '0.5em'}}>
                    비밀번호 찾기
                  </SignupPageLink>                
                </Grid>

              </Grid>
              <Copyright />
            </Box>
          </Box>
        </Grid>
      </Grid>
  );
};

export default SignupComponent;