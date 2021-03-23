import { applyMiddleware, 
   configureStore, 
   getDefaultMiddleware } from "@reduxjs/toolkit";
import { rootReducer } from "./rootReducer";
import {logger} from "redux-logger";


const middleware = [...getDefaultMiddleware(), logger]

export const store = configureStore({
   reducer: {
      rootReducer
   },
   middleware
})