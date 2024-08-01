
package CoreMasterMarket;

import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class DefinicaoOS {

  
    public enum OSType {
        Windows, MacOS, Linux
    }
    private static OSType detectedOS;

    public static OSType getOSType() {
        if (detectedOS == null) {
            String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((os.contains("mac")) || os.indexOf("darwinf") >= 0) {
                detectedOS = OSType.MacOS;
            } else {
                if (os.contains("win")) {
                    detectedOS = OSType.Windows;
                } else if (os.contains("nux")) {
                    detectedOS = OSType.Linux;
                }
            }

        }
        return detectedOS;

    }
}
