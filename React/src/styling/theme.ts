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
    },
    secondary: {
      main: "#f3f1ee",
    },
  },
  highlightOne: {
    main: "#1064a3",
  },
  typography: {
    fontSize: 12,
    fontFamily: ["Helvetica", "Arial", "sans-serif"].join(","),
  },
  components: {
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
