import { baseUrl } from "./api-config";
import { PostCodeResponse, PostCodeForm } from "./api-responses.interfaces";
import { fetchWithToken } from "./utils";

export const getAllPostCodes = async (): Promise<PostCodeResponse[]> => {
  const response: Response = await fetch(baseUrl + "/postcodes");
  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error("Failed to fetch all PostCodes. Please try again later");
  }

  return await response.json();
};

export const findPostCodesBySuburb = async (
  queryTerm: string
): Promise<PostCodeResponse[]> => {
  const response: Response = await fetch(
    `${baseUrl}/postcodes/postcodes?suburb=${queryTerm}`
  );

  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error(
      "Failed to fetch the associated postcode. Please try again"
    );
  }

  return await response.json();
};

export const findSuburbsByPostCode = async (
  queryTerm: string
): Promise<PostCodeResponse[]> => {
  const response: Response = await fetch(
    `${baseUrl}/postcodes/suburbs?postcode=${queryTerm}`
  );

  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error("Failed to fetch the associated suburb. Please try again");
  }

  return await response.json();
};

export const createPostCode = async (
  data: PostCodeForm
): Promise<PostCodeResponse> => {
  const response = await fetchWithToken(baseUrl + "/postcodes", {
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
      "Oops, something went wrong while trying to create a new PostCode. Please try again."
    );
  }

  return await response.json();
};

export const getPostCodebyId = async (
  id: number
): Promise<PostCodeResponse> => {
  const response = await fetchWithToken(`${baseUrl}/postcodes/${id}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) {
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error(
      `Failed to fetch PostCode with id: ${id}. Please try again later`
    );
  }

  return await response.json();
};

export const updatePostCodeById = async (
  id: number,
  data: PostCodeForm
): Promise<PostCodeResponse> => {
  const response = await fetchWithToken(`${baseUrl}/postcodes/${id}`, {
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
      `Oops, something went wrong while trying to update Postcode with id: ${id}. Please try again.`
    );
  }
  return await response.json();
};

export const deleteById = async (id: number) => {
  const response = await fetchWithToken(`${baseUrl}/postcodes/${id}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });
  if (response.status !== 204) {
    // Spring is sending back a 204 No Content HTTP request
    const errorData = await response.json();
    console.error("Error:", errorData.errorMessages);
    throw new Error(
      `Oops, something went wrong while trying to delete Postcode with id: ${id}. Please try again.`
    );
  }
};
