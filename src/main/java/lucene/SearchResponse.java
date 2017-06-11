package network;

public class SearchResponse {

    private String PMID;
    private boolean Articletitle;

    public String getPMID() {
        return PMID;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public boolean isArticletitle() {
        return Articletitle;
    }

    public void setArticletitle(boolean articletitle) {
        Articletitle = articletitle;
    }
}