import { baseUrl } from "./api-config";
import { PostCodeResponse } from "./api-responses.interfaces";

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

// export const updateTodoById = async (
//   id: number,
//   data: TodoFormData
// ): Promise<PostCodeResponse> => {
//   const response = await fetch(`${baseUrl}/postcodes/${id}`, {
//     method: "PATCH",
//     body: JSON.stringify(data),
//     headers: {
//       "Content-Type": "application/json",
//     },
//   });
//   if (!response.ok) {
//     console.warn(response.status);
//     throw new Error(
//       `Oops, something went wrong while trying to update Todo with id: ${id}. Please try again.`
//     );
//   }
//  return await response.json();
// };
