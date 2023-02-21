import React, { memo, useContext, useEffect, useState } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";

import styles from "./styles/VideoCardStyles.module.css";
import { generateRandomNumber } from "../../helpers/RandomNumbers";
import { ModalContext } from "../../context/ModalContext";
import { modalState } from "../../enum/ModalState";
import AlertModal from "../AlertModal/AlertModal";

function VideoCard({ data }) {
  const imageId = generateRandomNumber(1, 200);
  const { modalStatus, setModalStatus } = useContext(ModalContext);

  return (
    <>
    
    <Card
      className={styles.videocard}
      onClick={() => {
        
        const modal={status:modalState.OPENED,
          zoomLink:data.zoom_link,
          zoomPasscode:data.passcode
        }
        setModalStatus(modal);
        console.log("----------clicked--------",modalStatus)
      }}
    >
      <img
        src={`https://picsum.photos/id/${imageId}/300/250`}
        alt=""
        className={styles.video_thumbnail}
      />
      <CardContent className={styles.video_details}>
        <p className={styles.video_title}>
          {data.title} {data.id}
        </p>
      </CardContent>
    </Card>
    
    </>
  );
}

export default memo(VideoCard);
