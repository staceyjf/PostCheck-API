import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Alert,
  Backdrop,
  Box,
  Skeleton,
  Snackbar,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import {
  PostCodeResponse,
  SuburbResponse,
} from "../../services/api-responses.interfaces";
import {
  getAllPostCodes,
  findPostCodesBySuburb,
  findSuburbsByPostCode,
} from "../../services/postcode-services";
import ListItm from "../../components/ListItm/ListItm";
import Searchbar from "../../components/Searchbar/Searchbar";

const IndexPage = () => {
  const [postcodes, setPostcodes] = useState<PostCodeResponse[]>([]);
  const [searchTerm, setSearchTerm] = useState<string | null>(null);
  const [showResults, setShowResults] = useState<boolean>(true);

  const [error, setError] = useState<Error | null>(null);
  const [fetchStatus, setFetchStatus] = useState<string>("LOADING");

  const [openModal, setOpenModal] = useState(false);
  const [postcodeId, setPostcodeId] = useState<number | undefined>(undefined);

  const navigate = useNavigate();

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
    if (searchTerm && searchTerm.match(/\d{4}/g)) {
      handleSearch(findSuburbsByPostCode, searchTerm);
    } else if (searchTerm) {
      handleSearch(findPostCodesBySuburb, searchTerm.toLowerCase());
    } else if (!searchTerm) {
      setShowResults(true);
      fetchAllpostcodes();
    }
  }, [searchTerm]);

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
          <h4 style={{ margin: "1em 0" }}>
            Your search
            {searchTerm ? " for " + searchTerm + " returned " : " returned "}
            {postcodes.reduce(
              (acc, curr) => acc + curr.associatedSuburbs.length,
              0
            )}{" "}
            result(s).
          </h4>

          <Searchbar
            setSearchTerm={setSearchTerm}
            placeholder="Enter suburb, town, city or postcode"
          />
          {showResults && (
            <Table sx={{ width: "100%", marginTop: "2em" }}>
              <TableHead>
                <TableRow>
                  <TableCell>Postcode</TableCell>
                  <TableCell>Suburb</TableCell>
                  <TableCell></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {postcodes.map((postcode: PostCodeResponse) =>
                  postcode.associatedSuburbs.map(
                    (suburb: SuburbResponse, index: number) => (
                      <TableRow key={`${postcode.id}-${index}`}>
                        <ListItm
                          id={postcode.id}
                          postcode={postcode.postcode}
                          suburbName={suburb.name}
                          suburbState={suburb.state}
                        />
                      </TableRow>
                    )
                  )
                )}
              </TableBody>
            </Table>
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

export default IndexPage;
