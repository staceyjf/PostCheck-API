import { BrowserRouter, Route, Routes } from "react-router-dom";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import "dayjs/locale/en-gb";
import theme from "./styling/theme";
import Index from "./pages/Index/Index";
import "./App.scss";
import { Container, Box } from "@mui/material";
import Navbar from "./components/Navbar/Navbar";

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
              rowGap: "0.25rem",
              backgroundColor: "white",
              mx: "auto",
              borderRadius: "1rem",
              padding: 2,
            }}
          >
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
                  <Route path="" element={<Index />} />
                  {/* <Route path="/new" element={<AddTodoPage />} />
                      <Route
                        path="/:id/edit"
                        element={<UpdateTodoPage />}
                      /> */}
                </Routes>
              </Box>
            </BrowserRouter>
          </Container>
        </Box>
      </ThemeProvider>
    </>
  );
}

export default App;
