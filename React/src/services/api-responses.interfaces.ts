export interface PostCodeResponse {
  id: number;
  postcode: string;
  associatedSuburbs: SuburbResponse[];
}

export interface SuburbResponse {
  id: number;
  name: string;
  state: string;
}

export interface UserResponse {
  username: string;
  password?: string;
  role?: string;
}
