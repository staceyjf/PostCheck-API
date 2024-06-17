import { useState } from "react";
import { User } from "../../services/api-responses.interfaces";
import { Alert, Backdrop, Snackbar } from "@mui/material";

// define the props
interface LoginFormProps {
  placeholderUsername: string;
  placeholderPassword: string;
  setUser: (user: User | null) => void;
}

const LoginForm = ({
  placeholderUsername,
  placeholderPassword,
  setUser,
}: LoginFormProps) => {
  const [error, setError] = useState<Error | null>(null);

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.currentTarget;
    const username = new FormData(form).get("username") as string;
    const password = new FormData(form).get("psw") as string;

    if (!username) {
      setError(new Error("Username is missing"));
      return;
    }

    if (!password) {
      setError(new Error("password is missing"));
      return;
    }

    setUser({ username, password });
  };

  return (
    <>
      {error && (
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
      )}
      {!error && (
        <form onSubmit={onSubmit}>
          <input
            type="text"
            placeholder={placeholderUsername}
            name="username"
          />
          <input type="password" placeholder={placeholderPassword} name="psw" />
          <button>Login</button>
        </form>
      )}
    </>
  );
};

export default LoginForm;
