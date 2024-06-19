import { baseUrl } from "./api-config";
import { SuburbResponse } from "./api-responses.interfaces";

export const getAllSuburbs = async (): Promise<SuburbResponse[]> => {
  const response: Response = await fetch(baseUrl + "/suburbs");
  if (!response.ok) {
    console.warn(response.status);
    throw new Error("Failed to fetch all Suburbs. Please try again later");
  }

  return await response.json();
};
