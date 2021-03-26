import { RootState, EventListData, Event } from "../../types";
import {EventListAction, FETCH_LIST} from "./actions";
import data from "../../eventdata.json";
import data2 from "../../eventdata2.json"
import { fetchEventList } from "../../services/api";
import { useDispatch } from "react-redux";

const eventData: Event = data;
const eventData2: Event = data2;

const eventList: EventListData = {
   events: [eventData, eventData2]
}


export const reducer = async (
   state: RootState["eventList"] = {},
   action: EventListAction
) => {
   switch (action.type) {
      case FETCH_LIST: {
         const eventlist = await fetchEventList()         
         const newstate: RootState["eventList"] = eventlist
         return newstate
      }
      default:
         return state
   }
}