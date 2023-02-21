import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";

import Navbar from "./Navbar";

describe("Basic rendering of Navbar", () => {
  it("should render Navbar", () => {
    const navBarComponent = render(<Navbar />);

    expect(navBarComponent).toBeTruthy();
  });

  it("should render Image logo", () => {
    const navBarComponent = render(<Navbar />);
    const logo = navBarComponent.getByRole("img");

    expect(logo).toBeTruthy();
  });

  it("should render searchBox component", () => {
    render(<Navbar />);
    const searchBoxComponent = screen.getByTestId("search-box");

    expect(searchBoxComponent).toBeInTheDocument();
  });

  it("should render an svg element for account icon", () => {
    render(<Navbar />);
    const svgElement = screen.getByTestId("account-icon");

    expect(svgElement).toBeInTheDocument();
  });
});
