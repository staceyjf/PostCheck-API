import { useState } from "react";
import {
  Autocomplete,
  Box,
  Button,
  FormControl,
  TextField,
} from "@mui/material";
import {
  PostCodeForm,
  SuburbResponse,
} from "../../services/api-responses.interfaces";

interface CreateUpdateFormProps {
  defaultValues?: PostCodeForm;
  suburbs?: SuburbResponse[];
  mode: "Create" | "Edit";
  onSubmit: (postcode: string, suburbIds: number[]) => void;
}

const CreateUpdateForm = ({
  defaultValues,
  suburbs,
  mode,
  onSubmit,
}: CreateUpdateFormProps) => {
  console.log("This is suburbs for create:" + suburbs);
  console.log("This is defaultvalues for edit:" + defaultValues);

  const [postcodeError, setPostcodeError] = useState<string | null>(null);
  const [suburbError, setSuburbError] = useState<string | null>(null);

  const handlePostcodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const postcode = e.target.value;

    if (postcode === "") {
      setPostcodeError(`The postcode field is missing.`);
    } else if (isNaN(Number(postcode))) {
      setPostcodeError(`The postcode field needs to be numerical values.`);
    } else if (postcode.length !== 4) {
      setPostcodeError(`The postcode field needs to be four digits long.`);
    } else {
      // Reset errors if input is valid
      setPostcodeError(null);
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.currentTarget;
    const postcode = new FormData(form).get("postcode") as string;

    // const associatedSuburbs = new FormData(form).get("associatedSuburbs");
    // const associatedSuburbsIds = associatedSuburbs.map((suburb) => suburb.id);
    const associatedSuburbsIds = [1];

    // submit when there are no errors
    if (postcode && !postcodeError && !suburbError) {
      onSubmit(postcode, associatedSuburbsIds);
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
              error={!!postcodeError}
              id="postcode"
              name="postcode"
              label="Postcode"
              defaultValue={defaultValues?.postcode || "Add a postcode"}
              helperText={postcodeError || ""}
              variant="filled"
              onChange={handlePostcodeChange}
            />
            <Autocomplete
              multiple
              id="associatedSuburbs"
              isOptionEqualToValue={(option, value) => option.id === value.id}
              options={defaultValues?.associatedSuburbs || suburbs || []}
              defaultValue={defaultValues?.associatedSuburbs || []}
              getOptionLabel={(option) => `${option.name}, ${option.state}`}
              renderInput={(params) => (
                <TextField
                  {...params}
                  variant="filled"
                  color="secondary"
                  size="small"
                  margin="dense"
                  label="Associated Suburbs"
                />
              )}
            />

            <Button
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

export default CreateUpdateForm;
