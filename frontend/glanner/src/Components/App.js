import GlobalStyles from "./GlobalStyles";
import styled from "styled-components";
import Router from "../Components/Router";

const MainContainer = styled.div`
  text-align: center;
  height: 100%;
  width: 100%;
`;

const RouterContainer = styled.div`
  position: absolute;
  left: 280px;
  width: 80%;
  max-width: 100%;
  min-width: 900px;
  margin: 20px;
  white-space: normal;
  align-content: center;
  min-height: 400px;
  height: 95%;
`;

function App() {
  return (
    <MainContainer>
      <RouterContainer>
        <Router />
      </RouterContainer>
      <GlobalStyles />
    </MainContainer>
  );
}

export default App;
