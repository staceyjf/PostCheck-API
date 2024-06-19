export interface PostCodeResponse {
  id: number;
  postcode: string;
  associatedSuburbs: SuburbResponse[];
}

export interface PostCodeForm {
  id?: number;
  postcode?: string;
  associatedSuburbs?: SuburbResponse[];
}

export interface SuburbResponse {
  id: number;
  name: string;
  state: string;
}

export interface SuburbForm {
  id?: number;
  name?: string;
  state?: string;
}

export interface UserResponse {
  username: string;
  password?: string;
  role?: string;
}
