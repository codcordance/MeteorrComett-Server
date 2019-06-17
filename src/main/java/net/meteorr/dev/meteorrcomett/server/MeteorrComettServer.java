package net.meteorr.dev.meteorrcomett.server;

import net.meteorr.dev.meteorrcomett.server.exception.TerminalAlreadyInitializedException;
import net.meteorr.dev.meteorrcomett.server.terminal.MessageLevel;
import net.meteorr.dev.meteorrcomett.server.terminal.ServerTerminal;
import net.meteorr.dev.meteorrcomett.server.utils.ExceptionHandler;

/**
 * @author RedLux
 */
public class MeteorrComettServer {

    private final MeteorrComettServer instance;
    private Boolean running;
    private ExceptionHandler exceptionHandler;
    private ServerTerminal serverTerminal;

    public MeteorrComettServer() {
        instance = this;
        running = false;
        exceptionHandler = null;
        serverTerminal = null;
    }

    public MeteorrComettServer getInstance() {
        return this.instance;
    }

    public ServerTerminal getServerTerminal() {
        return this.serverTerminal;
    }

    public void main(String[] args) {
        this.exceptionHandler = new ExceptionHandler(getInstance());


        serverTerminal = new ServerTerminal(getInstance());

        try {
            serverTerminal.init();
        } catch (TerminalAlreadyInitializedException e) {
            this.exceptionHandler.handle(e);
        }

        /**=serverTerminal.print(MessageLevel.INFO, "Bonjour", "le serveur a démarré!");
        serverTerminal = new ServerTerminal(getInstance());

        serverTerminal.print(MessageLevel.WARNING, "Attention, avertissement!");
        running = true;
        serverTerminal.print(MessageLevel.ERROR, "Erreur", "404", "blabla");
        serverTerminal.print(MessageLevel.DEBUG, "Devine quoi ? C'est juste un debug!");
        serverTerminal.print(MessageLevel.CRITICAL, "boooom bada boom!", "grosse erreur qui casse tout!");**/

        try {
            throw new NullPointerException();
        } catch (Exception e) {
            this.exceptionHandler.handle(e);
        }

        /**ThreadConsoleReader threadConsoleReader = new ThreadConsoleReader(instance);
         threadConsoleReader.setDaemon(true);
         threadConsoleReader.start();**/
        /**try {
         TimeUnit.SECONDS.sleep(3);
         System.out.print("`\rok3\n");
         TimeUnit.SECONDS.sleep(3);
         System.out.print("`\rok3\n");
         TimeUnit.SECONDS.sleep(3);
         System.out.print("`\rok3\n");
         } catch (InterruptedException e) {
         e.printStackTrace();
         }**/

    }

    public void issueCommand(String command) {
        System.out.print("\rCommand issued: " + command + "\n");
    }

    public boolean isRunning() {
        return running;
    }
}
