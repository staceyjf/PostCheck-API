import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter, useLocation } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import { UserContext } from "../../context/userContextProvider";
import Navbar from "./Navbar";
import theme from "../../styling/theme";

// create a mock component to help us find the current location
const LocationDisplay = () => {
  const location = useLocation();
  return <div data-testid="location-display">{location.pathname}</div>;
};

// create mock user
const mockUser = { username: "admin", password: "admin", role: "ADMIN" };
const mockSignOut = vi.fn();

describe("Navbar", () => {
  it("should navigate to the homepage if the logo is clicked", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider
            value={{ user: mockUser, signOut: mockSignOut }}
          >
            <Navbar />
            <LocationDisplay />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    const logoLink = screen.getByTestId("navbar-logo");
    await userEvent.click(logoLink);

    const locationDisplay = await screen.findByTestId("location-display");

    waitFor(() => {
      expect(locationDisplay).toHaveTextContent("/");
    });
  });

  it("should navigate to add a postcode when menu item is clicked", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider
            value={{ user: mockUser, signOut: mockSignOut }}
          >
            <Navbar />
            <LocationDisplay />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    const accountProfile = screen.getByTestId("account-profile");
    userEvent.click(accountProfile);

    const locationDisplay = await screen.findByTestId("location-display");

    waitFor(() => {
      const menu = screen.getByTestId("menu");
      const menuItm = screen.getByTestId("add-postcode-item");
      userEvent.click(menuItm);
      expect(locationDisplay).toHaveTextContent("/postcodes/create");
    });
  });

  it("should navigate to add a suburb when menu item is clicked", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider
            value={{ user: mockUser, signOut: mockSignOut }}
          >
            <Navbar />
            <LocationDisplay />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    const accountProfile = screen.getByTestId("account-profile");
    userEvent.click(accountProfile);

    const locationDisplay = await screen.findByTestId("location-display");

    waitFor(() => {
      const menu = screen.getByTestId("menu");
      const menuItm = screen.getByTestId("add-suburb-item");
      userEvent.click(menuItm);
      expect(locationDisplay).toHaveTextContent("/suburbs/create");
    });
  });

  it("should display a login form if there is no logged in user ", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider value={{ signOut: mockSignOut }}>
            <Navbar />
            <LocationDisplay />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    const accountProfile = screen.getByTestId("account-profile");
    userEvent.click(accountProfile);

    waitFor(() => {
      const loginForm = screen.getByTestId("login-form");
      expect(loginForm).toBeInTheDocument();
    });
  });
});
