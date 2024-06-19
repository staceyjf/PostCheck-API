import { BrowserRouter, Route, Routes } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import "dayjs/locale/en-gb";
import theme from "./styling/theme";
import IndexPage from "./pages/IndexPage/IndexPage";
import "./App.scss";
import { Container, Box } from "@mui/material";
import Navbar from "./components/Navbar/Navbar";
import UserContextProvider from "./context/userContextProvider";
import CreateUpdatePage from "./pages/CreateUpdatePage/CreateUpdatePage";

function App() {
  return (
    <>
      <ThemeProvider theme={theme}>
        {/* Normalization add  */}
        <CssBaseline />
        <Box
          height="100%"
          width="100%"
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "flex-start",
            paddingTop: "5em",
            background: theme.palette.primary.light,
          }}
        >
          <Container
            maxWidth="md"
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "flex-start",
              rowGap: "2rem",
              backgroundColor: "white",
              mx: "auto",
              borderRadius: "1rem",
              padding: 2,
              overflow: "auto",
              height: "100vh",
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
