export const FETCH_LIST = "FETCH_LIST"

export type EventListAction =
   | {
      type: typeof FETCH_LIST
   }
   
export const fetchEventListFromApi = () => { 
   return {type: FETCH_LIST}
}