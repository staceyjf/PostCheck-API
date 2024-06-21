import { createTheme, responsiveFontSizes } from "@mui/material";

// adding a new custom colour variable
declare module "@mui/material/styles" {
  interface Theme {
    highlightOne: { main: string };
  }
  interface ThemeOptions {
    highlightOne?: { main?: string };
  }
}

let theme = createTheme({
  palette: {
    primary: {
      main: "#dc1928",
      dark: "#382f2d",
      light: "#f3f1ee",
    },
    secondary: {
      main: "#1064a3",
    },
  },
  typography: {
    fontSize: 12,
    fontFamily: ["Helvetica", "Arial", "sans-serif"].join(","),
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: {
          // Custom scrollbar styles for WebKit browsers
          "&::-webkit-scrollbar": {
            width: "8px",
            borderRadius: "4px", // Rounded corners
          },
          "&::-webkit-scrollbar-track": {
            boxShadow: "inset 0 0 6px rgba(0,0,0,0.3)",
          },
          "&::-webkit-scrollbar-thumb": {
            backgroundColor: "rgba(16, 100, 163, 0.5)", // Use secondary.main color with reduced opacity
            outline: "1px solid slategrey",
            "&:hover": {
              backgroundColor: "#1064a3", // Use secondary.main color
            },
          },
          // Custom scrollbar styles for Firefox
          scrollbarWidth: "thin",
          scrollbarColor: "rgba(16, 100, 163, 0.5) lightgrey", // Use secondary.main color with reduced opacity for the thumb
        },
      },
    },
    MuiFormLabel: {
      styleOverrides: {
        root: {
          fontSize: "1rem",
          fontWeight: 400,
        },
      },
    },
    MuiInput: {
      styleOverrides: {
        root: {
          fontSize: "0.8rem",
          fontWeight: 500,
        },
      },
    },
    MuiMenuItem: {
      styleOverrides: {
        root: {
          fontSize: "0.8rem",
          fontWeight: 400,
        },
      },
    },
    MuiSelect: {
      styleOverrides: {
        root: {
          fontSize: "0.8rem",
          fontWeight: 500,
        },
      },
    },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          fontSize: "1rem",
          fontWeight: 500,
        },
      },
    },
    MuiTableCell: {
      styleOverrides: {
        root: {
          fontSize: "0.85rem",
        },
      },
    },
  },
});

// adjusted based on the predefined breakpoints
theme = responsiveFontSizes(theme);

export default theme;
