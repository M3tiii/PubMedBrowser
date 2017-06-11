import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    public static void main(String [] args) {

        Pattern p = Pattern.compile("\\b(I|this|its.....)\\b\\s?");
        Matcher m = p.matcher("I love this phone, its super fast and there's so much new and cool things with jelly bean....but of recently I've seen some bugs.");
        String s = m.replaceAll("");
        System.out.println(s);

    }

}
