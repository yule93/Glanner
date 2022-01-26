import Navigator from "./Navigator";
import GlobalStyles from "./GlobalStyles";
import Router from "../Components/Router";
import styled from "styled-components";

const MainContainer = styled.div`
  text-align: center;
  height: 100%;
`;

const RouterContainer = styled.div`
  position: absolute;
  left: 280px;
  width: 80%;
  margin: 20px;
  white-space: normal;
  align-content: center;
  height: 90vh;
`;

function App() {
  return (
    <MainContainer>
      <Navigator
        PaperProps={{
          style: {
            height: 100 + "%",
            backgroundColor: "#f6f6f6",
            padding: "0 20px",
            display: "inline-block",
            
          },
        }}
      />
      <RouterContainer>
        <Router />
      </RouterContainer>
      <GlobalStyles />
    </MainContainer>
  );
}

export default App;
