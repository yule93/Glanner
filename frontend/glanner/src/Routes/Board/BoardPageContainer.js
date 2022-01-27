import React from "react";
import BoardPagePresenter from "./BoardPagePresenter";
import Helmet from "react-helmet";

export default class extends React.Component {
  render() {
    console.log();
    return (
      <>
        <Helmet>
          <title>Glanner | 게시판</title>
        </Helmet>
        <BoardPagePresenter/>
      </>
    );
  }
}
