export const BACKEND_ROOT: string = process.env.REACT_APP_BACKEND_ENDPOINT || "http://localhost:8080/api";

/** collection of all API paths */
export const ApiPath = {
  Login: "/auth/login/",
  Events: "/events/",
  Event: (id: string) => "/events/" + id,
};

/*
const APPLICATIONJSON : string = "application/json"
const CONTENTTYPE : string = "Content-Type"
const HTTP_POST: string = "POST"
const HTTP_GET: string = "GET"
const CORS_ACAO = 'access-control-allow-origin' // *
const CORS_ACAC = 'access-control-allow-credentials' // true
*/

export const authDoGet = (path: string): Promise<any> => {
  const token = localStorage.getItem("token");
  return fetch(`${BACKEND_ROOT}${path}`, {
    headers: { Autorization: "Bearer " + token },
  }).then((resp) => resp.json());
}

export const doGet = (path: string): Promise<any> => {
  return fetch(`${BACKEND_ROOT}${path}`).then((resp) => resp.json());
}

// TODO: error handling
export const doPost = (path: string, requestBody: any): Promise<any> => {
  return fetch(`${BACKEND_ROOT}${path}`, {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(requestBody),
  }).then((resp) => resp.json());
}
