import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Searchbar from "./Searchbar";

describe("Searchbar", () => {
  const setSearchTerm = vi.fn((fn) => fn);

  it("should call the SearchTerm setter, with the value of the Searchbar", async () => {
    render(
      <Searchbar
        setSearchTerm={setSearchTerm}
        placeholder="Enter suburb, town, city or postcode"
      />
    );

    const input = await screen.findByPlaceholderText(
      "Enter suburb, town, city or postcode"
    );
    const form = screen.getByTestId("search-form");

    const user = userEvent.setup();
    await user.type(input, "2098");

    expect(input).toHaveValue("2098");

    fireEvent.submit(form);

    expect(setSearchTerm).toHaveBeenCalledWith("2098");
  });

  it("should render user input into the searchbar correctly", async () => {
    render(
      <Searchbar
        setSearchTerm={setSearchTerm}
        placeholder="Enter suburb, town, city or postcode"
      />
    );

    const input = await screen.findByPlaceholderText(
      "Enter suburb, town, city or postcode"
    );
    const form = screen.getByTestId("search-form");

    const user = userEvent.setup();
    await user.type(input, "2098");

    expect(input).toHaveValue("2098");
  });
});
