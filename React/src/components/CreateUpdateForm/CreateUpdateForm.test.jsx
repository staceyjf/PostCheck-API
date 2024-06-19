import { render, screen, fireEvent } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import CreateUpdateForm from "./CreateUpdateForm";

describe("CreateUpdateForm", () => {
  const onSubmit = vi.fn((fn) => fn); //mock up of a function

  const mockedSuburb = {
    id: "1",
    name: "sydney",
    state: "nsw",
  };

  const defaultValues = {
    postcode: "2095",
    associatedSuburbs: [],
  };

  const suburbs = [mockedSuburb];
  //------------- Create -------------
  it("should call onSubmit, with the value of the CreateUpdateForm for Create Mode", async () => {
    render(
      <CreateUpdateForm onSubmit={onSubmit} mode="Create" suburbs={suburbs} />
    );

    const postcodeInput = await screen.findByLabelText("Postcode");
    const form = screen.getByTestId("postcode-form");

    const user = userEvent.setup();
    await user.type(postcodeInput, "2098");

    fireEvent.submit(form);

    expect(onSubmit).toHaveBeenCalled();
    expect(onSubmit).toHaveBeenCalledTimes(1);
  });

  it("should should render user input correctly into the postcode field for Create Mode", async () => {
    render(
      <CreateUpdateForm onSubmit={onSubmit} mode="Create" suburbs={suburbs} />
    );

    const postcodeInput = await screen.findByLabelText("Postcode");
    const form = screen.getByTestId("postcode-form");

    const user = userEvent.setup();
    await user.type(postcodeInput, "2098");

    expect(postcodeInput).toHaveValue("2098");
  });

  //------------- Edit -------------
  it("should call onSubmit, with the value of the CreateUpdateForm for Edit Mode", async () => {
    render(
      <CreateUpdateForm
        onSubmit={onSubmit}
        mode="Edit"
        suburbs={suburbs}
        defaultValues={defaultValues}
      />
    );

    const postcodeInput = await screen.findByLabelText("Postcode");
    const form = screen.getByTestId("postcode-form");

    const user = userEvent.setup();
    await user.type(postcodeInput, "2098");

    fireEvent.submit(form);

    expect(onSubmit).toHaveBeenCalled();
    expect(onSubmit).toHaveBeenCalledTimes(1);
  });

  it("should render the default values", async () => {
    render(
      <CreateUpdateForm
        onSubmit={onSubmit}
        mode="Edit"
        suburbs={suburbs}
        defaultValues={defaultValues}
      />
    );

    const postcodeInput = await screen.findByLabelText("Postcode");

    expect(postcodeInput).toHaveValue("2095");
  });
});
