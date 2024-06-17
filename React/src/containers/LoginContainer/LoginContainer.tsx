import { useState } from "react";
import LoginForm from "../../components/LoginForm/LoginForm";
import { User } from "../../services/api-responses.interfaces";
import { Box, Skeleton, Backdrop, Snackbar } from "@mui/material";
import Alert from "@mui/material/Alert";

const LoginContainer = () => {
  const [user, setUser] = useState<User | null>(null);
  const [error, setError] = useState<Error | null>(null);

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
      {!error && (
        <LoginForm
          placeholderUsername="Username"
          placeholderPassword="Password"
          setUser={setUser}
        />
      )}
    </>
  );
};

export default LoginContainer;
