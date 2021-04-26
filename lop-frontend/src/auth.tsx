import { createContext, useContext } from "react";
import { authenticate } from "./api/methods";
import { useLocalStorage } from "./useLocalStorage";

export type AuthContextType = {
  isSignedIn: () => boolean;
  signIn: (email: string, password: string) => Promise<boolean>;
  signOut: () => void;
  userId: string | undefined;
  token: string | undefined;
};

const AuthContext = createContext<AuthContextType>({
  isSignedIn: () => false,
  signIn: () => Promise.resolve(false),
  signOut: () => {},
  userId: undefined,
  token: undefined,
});

// provider for auth context
export const ProvideAuth = ({ children }: any) => {
  const auth = useNewAuthContext();
  return <AuthContext.Provider value={auth}>{children}</AuthContext.Provider>;
};

// consumer hook for auth context
export const useAuth = () => {
  return useContext(AuthContext);
};

// provider hook for auth context
function useNewAuthContext(): AuthContextType {
  type UserData = {
    userId: string;
    token: string;
  };

  const [userData, setUserData] = useLocalStorage<UserData>("userData", undefined);

  const signIn = (email: string, password: string) =>
    authenticate({ email, password }).then((data) => {
      if (data === null) {
        return false;
      }
      setUserData(data);
      return true;
    });

  const signOut = () => {
    setUserData(undefined);
  };

  // TODO(H-C-H): token expiry
  const isSignedIn = () => !!userData;

  return {
    userId: userData?.userId,
    token: userData?.token,
    signIn,
    signOut,
    isSignedIn,
  };
}