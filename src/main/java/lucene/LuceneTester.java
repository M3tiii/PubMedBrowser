package lucene;

import java.io.IOException;
import java.util.*;

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
        System.out.println("#Lucene " + numIndexed+ " File indexed, time taken: " +(endTime-startTime)+" ms");
    }

    public ArrayList<SearchResponse> search(String searchQuery) throws IOException, ParseException {
        System.out.println("#Lucene search " + searchQuery);
        searcher = new Searcher(indexDir);

        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        ArrayList<SearchResponse> res = new ArrayList<SearchResponse>();

        System.out.println("#Lucene " + hits.totalHits + " documents found. Time :" + (endTime - startTime));
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("#Lucene File: " + doc.get("PMID"));
            res.add(this.toResponse(doc.get("PMID"), doc.get("ArticleTitle"), doc.get("AbstractText"), scoreDoc.score));
        }

        searcher.close();
        return res;
    }

    public SearchResponse toResponse(String PMID, String articleTitle, String abstractText, float score) {
       SearchResponse article = new SearchResponse();
       article.setPMID(PMID);
       article.setArticleTitle(articleTitle);
       article.setAbstractText(abstractText);
       article.setScore(score);
       return article;
    }
}

