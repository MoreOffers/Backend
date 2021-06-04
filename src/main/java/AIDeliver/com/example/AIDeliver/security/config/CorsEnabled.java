package AIDeliver.com.example.AIDeliver.security.config;


import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;

@Configuration
@Component
public class CorsEnabled implements HttpFunction {
    // corsEnabled is an example of setting CORS headers.
    // For more information about CORS and CORS preflight requests, see
    // https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request.
    @Override
    public void service(HttpRequest request, HttpResponse response)
            throws IOException {
        // Set CORS headers
        //   Allows GETs from any origin with the Content-Type
        //   header and caches preflight response for 3600s
        response.appendHeader("Access-Control-Allow-Origin", "*");

        if ("OPTIONS".equals(request.getMethod())) {
            response.appendHeader("Access-Control-Allow-Methods", "POST");
            response.appendHeader("Access-Control-Allow-Headers", "Content-Type");
            response.appendHeader("Access-Control-Max-Age", "3600");
            response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);
            return;
        }

        response.appendHeader("Access-Control-Allow-Methods", "POST");
        response.appendHeader("Access-Control-Allow-Headers", "Content-Type");
        response.appendHeader("Access-Control-Max-Age", "3600");
        response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);

        // Handle the main request.
        BufferedWriter writer = response.getWriter();
        writer.write("CORS headers set successfully!");
    }
}