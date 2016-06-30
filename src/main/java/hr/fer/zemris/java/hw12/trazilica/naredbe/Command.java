package hr.fer.zemris.java.hw12.trazilica.naredbe;

import hr.fer.zemris.java.hw12.trazilica.Dictionary;
import hr.fer.zemris.java.hw12.trazilica.Status;

/**
 * {@code Command} defines an interface for commands which can be executed by
 * {@code Console} command line application. Commands are being executed over
 * specified {@code Dictionary} and arguments provided by user trough simple
 * shell.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public interface Command {

    /**
     * Executes this {@code Command} over specified {@code Dictionary} and
     * provided user arguments and returns {@code Status} which indicates if
     * application should continue with its work or it should terminate.
     * 
     * @param dictionary
     *            {@code Dictionary} holding files and vector
     * @param arguments
     *            Shell arguments provided by user
     * @return {@code Status} when this {@code Command} finishes its execution
     */
    Status execute(Dictionary dictionary, String arguments);

}
