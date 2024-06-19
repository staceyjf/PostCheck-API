import { useState } from "react";
import {
  Autocomplete,
  Box,
  Button,
  FormControl,
  TextField,
} from "@mui/material";
import { SuburbForm } from "../../services/api-responses.interfaces";
import { convertStateTypeToString, states, StateType } from "./AusStates";

interface CreateUpdateSuburbsFormProps {
  defaultValues?: SuburbForm;
  mode: "Create" | "Edit";
  onSubmit: (name: string, state: string) => void;
}

const CreateUpdateSuburbsForm = ({
  defaultValues,
  mode,
  onSubmit,
}: CreateUpdateSuburbsFormProps) => {
  const [selectedState, setSelectedState] = useState<StateType | null>(null);
  const [nameError, setNameError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const name = e.target.value;

    if (name === "") {
      setNameError(`The field is missing.`);
    } else {
      // Reset errors if input is valid
      setNameError(null);
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.currentTarget;
    const name = new FormData(form).get("name") as string;

    const state = convertStateTypeToString(selectedState);

    // submit when there are no errors
    if (name && !nameError && state) {
      onSubmit(name, state);
    }
  };

  return (
    <>
      <Box
        width="100%"
        display="flex"
        justifyContent="center"
        alignItems="center"
      >
        <form onSubmit={handleSubmit} style={{ width: "65%" }}>
          <FormControl fullWidth>
            <TextField
              color="secondary"
              size="small"
              margin="dense"
              error={!!nameError}
              id="name"
              name="name"
              label="Suburb name"
              defaultValue={defaultValues?.name}
              helperText={nameError || ""}
              variant="filled"
              onChange={handleChange}
            />

            <Autocomplete
              id="state"
              isOptionEqualToValue={(option, value) =>
                option.state === value.state
              }
              options={states}
              value={selectedState}
              onChange={(_event, newValue) => {
                setSelectedState(newValue);
              }}
              getOptionLabel={(option) => `${option.state}`}
              renderInput={(params) => (
                <TextField
                  {...params}
                  variant="filled"
                  color="secondary"
                  size="small"
                  margin="dense"
                  label="States"
                />
              )}
            />

            <Button
              type="submit"
              variant="contained"
              color="secondary"
              sx={{ margin: "3.5em" }}
            >
              {mode}
            </Button>
          </FormControl>
        </form>
      </Box>
    </>
  );
};

export default CreateUpdateSuburbsForm;
