package lucene;

public class SearchResponse {

    private String PMID;
    private String articleTitle;
    private String abstractText;
    private String authors;
    private float score;

    public String getPMID() {
        return PMID;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public void setScore(float score) { this.score = score; }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}