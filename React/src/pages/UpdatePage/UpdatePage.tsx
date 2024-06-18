import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getPostCodebyId } from "../../services/postcode-services";
import { UserContext } from "../../context/userContextProvider";
import { Alert, Backdrop, Box, Skeleton, Snackbar } from "@mui/material";
import CreateUpdateForm from "../../components/CreateUpdateForm/CreateUpdateForm";
import { PostCodeResponse } from "../../services/api-responses.interfaces";

const UpdatePage = () => {
  const navigate = useNavigate();
  const { id: idParam } = useParams();
  const Id = Number(idParam);
  const [defaultValues, setDefaultValues] = useState<
    PostCodeResponse | undefined
  >();
  const [error, setError] = useState<Error | null>(null);
  const [fetchStatus, setFetchStatus] = useState<String>("LOADING");
  const [open, setOpen] = useState(false);

  useEffect(() => {
    getPostCodebyId(Id)
      .then((data) => {
        setFetchStatus("SUCCESS");
        console.log(data);
        const newDefaultValues = {
          id: data.id,
          postcode: data.postcode,
          associatedSuburbs: data.associatedSuburbs,
        };
        setDefaultValues(newDefaultValues);
      })
      .catch((e: Error) => {
        setError(
          new Error("Failed to update this postcode. Please try again.")
        );
        setOpen(true);
        setFetchStatus("FAILED");
        console.error("ERROR: failed to updated id: " + { Id } + e);
      });
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

  const onSubmit = () => {
    console.log("hello");
  };

  console.log(defaultValues);

  //   const onSubmit: SubmitHandler<TodoFormData> = (data) => {
  //     if (isNaN(Id)) {
  //       console.error("Invalid Id");
  //       throw new Error("");
  //     }

  //     updateTodoById(Id, data)
  //       .then((_data) => {
  //         navigate("/todo");
  //         setError(null);
  //       })
  //       .catch((e: Error) => {
  //         setError(
  //           new Error("Failed to submit your edited todo. Please try again.")
  //         );
  //         setOpen(true);
  //         console.error(e);
  //       });
  //   };

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
          {/* <h1>Edit `{defaultValues?.title}` Todo</h1> */}
          {error && (
            <Backdrop open={open} sx={{ color: "#fff", zIndex: 1 }}>
              <Snackbar
                open={open}
                autoHideDuration={6000}
                onClose={handleClose}
              >
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
          {/* {defaultValues && (
            <CreateUpdateForm
              defaultValues={defaultValues}
              onSubmit={onSubmit}
              mode="Edit"
            />
          )} */}
        </>
      )}
    </Box>
  );
};

export default UpdatePage;
