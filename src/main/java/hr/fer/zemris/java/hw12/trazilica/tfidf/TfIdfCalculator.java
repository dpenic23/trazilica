package hr.fer.zemris.java.hw12.trazilica.tfidf;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * {@code TfIdfCalculator} provides static method for calculating TF and IDF
 * values of specified words. TF - Term Frequency is being calculated as number
 * of occurrences of single word in single document and IDF - Inverse Document
 * Frequency is associated with number of occurrences in all documents which
 * define language.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class TfIdfCalculator {

    /**
     * Calculates TF value of specified word associated with document which
     * words are provided trough documentWords collection.
     * 
     * @param documentWords
     *            Collection of words inside current document
     * @param word
     *            Word which TF value is being calculated
     * @return TF value of specified word
     */
    public double calculateTf(Collection<String> documentWords, String word) {
        double count = 0;

        for (String s : documentWords) {
            if (s.equals(word)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Calculates IDF value of specified value associated with all documents
     * provided trough documents collection.
     * 
     * @param documents
     *            Collection of all documents and their words
     * @param word
     *            Word which IDF value is being calculated
     * @return IDF value of specified word
     */
    public double calculateIdf(Map<Path, List<String>> documents, String word) {
        double count = 0;

        for (List<String> document : documents.values()) {
            if (document.contains(word)) {
                count++;
            }
        }

        return Math.log(documents.size() / count);

    }

}
