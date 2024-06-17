import { createContext, useEffect, useState } from "react";
import { UserResponse } from "../services/api-responses.interfaces";

interface UserContext {
  user: UserResponse | null;
  setUser: React.Dispatch<React.SetStateAction<UserResponse | null>>;
}

// default is provided when the context is consumed without a provider
const defaults: UserContext = {
  user: null,
  setUser: () => {
    throw new Error("setUser must be used within a UserContextProvider");
  },
};

export const UserContext = createContext(defaults);

interface UserContextProviderProps {
  // allows for all valid types including numbers, strings etc
  children: React.ReactNode;
}

const UserContextProvider = ({ children }: UserContextProviderProps) => {
  const [user, setUser] = useState<UserResponse | null>(null);

  useEffect(() => {
    getUser()
      .then((data) => setUser(data))
      .catch((error) => console.warn(error));
  }, []);

  return (
    <UserContext.Provider value={{ user, setUser }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContextProvider;
