import { makeStyles } from "@material-ui/core";

export default makeStyles((theme) => ({
  dialogBase: {
    marginRight: "500px",
  },
  closeButton: {
    color: "white",
    width: "18px",
    height: "18px",
    marginLeft: "6px",
    marginRight: "6px",
    alignItems: "center",
    display: "flex",
  },

  alertSuccess: {
    backgroundColor: "#32CD32",
    color: "white",
    padding: "5px 7px 5px 5px",
    alignItems: "center",
    display: "flex",
  },

  alertFailure: {
    backgroundColor: "red",
    color: "white",
    padding: "5px 7px 5px 5px",
    alignItems: "center",
    display: "flex",
  },
}));
