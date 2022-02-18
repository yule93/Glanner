import * as React from 'react';
import {CssBaseline, FormControlLabel, Checkbox, Paper, Box, Grid, Typography} from '@mui/material';
import initialScreen from "./initial_screen.png";
import RadioButtonUncheckedIcon from '@mui/icons-material/RadioButtonUnchecked';
import RadioButtonCheckedIcon from '@mui/icons-material/RadioButtonChecked';
import { SignupPageButton, SignupPageLink, SignupInput, SignupPageLabel, Copyright } from './SignupComponent.styles';
import { useForm } from "react-hook-form";
import { useRef } from 'react';
import "./SignupComponent.scoped.css";
import { useState } from 'react';
import axios from 'axios';

const SignupComponent = ({signupPage, setSignupPage}) => {
  const { register, watch, formState: {errors}, handleSubmit, getValues } = useForm();
  const [consent, setConsent] = useState(false);
  const [randomNum, setRandomNum] = useState('');
  const [verificationNumber, setVerificationNumber] = useState("");
  const [isSent, setIsSent] = useState(false);
  const [isPassed, setIsPassed] = useState(false);
  const password = useRef();
  password.current = watch("password");
  const onSubmit = (data) => {
    if (!consent) {
      alert('서비스 이용약관에 동의해주세요.')
      return
    }
    if (!isPassed) {
      alert('연락처 인증이 필요합니다.')
      return
    }

    const joinData = {
      email: data.email,
      name: data.name,
      password: data.password,
      phoneNumber: data.phone
    }

    axios(`/api/user`, {method: 'POST', data: joinData})
      .then(res => {
        // console.log(res.data)
        alert('회원가입이 정상적으로 완료되었습니다.')
        setSignupPage(!signupPage)
      })
      .catch(err => {
        console.log(err)
      })    
  }
  // const onVerificate = () => {
  //   localStorage.setItem('expiredAt', new Date().getTime())

  // // }
  // React.useEffect(() => { 
  //   if (time > 0) {
  //     const Counter = setInterval(() => {
  //       const gap = Math.floor((localStorage.getItem('expireAt') - new Date().getTime()) / 100000)
        
  //       console.log(gap)
  //       setTime(time + gap)
  //     }, 1000)
  //     return () => clearInterval(Counter)
  //   }
  // }, [localStorage.getItem('expireAt'), time])

  const regPhone = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/
  const [min, setMin] = useState(3);
  const [sec, setSec] = useState(20);
  const time = useRef(180);
  const timerId = useRef(null);

  React.useEffect(() => {
    if (isSent) {
      timerId.current = setInterval(() => {
        setMin(parseInt(time.current / 60));
        setSec(time.current % 60);
        time.current -= 1;
      }, 1000);
      
      return () => clearInterval(timerId.current);
    }
  }, [isSent]);

  React.useEffect(() => {
    // 만약 타임 아웃이 발생했을 경우
    if (time.current <= -1) {
      alert('다시 인증해주세요.')
      clearInterval(timerId.current);
      setIsSent(false)
      // dispatch event
      setMin(3)
      setSec(20)
    }
    if (time.current == 200) {
      clearInterval(timerId.current);
      time.current = 180
      setMin(3)
      setSec(0)
    }
  }, [sec]);

  const generateRandomCode = (n) => {
    let str = ''
    for (let i = 0; i < n; i++) {
      str += Math.floor(Math.random() * 10)
    }
    return str
  }

  const sendNumber = (phoneNum) => {
    if (!regPhone.test(phoneNum)) {
      alert('휴대폰 번호 형식이 올바르지 않습니다.')
      return
    }
    time.current = 200
    
    const number = generateRandomCode(6)
    setRandomNum(number)
    console.log(phoneNum)
    axios(`/api/notification/sms`, {method: 'POST', data: {
      content: `[Glanner] 본인 확인을 위해 인증번호[${number}]를 입력해 주세요.`,
      to: phoneNum
    }})
      .then(res => {
        alert('인증번호가 발송되었습니다.')
        setIsSent(true)
      })
      .catch(err => console.log(err))
  }
  
  return (
      <Grid container component="main" sx={{ height: '100vh', width: '100%'}}>
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
        <Grid item xs={12} sm={12} md={4} component={Paper} elevation={6} square backgroundColor="#F6F6F6" sx={{height: "100%", display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
          <Box
            sx={{
              
              // mx: {
              //   xs: 0,
              //   sm: 0
              // },
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            <Typography component="h1" variant="h4" color="#6F6F6F" fontFamily="Rozha One">
              Sign Up
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit(onSubmit)} sx={{ mt: 1, width: '100%'}} method="POST">


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
                    {...register('name', {required: true, maxLength: 8})}          
                    />
                </Grid>             
                {errors.name && errors.name.type === "required" && <p className='error-text'>이름을 입력해주세요</p>}
                {errors.name && errors.name.type === "maxLength" && <p className='error-text'>이름을 8자 이내로 입력해주세요</p>}  
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
                    {...register('email', {required: true, pattern: /^\S+@\S+$/i})}              
                    />
                </Grid>
                {errors.email && errors.email.type === "required" && <p className='error-text'>이메일을 입력해주세요</p>}
                {errors.email && errors.email.type === "pattern" && <p className='error-text'>이메일 형식으로 입력해주세요</p>}                
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
                    {...register('password', {required: true, minLength: 6})}
                  />
                </Grid>
                {errors.password && errors.password.type === "required" && <p className='error-text'>비밀번호를 입력해주세요</p>}
                {errors.password && errors.password.type === "minLength" && <p className='error-text'>비밀번호를 6자 이상으로 입력해주세요</p>}
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
                    {...register('password_confirm', {required: true, validate: (value) => value === password.current})}
                  />
                </Grid>
                {errors.password_confirm && errors.password_confirm.type === "required" && <p className='error-text'>비밀번호를 입력해주세요</p>}
                {errors.password_confirm && errors.password_confirm.type === "validate" && <p className='error-text'>비밀번호가 일치하지 않습니다</p>}
              </Grid>

              {/* 연락처 폼 */}
              <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>

                <Grid item xs={4}>
                  <SignupPageLabel htmlFor='phone'>휴대전화</SignupPageLabel>
                </Grid>
                <Grid item xs={4}>
                  <SignupInput
                    InputProps={{readOnly: isSent || isPassed}}                    
                    required
                    fullWidth
                    id="phone"
                    placeholder='숫자만 입력'
                    name="phone"
                    // autoComplete="email"

                    {...register('phone', {required: true, pattern: /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/})}                
                    />
                </Grid>

                
                {/* 추후 인증 과정에서 로딩중 버튼 고민 중 */}
                {/* <LoadingButton loading variant="outlined">Submit</LoadingButton> */}  
                <SignupPageButton sx={{ ml: 1.3 }} onClick={() => sendNumber(getValues("phone"))} disabled={isPassed}>
                  {!isSent ? <>인증</> : <>재전송</> }
                </SignupPageButton>
                  
                
              {errors.phone && errors.phone.type === "required" && <p className='error-text'>연락처를 입력해주세요</p>}
              {errors.phone && errors.phone.type === "pattern" && <p className='error-text'>유효한 연락처가 아닙니다</p>}
              </Grid>

              {/* 인증번호 폼 */}
              {isSent && <Grid container direction='row' alignItems='center' sx={{ mt: '1em'}}>
                  <Grid item xs={4} >
                    <SignupPageLabel htmlFor='passwordConfirm' >인증번호</SignupPageLabel>
                  </Grid>
                  <Grid item xs={4}>
                    <SignupInput
                      required
                      fullWidth
                      name="verification"
                      type="text"
                      id="verification"
                      autoComplete="verification"
                      value={verificationNumber}
                      onChange={e => {
                        setVerificationNumber(e.target.value)                      
                      }}
                      placeholder="숫자 6자리 입력"
                      InputProps={{
                        endAdornment: (
                          <Box>
                            {min}:{String(sec).padStart(2, '0')}
                          </Box>
                        ),
                      }}
                    />
                  </Grid>
                  <SignupPageButton sx={{ ml: 1.3 }} onClick={() => {
                    if (String(verificationNumber) === randomNum) {
                      setIsPassed(true)
                      alert('인증이 완료되었습니다.')
                      setIsSent(false)
                    } else {
                      alert('인증번호가 일치하지 않습니다.')
                    }
                  }}>
                   확인
                  </SignupPageButton>
                  {errors.verification && errors.password_confirm.type === "required" && <p className='error-text'>비밀번호를 입력해주세요</p>}
                  {errors.verification && errors.password_confirm.type === "validate" && <p className='error-text'>비밀번호가 일치하지 않습니다</p>}
                </Grid>
              }
                      

              <Grid container sx={{mt: '2em'}}>

                {/* 서비스 이용약관 동의 버튼 */}
                <Grid item xs={4} />
                <Grid item xs={7}>
                  
                  <FormControlLabel
                    control={
                      <Checkbox
                        onClick={() => {
                          if (consent) setConsent(false)
                          else setConsent(true)
                          // console.log(consent)
                        }} 
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
                        component='span'
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
                  <SignupPageLink 
                    onClick={(e) => {
                      e.preventDefault();
                      setSignupPage(!signupPage)}} 
                      href="#" 
                      variant="body2" 
                      sx={{fontWeight: '600'}}
                  >
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