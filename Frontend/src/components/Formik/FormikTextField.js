import React from "react";
import { useField } from "formik";
import { TextField } from "@material-ui/core";
import styles from "./styles/formikTextFieldStyles";
import PropTypes from "prop-types";

const FormikTextField = (props) => {
  const classes = styles();
  const [field, meta] = useField(props.name);

  const { value, onChange, onBlur } = field;
  const { error, touched } = meta;

  return (
    <TextField
      value={value}
      onChange={onChange}
      onBlur={onBlur}
      error={touched && Boolean(error)}
      helperText={touched ? error : ""}
      InputLabelProps={{
        style: {
          color: "black",
          fontSize: 23,
          fontFamily: "serif",
          fontWeight: "bold",
        },
        shrink: true,
      }}
      InputProps={{
        style: { backgroundColor: "#DCDCDC", height: "30", marginTop: "25px" },
      }}
      FormHelperTextProps={{
        className: classes.helperText,
      }}
      {...props}
    />
  );
};

FormikTextField.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
};

export default FormikTextField;
