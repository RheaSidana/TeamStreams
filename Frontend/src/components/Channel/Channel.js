import axios from "axios";
import React, { useEffect, useState } from "react";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import PersonIcon from "@mui/icons-material/Person";
import Groups3Icon from "@mui/icons-material/Groups3";
import styles from "./styles/ChannelStyles.module.css";
import { useParams } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { ModalContext } from "../../context/ModalContext";
import { modalState } from "../../enum/ModalState";
import AlertModal from "../AlertModal/AlertModal";
import { ProfileContent } from "../profile/ProfileContent";
import ProfileTabComponent from "../profile/ProfileTabComponent";
import VideoCard from "../videoCard/VideoCard";
import {default as VideoStyles} from "../videoCard/styles/VideoCardStyles.module.css"
import { CompressOutlined } from "@mui/icons-material";
import MembersCard from "./MembersCard";




export const Channel = () => {
  const { id } = useParams();
  const [channelDetails, setChannelDetails] = useState({});
  const [channelMembersList, setChannelMembersList] = useState({});
  const [modalStatus, setModalStatus] = useState({
    status: modalState.NOT_RENDERED,
    zoomLink: '',
    zoomPasscode: ''

  });

  console.log(channelDetails)
  useEffect(() => {
    axios.get(`http://localhost:8000/channel/${id}/`).then((response) => {
      setChannelDetails(response.data);
    });
    axios.get(`http://localhost:8000/channel/${id}/view-members`).then((response) => {
      setChannelMembersList(response.data);
    });
    // eslint-disable-next-line
  }, []);


  const handleJoinTeamEvent = () => {
    axios.post(`http://localhost:8000/user/sendRequest?user_id=${9}&channel_id=${id}`).then((response) => {
      toast.success("Request Submitted Successfully !", {
        position: toast.POSITION.TOP_RIGHT
      });
    }).catch((error) => {
      toast.info(error.response.data, {
        position: toast.POSITION.TOP_RIGHT
      });
    })
  }



  

 


  const recordingTab = (recordings) => {
    if(recordings!=undefined){
      return (
        <div className={VideoStyles.videocards} >
          {console.log(recordings)}
          {recordings
            .map((data) => {
              return (
                
                <VideoCard key={data.id} data={data} />
              )
              
            })
          }
        </div>
      )
    }
    else{
      return(<></>)
    }
    
  }

  const membersTab = (members_list)=>{
    if(members_list != undefined){
      return (
        <MembersCard members={members_list} />
      )  
    }
    else{
      return(<></>)
    }
  }

  

  var content = {
    "About": channelDetails.description,
    "Recordings": recordingTab(channelDetails.video),
    "Member": membersTab(channelMembersList),
  }





  return (
    <ModalContext.Provider value={{ modalStatus, setModalStatus }}>
      <div className={styles.channel_page}>
        <ProfileContent id={id} type='channel' title={channelDetails.name} location={channelDetails.city + ', ' + channelDetails.country} owner='Jaya Gupta' member_count={channelMembersList.length} />
        <div className={styles.channel_tabs}>
          
          <ProfileTabComponent type="channel" content={content} />
        </div>


      </div>
      <AlertModal />
    </ModalContext.Provider>
  );


};
