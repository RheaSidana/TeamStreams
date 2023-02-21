import { render, screen } from "@testing-library/react";
import { userEvent } from "@testing-library/user-event";
import { shallow } from "enzyme";
import Adapter from "enzyme-adapter-react-15";

import SearchBox from "./SearchBox";

describe("Basic rendering of SearchBox", () => {
  it("should render SearchBox", () => {
    const searchBoxComponent = render(<SearchBox />);

    expect(searchBoxComponent).toBeTruthy();
  });

  it("should have a input element", () => {
    render(<SearchBox />);
    const inputElement = screen.getByPlaceholderText("Search videos ...");

    expect(inputElement).toBeInTheDocument();
    expect(inputElement).toHaveAttribute("type", "text");
  });

  it("should render an svg element for searchButton", () => {
    render(<SearchBox />);
    const svgElement = screen.getByTestId("search-button");

    expect(svgElement).toBeInTheDocument();
  });
});

describe("Functionality of SearchBox", () => {
  // it("should be able to accept characters in the input field", () => {
  //     render(<SearchBox />);
  //     const inputElement = screen.getByRole('input');

  //     userEvent.type(inputElement, "123");

  // //     const inputEl = screen.getByTestId("email-input");
  // // userEvent.type(inputEl, "test@mail.com");

  //     expect(screen.getByTestId("input")).toHaveValue("123");
  // })

  it("should be able to click search button", () => {
    const mockCallBack = jest.fn();

    const searchBoxComponent = shallow(<SearchBox />);
    const button = searchBoxComponent.getByRole("div");
    button.find("button").simulate("click");
    expect(mockCallBack.mock.calls.length).toEqual(1);
  });
});
