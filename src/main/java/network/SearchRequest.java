package network;

public class SearchRequest {

    private String text;
    private boolean isAdvance;
    private String author;

    public String getText() {
        return text;
    }

    public boolean isAdvance() {
        return isAdvance;
    }

    public String getAuthor() {
        return author;
    }
}