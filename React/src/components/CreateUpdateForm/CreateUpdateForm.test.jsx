import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import TodoForm from "./CreateUpdateForm";
import { ColoursContext } from "../../context/ColourContextProvider";

describe("TodoForm", () => {
  const onSubmit = vi.fn((fn) => fn); //mock up of a function
  const defaultValues = {
    title: "Tester title",
    task: "Tester task",
    dueDate: "2024-05-30T00:28:32.724Z",
    isComplete: false,
    colourId: "1",
  };
  const colours = [
    { id: "1", name: "Red" },
    { id: "2", name: "Blue" },
  ];

  beforeEach(() => {
    render(
      <ColoursContext.Provider value={{ colours }}>
        <TodoForm
          defaultValues={defaultValues}
          onSubmit={onSubmit}
          mode="Create"
        />
      </ColoursContext.Provider>
    );
  });

  //------------- rendering user input -------------
  it("should return the user input for the title text field", async () => {
    const input = await screen.findByLabelText("Title");
    const user = userEvent.setup();
    await user.type(input, "My new title");
    expect(input).toHaveValue("My new title");
  });

  it("should return the user input for the task text field", async () => {
    const input = await screen.findByLabelText("Task details");
    const user = userEvent.setup();
    await user.type(input, "New task titles");
    expect(input).toHaveValue("New task titles");
  });

  it("should return a string of the user's input for the datePicker", async () => {
    const input = await screen.findByLabelText("Due Date"); // need to wait for the DatePicker to render
    const date = "30/05/2024"; // need the correct input format
    await userEvent.type(input, date);
    expect(input).toHaveValue(date);
  });

  it("should return the correct boolean value based on user's input for the isComplete toggle", async () => {
    const switchElement = await screen.findByLabelText("Task Status");
    expect(switchElement).not.toBeChecked();
    const user = userEvent.setup();
    await user.click(switchElement);
    expect(switchElement).toBeChecked();
    await user.click(switchElement);
    expect(switchElement).not.toBeChecked();
  });

  it("should return the user's input for the select", async () => {
    const select = await screen.findByRole("combobox");
    const user = userEvent.setup();
    await user.click(select); // Open the select dropdown
    const menuItem = await screen.findByRole("option", { name: "Red" });
    await user.click(menuItem); // Select the option / li element
    expect(menuItem).toHaveTextContent("Red");
  });

  //------------- onsubmit -------------
  it("Should call onSubmit, with the value of the todoform", async () => {
    const btn = screen.getByTestId("submitbtn");
    const user = userEvent.setup();
    await user.click(btn);
    expect(onSubmit).toHaveBeenCalled();
    expect(onSubmit).toHaveBeenCalledTimes(1);
    expect(onSubmit.mock.calls[0][0]).toStrictEqual({
      title: "Tester title",
      task: "Tester task",
      dueDate: "2024-05-30T00:28:32.724Z",
      isComplete: false,
      colourId: "1",
    });
  });
});
