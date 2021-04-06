export const BACKEND_ROOT: string =
  process.env.REACT_APP_BACKEND_ENDPOINT || "http://localhost:8080/api";

// TODO: find a type that can express dynamic paths. Should we go back to using
// strings ?
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

export function authDoGet(path: ApiPath): Promise<any> {
  const token = localStorage.getItem("token");
  return fetch(`${BACKEND_ROOT}${path}`, {
    headers: { Autorization: "Bearer " + token },
  }).then((resp) => resp.json());
}

export function doGet(path: ApiPath): Promise<any> {
  return fetch(`${BACKEND_ROOT}${path}`).then((resp) => resp.json());
}

// TODO: error handling
export function doPost(path: ApiPath, requestBody: any): Promise<any> {
  return fetch(`${BACKEND_ROOT}${path}`, {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(requestBody),
  }).then((resp) => resp.json());
}
