import { useContext, useState } from "react";
import { Box, useTheme, IconButton, Menu, MenuItem } from "@mui/material";
import { NavLink } from "react-router-dom";
import { UserContext } from "../../context/userContextProvider";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import logo from "../../assets/post.png";
import LoginContainer from "../../containers/LoginContainer/LoginContainer";
import styles from "./Navbar.module.scss";

const Navbar = () => {
  const theme = useTheme();
  const { user, signOut } = useContext(UserContext);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

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
        <IconButton onClick={handleMenu}>
          <AccountCircleIcon
            aria-label="Login"
            aria-haspopup="true"
            style={{ fontSize: 40, color: theme.palette.primary.dark }}
          />
        </IconButton>
        <Menu
          id="signin"
          anchorEl={anchorEl}
          anchorOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          keepMounted
          transformOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          open={Boolean(anchorEl)}
          onClose={handleClose}
        >
          <MenuItem disableRipple>
            <LoginContainer />
          </MenuItem>
          {user && (
            <>
              <MenuItem onClick={handleClose}>Add a postcode</MenuItem>
              <MenuItem onClick={handleClose}>Add a suburb</MenuItem>
              <MenuItem onClick={handleClose}>Register a new user</MenuItem>
              <MenuItem onClick={signOut}>Logout</MenuItem>
            </>
          )}
        </Menu>
        {/* 
        {user && (
          <NavLink to="/new">
            <AddCircleIcon
              aria-label="Add a postcode"
              style={{ fontSize: 40, color: theme.palette.primary.main }}
            />{" "}
          </NavLink>
        )} */}
      </Box>
    </nav>
  );
};

export default Navbar;
