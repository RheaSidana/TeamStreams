import React from "react";
import { FormControl, InputLabel, Select } from "@material-ui/core";
import { useField } from "formik";
import PropTypes from "prop-types";

const FormikSelect = (props) => {
  const [field] = useField(props.name);

  const { onChange } = field;

  const { className, options, id, dropdownLabel, ...otherProps } = props;

  return (
    <FormControl className={className} style={{ paddingRight: "40px" }}>
      <InputLabel
        id={id}
        style={{
          color: "black",
          fontFamily: "serif",
          fontSize: 23,
          fontWeight: "bold",
        }}
      >
        {dropdownLabel}
      </InputLabel>
      <Select
        native
        labelId={id}
        onChange={onChange}
        style={{ backgroundColor: "#DCDCDC", marginTop: "25px" }}
        {...otherProps}
      >
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.display}
          </option>
        ))}
      </Select>
    </FormControl>
  );
};

FormikSelect.propTypes = {
  name: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  options: PropTypes.array.isRequired,
};

export default FormikSelect;
