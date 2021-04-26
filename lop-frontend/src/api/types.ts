export type AuthenticateCredentials = {
  email: string;
  password: string;
};

export type AuthenticateResult = { token: string; userId: string } | null;

export type CreateUserFields = {
  address: string;
  birthdate: string;
  city: string;
  email: string;
  firstname: string;
  lastname: string;
  password: string;
};

export type OrganizerProfile = {
  organizerName: string;
  address: string;
};

export type Contact = {
  name: string;
  email: string;
  phone: string;
};

export type Location = {
  id: string;
  county: string;
  municipality: string;
  place: string;
  latitude: string;
  longitude: string;
};

export type Event = {
  id: string;
  name: string;
  organizer?: OrganizerProfile;
  eventStart: string;
  generalInfo: string;
  contacts: Contact[];
  races: Race[];
  location: Location;
};

export type Race = {
  id: string;
  distance: number;
  startlocation: string;
  startTime?: string;
  info?: string;
  elevation?: number;
  vertical?: boolean;
  children?: boolean;
  womenOnly?: boolean;
  relay?: boolean;
  multiSport?: boolean;
  obstacleRun?: boolean;
  hillRun?: boolean;
};

export type CreateEventFields = {
  name: string;
  eventStart: string;
  generalInfo: string;
  location: string;
};

export type CreateRaceFields = {
  distance: number;
  startTime: Date;
  elevation: number;
  hillRun: boolean;
  children: boolean;
  womenOnly: boolean;
  relay: boolean;
  multiSport: boolean;
  obstacleRun: boolean;
  info: string;
  participants: number;
};

enum SortBy {
  DISTANCE_DESC = "DISTANCE_DESC",
  DISTANCE_ASC = "DISTANCE_ASC",
  ELEVATION_DESC = "ELEVATION_DESC",
  ELEVATION_ASC = "ELEVATION_ASC",
  PARTICIPANTS_DESC = "PARTICIPANTS_DESC",
  PARTICIPANTS_ASC = "PARTICIPANTS_ASC",
}

export type EventSearchParams = {
  term?: string;
  geoLocName?: string;
  sort?: SortBy;
};

export type RaceSearchParams = {
  hillRun?: boolean;
  children?: boolean;
  womenOnly?: boolean;
  relay?: boolean;
  multiSport?: boolean;
  obstacleRun?: boolean;
  sort?: SortBy;
};
