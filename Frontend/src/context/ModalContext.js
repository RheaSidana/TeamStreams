import React from "react";
import { modalState } from "../enum/ModalState";

const ModalContext = React.createContext({
  modalStatus: {
    status:modalState.NOT_RENDERED,
    zoomLink:"",
    zoomPasscode:''
  },
  setModalStatus: () => {},
});

export { ModalContext };
