import { getToken } from "./user-services";

// helper function to add the token
// RequestInit - interface from fetch which represent the options you can
// set for a request
export const fetchWithToken = async (
  url: string,
  options: RequestInit = {}
) => {
  const token = getToken();
  if (!token) throw new Error("Access restricted - please log in.");

  options.headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`,
  };

  return fetch(url, options);
};
