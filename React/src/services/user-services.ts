import { baseUrl } from "./api-config";
import { UserResponse } from "./api-responses.interfaces";

// export const getUser = async (id: number): Promise<UserResponse> => {
//   const response: Response = await fetch(`${baseUrl}/auth/${id}`);
//   if (!response.ok) {
//     console.warn(response.status);
//     throw new Error("Failed to fetch user. Please try again later");
//   }

//   return await response.json();
// };

export const signIn = async (
  login: string,
  password: string
): Promise<string> => {
  const response: Response = await fetch(`${baseUrl}/auth/signin`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ login, password }),
  });

  if (!response.ok) {
    console.warn(response.status);
    throw new Error("Failed to sign in. Please try again later");
  }

  return await response.json(); // returns the token
};
