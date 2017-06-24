package network;

import lucene.LuceneTester;
import org.apache.lucene.queryParser.ParseException;
import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.*;

public class SparkRouter {

    private JsonTransformer jsonTransformer = new JsonTransformer();
    private LuceneTester lucene;

    public void run(LuceneTester lucene) throws IOException, ParseException {
        this.lucene = lucene;
        System.out.println("#Spark start");
        this.handleSearchRequest();
    }

    public void handleSearchRequest() throws IOException, ParseException {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        post("/search", (Request request, Response response) -> {
            System.out.println("#Spark get search");
            SearchRequest searchRequest = jsonTransformer.fromJson(request.body(), SearchRequest.class);
            try {
                Object res = this.lucene.search(searchRequest.getText(), searchRequest.isAdvance());
                return jsonTransformer.toJson(res);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "NONE";
        });

    }
}