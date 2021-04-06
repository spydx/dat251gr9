import { EVENTSPATH, fetcher } from "../services/api";

test('Accessing api then get some data', async () => {
      const data = await fetcher(EVENTSPATH);
      expect(data).toBeTruthy();
})