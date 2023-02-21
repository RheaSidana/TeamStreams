import { makeStyles } from "@material-ui/core";

export default makeStyles((theme) => ({
  paper: {
    height: "auto",
    margin: "15vh 15vh 15vh 15vh",
    maxWidth: "100%",
    borderRadius: "15px !important",
    padding: "4%",
  },
  styledForm: {
    padding: "20px",
    maxWidth: "100%",
  },
  heading: {
    fontSize: "1.5rem",
    fontWeight: "900",
  },
  formikField: {
    marginTop: "30px",
    width: "100%",
  },
  formikButton: {
    display: "flex",
    justifyContent: "flex-end",
  },
  location: {
    color: "black",
    fontSize: "1.25rem",
    fontFamily: "serif",
    fontWeight: "bold",
    display: "flex",
    alignItems: "center",
    marginTop: "20px",
  },
  locationIcon: {
    height: "23px",
  },
  locationFields: {
    display: "flex",
    alignItems: "center",
    flexWrap: "wrap",
  },
  email: {
    width: "90%",
  },
  addEmail: {
    cursor: "pointer",
    marginTop: "20px",
    color: "black",
    padding: "15px 0px 0px 15px",
  },
  emailError: {
    marginBottom: "5px",
    fontSize: "0.75rem",
  },
}));
