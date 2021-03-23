import { RootState , Event, EventList } from './types';
import data from "./eventdata.json";

const eventData: Event = data;

const eventList: EventList = {
   events: [eventData, eventData]
}

export const initalState : RootState = {
   eventList: eventList
}

export const rootReducer = (state: RootState = initalState) => {
   return state
}