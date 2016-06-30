package hr.fer.zemris.java.hw12.trazilica.naredbe;

import java.nio.file.Path;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw12.trazilica.Dictionary;
import hr.fer.zemris.java.hw12.trazilica.Status;

/**
 * {@code ResultsCommand} is a concrete implementation of {@code Command}
 * interface which prints last result of {@code QueryCommand} on this simple
 * shell prompt. If before calling this command, {@code QueryCommand} was not
 * called, this command does nothing.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class ResultsCommand implements Command {

    @Override
    public Status execute(Dictionary dictionary, String arguments) {
        if (dictionary.getCurrentResults().size() == 0) {
            System.out.println("There are no current results.");
            return Status.CONTINUE;
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
