import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";

import ChannelCreation from "../channel creation/ChannelCreation";
import Home from "./../home/Home";
import { Channel } from "../Channel/Channel";

function RootRouter() {
  return (
    <Router>
      <Switch>
        <Route path="/" exact>
          <Home />
        </Route>
        <Route path="/createchannel">
          <ChannelCreation />
        </Route>
        <Route exact path="/channel/:id" component={Channel} element={<Channel />}></Route>
      </Switch>
    </Router>
  );
}

export default RootRouter;
