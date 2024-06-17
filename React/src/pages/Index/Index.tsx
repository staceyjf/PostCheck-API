import React from "react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Alert,
  Backdrop,
  Box,
  Divider,
  List,
  Skeleton,
  Snackbar,
} from "@mui/material";
import {
  PostCodeResponse,
  SuburbResponse,
} from "../../services/api-responses.interfaces";
import {
  getAllPostCodes,
  findPostCodesBySuburb,
  findSuburbsByPostCode,
} from "../../services/services";
import ListItm from "../../components/ListItm/ListItm";
import Searchbar from "../../components/Searchbar/Searchbar";

const Index = () => {
  const [postcodes, setPostcodes] = useState<PostCodeResponse[]>([]);

  const [error, setError] = useState<Error | null>(null);
  const [fetchStatus, setFetchStatus] = useState<string>("LOADING");
  const [openModal, setOpenModal] = useState(false);
  const [postcodeId, setPostcodeId] = useState<number | undefined>(undefined);

  const [searchTerm, setSearchTerm] = useState<string | null>(null);
  const navigate = useNavigate();

  // get all postcodes
  useEffect(() => {
    fetchAllpostcodes();
  }, []);

  // search term
  useEffect(() => {
    // numbers for postcodes
    if (searchTerm && searchTerm.match(/\d{4}/g)) {
      findSuburbsByPostCode(searchTerm)
        .then((data) => setPostcodes(data))
        .catch((error) => setError(error));
      // all else will be considered a sururb
    } else if (searchTerm) {
      findPostCodesBySuburb(searchTerm)
        .then((data) => setPostcodes(data))
        .catch((error) => setError(error));
    }
  }, [searchTerm]);

  const fetchAllpostcodes = async () => {
    try {
      const allpostcodes = await getAllPostCodes();
      setFetchStatus("SUCCESS");
      setPostcodes(allpostcodes);
    } catch (e: any) {
      setError(new Error("Failed to fetch postcodes. Please try again."));
      setFetchStatus("FAILED");
      console.error(e);
    }
  };

  console.log(postcodes);

  return (
    <section style={{ width: "100%" }}>
      {fetchStatus === "LOADING" && (
        <>
          <Box
            display="flex"
            flexDirection="column"
            justifyContent="center"
            rowGap="0.5rem"
          >
            <Skeleton data-testid="loading" />
            <Skeleton width="90%" />
            <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
            <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
            <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
          </Box>
        </>
      )}
      {fetchStatus === "FAILED" && (
        <Backdrop open={true} sx={{ color: "#fff", zIndex: 1 }}>
          <Snackbar
            open={true}
            autoHideDuration={6000}
            onClose={() => setError(null)}
          >
            <Alert
              severity="error"
              variant="filled"
              sx={{ width: "100%" }}
              aria-live="assertive"
              data-testid="error-alert"
            >
              {error?.message}
            </Alert>
          </Snackbar>
        </Backdrop>
      )}
      {fetchStatus === "SUCCESS" && (
        <Box display="flex" flexDirection="column">
          <h1 style={{ margin: "0", fontSize: "2em" }}>Find a postcode</h1>
          <h4>Your search for "" returned "" result(s).</h4>
          <h4>Please select an item from the list below to view details.</h4>

          <Searchbar
            setSearchTerm={setSearchTerm}
            placeholder="Enter suburb, town, city or postcode"
          />

          <List>
            {postcodes.map((postcode: PostCodeResponse) =>
              postcode.associatedSuburbs.map(
                (suburb: SuburbResponse, index: number) => (
                  <React.Fragment key={`${postcode.id}-${index}`}>
                    <ListItm
                      id={Number(postcode.id)}
                      postcode={postcode.postcode}
                      suburbName={suburb.name}
                      suburbState={suburb.state}
                      // deleteOnClick={deleteTodoOnClick}
                      // handleEdit={handleTodoEdit}
                      // handleIsComplete={handleIsComplete}
                    />
                    <Divider component="li" />
                  </React.Fragment>
                )
              )
            )}
          </List>
        </Box>
      )}
      {/* {openModal && (
        <DeleteConfirmationModel
          postcodeId={postcodeId}
          openModal={openModal}
          setOpenModal={setOpenModal}
          handleDelete={handleTodoDelete}
        />
      )} */}
    </section>
  );
};

export default Index;
