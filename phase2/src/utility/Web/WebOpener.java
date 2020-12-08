package utility.Web;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class which handles opening of the user's web browser
 */
public class WebOpener implements WebAccessible {
    /**
     * Tries to open the given link in the user's web browser. Returns true on success, false otherwise
     * @param link the website link to be opened
     * @return true on success, false otherwise
     */
    public boolean openWeb(String link) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(link));
                return true;
            } catch (IOException | URISyntaxException e) {
                return false;
            }
        }
        return false;
    }
}
