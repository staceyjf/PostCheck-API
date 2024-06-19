import { baseUrl } from "./api-config";
import { SuburbResponse, SuburbForm } from "./api-responses.interfaces";
import { fetchWithToken } from "./utils";

export const getAllSuburbs = async (): Promise<SuburbResponse[]> => {
  const response: Response = await fetch(baseUrl + "/suburbs");
  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error("Failed to fetch all Suburbs. Please try again later");
  }

  return await response.json();
};

export const createSuburb = async (
  data: SuburbForm
): Promise<SuburbResponse> => {
  const response = await fetchWithToken(baseUrl + "/suburbs", {
    method: "POST",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });
  console.log(response);
  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error(
      "Oops, something went wrong while trying to create a new Suburb. Please try again."
    );
  }

  return await response.json();
};

export const getSuburbById = async (id: number): Promise<SuburbResponse> => {
  const response = await fetchWithToken(`${baseUrl}/suburbs/${id}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error(
      `Failed to fetch Suburbs with id: ${id}. Please try again later`
    );
  }

  return await response.json();
};

export const updateSuburbById = async (
  id: number,
  data: SuburbForm
): Promise<SuburbResponse> => {
  const response = await fetchWithToken(`${baseUrl}/suburbs/${id}`, {
    method: "PATCH",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });
  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error(
      `Oops, something went wrong while trying to update Suburbs with id: ${id}. Please try again.`
    );
  }
  return await response.json();
};
