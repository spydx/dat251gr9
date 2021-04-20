declare module "*.json" {
  const value: any;
  export default value;
}

export type RootState = {
  eventList: Event[];
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

export type Contact = {
  name: string;
  email: string;
  phone: string;
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

export type Location = {
  id: string;
  county: string;
  municipality: string;
  place: string;
  latitude: string;
  longitude: string;
};

export type OrganizerProfile = {
  organizerName: string,
  address: string,
}