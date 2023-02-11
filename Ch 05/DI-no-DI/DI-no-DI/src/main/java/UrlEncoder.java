import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncoder {
    public String encode(String message) throws UnsupportedEncodingException {
        return URLEncoder.encode(message, "UTF-8");
    }
}
