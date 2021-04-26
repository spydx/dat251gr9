package no.hvl.dat251.gr9.lopbackend.geocoding;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;


public class APIRequest {

    public static double[] getLatitudeAndLongitude(String location) throws ExecutionException, InterruptedException {
        location = formatString(location);
        String apiRequest =
                "http://api.positionstack.com" +                        //WEBPAGE
                        "/v1/forward?" +                                //TYPE OF REQUEST
                        "access_key=b3385e0a99657ef22865ce4f7c31dd96" + //API ACCESS KEY
                        "&query=" + location;                           //QUERY WITH LOCATION

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiRequest))
                .build();
        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get().body();
        double latitude = Double.parseDouble(getSpecifiedDataFromResponse("latitude", response));
        double longitude = Double.parseDouble(getSpecifiedDataFromResponse("longitude", response));
        return new double[]{latitude, longitude};
    }

    private static String formatString(String str){
        String[] strSplit = str.split(" ");
        if(strSplit.length <= 1) return str;
        StringBuilder formattedString = new StringBuilder();
        for(int i = 0; i < strSplit.length-1; i++){
            formattedString.append(strSplit[i]);
            formattedString.append("%20");
        }
        formattedString.append(strSplit[strSplit.length - 1]);
        return formattedString.toString();
    }

    static String getSpecifiedDataFromResponse(String specifiedData, String response) {
        JSONParser parser = new JSONParser();
        try{
            Object object = parser.parse(response);
            JSONObject jsonObject = (JSONObject)object;
            JSONArray arr = (JSONArray) jsonObject.get("data");
            jsonObject = (JSONObject) arr.get(0);
            return jsonObject.getAsString(specifiedData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
