import { useEffect, useState } from "react";
import {
  Alert,
  Backdrop,
  Box,
  Skeleton,
  Snackbar,
  Typography,
} from "@mui/material";
import { PostCodeResponse } from "../../services/api-responses.interfaces";
import {
  getAllPostCodes,
  findPostCodesBySuburb,
  findSuburbsByPostCode,
  deleteById,
} from "../../services/postcode-services";
import Searchbar from "../../components/Searchbar/Searchbar";
import DeleteConfirmationModel from "../../components/DeleteConfirmationModal/DeleteConfirmationModel";
import ListContainer from "../../containers/ListContainer/ListContainer";

const IndexPage = () => {
  const [postcodes, setPostcodes] = useState<PostCodeResponse[]>([]);
  const [searchTerm, setSearchTerm] = useState<string | null>(null);
  const [showResults, setShowResults] = useState<boolean>(true);

  const [error, setError] = useState<Error | null>(null);
  const [fetchStatus, setFetchStatus] = useState<string>("LOADING");

  const [openModal, setOpenModal] = useState(false);
  const [postcodeId, setPostcodeId] = useState<number | undefined>(undefined);

  const fetchAllpostcodes = () => {
    getAllPostCodes()
      .then((data) => {
        setPostcodes(data);
        setFetchStatus("SUCCESS");
      })
      .catch((e: any) => {
        setError(new Error("Failed to fetch postcodes. Please try again."));
        setFetchStatus("FAILED");
        console.error("ERROR: " + e);
      });
  };

  const handleSearch = (searchFunction: Function, searchTerm: string) => {
    setShowResults(true);
    searchFunction(searchTerm)
      .then((data: any) => {
        setPostcodes(data);
        setFetchStatus("SUCCESS");
        if (data.length === 0) {
          setShowResults(false);
        }
      })
      .catch((e: any) => {
        setError(new Error("Failed to fetch postcodes. Please try again."));
        setFetchStatus("FAILED");
        console.error("ERROR: " + e);
      });
  };

  useEffect(() => {
    setShowResults(true);
    fetchAllpostcodes();
  }, []);

  useEffect(() => {
    if (searchTerm && searchTerm.match(/\d+/)) {
      handleSearch(findSuburbsByPostCode, searchTerm);
    } else if (searchTerm) {
      handleSearch(findPostCodesBySuburb, searchTerm.toLowerCase());
    } else if (!searchTerm) {
      setShowResults(true);
      fetchAllpostcodes();
    }
  }, [searchTerm]);

  const handleDelete = async (id: number | undefined) => {
    if (id === undefined) {
      console.error("Id is undefined for deleting a todo by id");
      throw new Error(`Unable to delete todo  with id: ${id}`);
    }

    try {
      await deleteById(id);
      setPostcodes(postcodes.filter((item) => item.id !== id));
      setOpenModal(false);
    } catch (e: any) {
      setError(new Error("Failed to delete postcode. Please try again."));
      setFetchStatus("FAILED");
      console.error(e);
    }
  };

  return (
    <section style={{ width: "100%" }}>
      {fetchStatus === "LOADING" && (
        <Box
          display="flex"
          flexDirection="column"
          justifyContent="center"
          rowGap="2rem"
        >
          <Skeleton data-testid="loading" />
          <Skeleton width="90%" />
          <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
          <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
          <Skeleton variant="rounded" width="100%" height={60}></Skeleton>
        </Box>
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
        <Box display="flex" flexDirection="column" rowGap="0.75em">
          <h1 style={{ margin: "0", fontSize: "2em" }}>Find a postcode</h1>

          <h3 style={{ margin: "0" }}>Search</h3>
          <Searchbar
            setSearchTerm={setSearchTerm}
            placeholder="Enter suburb, town, city or postcode"
          />
          {showResults && (
            <>
              <h3 style={{ margin: "2rem 0 0 0" }}>Results</h3>
              <h4 style={{ margin: "0" }}>
                Your search
                {searchTerm
                  ? " for " + searchTerm + " returned "
                  : " returned "}
                {postcodes.reduce((acc, curr) => {
                  return (
                    acc +
                    curr.associatedSuburbs.length -
                    (curr.associatedSuburbs.length > 0 ? 1 : 0)
                  );
                }, postcodes.length)}{" "}
                result(s).
              </h4>
              <ListContainer
                postcodes={postcodes}
                setPostcodeId={setPostcodeId}
                setOpenModal={setOpenModal}
              />
            </>
          )}
          {!showResults && (
            <Box marginTop="1em" textAlign="center">
              <Typography>
                Sorry no postcode(s) or suburb(s) could be found
              </Typography>
            </Box>
          )}
        </Box>
      )}
      {openModal && (
        <DeleteConfirmationModel
          Id={postcodeId}
          openModal={openModal}
          setOpenModal={setOpenModal}
          handleDelete={handleDelete}
        />
      )}
    </section>
  );
};

export default IndexPage;
