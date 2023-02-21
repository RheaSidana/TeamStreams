import React from "react";
import AccountCircleRoundedIcon from "@mui/icons-material/AccountCircleRounded";

import SearchBox from "../searchBox/SearchBox";
import styles from "./styles/NavbarStyles.module.css";
import logo from "./../../assets/logo.png";

function Navbar() {
  return (
    <div className={styles.navbar}>
      <img src={logo} alt="logo" className={styles.navbar_logo} />
      <SearchBox />
      <AccountCircleRoundedIcon
        className={styles.account_circle}
        data-testid="account-icon"
      />
    </div>
  );
}

export default Navbar;
