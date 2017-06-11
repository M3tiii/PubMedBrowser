package network;

import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class SparkRouter {

    private JsonTransformer jsonTransformer = new JsonTransformer();

    public void run() {
        System.out.println("#Spark start");
        this.handleSearchRequest();
    }

    public void handleSearchRequest() {
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

            return jsonTransformer.toJson(searchRequest);
        });

    }
}