import { Box, useTheme } from "@mui/material";
import { NavLink } from "react-router-dom";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import logo from "../../assets/post.png";
import LoginContainer from "../../containers/LoginContainer/LoginContainer";

const Navbar = () => {
  const theme = useTheme();

  return (
    <nav style={{ width: "100%" }}>
      <Box
        display="flex"
        width="100%"
        justifyContent="space-between"
        alignItems="center"
        mt={1}
        mb={1}
        px={3}
      >
        <NavLink to="/">
          <Box display="flex" justifyContent="center" alignItems="center">
            <img src={logo} alt="PostCheck Logo" style={{ width: "150px" }} />
          </Box>
        </NavLink>
        <LoginContainer />
        <NavLink to="/new">
          <AddCircleIcon
            aria-label="Add a postcode"
            style={{ fontSize: 40, color: theme.palette.primary.main }}
          />{" "}
        </NavLink>
      </Box>
    </nav>
  );
};

export default Navbar;
