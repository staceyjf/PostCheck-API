import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  createSuburb,
  getSuburbById,
  updateSuburbById,
} from "../../services/suburb-services";
import { Alert, Backdrop, Box, Skeleton, Snackbar } from "@mui/material";
import CreateUpdateSuburbsForm from "../../components/CreateUpdateSuburbsForm/CreateUpdateSuburbsForm";
import { SuburbForm } from "../../services/api-responses.interfaces";

interface CreateUpdateSuburbsPageProps {
  mode: string;
}

const CreateUpdateSuburbsPage = ({ mode }: CreateUpdateSuburbsPageProps) => {
  const navigate = useNavigate();
  const { id: idParam } = useParams();
  const Id = Number(idParam);
  const [defaultValues, setDefaultValues] = useState<SuburbForm | undefined>();
  const [error, setError] = useState<Error | null>(null);
  const [fetchStatus, setFetchStatus] = useState<String>("LOADING");
  const [open, setOpen] = useState(false);

  // helper error to reduce code in the useEffect
  const handleError = (
    error: Error,
    errorMessage: string,
    errorLog: string
  ) => {
    setError(new Error(errorMessage));
    setOpen(true);
    setFetchStatus("FAILED");
    console.error(errorLog + " " + error);
  };

  useEffect(() => {
    if (mode === "Edit") {
      getSuburbById(Id)
        .then((data) => {
          setFetchStatus("SUCCESS");
          const newDefaultValues: SuburbForm = {
            id: data.id,
            name: data.name,
            state: data.state,
          };
          setDefaultValues(newDefaultValues);
        })
        .catch((e: Error) =>
          handleError(
            e,
            "Failed to fetch Suburb. Please try again.",
            "ERROR: failed to updated id: " + Id + ", " + e
          )
        );
    } else {
      setFetchStatus("SUCCESS");
    }
  }, []);

  const handleClose = (
    _event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  const onSubmit = (name: string, state: string) => {
    const data = { name, state };

    if (mode === "Edit") {
      if (isNaN(Id)) {
        console.error("Invalid Id");
        // TODO: add 404 page
        setError(new Error(`Oops, something when wrong. Please try again`));
        setOpen(true);
        setFetchStatus("FAILED");
      }
      updateSuburbById(Id, data)
        .then((_data) => {
          navigate("/");
          setError(null);
        })
        .catch((e: Error) => {
          setError(new Error(`Failed to update suburb. Please try again.`));
          setOpen(true);
          setFetchStatus("FAILED");
          console.error(
            "ERROR: failed to update item with id: " + Id + ", " + e
          );
        });
    } else {
      createSuburb(data)
        .then((_data) => {
          console.log(data);
          navigate("/");
          setError(null);
        })
        .catch((e: Error) => {
          setError(new Error(`Failed to create a suburb. Please try again.`));
          setOpen(true);
          setFetchStatus("FAILED");
          console.error("ERROR: " + e);
        });
    }
  };

  return (
    <Box width="100%">
      {fetchStatus === "LOADING" && (
        <>
          <Box
            display="flex"
            flexDirection="column"
            rowGap="0.5rem"
            justifyContent="center"
            data-testid="loading"
          >
            <Skeleton />
            <Skeleton width="60%" />
            <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
            <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
            <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
          </Box>
        </>
      )}
      {fetchStatus === "FAILED" && (
        <Backdrop open={open} sx={{ color: "#fff", zIndex: 1 }}>
          <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
            <Alert
              severity="error"
              variant="filled"
              onClose={handleClose}
              sx={{ width: "100%" }}
            >
              {error?.message}
            </Alert>
          </Snackbar>
        </Backdrop>
      )}
      {fetchStatus === "SUCCESS" && (
        <>
          <h1>
            {mode}{" "}
            <span style={{ fontStyle: "italic" }}>{defaultValues?.name}</span>{" "}
            Suburb
          </h1>
          {defaultValues && (
            <CreateUpdateSuburbsForm
              defaultValues={defaultValues}
              onSubmit={onSubmit}
              mode="Edit"
            />
          )}
          {!defaultValues && (
            <CreateUpdateSuburbsForm onSubmit={onSubmit} mode="Create" />
          )}
        </>
      )}
    </Box>
  );
};

export default CreateUpdateSuburbsPage;
