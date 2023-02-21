import { useState } from "react";
import createChannel from "./../service/ChannelCreationApiService";

const useChannelCreation = () => {
  const [error, setError] = useState("");
  const [response, setResponse] = useState("");

  const handleChannelCreation = async (values, emails, setAlert) => {
    const payload = {
      name: values.channelName,
      country: values.country,
      city: values.city,
      emails: emails,
      owner_id: "9", // dummy should be replaced in future
      description: values.description,
    };
    setResponse(payload);

    try {
      var r = await createChannel.create(payload);
      setResponse(r);
      setAlert(true);
    } catch (err) {
      if (err.response && err.response.status === 400) {
        setError(err.response.data.details[0]);
      } else {
        throw err;
      }
    }
  };

  return {
    error: error,
    handleChannelCreation: handleChannelCreation,
  };
};

export default useChannelCreation;