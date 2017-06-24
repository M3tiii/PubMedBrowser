import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {

    private static String dataDir = "/Data";
    private static IndexWriter writer;
    private static org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
    private static String temp = "";

    public static void main(String[] args) {

        try {

            File file = new File(System.getProperty("user.dir") + dataDir + "/medline17n0893.xml");

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);

            if (doc.hasChildNodes()) {

                printNote(doc.getChildNodes());

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void printNote(NodeList nodeList) throws IOException {

        String indexDir = "./Index";

        //this directory will contain the indexes
        Directory indexDirectory =
                FSDirectory.open(new File(indexDir));

        //create the indexer
        writer = new IndexWriter(indexDirectory,
                new StandardAnalyzer(Version.LUCENE_36),true,
                IndexWriter.MaxFieldLength.UNLIMITED);

        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                if (tempNode.getNodeName() == "PMID" && tempNode.getNodeValue() != null) {

                    System.out.println(temp);
                    document.add(new Field("Authors", temp, Field.Store.YES, Field.Index.NOT_ANALYZED));
                    writer.addDocument(document);
                    temp = "";

                    document = new org.apache.lucene.document.Document();
                    document.add(new Field("PMID",tempNode.getNodeValue(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                } else if (tempNode.getNodeName() == "ArticleTitle" && tempNode.getNodeValue() != null) {

                    document.add(new Field("ArticleTitle",tempNode.getNodeValue(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                } else if (tempNode.getNodeName() == "DescriptorName" && tempNode.getNodeValue() != null) {

                    document.add(new Field("MeshHeading",tempNode.getNodeValue(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                } else if (tempNode.getNodeName() == "AbstractText" && tempNode.getNodeValue() != null) {

                    document.add(new Field("Abstract",tempNode.getNodeValue(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                } else if (tempNode.getNodeName() == "LastName" && tempNode.getNodeValue() != null) {

                    temp += tempNode.getNodeValue();

                } else if (tempNode.getNodeName() == "ForeName" && tempNode.getNodeValue() != null) {

                    temp += tempNode.getNodeValue();

                }


                // get node name and value
//                    System.out.println("\nNode Name = " + tempNode.getNodeName() + " [OPEN]");
//                    System.out.println("Node Value = " + tempNode.getTextContent());

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
//                        System.out.println("JEST");

                    }

//                    if (tempNode.getNodeName() == "AuthorList"){// && tempNode.getNodeValue() != null) {
//
//                        document.add(new Field("Authors",tempNode.getNodeValue(), Field.Store.YES, Field.Index.NOT_ANALYZED));
//
//                    }

//                    System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");

            }

        }

        System.out.println("Num indexed: " + writer.numDocs());

    }

}
