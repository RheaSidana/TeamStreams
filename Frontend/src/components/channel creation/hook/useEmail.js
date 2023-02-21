import { useState } from "react";

const useEmail = () => {
  const [emailError, setEmailError] = useState(false);
  const [emailId, setEmailId] = useState("");
  const [emailsList, setEmailsList] = useState([]);
  const thoughtworksMailRegex = new RegExp(
    "^[a-zA-Z0-9]*.\\..[a-zA-Z0-9]*.@thoughtworks.com"
  );

  const emailAlreadyAddedToList = () => {
    var listHasEmailId = false;
    const val =
      emailsList.length > 0
        ? emailsList.map((emailobj) => {
            if (emailobj.email === emailId) listHasEmailId = true;
            return null;
          })
        : null;
    return listHasEmailId;
  };

  const handleEmailId = (email) => {
    setEmailId(email);
  };

  const createEmailObj = () => {
    var id = 1;
    if (emailsList.length === 0) {
      return { id: id, email: emailId };
    }
    var obj = null;
    const val =
      emailsList.length > 0
        ? emailsList.map((emailobj) => {
            if (emailobj.id === id) id++;
            else if (emailobj.id > id) {
              const object = {
                id: id,
                email: emailId,
              };
              obj = object;
              id++;
            }
            return null;
          })
        : null;

    if (obj === null) {
      return { id: id, email: emailId };
    }
    return obj;
  };

  const handleAddEmail = () => {
    setEmailError("");
    
    if (!thoughtworksMailRegex.test(emailId)) {
      setEmailError("Correct email is required");
      return;
    }

    if (emailAlreadyAddedToList()) {
      setEmailError("Email already added");
      return;
    }
    const emailObject = createEmailObj();

    setEmailsList([...emailsList, emailObject]);
    setEmailId("");
  };

  const handleDeleteEmail = (id) => {
    const newList = emailsList.filter((email) => email.id !== id);

    setEmailsList([...newList]);
  };

  return {
    email: emailId,
    emails: emailsList,
    emailError: emailError,
    handleAddEmail: handleAddEmail,
    handleDeleteEmail: handleDeleteEmail,
    handleEmail: handleEmailId,
  };
};

export default useEmail;
