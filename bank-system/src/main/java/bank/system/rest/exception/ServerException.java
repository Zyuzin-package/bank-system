package bank.system.rest.exception;
/**
 * The error that is thrown when an error occurs while server processing data
 */
public class ServerException extends Exception{
    public ServerException(String message) {
        super(message);
    }
}
