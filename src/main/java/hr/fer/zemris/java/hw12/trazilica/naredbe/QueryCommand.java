package hr.fer.zemris.java.hw12.trazilica.naredbe;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw12.trazilica.Dictionary;
import hr.fer.zemris.java.hw12.trazilica.Status;
import hr.fer.zemris.java.hw12.trazilica.tfidf.TfIdfVector;

/**
 * {@code QueryCommand} is a concrete implementation of {@code Command}
 * interface which accepts various number of arguments which are being
 * interpreted as language words. When this command is being executed, all files
 * are being searched and top 10 with greatest similarity with specified query
 * are being printed on {@code System.out}.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class QueryCommand implements Command {

    @Override
    public Status execute(Dictionary dictionary, String arguments) {
        String[] queryWords = arguments.split("\\s+");
        List<String> queryWordsList = new ArrayList<>();

        // Take only valid words
        for (String word : queryWords) {
            if (dictionary.getVocabulary().contains(word)) {
                queryWordsList.add(word);
            }
        }

        System.out.println("Query is: " + queryWordsList);

        // Create vector representing query
        TfIdfVector vector = new TfIdfVector(queryWordsList,
                dictionary.getVocabulary(), dictionary.getDocuments());

        // Find similarities with other files
        for (Path document : dictionary.getVectors().keySet()) {
            TfIdfVector documentVector = dictionary.getVectors().get(document);

            double similarity = vector.scalarMultiply(documentVector)
                    / (vector.norm() * documentVector.norm());

            if (similarity > 0) {
                dictionary.getCurrentResults().put(similarity, document);
            }
        }

        if (dictionary.getCurrentResults().size() > 0) {
            System.out.println("Najboljih 10 rezultata: ");
        } else {
            System.out.println("No results for specified query.");
        }

        int index = 0;
        for (Entry<Double, Path> entry : dictionary.getCurrentResults()
                .entrySet()) {
            System.out.print(String.format("[%2d] (%.4f) %s%n", index, entry
                    .getKey().doubleValue(), entry.getValue().toString()));

            index++;
        }

        return Status.CONTINUE;
    }

}
