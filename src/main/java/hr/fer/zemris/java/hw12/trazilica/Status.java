package hr.fer.zemris.java.hw12.trazilica;

/**
 * Constants used to define current shell status. It indicates to the shell what
 * is next action to be done, to continue with command executions or terminate
 * execution.
 * 
 * @author Domagoj Penic
 * @version 2.6.2015.
 *
 */
public enum Status {

    /**
     * IndicateS to the shell that its execution should be continued.
     */
    CONTINUE,

    /**
     * Indicates to the shell that it should terminate its execution.
     */
    TERMINATE;

}
