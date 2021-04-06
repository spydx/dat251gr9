import { RootState } from "../../types";
import {EventListAction, FETCH_LIST} from "./actions";
//import { fetchEventList } from "../../services/api";


export const reducer = async (
   state: RootState["eventList"] = [],
   action: EventListAction
) => {
   switch (action.type) {
      case FETCH_LIST: {
         //const eventlist = await fetchEventList()         
         //const newstate: RootState["eventList"] = eventlist
         return state
      }
      default:
         return state
   }
}