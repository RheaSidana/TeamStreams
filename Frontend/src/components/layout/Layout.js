import React from "react";
import { Box } from "@mui/material";
import RootRouter from "../router/RootRouter";
import classes from "./styles/Layout.module.css";
import Navbar from "../navbar/Navbar";

const Layout = () => {
  return (
    <Box>
      <Navbar />
      <div className={classes.containerStyle}>
        <RootRouter></RootRouter>
      </div>
    </Box>
  );
};

export default Layout;
