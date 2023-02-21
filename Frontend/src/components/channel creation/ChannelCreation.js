import React from "react";
import { useState } from "react";
import { Paper } from "@mui/material";
import { Typography } from "@material-ui/core";
import { Form, Formik } from "formik";
import {
  channelCreationSchema,
  initialValues,
} from "./service/ChannelCreationService";
import styles from "./styles/ChannelCreationStyles";
import FormikTextField from "../Formik/FormikTextField";
import FormikButton from "../Formik/FormikButton";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import FormikSelect from "../Formik/FormikSelect";
import useChannelCreation from "./hook/useChannelCreation";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import useCountry from "./hook/useCountry";
import useCity from "./hook/useCity";
import useEmail from "./hook/useEmail";
import Emails from "../emails/emails";
import ChannelCreationConfirmation from "./ChannelCreationConfirmation";

export default function CreateChannel() {
  const classesStyle = styles();
  const [createChannel, setCreateChannel] = useState(initialValues);
  const [showChannelCreationConfirmation, setShowChannelCreationConfirmation] =
    useState(false);
  const { countries } = useCountry(createChannel);
  const { cities } = useCity(createChannel);
  const {
    email,
    emails,
    emailError,
    handleEmail,
    handleAddEmail,
    handleDeleteEmail,
  } = useEmail();
  const {
    handleChannelCreation,
    // printObject,
    error,
    // response,
  } = useChannelCreation();

  const handleSubmit = (values) => {
    handleChannelCreation(values, emails, setShowChannelCreationConfirmation);
    setCreateChannel({ ...createChannel });
  };

  return (
    <>
      <Paper elevation={5} className={classesStyle.paper}>
        <Formik
          enableReinitialize={true}
          initialValues={createChannel}
          validationSchema={channelCreationSchema}
          onSubmit={handleSubmit}
        >
          <Form className={classesStyle.styledForm}>
            <Typography className={classesStyle.heading}>
              CREATE CHANNEL
            </Typography>
            <FormikTextField
              required
              margin="dense"
              name="channelName"
              label="Name of the Channel"
              data-testid="channel-name-input"
              className={classesStyle.formikField}
            />
            <Typography className={classesStyle.location}>
              Location <LocationOnIcon className={classesStyle.locationIcon} />{" "}
              *
            </Typography>
            <div className={classesStyle.locationFields}>
              <FormikSelect
                options={countries}
                dropdownLabel={"Country"}
                name="country"
                id="country"
              />
              <FormikSelect
                required
                options={cities}
                dropdownLabel="City"
                name="city"
                id="city"
              />
            </div>
            <Typography className={classesStyle.location}>
              Add Members
            </Typography>
            <div>
              <FormikTextField
                margin="dense"
                name="email"
                label="Email ID"
                value={email}
                data-testid="channel-email-input"
                className={classesStyle.email}
                onChange={(e) => {
                  handleEmail(e.target.value);
                }}
              />
              <AddCircleOutlineIcon
                className={classesStyle.addEmail}
                onClick={(e) => handleAddEmail()}
                type="button"
              />
            </div>
            <Typography
              variant="body1"
              color="error"
              className={classesStyle.emailError}
            >
              {emailError}
            </Typography>
            <Emails emails={emails} handleDeleteEmail={handleDeleteEmail} />
            <FormikTextField
              required
              margin="dense"
              name="description"
              label="Description"
              data-testid="channel-description-input"
              className={classesStyle.formikField}
              multiline
              minRows={3}
            />
            <Typography
              variant="body1"
              color="error"
              style={{ marginTop: "28px", fontSize: "0.75rem" }}
            >
              {error}
            </Typography>
            <div className={classesStyle.formikButton}>
              <FormikButton
                btnText="Create Channel"
                data-testId="channel-create-button"
                type="submit"
              />
            </div>
          </Form>
        </Formik>
      </Paper>
      <ChannelCreationConfirmation
        open={showChannelCreationConfirmation}
        onClose={() => {
          setShowChannelCreationConfirmation(false);
        }}
      />
    </>
  );
}
