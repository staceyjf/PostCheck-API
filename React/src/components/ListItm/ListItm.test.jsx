import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import ListItem from "./ListItm";

describe("ListItem", () => {
  const deleteOnClick = vi.fn((id) => console.log(id));
  const handleEdit = vi.fn((id) => console.log(id));
  const isCompleteMock = vi.fn((id, isComplete) => console.log(id, isComplete));

  beforeEach(() => {
    render(
      <ListItem
        id={5}
        createdAt={new Date("2024-05-21")}
        dueDate={new Date("2024-05-22")}
        title="My test heading"
        task="My test paragraph with lots and lots of words."
        deleteOnClick={deleteOnClick}
        handleEdit={handleEdit}
        handleIsComplete={isCompleteMock}
      />
    );
  });

  // -----------Todo header-----------
  it("should render a ListItem title based on props", () => {
    const headingElement = screen.getByText(/My test heading/i);
    expect(headingElement).toBeInTheDocument();
  });

  // -----------Todo contents-----------
  it("should render a created at, due date and task details ListItem based on props", () => {
    const createdDateElement = screen.getByText(/21-05-24/i);
    const dueDateElement = screen.getByText(/22-05-24/i);
    const taskElement = screen.getByText(/My test paragraph with/i);

    expect(createdDateElement).toBeInTheDocument();
    expect(dueDateElement).toBeInTheDocument();
    expect(taskElement).toBeInTheDocument();
  });

  // -----------Switch / Checkbox-----------
  it("should default the isComplete switch to false when no value is provided", () => {
    const switchElement = screen.getByRole("checkbox");
    expect(switchElement).not.toBeChecked();
  });

  it("should toggle the switch when it is clicked", async () => {
    const switchElement = screen.getByRole("checkbox");
    expect(switchElement).not.toBeChecked();
    await userEvent.click(switchElement);
    expect(switchElement).toBeChecked();
    await userEvent.click(switchElement);
    expect(switchElement).not.toBeChecked();
  });

  //-----------on click - delete & edit-----------
  it("should call deleteOnClick with the right id value, multiple times", async () => {
    const btn = screen.getByLabelText("delete");
    await userEvent.click(btn);
    expect(deleteOnClick).toHaveBeenCalledTimes(1);
    await userEvent.click(btn);
    expect(deleteOnClick).toHaveBeenCalledTimes(2);
    expect(deleteOnClick).toHaveBeenNthCalledWith(1, 5);
    expect(deleteOnClick).toHaveBeenNthCalledWith(2, 5);
  });

  it("should call handleEdit with the right id value, multiple times", async () => {
    const btn = screen.getByLabelText("edit");
    await userEvent.click(btn);
    expect(handleEdit).toHaveBeenCalledTimes(1);
    await userEvent.click(btn);
    expect(handleEdit).toHaveBeenCalledTimes(2);
    expect(handleEdit).toHaveBeenNthCalledWith(1, 5);
    expect(handleEdit).toHaveBeenNthCalledWith(2, 5);
  });
});
