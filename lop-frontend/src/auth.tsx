import { createContext, useContext, useState } from "react";
import { authenticate } from "./api/methods";

export type AuthContextType = {
  isSignedIn: () => boolean;
  signIn: (email: string, password: string) => Promise<boolean>;
  signOut: () => void;
  userId: string | null;
  token: string | null;
};

const AuthContext = createContext<AuthContextType>({
  isSignedIn: () => false,
  signIn: () => Promise.resolve(false),
  signOut: () => {},
  userId: null,
  token: null,
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
  const [userId, setUserId] = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);

  const signIn = (email: string, password: string) =>
    authenticate({ email, password }).then((data) => {
      if (data === null) {
        return false;
      }
      setUserId(data.userId);
      setToken(data.token);
      return true;
    });

  const signOut = () => {
    setUserId(null);
    setToken(null);
  };

  const isSignedIn = () => userId !== null;

  return {
    userId,
    token,
    signIn,
    signOut,
    isSignedIn,
  };
}
