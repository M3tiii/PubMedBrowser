package lucene;

import org.apache.lucene.search.*;
import org.apache.lucene.search.function.*;
import org.apache.lucene.index.*;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

public class MyScoreQuery extends CustomScoreQuery {
    private Query query;

    public MyScoreQuery(Query query) {
        super(query);
        this.query = query;
    }


    @Override
    public CustomScoreProvider getCustomScoreProvider(final IndexReader reader) {
        return new CustomScoreProvider(reader) {
            @Override
            public  float customScore(int doc, float subQueryScore,float valSrcScores){
                double total = 0;
                try {
                    TermFreqVector freqVector = reader.getTermFreqVector(doc, "ArticleTitle");
                    if(freqVector != null) {
                        int freqs[] = freqVector.getTermFrequencies();

                        Set<Term> terms = new HashSet<>();
                        query.extractTerms(terms);

                        int hitCounter = 0;
                        int termsUsed = 0;

                        for (Term term : terms) {
                            int index = freqVector.indexOf(term.text());

                            if (index != -1) {
                                termsUsed ++;
                                hitCounter += freqs[index];
                            }
                        }

                        total = (double)(hitCounter * termsUsed) / (double)(freqs.length * terms.size()) * 2000;
                    }

                    freqVector = reader.getTermFreqVector(doc, "AbstractText");
                    if(freqVector != null && total >= 0) {
                        int freqs[] = freqVector.getTermFrequencies();

                        Set<Term> terms = new HashSet<>();
                        query.extractTerms(terms);

                        int hitCounter = 0;
                        int termsUsed = 0;

                        for (Term term : terms) {
                            int index = freqVector.indexOf(term.text());

                            if (index != -1) {
                                termsUsed ++;
                                hitCounter += freqs[index];
                            }
                        }

                        total += (double)(hitCounter * termsUsed) / (double)(freqs.length * terms.size())*1000;
                        System.out.println(total + ": " + hitCounter + " , " + termsUsed+ " , " + freqs.length+ " , " + terms.size());
                    }

                    System.out.println(doc + " score: " + total);

                } catch(Exception e) {
                    e.printStackTrace();
                }
                return (int)total;
            }
        };
    }
}

