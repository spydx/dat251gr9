/**
 * An adaptor for the api.
 *
 * The wrappers should fail only if:
 * - An undocumented response is returned from the server
 * - No response is received
 *
 * The error will be an instance of AxiosError.
 */

import axios from "axios";
import { BACKEND_ROOT } from "./base";
import {
  AuthenticateCredentials,
  AuthenticateResult,
  CreateEventFields,
  CreateRaceFields,
  CreateUserFields,
  Event,
  EventSearchParams,
  Race,
  RaceSearchParams
} from "./types";

////////////////////////////////////////////////////////////////////////////////
// utils

const backend = axios.create({
  baseURL: BACKEND_ROOT,
});

enum HttpStatus {
  OK = 200,
  CREATED = 201,
  BAD_REQUEST = 400,
  NOT_FOUND = 404,
  UNAUTHORIZED = 401,
}

// helper for converting certain failures to success
function resque<T>(status: number, getDefault: () => T): (error: any) => Promise<T> {
  return (error) => {
    if (error.response?.status === status) {
      return Promise.resolve(getDefault());
    } else {
      return Promise.reject(error);
    }
  };
}

////////////////////////////////////////////////////////////////////////////////
// authentication

// an object on success and null if unsuccessful
export function authenticate(credentials: AuthenticateCredentials): Promise<AuthenticateResult> {
  /* back end returns
   * - OK<{token, profile}> (success)
   * - UNAUTHORIZED (invalid credentials)
   */

  return backend
    .post("/auth/login", credentials)
    .then((response) => ({
      token: response.data.token,
      userId: response.data.profile,
    }))
    .catch(resque(HttpStatus.UNAUTHORIZED, () => null));
}

// true if successful and false if unsuccessful
export function createUser(fields: CreateUserFields): Promise<boolean> {
  /* back end returns
   * - BAD_REQUEST (username already registered)
   * - CREATED<junk> (success)
   */

  return backend
    .post("/auth/register", fields, {})
    .then((_response) => true)
    .catch(resque(HttpStatus.BAD_REQUEST, () => false));
}

export function validateToken(token: string): Promise<boolean> {
  /* back end returns
   * - Ok<ValidateTokenResult> (success or failure)
   */

  type ValidateTokenResult = {
    token: string; // junk
    valid: boolean;
    tokenExpirationDate: string; // junk
  };

  return backend
    .post<ValidateTokenResult>("/auth/validateToken", null, {
      params: { token },
    })
    .then((response) => response.data.valid);
}

////////////////////////////////////////////////////////////////////////////////
// events

export function getEvent(eventId: string): Promise<Event | null> {
  /* back end returns
   * - OK<Event>
   * - NOT_FOUND
   */

  return backend
    .get(`/events/${eventId}`)
    .then((response) => response.data)
    .catch(resque(HttpStatus.NOT_FOUND, () => null));
}

// succeeds with a list of events
export function getAllEvents(): Promise<Event[]> {
  /* back end returns
   * - OK<Race>
   * - NOT_FOUND (can't actually happen)
   */

  return backend.get("/events/").then((response) => response.data);
}

export function getAllRaces(eventId: string): Promise<Race | null> {
  /* back end returns
   * - OK<Race[]>
   * - NOT_FOUND (can't actually happen)
   */

  return backend.get(`/events/${eventId}/race`).then((response) => response.data);
}

export function createEvent(fields: CreateEventFields): Promise<boolean> {
  /* back end returns
   * - OK<Event>
   * - BAD_REQUEST (unauthorized OR not an organizer)
   */

  const token = localStorage.getItem("token");
  return backend
    .post("/events/", fields, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((_response) => true)
    .catch(resque(HttpStatus.BAD_REQUEST, () => false));
}

export function createRace(eventId: string, fields: CreateRaceFields): Promise<boolean> {
  /* back end returns
   * - OK<Race>
   * - BAD_REQUEST (unauthorized OR not an organizer)
   */

  const token = localStorage.getItem("token");
  return backend
    .post(`/events/${eventId}/race`, fields, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then((_response) => true)
    .catch(resque(HttpStatus.BAD_REQUEST, () => false));
}

export function getRacesBySearch(params: RaceSearchParams): Promise<Race[]> {
  /* back end returns
   * - OK<Race[]>
   * - NOT_FOUND (means [])
   */

  return backend
    .get("/events/races/search", { params })
    .then((response) => response.data)
    .catch(resque(HttpStatus.NOT_FOUND, () => []));
}

export function getEventsBySearch(params: EventSearchParams): Promise<Event[]> {
  /* back end returns
   * - OK<Race[]>
   * - NOT_FOUND (means [])
   */

  return backend
    .get("/events/search", { params })
    .then((response) => response.data)
    .catch(resque(HttpStatus.NOT_FOUND, () => []));
}
