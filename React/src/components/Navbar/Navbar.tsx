import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, useTheme, IconButton, Menu, MenuItem } from "@mui/material";
import { NavLink } from "react-router-dom";
import { UserContext } from "../../context/userContextProvider";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import logo from "../../assets/post.png";
import LoginContainer from "../../containers/LoginContainer/LoginContainer";

const Navbar = () => {
  const theme = useTheme();
  const navigate = useNavigate();
  const { user, signOut } = useContext(UserContext);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleMenuItm = (clickDestination: string) => {
    if (clickDestination) {
      navigate(`${clickDestination}`);
    }
    handleClose();
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
        <NavLink to="/" data-testid="navbar-logo">
          <Box display="flex" justifyContent="center" alignItems="center">
            <img src={logo} alt="PostCheck Logo" style={{ width: "150px" }} />
          </Box>
        </NavLink>
        <IconButton onClick={handleMenu}>
          <AccountCircleIcon
            aria-label="Login"
            aria-haspopup="true"
            style={{ fontSize: 40, color: theme.palette.primary.dark }}
            data-testid="account-profile"
          />
        </IconButton>
        <Menu
          id="menu"
          anchorEl={anchorEl}
          anchorOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          // keepMounted
          transformOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          open={Boolean(anchorEl)}
          onClose={handleClose}
          data-testid="menu"
        >
          <MenuItem disableRipple data-testid="login-form">
            <LoginContainer />
          </MenuItem>
          {user &&
            [
              {
                text: "Add a postcode",
                onClick: () => handleMenuItm("/postcodes/create"),
                testId: "add-postcode-item",
              },
              {
                text: "Add a suburb",
                onClick: () => handleMenuItm("/suburbs/create"),
                testId: "add-suburb-item",
              },
              { text: "Register a new user", onClick: handleClose },
              { text: "Logout", onClick: signOut },
            ].map((item, index) => (
              <MenuItem key={index} onClick={item.onClick}>
                {item.text}
              </MenuItem>
            ))}
        </Menu>
      </Box>
    </nav>
  );
};

export default Navbar;
