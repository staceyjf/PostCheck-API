import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  createPostCode,
  getPostCodebyId,
  updatePostCodeById,
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
    }

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

    console.log("this is data");
    console.log(data);

    if (mode === "Edit") {
      if (isNaN(Id)) {
        console.error("Invalid Id");
        // TODO: add 404 page
        setError(new Error(`Oops, something when wrong. Please try again`));
        setOpen(true);
        setFetchStatus("FAILED");
      }
      updatePostCodeById(Id, data)
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
    } else {
      createPostCode(data)
        .then((_data) => {
          console.log(data);
          navigate("/");
          setError(null);
        })
        .catch((e: Error) => {
          setError(new Error(`Failed to create a postcode. Please try again.`));
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
            <span style={{ fontStyle: "italic" }}>
              {defaultValues?.postcode}
            </span>{" "}
            Postcode
          </h1>
          {defaultValues && (
            <CreateUpdateForm
              defaultValues={defaultValues}
              onSubmit={onSubmit}
              suburbs={suburbs}
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
