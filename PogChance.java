import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PogChance {
    int skipCounter = 0;
    private static String teamURL = ("https://atlas.abiosgaming.com/v3/teams?" +
            "secret=" + System.getenv("ABIOS_SECRET_KEY") + "&filter=region.id=2,active=true,");
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(teamURL)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(PogChance::parse)
                .join();

    }
    public static String parse(String responseBody) {
        JSONArray teams = new JSONArray(responseBody);
        int rowCounter = 0;
        for(int i = 0; i< teams.length(); i++){
            JSONObject team = teams.getJSONObject(i);
            int id = team.getInt("id");
            String name = team.getString("name");
            JSONObject regionJSON = team.getJSONObject("region");
            String region = regionJSON.getString("name");
            System.out.println((i+1) + ":" + name + ": " + region);
            rowCounter++;
        }
        return null;
    }
}
