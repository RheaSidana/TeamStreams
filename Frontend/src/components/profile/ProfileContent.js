import LocationOnIcon from "@mui/icons-material/LocationOn";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import PersonIcon from "@mui/icons-material/Person";
import Groups3Icon from "@mui/icons-material/Groups3";
import { ToastContainer, toast } from 'react-toastify';
import styles from "./styles/ProfileStyles.module.css"
import axios from "axios";




export const ProfileContent = ({id, type, title, location, owner, member_count}) => {

    const handleJoinTeamEvent = () => {
        axios.post(`http://localhost:8000/user/sendRequest?user_id=${9}&profile_content_id=${id}`).then((response) => {
          toast.success("Request Submitted Successfully !", {
            position: toast.POSITION.TOP_RIGHT
          });
        }).catch((error) => {
          toast.info(error.response.data, {
            position: toast.POSITION.TOP_RIGHT
          });
        })
      }


    return (
        <>

            <div className={styles.profile_content_info}>
        <div className={styles.profile_content_logo}>
          <img
            src={`https://picsum.photos/id/${id}/300/250`}
            alt=""
            className={styles.profile_content_logo_image}
          />
        </div>
        <div className={styles.profile_content_details}>
          <div className={styles.profile_content_info_content}>
            <div className={styles.profile_content_name}>
              {`${title}`}
            </div>
            <div className={styles.location}>
              <LocationOnIcon />
              <p style={{ paddingLeft: '10px' }}> {location} </p>
            </div>
            {`${type}`=='channel' ? (
                <>
                <div className={styles.owner_name}>
                <PersonIcon />
                <p style={{ paddingLeft: '10px' }}> {owner} </p>
              </div>
              <div className={styles.member_count}>
                <Groups3Icon />
                <p style={{ paddingLeft: '10px' }}> {member_count} Members </p>
              </div>
                </>
                
            ) : <></>}
            
          </div>
          {`${type}`=='channel' ? (
          <button variant="outlined" className={styles.join_button} onClick={handleJoinTeamEvent}>
            <div style={{ display: 'flex', justifyContent: 'space-evenly' , alignItems:'center',marginTop:"-6px"}}>
              <PersonAddIcon />
              <h3> Join Team </h3>
              <ToastContainer />
            </div>

          </button>
          ) : <></>}
        </div>
      </div>
        </>
    )
}