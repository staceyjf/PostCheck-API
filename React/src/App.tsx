import { BrowserRouter, Route, Routes } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import theme from "./styling/theme";
import IndexPage from "./pages/IndexPage/IndexPage";
import "./App.scss";
import { Container, Box } from "@mui/material";
import Navbar from "./components/Navbar/Navbar";
import UserContextProvider from "./context/userContextProvider";
import CreateUpdatePage from "./pages/CreateUpdatePage/CreateUpdatePage";
import CreateUpdateSuburbsPage from "./pages/CreateUpdateSuburbsPage/CreateUpdateSuburbsPage";

function App() {
  return (
    <>
      <ThemeProvider theme={theme}>
        {/* Normalization add  */}
        <CssBaseline />
        <Box
          height="100vh"
          width="100%"
          sx={{
            position: "relative",
            display: "flex",
            justifyContent: "center",
            alignItems: "flex-start",
            paddingTop: "5em",
            paddingBottom: "5em",
            background: theme.palette.primary.light,
            overflow: "auto",
            zIndex: 0,
          }}
        >
          <Box
            height="25vh"
            width="100%"
            sx={{
              position: "absolute",
              top: 0,
              backgroundColor: theme.palette.secondary.main,
              zIndex: 1,
            }}
          />
          <Container
            maxWidth="md"
            sx={{
              position: "relative",
              zIndex: 2,
              display: "flex",
              flexDirection: "column",
              alignItems: "flex-start",
              rowGap: "2rem",
              backgroundColor: "white",
              mx: "auto",
              borderRadius: "1rem",
              padding: 2,
            }}
          >
            <UserContextProvider>
              <BrowserRouter>
                <Navbar />
                <Box
                  flexGrow={1}
                  display="flex"
                  justifyContent="center"
                  alignItems="center"
                  mt={1}
                  mb={1}
                  px={3}
                  width="100%"
                >
                  <Routes>
                    <Route path="" element={<IndexPage />} />
                    <Route
                      path="/postcodes/create"
                      element={<CreateUpdatePage mode="Create" />}
                    />
                    <Route
                      path="/suburbs/create"
                      element={<CreateUpdateSuburbsPage mode="Create" />}
                    />
                    <Route
                      path="/postcodes/:id/edit"
                      element={<CreateUpdatePage mode="Edit" />}
                    />
                  </Routes>
                </Box>
              </BrowserRouter>
            </UserContextProvider>
          </Container>
        </Box>
      </ThemeProvider>
    </>
  );
}

export default App;
