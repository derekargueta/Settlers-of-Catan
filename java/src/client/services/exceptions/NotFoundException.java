package client.services.exceptions;

/**
 * @author Derek Argueta
 */
public class NotFoundException extends BadHttpRequestException {
    public NotFoundException() {
        super("Received Not Found error (404). Please review what endpoint is getting called");
    }
}
