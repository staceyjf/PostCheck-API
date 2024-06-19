import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import userEvent from "@testing-library/user-event";
import { ThemeProvider } from "@mui/material/styles";
import { UserContext } from "../../context/userContextProvider";
import ListItem from "./ListItm";
import theme from "../../styling/theme";

describe("ListItem", () => {
  const deleteOnClick = vi.fn((id) => console.log(id));
  const handleEdit = vi.fn((id) => console.log(id));

  // create mock user
  const mockUser = { username: "admin", password: "admin", role: "ADMIN" };
  const mockSignOut = vi.fn();

  beforeEach(() => {
    render(
      <ListItem
        id={5}
        postcode="2095"
        suburbName="Manly"
        suburbState="NSW"
        deleteOnClick={deleteOnClick}
        handleEdit={handleEdit}
      />
    );
  });

  it("should render a ListItem postcode based on props", () => {
    const element = screen.getByText(/2095/i);
    expect(element).toBeInTheDocument();
  });

  it("should render a ListItem suburbName based on props", () => {
    const element = screen.getByText(/Manly/i);
    expect(element).toBeInTheDocument();
  });

  it("should render a ListItem suburbState based on props", () => {
    const element = screen.getByText(/NSW/i);
    expect(element).toBeInTheDocument();
  });

  it("should call deleteOnClick with the right id value, multiple times with a signed in user", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider
            value={{ user: mockUser, signOut: mockSignOut }}
          >
            <ListItem
              id={5}
              postcode="2095"
              suburbName="Manly"
              suburbState="NSW"
              deleteOnClick={deleteOnClick}
              handleEdit={handleEdit}
            />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    const btn = screen.getByLabelText("delete");
    await userEvent.click(btn);
    expect(deleteOnClick).toHaveBeenCalledTimes(1);
    await userEvent.click(btn);
    expect(deleteOnClick).toHaveBeenCalledTimes(2);
    expect(deleteOnClick).toHaveBeenNthCalledWith(1, 5);
    expect(deleteOnClick).toHaveBeenNthCalledWith(2, 5);
  });

  it("should call handleEdit with the right id value, multiple times with a signed in user", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider
            value={{ user: mockUser, signOut: mockSignOut }}
          >
            <ListItem
              id={5}
              postcode="2095"
              suburbName="Manly"
              suburbState="NSW"
              deleteOnClick={deleteOnClick}
              handleEdit={handleEdit}
            />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    const btn = screen.getByLabelText("edit");
    await userEvent.click(btn);
    expect(handleEdit).toHaveBeenCalledTimes(1);
    await userEvent.click(btn);
    expect(handleEdit).toHaveBeenCalledTimes(2);
    expect(handleEdit).toHaveBeenNthCalledWith(1, 5);
    expect(handleEdit).toHaveBeenNthCalledWith(2, 5);
  });

  it("should not display the Delete Icon if the user is not logged in", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider value={{ signOut: mockSignOut }}>
            <ListItem
              id={5}
              postcode="2095"
              suburbName="Manly"
              suburbState="NSW"
              deleteOnClick={deleteOnClick}
              handleEdit={handleEdit}
            />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    // query returns null if it cant find the icon
    const icon = screen.queryByLabelText("delete");
    expect(icon).toBeNull();
  });

  it("should not display the Edit Icon if the user is not logged in", async () => {
    render(
      <MemoryRouter initialEntries={["/"]}>
        <ThemeProvider theme={theme}>
          <UserContext.Provider value={{ signOut: mockSignOut }}>
            <ListItem
              id={5}
              postcode="2095"
              suburbName="Manly"
              suburbState="NSW"
              deleteOnClick={deleteOnClick}
              handleEdit={handleEdit}
            />
          </UserContext.Provider>
        </ThemeProvider>
      </MemoryRouter>
    );

    // query returns null if it cant find the icon
    const icon = screen.queryByLabelText("edit");
    expect(icon).toBeNull();
  });
});
