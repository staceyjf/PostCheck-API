import { baseUrl } from "./api-config";
import { PostCodeResponse, PostCodeForm } from "./api-responses.interfaces";
import { getToken } from "./user-services";

export const getAllPostCodes = async (): Promise<PostCodeResponse[]> => {
  const response: Response = await fetch(baseUrl + "/postcodes");
  if (!response.ok) {
    console.warn(response.status);
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
    console.warn(response.status);
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
    console.warn(response.status);
    throw new Error("Failed to fetch the associated suburb. Please try again");
  }

  return await response.json();
};

// helper function to add the token
// RequestInit - interface from fetch which represent the options you can
// set for a request
const fetchWithToken = async (url: string, options: RequestInit = {}) => {
  const token = getToken();
  if (!token) throw new Error("Access restricted - please log in.");

  options.headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`,
  };

  return fetch(url, options);
};

// export const createPostCode = async (data: PostCodeFormData): Promise<PostCodeResponse> => {
//   const response = await fetch(baseUrl + "/postcodes", {
//     method: "POST",
//     body: JSON.stringify(data),
//     headers: {
//       "Content-Type": "application/json",
//     },
//   });
//   if (!response.ok
//     console.warn(response.status);
//     throw new Error(
//       "Oops, something went wrong while trying to create a new Todo. Please try again."
//     );
//   }

//   return await response.json();

// };

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
    console.warn(response.status);
    throw new Error(
      `Failed to fetch PostCode with id: ${id}. Please try again later`
    );
  }

  return await response.json();
};

export const updatePostById = async (
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
    console.warn(response.status);
    throw new Error(
      `Oops, something went wrong while trying to update Postcode with id: ${id}. Please try again.`
    );
  }
  return await response.json();
};
