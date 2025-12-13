import useAuth from "@/auth/store";
import { refreshToken } from "@/services/AuthService";
import axios from "axios";
import { is } from "date-fns/locale";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8083/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
  timeout: 10000,
});

//every request: before
apiClient.interceptors.request.use((config) => {
  const accessToken = useAuth.getState().accessToken;
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
});

let isRefreshing = false;
let pending: any[] = [];

function queueRequest(cb: any) {
  pending.push(cb);
}

function resolveQueue(newToken: string) {
  pending.forEach((cb) => cb(newToken));
  pending = [];
}

// response interceptors
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const is401 = error.response.status === 401;
    const original = error.config;
    console.log(original);
    console.log("original retry: ", original._retry);
    if (!is401 || original._retry) {
      //message:
      return Promise.reject(error);
    }

    original._retry = true;
    //we will try to refresh the token:
    if (isRefreshing) {
      console.log("added to queue");
      return new Promise((resolve, reject) => {
        queueRequest((newToken: string) => {
          if (!newToken) return reject();
          original.headers.Authorization = `Bearer ${newToken}`;
          resolve(apiClient(original));
        });
      });
    }

    //start refresh
    isRefreshing = true;

    try {
      console.log("start refreshing...");
      const loginResponse = await refreshToken();
      const newToken = loginResponse.accessToken;
      if (!newToken) throw new Error("no access token received");
      useAuth
        .getState()
        .changeLocalLoginData(
          loginResponse.accessToken,
          loginResponse.user,
          true
        );
      //
      resolveQueue(newToken);
      original.headers.Authorization = `Bearer ${newToken}`;
      return apiClient(original);
    } catch (error) {
      resolveQueue("null");
      useAuth.getState().logout();
      return Promise.reject(error);
    } finally {
      isRefreshing = false;
    }
  }
);

export default apiClient;
