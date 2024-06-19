import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  getPostCodebyId,
  updatePostById,
} from "../../services/postcode-services";
import { Alert, Backdrop, Box, Skeleton, Snackbar } from "@mui/material";
import CreateUpdateForm from "../../components/CreateUpdateForm/CreateUpdateForm";
import {
  PostCodeForm,
  SuburbResponse,
} from "../../services/api-responses.interfaces";
import { getAllSuburbs } from "../../services/suburb-services";

interface CreateUpdatePageProps {
  mode: string;
}

const CreateUpdatePage = ({ mode }: CreateUpdatePageProps) => {
  const navigate = useNavigate();
  const { id: idParam } = useParams();
  const Id = Number(idParam);
  const [defaultValues, setDefaultValues] = useState<
    PostCodeForm | undefined
  >();
  const [suburbs, setSuburbs] = useState<SuburbResponse[]>([]);
  const [error, setError] = useState<Error | null>(null);
  const [fetchStatus, setFetchStatus] = useState<String>("LOADING");
  const [open, setOpen] = useState(false);

  console.log(defaultValues);

  // helper error to reduce code in the useEffect
  const handleError = (e: Error, errorMessage: string, errorLog: string) => {
    setError(new Error(errorMessage));
    setOpen(true);
    setFetchStatus("FAILED");
    console.error(errorLog);
  };

  useEffect(() => {
    if (mode === "Edit") {
      getPostCodebyId(Id)
        .then((data) => {
          setFetchStatus("SUCCESS");
          const newDefaultValues = {
            id: data.id,
            postcode: data.postcode,
            associatedSuburbs: data.associatedSuburbs,
          };
          setDefaultValues(newDefaultValues);
        })
        .catch((e: Error) =>
          handleError(
            e,
            "Failed to fetch Postpost. Please try again.",
            "ERROR: failed to updated id: " + Id + ", " + e
          )
        );
    } else {
      getAllSuburbs()
        .then((data) => {
          setFetchStatus("SUCCESS");
          setSuburbs(data);
        })
        .catch((e: Error) =>
          handleError(
            e,
            "Failed to fetch Postpost. Please try again.",
            "ERROR: failed to fetch all postcodes"
          )
        );
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

  const onSubmit = (postcode: string, suburbIds: number[]) => {
    const data = { postcode, suburbIds };
    if (isNaN(Id)) {
      console.error("Invalid Id");
      // TODO: add 404 page
      throw new Error("Oops, something when wrong. Please try again");
    }

    if (mode === "edit") {
      updatePostById(Id, data)
        .then((_data) => {
          navigate("/");
          setError(null);
        })
        .catch((e: Error) => {
          setError(new Error(`Failed to update postcode. Please try again.`));
          setOpen(true);
          setFetchStatus("FAILED");
          console.error(
            "ERROR: failed to update item with id: " + Id + ", " + e
          );
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
          <h1>Edit {defaultValues?.postcode} Postcode</h1>
          {defaultValues && (
            <CreateUpdateForm
              defaultValues={defaultValues}
              onSubmit={onSubmit}
              mode="Edit"
            />
          )}
          {!defaultValues && (
            <CreateUpdateForm
              onSubmit={onSubmit}
              mode="Create"
              suburbs={suburbs}
            />
          )}
        </>
      )}
    </Box>
  );
};

export default CreateUpdatePage;
