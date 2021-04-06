export const backendRoot: string =
  process.env.REACT_APP_BACKEND_ENDPOINT || "http://localhost:8080/api";

export enum ApiPath {
  Login = "/auth/login/",
  Events = "/events/",
}

/*
const APPLICATIONJSON : string = "application/json"
const CONTENTTYPE : string = "Content-Type"
const HTTP_POST: string = "POST"
const HTTP_GET: string = "GET"
const CORS_ACAO = 'access-control-allow-origin' // *
const CORS_ACAC = 'access-control-allow-credentials' // true
*/

export function authDoGet(endPoint: ApiPath): Promise<any> {
  const token = localStorage.getItem("token");
  return fetch(`${backendRoot}${endPoint}`, {
    headers: { Autorization: "Bearer " + token },
  }).then((resp) => resp.json());
}

export function doGet(endPoint: ApiPath): Promise<any> {
  return fetch(`${backendRoot}${endPoint}`).then((resp) => resp.json());
}

// TODO: error handling
export function doPost(endPoint: ApiPath, requestBody: any): Promise<any> {
  return fetch(`${backendRoot}${endPoint}`, {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(requestBody),
  }).then((resp) => resp.json());
}
