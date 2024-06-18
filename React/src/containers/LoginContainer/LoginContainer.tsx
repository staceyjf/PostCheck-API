import { useState, useContext } from "react";
import LoginForm from "../../components/LoginForm/LoginForm";
import { UserContext } from "../../context/userContextProvider";
import { Backdrop, Snackbar, Alert } from "@mui/material";

const LoginContainer = () => {
  const [error, setError] = useState<Error | null>(null);
  const { user, userSignIn } = useContext(UserContext);

  const onSubmit = (username: string, password: string) => {
    userSignIn(username, password).catch((e: any) => {
      setError(new Error("Failed to sign in. Please try again."));
      console.error("ERROR: " + e);
    });
  };

  return (
    <>
      {error && (
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
      {!error && !user && (
        <LoginForm
          placeholderUsername="Username"
          placeholderPassword="Password"
          onSubmit={onSubmit}
        />
      )}
    </>
  );
};

export default LoginContainer;
