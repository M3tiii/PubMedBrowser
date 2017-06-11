package network;

import static spark.Spark.*;

public class SparkRouter {

    private JsonTransformer jsonTransformer = new JsonTransformer();

    public void run() {
        System.out.println("#Spark start");
        this.handleSearchRequest();
    }

    public void handleSearchRequest() {
        post("/search", (request, response) -> {
            System.out.println("#Spark get search");
//            SearchClass element = jsonTransformer.fromJson(request.body(), SearchClass.class);
//            return jsonTransformer.toJson(element);
            return "OK";
        });

    }
}