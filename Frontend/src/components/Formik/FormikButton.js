import React from "react";
import { Button } from "@material-ui/core";
import styles from "../Formik/styles/FormikButtonStyles";

const FormikButton = (props) => {
  const classes = styles();
  var btnMaxWidth = props.btnText.length + "ch";

  return (
    <Button
      data-testid={props.testId}
      style={{
        width: { btnMaxWidth },
      }}
      className={classes.btn}
      type={props.type}
    >
      {props.btnText}
    </Button>
  );
};

export default FormikButton;
