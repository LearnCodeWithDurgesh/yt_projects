import type User from "./User";

export default interface LoginResponseData {
  accessToken: string;
  user: User;
  refreshToken: string;
  expiresIn: number;
}
