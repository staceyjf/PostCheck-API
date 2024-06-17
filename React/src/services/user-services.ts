import { baseUrl } from "./api-config";
import { UserResponse } from "./api-responses.interfaces";

export const getUser = async (): Promise<UserResponse> => {
  const response: Response = await fetch(baseUrl);
  if (!response.ok) {
    console.warn(response.status);
    throw new Error("Failed to fetch user. Please try again later");
  }

  return await response.json();
};
