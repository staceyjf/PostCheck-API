import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, useLocation } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import Header from "./Navbar";
import theme from "../../styling/theme";

// create a mock component to help us find the current location
const LocationDisplay = () => {
  const location = useLocation();
  return <div data-testid="location-display">{location.pathname}</div>;
};

describe("Header", () => {
  // -----------Logo-----------
  it("should navigate to the homepage if the logo is clicked", async () => {
    // use <MemoryRouter> from react dom router when you want to manually control the history of a url
    // it enables us to render header on the /todo/new page
    render(
      <MemoryRouter initialEntries={["/new"]}>
        <ThemeProvider theme={theme}>
          <Header />
          <LocationDisplay />
        </ThemeProvider>
      </MemoryRouter>
    );

    // user interaction
    const logo = screen.getByAltText("PostCheck Logo");
    await userEvent.click(logo);

    // check to see if we are on the home page post a click
    const locationDisplay = await screen.findByTestId("location-display");

    // check if this can be removed
    await waitFor(() => {
      expect(locationDisplay).toHaveTextContent("/");
    });
  });

  // -----------Logo-----------
  it("should navigate to the add a todo if the circle icon is clicked", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <Header />
          <LocationDisplay />
        </ThemeProvider>
      </MemoryRouter>
    );

    const circleIcon = screen.getByLabelText("Add a postcode");
    userEvent.click(circleIcon);

    const locationDisplay = await screen.findByTestId("location-display");

    waitFor(() => {
      expect(locationDisplay).toHaveTextContent("/new");
    });
  });
});
