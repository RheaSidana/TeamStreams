import { Typography } from "@mui/material";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import React from "react";

export default function EmailCard(props) {
  var maxLength = props.emailObj.email.length + "ch";
  return (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        flexWrap: "wrap",
        padding: "0px 8px 0px 0px",
      }}
    >
      <Typography
        paragraph={true}
        sx={{
          backgroundColor: "#DCDCDC",
          maxWidth: maxLength,
          fontSize: "18px",
        }}
      >
        {props.emailObj.email}
      </Typography>
      <RemoveCircleOutlineIcon
        style={{
          fontSize: "26px",
          backgroundColor: "#DCDCDC",
          marginBottom: "16px",
          color: "black",
        }}
        type="button"
        onClick={(e) => {
          props.handleDeleteEmail(props.emailObj.id);
        }}
      />
    </div>
  );
}
