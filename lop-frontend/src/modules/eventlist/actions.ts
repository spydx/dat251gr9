export const FETCH_LIST = "FETCH_LIST"

export type EventListAction =
   | {
      type: typeof FETCH_LIST
      payload: string
   }