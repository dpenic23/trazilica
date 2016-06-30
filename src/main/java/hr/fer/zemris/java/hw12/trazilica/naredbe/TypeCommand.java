package hr.fer.zemris.java.hw12.trazilica.naredbe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw12.trazilica.Dictionary;
import hr.fer.zemris.java.hw12.trazilica.Status;

/**
 * {@code TypeCommand} is a concrete implementation of {@code Command} interface
 * which accepts only one argument which represents file index as a result of
 * previous query. File with selected index, i.e. its content is being printed
 * on {@code System.out}. If there is no previous query made, this command does
 * nothing.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class TypeCommand implements Command {

    @Override
    public Status execute(Dictionary dictionary, String arguments) {
        int index = 0;

        try {
            index = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            System.out.println("Arguments can not be interpreted"
                    + " as integer number.");
            return Status.CONTINUE;
        }

        if (index < 0 || index >= dictionary.getCurrentResults().size()) {
            System.out.println("Selected index does not exist.");
            return Status.CONTINUE;
        }

        List<Path> documents = new ArrayList<>(dictionary.getCurrentResults()
                .values());

        try {
            String documentContent = new String(Files.readAllBytes(documents
                    .get(index)), StandardCharsets.UTF_8);
            System.out.println(documentContent);
        } catch (IOException e) {
            System.out.println("IO error occured.");
            System.out.println(e.getMessage());
        }

        return Status.CONTINUE;
    }

}
