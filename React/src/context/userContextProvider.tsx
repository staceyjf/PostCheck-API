import { createContext, useState } from "react";
import { UserResponse } from "../services/api-responses.interfaces";
import { signIn, getToken } from "../services/user-services";

interface UserContext {
  user: UserResponse | null;
  setUser: (user: UserResponse | null) => void;
  userSignIn: (username: string, password: string) => Promise<void>;
  signOut: () => void;
}

// default is provided when the context is consumed without a provider
const defaults: UserContext = {
  user: null,
  setUser: () => {
    throw new Error("setUser must be used within a UserContextProvider");
  },
  userSignIn: async () => {
    throw new Error("signIn must be used within a UserContextProvider");
  },
  signOut: () => {
    throw new Error("signOut must be used within a UserContextProvider");
  },
};

export const UserContext = createContext(defaults);

interface UserContextProviderProps {
  // allows for all valid types including numbers, strings etc
  children: React.ReactNode;
}

const UserContextProvider = ({ children }: UserContextProviderProps) => {
  const [user, setUser] = useState<UserResponse | null>(null);

  const userSignIn = async (login: string, password: string) => {
    try {
      const response = await signIn(login, password);
      const token = response ? response.accessToken : null;
      if (!token) {
        console.error("ERROR: Received null token.");
        throw new Error(
          "There was an issue with signing in. Please try again."
        );
      }
      console.log(token);
      localStorage.setItem("token", token);
      getUser();
    } catch (error) {
      console.error("ERROR: " + error);
      throw new Error("There was an issue with signing in. Please try again.");
    }
  };

  const signOut = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  const getUser = () => {
    const token: string | null = getToken();
    // extract the user info from the token payload
    if (token) {
      const payload = JSON.parse(atob(token.split(".")[1]));
      const user: UserResponse = {
        username: payload.username,
        role: payload.role,
      };
      setUser(user);
      return;
    }
    return null;
  };

  return (
    <UserContext.Provider
      value={{
        user,
        setUser,
        userSignIn,
        signOut,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export default UserContextProvider;
