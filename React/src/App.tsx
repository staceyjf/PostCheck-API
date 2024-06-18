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
import UpdatePage from "./pages/UpdatePage/UpdatePage";

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
            display: "flex",
            justifyContent: "center",
            alignItems: "flex-start",
            paddingTop: "5em",
            background: theme.palette.secondary.main,
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
                    {/* <Route path="/new" element={<AddTodoPage />} /> */}
                    <Route
                      path="/postcodes/:id/edit"
                      element={<UpdatePage />}
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
