import axios from "axios";
import { urls } from "../config/env-config";

const promiseWithErrorHandling = (promise) => {
  return promise.catch((err) => {
    if (err.response && err.response.status === 500) {
      // noinspection JSCheckFunctionSignatures
      console.log("error",err);
      // window.location.assign("/error");
    } else {
      console.log("error",err);
      throw err;
    }
  });
};

export default {
  post: async (path, payload) => {
    return promiseWithErrorHandling(
      axios.post(`${urls.service}/${path}`, payload)
      // axios.post("xyz",payload)
    );
  },
};
