package hr.fer.zemris.java.hw12.trazilica.naredbe;

import hr.fer.zemris.java.hw12.trazilica.Dictionary;
import hr.fer.zemris.java.hw12.trazilica.Status;

/**
 * {@code ExitCommand} is a concrete implementation of {@code Command} interface
 * which indicates simple shell that it should end with its work. After this
 * command is executed, application will be terminated.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class ExitCommand implements Command {

    @Override
    public Status execute(Dictionary dictionary, String arguments) {
        return Status.TERMINATE;
    }

}
