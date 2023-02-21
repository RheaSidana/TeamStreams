import ApiService from "../../../services/ApiService";

export default {
  create: async (payload) => {
    const response = await ApiService.post("channel/createChannel", payload);
    return response.data;
  },
};
