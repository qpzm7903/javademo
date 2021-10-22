import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * demo for request some url
 *
 * @author qpzm7903
 * @since 2021-10-21-21:58
 */
public class SampleClass {
    public static void main(String[] args) throws IOException {
        fetch("http://www.baidu.com");
        fetch("https://www.zhihu.com/hot");
    }

    private static void fetch(final String address) throws IOException {

        final URL url = new URL(address);
        final URLConnection connection = url.openConnection();

        try (final BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            String inputLine;
            final StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            System.out.println("Content size: " + sb.length());
        }
    }
}
