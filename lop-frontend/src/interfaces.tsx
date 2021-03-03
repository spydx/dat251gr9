export interface RaceListInterface {
   races: RaceEventInterface[]
}

export interface RaceEventInterface {
   name: string,
   eventday: string,
   generalinfo: string,
   races: DistancesInterfaces[],
}

export interface DistancesInterfaces {
   distance: string,
}