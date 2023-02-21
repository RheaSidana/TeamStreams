import React from "react";
import SearchIcon from "@mui/icons-material/Search";

import styles from "./styles/SearchBoxStyles.module.css";

function SearchBox() {
  const handleKeyDown = (event) => {
    if (event.keyCode === 13) {
      console.log("Enter key pressed");
    }
  };

  const handleClick = () => {
    console.log("click");
  };

  return (
    <div className={styles.searchBox} data-testid="search-box">
      <input
        className={styles.search_input}
        type="text"
        placeholder="Search videos ..."
        onKeyDown={handleKeyDown}
        data-testid="input"
      />
      <div className={styles.search_button} onClick={handleClick}>
        <SearchIcon
          className={styles.search_icon}
          data-testid="search-button"
        />
      </div>
    </div>
  );
}

export default SearchBox;
