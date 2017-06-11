import lucene.LuceneTester;
import network.SparkRouter;
import org.apache.lucene.queryParser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String [] args) throws IOException, ParseException {
        System.out.println("#Server start");
        System.out.println("#Server working directory " + System.getProperty("user.dir"));
        SparkRouter router = new SparkRouter();
        LuceneTester lucene = new LuceneTester();
        lucene.run();
        router.run(lucene);
//        lucene.search("string2");
    }
}