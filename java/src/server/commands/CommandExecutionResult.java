package server.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a transportation object to store the result of command executions.
 * It emulates some of the fields of an HTTP response, so that we can use an
 * actual organized object to store results, rather than relying on a more dangerous
 * or unstable protocol such as arbitrary strings.
 *
 * @author Derek Argueta
 */
public final class CommandExecutionResult {
    private Map<String, String> newCookies;
    private String body;
    private boolean errorOccurred;
    private int status;

    public CommandExecutionResult(final String info) {
        this.newCookies = new HashMap<>();
        this.body = info;
        this.status = 200;
        this.errorOccurred = false;
    }

    public void triggerError(int status) {
        this.errorOccurred = true;
        this.status = status;
    }

    public Map<String, String> getNewCookies() {
        return this.newCookies;
    }

    public void addCookie(String key, String value) {
        this.newCookies.put(key, value);
    }

    public int getStatus() {
        return this.status;
    }

    public String getBody() {
        return this.body;
    }

    public boolean errorOccurred() {
        return this.errorOccurred;
    }

    public boolean hasNewCookies() {
        return this.newCookies != null && this.newCookies.size() > 0;
    }
}
