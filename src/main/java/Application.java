import gates.Input;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        Input input = new Input();
        input.modeSelection();
    }
}
