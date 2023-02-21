import styles from "./styles/ChannelCreationConfirmationStyles";
import { Dialog, Snackbar } from "@mui/material";
import { Alert } from "@mui/material";
// import { Dialog, IconButton, Snackbar } from "@material-ui/core";

const ChannelCreationConfirmation = ({ open, onClose }) => {
  const handleClose = () => {
    onClose();
    window.location.reload(false);
  };

  const classes = styles();

  return (
    <>
      <Dialog className={classes.dialogBase} open={open} onClose={handleClose}>
        <Snackbar
          open={open}
          onClose={handleClose}
          data-testid="alert"
          anchorOrigin={{ vertical: "top", horizontal: "center" }}
        >
          <Alert
            onClose={handleClose}
            severity="success"
            sx={{ width: "100%" }}
          >
            Channel Created Successfully!!
          </Alert>
        </Snackbar>
      </Dialog>
    </>
  );
};
export default ChannelCreationConfirmation;
