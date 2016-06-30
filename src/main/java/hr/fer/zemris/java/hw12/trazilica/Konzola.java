package hr.fer.zemris.java.hw12.trazilica;

import hr.fer.zemris.java.hw12.trazilica.naredbe.Command;
import hr.fer.zemris.java.hw12.trazilica.naredbe.ExitCommand;
import hr.fer.zemris.java.hw12.trazilica.naredbe.QueryCommand;
import hr.fer.zemris.java.hw12.trazilica.naredbe.ResultsCommand;
import hr.fer.zemris.java.hw12.trazilica.naredbe.TypeCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is command line application which communicates with user trough
 * simple shell. It provides several commands which can be requested by user,
 * such as query and type commands. This application accepts only one command
 * line argument, path to the root file which contains text files which define
 * language vocabulary. If some error occurs, appropriate message will be
 * printed and program will be terminated.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public class Konzola {

    /**
     * Collection of available commands for this simple shell.
     */
    private static Map<String, Command> commands;

    // Initialize available commands
    static {
        commands = new HashMap<>();

        commands.put("query", new QueryCommand());
        commands.put("type", new TypeCommand());
        commands.put("results", new ResultsCommand());
        commands.put("exit", new ExitCommand());
    }

    /**
     * Method called once program is run. Arguments are described below.
     * 
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            terminate(String.format("Illegal number of arguments: %d.%n"
                    + "Expected only 1, path to the directory"
                    + " with text files.", args.length));
        }

        final String rootDirectory = args[0];
        final String stopWords = "dictionary/hrvatski_stoprijeci.txt";

        Dictionary dictionary = null;
        try {
            dictionary = new Dictionary(rootDirectory, stopWords);
        } catch (IllegalArgumentException | IOException e) {
            terminate(e.getMessage());
        }

        System.out.println("Veličina riječnika je "
                + dictionary.getVocabulary().size() + " riječi");
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command > ");

            // Get request from user
            String userRequest = scanner.nextLine().trim();

            if (userRequest.isEmpty()) {
                continue;
            }

            // Extract name and arguments
            String commandName = extractCommandName(userRequest);
            String commandArgument = extractCommandArgument(userRequest);

            Command command = commands.get(commandName);

            // Check if command exists
            if (command == null) {
                System.out.println("Unrecognized command.");
                System.out.println();
                continue;
            }

            if (command.execute(dictionary, commandArgument) == Status.TERMINATE) {
                break;
            }
        }

        System.out.println();
        System.out.println("Thank you and goodbye!");
        scanner.close();
    }

    /**
     * Terminates this application and returns exit status. Before application
     * is actually terminated, specified message will be printed on
     * {@code System.out}.
     * 
     * @param message
     *            Message to be printed for user before exiting this application
     */
    private static void terminate(String message) {
        System.out.println(message);
        System.exit(-1);
    }

    /**
     * Extracts command name from specified line. Name and arguments should be
     * separated with one or more white-spaces.
     * 
     * @param line
     *            Line from which command name is extracted
     * @return {@code String} representation of command name
     */
    private static String extractCommandName(String line) {
        String[] elements = line.split("\\s+", 2);
        return elements[0].trim().toLowerCase();
    }

    /**
     * Extracts command arguments from specified line. Name and arguments should
     * be separated with one or more white-spaces. If command does not contain
     * any arguments, empty {@code String} is returned;
     * 
     * @param line
     *            Line from which command arguments are extracted
     * @return {@code String} representation of command arguments
     */
    private static String extractCommandArgument(String line) {
        String[] elements = line.split("\\s+", 2);

        if (elements.length == 1) {
            return "";
        }

        return elements[1].trim();
    }

}
