import React, { useState, useEffect, memo } from "react";
import axios from "axios";
import ArrowCircleLeftSharpIcon from "@mui/icons-material/ArrowCircleLeftSharp";
import ArrowCircleRightSharpIcon from "@mui/icons-material/ArrowCircleRightSharp";

import styles from "./styles/JoinedChannelStyles.module.css";
import VideoCard from "../../videoCard/VideoCard";
import { Channel } from "../../Channel/Channel";
import { Link } from "react-router-dom";
import { ModalContext } from "../../../context/ModalContext";
import { modalState } from "../../../enum/ModalState";
import AlertModal from "../../AlertModal/AlertModal";
import { useParams } from "react-router-dom";


function JoinedChannel({ id, color, name }) {
  const [videosData, setVideosData] = useState([]);
  const [startIndex, setStartIndex] = useState(0);
  const [endIndex, setEndIndex] = useState(0);
  const [modalStatus, setModalStatus] = useState({
    status:modalState.NOT_RENDERED,
    zoomLink:'',
    zoomPasscode:''

});

  useEffect(() => {
    axios
      .get(`http://localhost:8000/channel/${id}/videos/`)
      .then((response) => {
        console.log("-----videos-----",response)
        setVideosData(() => {
          setEndIndex(() => Math.min(4, response.data.length));
          return response.data;
        });
      });
    // eslint-disable-next-line
  }, []);
  
  const moveCardsInRight = () => {
    if (endIndex <= videosData.length) {
      setStartIndex(startIndex + 1);
      setEndIndex(endIndex + 1);
    }
  };

  const moveCardsInLeft = () => {
    if (startIndex > 0) {
      setStartIndex(startIndex - 1);
      setEndIndex(endIndex - 1);
    }
  };
  
  return (
    <ModalContext.Provider value={{ modalStatus, setModalStatus }}>

    
    <div className={styles.channel} style={{ backgroundColor: color }}>
      <div className={styles.channel_info}>
  

      <Link to={{pathname:`/channel/${id}`}}   >
        <div className={styles.channel_logo} >
        
          <img
            src={`https://picsum.photos/id/${id}/300/250`}
            alt=""
            className={styles.channel_logo_image}
          />
          
        </div>
        </Link>
        

        <div className={styles.channel_name}> 
        <Link to={{pathname:`/channel/${id}`}} > {name} </Link>  
        </div>
        
      </div>
      
      <div className={styles.video_section}>
        {console.log(videosData)}
        {videosData.length > 4 && startIndex !== 0 && (
          <ArrowCircleLeftSharpIcon
            className={styles.left_arrow}
            onClick={moveCardsInLeft}
          />
        )}
      
        <div className={styles.videocards}>
          {videosData
            .map((data) => {
              return <VideoCard key={data.id} data={data} />;
            })
            .slice(startIndex, endIndex)}
        </div>
        {videosData.length > 4 && endIndex <= videosData.length - 1 && (
          <ArrowCircleRightSharpIcon
            className={styles.right_arrow}
            onClick={moveCardsInRight}
          />
        )}
      </div>
    </div>
<AlertModal />
    </ModalContext.Provider>
  );
}

export default memo(JoinedChannel);
