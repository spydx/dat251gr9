import { fetchEventList } from "../services/api";


//TODO: Implement this test
test('Accessing api then get some data', async () => {
   try {
      const eventlist = await fetchEventList();
      console.log(eventlist);            
   } catch (error) {
      console.log("Failed")
   }
})