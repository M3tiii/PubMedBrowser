package lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {

    String indexDir = "./Index";
    Indexer indexer;
    Searcher searcher;

    public void run() {
        System.out.println("#Lucene start");
        LuceneTester tester;
        try {
            tester = new LuceneTester();
            tester.createIndex();
<<<<<<< HEAD
            tester.search("Female");
=======
>>>>>>> 47d3c3d32935df912ccfcae5f88a4537d6d1c9d4
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createIndex() throws IOException {
        indexer = new Indexer(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex();
        long endTime = System.currentTimeMillis();
        indexer.close();
<<<<<<< HEAD
        System.out.println(numIndexed + " File indexed, time taken: " +(endTime-startTime)+" ms");
=======
        System.out.println("#Lucene " + numIndexed+ " File indexed, time taken: " +(endTime-startTime)+" ms");
>>>>>>> 47d3c3d32935df912ccfcae5f88a4537d6d1c9d4
    }

    public void search(String searchQuery) throws IOException, ParseException {
        System.out.println("#Lucene search " + searchQuery);
        searcher = new Searcher(indexDir);

        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        System.out.println("#Lucene " + hits.totalHits + " documents found. Time :" + (endTime - startTime));
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("#Lucene File: " + doc.get("name"));
        }

        searcher.close();
    }
}