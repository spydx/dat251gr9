declare module "*.json" {
   const value: any;
   export default value;
}

export type RootState = {
   eventList: Event[]
}


export type Event = {
   id: string
   name: string
   organizer?: string
   eventstart: string
   generalinfo: string
   contacts: Contact[]
   races: Race[]
   location: Location
}

export type Contact = {
   name: string
   email: string
   phone: string
}

export type Race = {
   id: string
   distance: number
   startlocation: string
   starttime?: string
   info?: string
   elevation?: number
   vertical?: boolean
   children?: boolean
   womenonly?: boolean
   relay?: boolean
   multisport?: boolean
   obstaclerun?: boolean

}

export type Location = {
   id: number, 
   county: string,
   municipality: string
   place: string
   latitude: string
   longitude: string

}