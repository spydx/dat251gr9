import { 
   configureStore, 
   getDefaultMiddleware,
   combineReducers
} from "@reduxjs/toolkit";
import { reducer as eventList } from "./modules/eventlist/reducer";
import {logger} from "redux-logger";


const customizedMiddleware = getDefaultMiddleware({
   serializableCheck: false
})

const middleware = [...customizedMiddleware, logger]

export const store = configureStore({ reducer: combineReducers({
   eventList
}), middleware
})