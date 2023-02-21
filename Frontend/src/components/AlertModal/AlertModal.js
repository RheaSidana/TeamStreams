import React, { useContext,useEffect } from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import { ModalContext } from "../../context/ModalContext";
import { modalState } from "../../enum/ModalState";
import {CopyToClipboard} from 'react-copy-to-clipboard';

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "20%",
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

export default function AlertModal() {
  const { modalStatus, setModalStatus } = useContext(ModalContext);

  const handleClose = () => {
    // navigator.clipboard.writeText(modalStatus.zoomPasscode).then(()=>{
    //   copy(modalStatus.zoomPasscode)
    //   window.open(modalStatus.zoomLink,"_blank")
    //   const modal={
    //     status:modalState.CLOSED,
    //     zoomLink:'',
    //     zoomPasscode:''
    //   };
    //   setModalStatus(modal);
      
    // })

        window.open(modalStatus.zoomLink,"_blank")
        const modal={
          status:modalState.CLOSED,
          zoomLink:'',
          zoomPasscode:''
        };
        setModalStatus(modal);
      
    console.log("----copied-----",navigator.clipboard.read())
    
  };


  return (
    
    <div>
      {console.log("-----insidealert--------",modalStatus.zoomLink,modalStatus.zoomPasscode)}
      <Modal
        open={modalStatus.status === modalState.OPENED}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <div>
            Video's passcode is copied to the clipboard. Kindly paste in the
            passcode field.
          </div>
          <b
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "space-between",
            }}
          >
            Press OK to continue.
            <CopyToClipboard text={modalStatus.zoomPasscode}
          >
          <Button
              variant="contained"
              style={{ float: "right" }}
              onClick={handleClose}
            >
              Ok
            </Button>
        </CopyToClipboard>
            
          </b>
        </Box>
      </Modal>
    </div>
  );
}
