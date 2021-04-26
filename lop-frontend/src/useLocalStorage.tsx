import { useEffect, useState } from "react";

function unsafeParseJSON<T>(item: string): T {
  return JSON.parse(item);
}

/**
 * A hook for syncing state to local storage and keeping the state in sync between multiple
 * instances of the app.
 *
 * A value of `undefined` corresponds to no value in localStorage.
 *
 * Based on https://usehooks.com/useLocalStorage/
 */
export function useLocalStorage<T>(
  key: string,
  initialValue: T | undefined
): [T | undefined, (newValue: T | undefined) => void] {
  const [value, setValue] = useState<T | undefined>(() => {
    const item = window.localStorage.getItem(key);
    if (item === null) {
      // update storage to reflect current value
      if (initialValue !== undefined) {
        window.localStorage.setItem(key, JSON.stringify(initialValue));
      }
      return initialValue;
    } else {
      return unsafeParseJSON(item);
    }
  });

  // update the value when storage updates
  useEffect(() => {
    const listener = (event: StorageEvent) => {
      if (event.key === key) {
        const item = event.newValue;
        setValue(item === null ? undefined : unsafeParseJSON(item));
      }
    };
    window.addEventListener("storage", listener);
    return () => {
      window.removeEventListener("storage", listener);
    };
  });

  // update storage when the value updates
  const wrappedSetValue = (newValue: T | undefined) => {
    setValue(newValue);
    if (newValue !== undefined) {
      window.localStorage.setItem(key, JSON.stringify(newValue));
    } else {
      window.localStorage.removeItem(key);
    }
  };

  return [value, wrappedSetValue];
}
