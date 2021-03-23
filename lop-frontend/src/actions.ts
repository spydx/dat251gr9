
export const BEGIN_LOGIN = "BEGIN_LOGIN"
export const BEGIN_LOGOUT = "BEGIN_LOGOUT"

export type Action =
   | {
      type: typeof BEGIN_LOGIN
      payload: {
         username: string
         password: string
      }
   }
   | {
      type: typeof BEGIN_LOGOUT
      payload: boolean
   }
