export const BACKEND_ROOT: string = process.env.REACT_APP_BACKEND_ENDPOINT || "http://localhost:8080/api";

/** collection of all API paths */
export const ApiPath = {
  Login: "/auth/login/",
  Events: "/events/",
  Event: (id: string) => "/events/" + id,
};

// Some error classes

export class ResponseError extends Error {
  public status: number;

  constructor(status: number) {
    super("");
    this.status = status;
  }
}

export class NetworkError extends Error {
  constructor() {
    super("");
  }
}

//

function robustFetch(input: RequestInfo, init?: RequestInit) {
  const handleNetworkError = (whatsThis: any) => {
    console.log("got", whatsThis);
    return Promise.reject(new NetworkError());
  };
  const handleResponse = (response: Response) => {
    if (response.ok) {
      return response.json().then((data) => ({ data, status: response.status }));
    } else {
      return Promise.reject(new ResponseError(response.status));
    }
  };
  return fetch(input, init).catch(handleNetworkError).then(handleResponse);
}

//

export const authDoGet = async (path: string): Promise<any> => {
  const token = localStorage.getItem("token");
  const resp = await fetch(`${BACKEND_ROOT}${path}`, {
    headers: { Autorization: "Bearer " + token },
  });
  return resp.json();
};

export const doGet = async (path: string): Promise<any> => {
  const resp = await fetch(`${BACKEND_ROOT}${path}`);
  return resp.json();
};

export const doPost = (path: string, requestBody: any): Promise<any> => {
  return robustFetch(`${BACKEND_ROOT}${path}`, {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(requestBody),
  });
};
