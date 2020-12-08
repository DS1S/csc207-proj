package utility.Web;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A WebAccessible class used for opening valid URIs on a browser.
 */
public class WebOpener implements WebAccessible {
    /**
     * Attempts to open the given link (as a string) on a browser.
     * @param link The given URI in a string format.
     * @return True if the link was opened, false otherwise.
     */
    public boolean openWeb(String link) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                String splitLink = link.substring(link.indexOf(":") + 1).trim();
                Desktop.getDesktop().browse(new URI(splitLink));
                return true;
            } catch (IOException | URISyntaxException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
