package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import java.io.StringReader;

public class Searcher {

    IndexSearcher indexSearcher;
    QueryParser queryParser;

    public Searcher(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
        indexSearcher = new IndexSearcher(indexDirectory);

        queryParser = new QueryParser(Version.LUCENE_36, "MeshHeading", new StandardAnalyzer(Version.LUCENE_36));

    }

    public String removeStopWords(String string) throws IOException
    {
        StandardAnalyzer ana = new StandardAnalyzer(Version.LUCENE_30);
        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_36,new StringReader(string));
        StringBuilder sb = new StringBuilder();
        tokenStream = new StopFilter(Version.LUCENE_36, tokenStream, ana.STOP_WORDS_SET);
        CharTermAttribute token = tokenStream.getAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken())
        {
            if (sb.length() > 0)
            {
                sb.append(" ");
            }
            sb.append(token.toString());
        }
        return sb.toString();
    }

    public TopDocs search( String searchQuery, boolean isAdvance, String author) throws IOException, ParseException {
//        query = queryParser.parse(searchQuery);
//        return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);

//        Query querySimple = new TermQuery(new Term("MeshHeading", searchQuery));
//        BooleanQuery booleanQuery = new BooleanQuery();
//        booleanQuery.add(querySimple, BooleanClause.Occur.SHOULD);
//        return indexSearcher.search(booleanQuery, LuceneConstants.MAX_SEARCH);
        String special = "";
        Boolean passed = false;
        if(!searchQuery.equals("")) {
            passed = true;
            searchQuery = removeStopWords(searchQuery);
            System.out.println("#Lucene query " + searchQuery + " | author: " + author);

            special = "MeshHeading:" + searchQuery;

            if (isAdvance) {
                special += " OR AbstractText:" + searchQuery + " OR ArticleTitle:" + searchQuery;
            }
        }

        if(!author.equals("")) {
            System.out.println("#author " + author);
            if(passed) {
                special += " OR AuthorList:" + author;
            } else {
                special += " AuthorList:" + author;
            }
        }

        return indexSearcher.search(queryParser.parse(special), LuceneConstants.MAX_SEARCH);
    }


    public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    public void close() throws IOException {
        indexSearcher.close();
    }
}
