import { Event } from '../types'

export const backendRoot: string = process.env.REACT_APP_BACKEND_ENDPOINT || "http://localhost:8080/api/"

export const EVENTSPATH: string = "events/"

/*
const APPLICATIONJSON : string = "application/json"
const CONTENTTYPE : string = "Content-Type"
const HTTP_POST: string = "POST"
const HTTP_GET: string = "GET"
const CORS_ACAO = 'access-control-allow-origin' // *
const CORS_ACAC = 'access-control-allow-credentials' // true
*/

export const authFetcher = (url:string, token:string) => fetch(`${backendRoot}${url}`, {
      headers: {"Autorization": "Bearer " + token}
   }).then( resp => resp.json());

export const fetcher =  (url:string) => fetch(`${backendRoot}${url}`)
      .then( resp => resp.json());
