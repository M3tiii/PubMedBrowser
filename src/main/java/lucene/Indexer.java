package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        //this directory will contain the indexes
        Directory indexDirectory =
                FSDirectory.open(new File(indexDirectoryPath));

        //create the indexer
        writer = new IndexWriter(indexDirectory,
                new StandardAnalyzer(Version.LUCENE_36),true,
                IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }


    public int createIndex() throws IOException {

        Document document = new Document();
        document.add(new Field("name","article1", Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field("FIELD1","string1", Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field("FIELD1","string2", Field.Store.YES, Field.Index.NOT_ANALYZED));
        writer.addDocument(document);

        document = new Document();
        document.add(new Field("name","article2", Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field("FIELD1","string2", Field.Store.YES, Field.Index.NOT_ANALYZED));
        writer.addDocument(document);

        //get all files in the data directory
//        File[] files = new File(dataDirPath).listFiles();
//
//        for (File file : files) {
//            if(!file.isDirectory()
//                    && !file.isHidden()
//                    && file.exists()
//                    && file.canRead()
//                    && filter.accept(file)
//                    ){
//                indexFile(file);
//            }
//        }
        return writer.numDocs();
    }
}
