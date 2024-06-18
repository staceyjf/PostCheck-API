import { useState } from "react";
import { Alert, Snackbar } from "@mui/material";

// define the props
interface LoginFormProps {
  placeholderUsername: string;
  placeholderPassword: string;
  onSubmit: (username: string, password: string) => void;
}

const LoginForm = ({
  placeholderUsername,
  placeholderPassword,
  onSubmit,
}: LoginFormProps) => {
  const [error, setError] = useState<Error | null>(null);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.currentTarget;
    const username = new FormData(form).get("username") as string;
    const password = new FormData(form).get("psw") as string;

    if (!username) {
      setError(new Error("Username is missing"));
      return;
    }

    if (!password) {
      setError(new Error("Password is missing"));
      return;
    }

    onSubmit(username, password);
    form.reset();
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
        <form onSubmit={handleSubmit}>
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
