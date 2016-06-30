package hr.fer.zemris.java.hw12.trazilica.tfidf;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * {@code TfIdfVector} represents vector which components are TF-IDF values of
 * every word which is being contained by some document. These vectors can be
 * multiplied such as ordinary vectors and their norm can also be calculated.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class TfIdfVector {

    /**
     * Instance of {@code TfIdfCalculator} used for TF and IDF values
     * calculation.
     */
    private static final TfIdfCalculator TFIDF_CALCULATOR = new TfIdfCalculator();

    /**
     * Components of this vector.
     */
    private double[] components;

    /**
     * Creates new {@code TfIdfVector} which represents single document with
     * specified words which are part of specified vocabulary.
     * 
     * @param documentWords
     *            Collection of words contained by this document
     * @param vocabulary
     *            Collection of vocabulary words
     * @param documents
     *            All documents which define current language
     */
    public TfIdfVector(List<String> documentWords,
            Collection<String> vocabulary, Map<Path, List<String>> documents) {
        components = new double[vocabulary.size()];

        int count = 0;
        for (String word : vocabulary) {
            if (documentWords.contains(word)) {
                double tf = TFIDF_CALCULATOR.calculateTf(documentWords, word);
                double idf = TFIDF_CALCULATOR.calculateIdf(documents, word);
                double tfidf = tf * idf;
                components[count++] = tfidf;
            } else {
                components[count++] = 0;
            }
        }
    }

    /**
     * Returns size of this {@code TfIdfVector}.
     * 
     * @return Size of this {@code TfIdfVector}
     */
    public int size() {
        return components.length;
    }

    /**
     * Returns value of component on specified index of this {@code TfIdfVector}
     * .
     * 
     * @param index
     *            Component index which value should be returned
     * @return Value of component on specified index
     */
    public double get(int index) {
        return components[index];
    }

    /**
     * Calculates norm of this {@code TfIdfVector} by standard vector formula,
     * where norm = square-root(x1^2 + x2^2 + ...).
     * 
     * @return Norm of this {@code TfIdfVector}
     */
    public double norm() {
        double normRoot = 0;

        for (double component : components) {
            normRoot += component * component;
        }

        return Math.sqrt(normRoot);
    }

    /**
     * Returns value as a result of scalar multiplication of this
     * {@code TfIdfVector} and specified one. Scalar multiplying is done as
     * ordinary one, by multiplying vector components and summing them.
     * 
     * @param vector
     *            {@code TfIdfVector} to be multiplied with this one
     * @return Result of scalar multiplication
     */
    public double scalarMultiply(TfIdfVector vector) {
        int scalar = 0;

        for (int index = 0; index < components.length; index++) {
            scalar += this.components[index] * vector.components[index];
        }

        return scalar;
    }

}
