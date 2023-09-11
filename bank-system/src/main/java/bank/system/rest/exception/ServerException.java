package bank.system.rest.exception;
/**
 * The error that is thrown when an error occurs on the server
 */
public class ServerException extends Exception{
    public ServerException(String message) {
        super(message);
    }
}
