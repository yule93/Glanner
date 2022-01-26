import React from "react";
import Helmet from "react-helmet";
import styled from "styled-components";
import BigCalendar from "./BigCalendar/BigCalendar";

const MainPageContainer = styled.div`
  width: 100%;
  border-top: 1px solid #e5e5e5;
  overflow: hidden;
  height: 90%;
`;


export default function MainPagePresenter() {
  return (
    <>
      <Helmet>
        <title>Glanner | 메인 플래너</title>
      </Helmet>
      <MainPageContainer>
        <BigCalendar />
      </MainPageContainer>
    </>
  );
}
