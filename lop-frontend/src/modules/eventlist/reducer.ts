import { RootState, Event } from "../../types";
import {EventListAction, FETCH_LIST} from "./actions";
import { fetchEventList } from "../../services/api";
import { useDispatch } from "react-redux";


export const reducer = async (
   state: RootState["eventList"] = [],
   action: EventListAction
) => {
   switch (action.type) {
      case FETCH_LIST: {
         const eventlist = await fetchEventList()         
         //const newstate: RootState["eventList"] = eventlist
         return state
      }
      default:
         return state
   }
}