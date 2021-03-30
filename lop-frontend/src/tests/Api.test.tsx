import { fetchEventList } from "../services/api";


//TODO: Implement this test
test('Accessing api then get some data', async () => {
   try {
      const eventlist = await fetchEventList();
   } catch (error) {
      console.log("Failed")
   }
})