package utility.Web;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// TODO: add javadocs
public class WebOpener implements WebAccessible {
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
