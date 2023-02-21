import React, { useState, useEffect } from "react";
import axios from "axios";

import styles from "./styles/HomeStyles.module.css";
import JoinedChannel from "./joinedChannel/JoinedChannel";
import { ModalContext } from "./../../context/ModalContext";
import Modal from "../AlertModal/AlertModal";
import { modalState } from "../../enum/ModalState";

const colors = {
  blue: "rgb(74, 239, 239, 0.1)",
  gray: "rgb(124, 131, 154, 0.1)",
};

function Home() {
  const [joinedChannels, setJoinedChannels] = useState({});
  const [modalStatus, setModalStatus] = useState(modalState.NOT_RENDERED);


  // Todo :- change hard coded value of user_id 
  useEffect(() => {
    axios.get("http://localhost:8000/user/channels/2/").then((response) => {
      setJoinedChannels(() => {
        return response.data;
      });
    });
  }, []);

  return (
      <div className={styles.home}>
        {Object.keys(joinedChannels).map((key, index) => {
          return (
            <JoinedChannel
              key={key} 
              id={joinedChannels[key].id}
              color={index % 2 === 1 ? colors.blue : colors.gray}
              name={joinedChannels[key].name}
            />
          );
        })}
      </div>
      
  );
}

export default Home;
