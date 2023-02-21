import { object, string } from "yup";

export const initialValues = (firstCountry) => {
  return{
  channelName: "",
  country: firstCountry,
  city: "",
  description: "",
  };
};

export const channelCreationSchema = object({
  channelName: string("Enter channel name").required(
    "Channel Name is required"
  ),
  country: string("Enter country").required("Country is required"),
  city: string("Enter city").required("City is required"),
  description: string("Enter description").required("Description is required"),
});
