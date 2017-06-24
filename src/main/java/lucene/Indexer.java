package lucene;

import java.io.File;
import java.io.IOException;
import java.io.FileFilter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.lang.*;

public class Indexer {

    private IndexWriter writer;
    private String dataDir = "/Data";
    private Document document = new Document();
    private static String tempAuthors = "";

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

        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            File dir = new File(System.getProperty("user.dir") + dataDir);

            FileFilter filter = (File file) -> file.isFile() && file.getName().endsWith(".xml");
            File[] paths = dir.listFiles(filter);
            org.w3c.dom.Document doc;

            System.out.println("#Lucene " + paths.length + " file to load.");
            for(int i = 0; i < paths.length; i++) {
                System.out.println("#Lucene parse file " + i);
                doc = dBuilder.parse(paths[i]);
                if (doc.hasChildNodes()) {
                    printNote(doc.getChildNodes());
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //TODO analyzed by lucene FIELD.INDEX....
        //TODO SAVE ORGINAL ABSTRACT AND CLEREAD
        return writer.numDocs();
    }

    private void printNote(NodeList nodeList) throws IOException {

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                if (tempNode.getNodeName().equals("PMID")) {
                    if (tempAuthors != null && tempAuthors.length() > 0) {
                        tempAuthors = tempAuthors.substring(2);
                    }

//                    System.out.println(tempAuthors);
                    document.add(new Field("AuthorList", tempAuthors, Field.Store.YES, Field.Index.ANALYZED ));
                    document.add(new Field("AuthorListOriginal", tempAuthors, Field.Store.YES, Field.Index.NOT_ANALYZED));

                    writer.addDocument(document);

                    tempAuthors = "";

                    document = new Document();
                    document.add(new Field("PMID", tempNode.getTextContent(), Field.Store.YES, Field.Index.NOT_ANALYZED ));

                } else if (tempNode.getNodeName().equals("ArticleTitle")) {

                    document.add(new Field("ArticleTitle", tempNode.getTextContent(), Field.Store.YES, Field.Index.ANALYZED ));
                    document.add(new Field("ArticleTitleOrginal", tempNode.getTextContent(), Field.Store.YES, Field.Index.NOT_ANALYZED ));

                } else if (tempNode.getNodeName().equals("DescriptorName")) {
                    //if(tempNode.getTextContent().equals("Female")){System.out.println("jest Female: " + tempNode.getTextContent() + ".");}
                    document.add(new Field("MeshHeading", tempNode.getTextContent(), Field.Store.YES, Field.Index.ANALYZED ));

                } else if (tempNode.getNodeName().equals("AbstractText")) {

                    document.add(new Field("AbstractText", tempNode.getTextContent(), Field.Store.YES, Field.Index.ANALYZED ));
                    document.add(new Field("AbstractTextOrginal", tempNode.getTextContent(), Field.Store.YES, Field.Index.NOT_ANALYZED ));

                } else if (tempNode.getNodeName().equals("LastName")) {

                    tempAuthors += ", ";
                    tempAuthors += tempNode.getTextContent();

                } else if (tempNode.getNodeName().equals("ForeName")) {
//                    tempAuthors += ",";
//                    tempAuthors += tempNode.getTextContent();
                }

                // get node name and value
//                System.out.println("\nNode Name = " + tempNode.getNodeName() + " [OPEN]");
//                System.out.println("Node Value = " + tempNode.getTextContent());

//                    if (tempNode.hasAttributes()) {
//
//                        // get attributes names and values
//                        NamedNodeMap nodeMap = tempNode.getAttributes();

//                    for (int i = 0; i < nodeMap.getLength(); i++) {
//
//                        Node node = nodeMap.item(i);
//                        System.out.println("attr name: " + node.getNodeName());
//                        System.out.println("attr value: " + node.getNodeValue());
//
//                    }

//                    }

                if (tempNode.hasChildNodes()) {

                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());
//                    System.out.println("JEST");

                }

//                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

            }

        }

    }

}
