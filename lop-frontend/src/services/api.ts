import {EventListData} from "../types";

const backendRoot: string = process.env.REACT_APP_BACKEND_ENDPOINT || "http://localhost:8080/api/"
const EVENTSPATH:string = "events/"

const APPLICATIONJSON : string = "application/json"
const CONTENTTYPE : string = "Content-Type"
const HTTP_POST: string = "POST"
const HTTP_GET: string = "GET"
const CORS_ACAO = 'access-control-allow-origin' // *
const CORS_ACAC = 'access-control-allow-credentials' // true

export const fetchEventList = () => {
   console.log("Url: " + backendRoot+EVENTSPATH);
   return fetch(backendRoot+EVENTSPATH, { headers: { 
      Accept: APPLICATIONJSON,
      CONTENTTYPE: APPLICATIONJSON,
      CORS_ACAO: '*'
   }}).then(
      (response) => {
         return response.json() as Promise<EventListData>
      }
   )
}