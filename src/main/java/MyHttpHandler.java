import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class MyHttpHandler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger(MyHttpHandler.class.getName());

    public void handle(HttpExchange httpExchange) throws IOException {
        handleRequest(httpExchange);
        LOGGER.info("handleOtherRequest finished");
    }

    private void handleRequest(HttpExchange httpExchange) throws IOException {
        System.out.println(httpExchange.getRequestHeaders().entrySet());

        OutputStream outputStream = httpExchange.getResponseBody();
        String requestMethod = httpExchange.getRequestMethod();
        String requestURI = httpExchange.getRequestURI().toString();
        int rCode;

        StringBuilder htmlBuilder = new StringBuilder();
        if (requestMethod.equals("GET") && requestURI.equals("/about")) {
            htmlBuilder.append("<html>")
                    .append("<body>")
                    .append("<h1>")
                    .append("User information:")
                    .append("name: Dmitriy")
                    .append("age: 27")
                    .append("company: Sberbank")
                    .append("</h1>")
                    .append("</body>")
                    .append("</html>");
            rCode = 200;
        } else {
            htmlBuilder.append("<html>")
                    .append("<body>")
                    .append("<h1>")
                    .append("Error 404, page not found.")
                    .append("</h1>")
                    .append("</body>")
                    .append("</html>");
            rCode = 404;
        }


        String htmlResponse = htmlBuilder.toString();
        httpExchange.sendResponseHeaders(rCode, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
