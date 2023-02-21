import { render } from "@testing-library/react";
import ChannelCreation from "./ChannelCreation";

describe("Channel Componnent LayOut", () => {
  it("should have a heading when rendered", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const headingElement = getByTestId("channel-creation-heading");
    expect(headingElement).toHaveTextContent("CREATE CHANNEL");
  });

  it.skip("should have the label for identifying the input for adding the channel name", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelNameLabel = getByTestId("channel-name-label");
    expect(channelNameLabel).toHaveTextContent("Name of Channel *");
  });

  it("should have the heading for identifying the section for adding the location", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelLocationHeading = getByTestId("channel-location-heading");
    expect(channelLocationHeading).toHaveTextContent("Location*");
  });

  it.skip("should have the label for identifying the input for adding the country", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelLocationHeading = getByTestId("channel-country-label");
    expect(channelLocationHeading).toHaveTextContent("Country");
  });
  it("should have the heading for identifying the section for adding the members", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelAddMembersHeading = getByTestId("channel-addMembers-heading");
    expect(channelAddMembersHeading).toHaveTextContent("Add Members");
  });

  it("should have the button for submitting the data of the form", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelCreationButton = getByTestId("channel-create-button");
    expect(channelCreationButton).toHaveTextContent("Create Channel");
  });

  it("should have the icon for identifying the section for adding the location", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelLocationIcon = getByTestId("channel-location-icon");
    expect(channelLocationIcon).toBeInTheDocument();
  });

  it.skip("should have the input for adding the channel name", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelNameInput = getByTestId("channel-name-input");
    expect(channelNameInput).toHaveAttribute("name", "channelName");
  });

  it.skip("should have the input for adding the channel city", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelCityInput = getByTestId("channel-city-input");
    expect(channelCityInput).toHaveAttribute("name", "city");
  });

  it.skip("should have the input for adding the channel country", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelCountryInput = getByTestId("channel-country-input");
    expect(channelCountryInput).toHaveAttribute("name", "country");
  });

  it.skip("should have the input for adding the channel city", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelCountryInput = getByTestId("channel-city-input");
    expect(channelCountryInput).toHaveAttribute("name", "city");
  });

  it("should have the input for adding the email", () => {
    const channelCreation = render(<ChannelCreation />);
    const channelEmailInput = channelCreation.getByTestId(
      "channel-email-input"
    );
    expect(channelEmailInput).toBeDefined();
  });

  it("should have the input for adding the description", () => {
    const { getByTestId } = render(<ChannelCreation />);
    const channelDescriptionInput = getByTestId("channel-description-input");
    expect(channelDescriptionInput).toBeDefined();
  });
});
