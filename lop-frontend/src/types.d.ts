declare module "*.json" {
   const value: any;
   export default value;
}


export type RootState = {
   eventList: EventList
}

export type EventList = {
   events: Event[]
}
export type EventListProps = {
   id: number
   events: Events[]
}

export type EventCardProps = {
   id: number 
   event: Event
}

export type Event = {
   id: string
   name: string
   eventday: string
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
   id: number
   distance: number
   startlocation: string
   starttime?: string
   info?: string
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