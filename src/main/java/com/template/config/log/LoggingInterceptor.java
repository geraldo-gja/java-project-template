package com.template.config.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring MVC interceptor that logs incoming HTTP requests.
 * <p>
 * Logs HTTP method, request URI and the request duration (ms). Optionally includes
 * the authenticated user if available. Applies ANSI color codes per HTTP method
 * (Swagger scheme) when the `colorsEnabled` flag is enabled
 */
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    private final String RESET = "\u001B[0m";

    @Value("${logging.colors.enabled:false}")
    private boolean colorsEnabled;


    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        manageDuration(request);
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        printLogRequest(request);
    }

    private void printLogRequest(HttpServletRequest request) {

         //String username = getAuthenticatetUserName();
        long durationMs = manageDuration(request);
        String method = request.getMethod();
        String color = changeColorMessage(method);

        //  logger.info("REQUEST: " + color + method + " " + request.getRequestURI() + RESET + " - USER: " + username);
        logger.info("REQUEST: " + color + method + " " + request.getRequestURI() + RESET + " - DURATION " + durationMs + " ms");
    }
    /**
     * Records a start timestamp on the request or returns elapsed time in ms.
     *
     * @param request the HttpServletRequest
     * @return elapsed milliseconds if start timestamp existed; otherwise -1 after initializing
     */
    private long manageDuration(HttpServletRequest request) {

        final String ATTR_START_TIME = "startTime";

        Object attr = request.getAttribute(ATTR_START_TIME);
        if (attr instanceof Long) {
            return (System.nanoTime() - (Long) attr) / 1_000_000;
        }

        long start = System.nanoTime();
        request.setAttribute(ATTR_START_TIME, start);
        return -1;
    }

    /**
     * Returns the ANSI color code corresponding to the HTTP method (Swagger scheme).
     * <p>
     * Notes:
     * - The `colorsEnabled` flag controls whether colors are applied; when `false` the method
     *   returns the `RESET` code (no color). Edit this in the `application.yml` file.
     * - In some logging systems (Graylog, Cloud Logging) ANSI sequences may be
     *   stored as literal text or interfere with log processing/querying.
     *   If this happens, keep colors only on localhost and disable them in other environments.
     *
     * @param method HTTP method of the request (e.g., "GET", "POST")
     * @return ANSI escape code corresponding to the color or `RESET` when disabled/fallback
     */
    private String changeColorMessage(String method) {

        if (!colorsEnabled) {
            return RESET;
        }
        final String BLUE = "\u001B[34m";
        final String GREEN = "\u001B[32m";
        final String ORANGE = "\u001B[33m";
        final String RED = "\u001B[31m";

        return switch (method) {
            case "GET" -> BLUE;
            case "POST" -> GREEN;
            case "PUT" -> ORANGE;
            case "DELETE" -> RED;
            default -> RESET;
        };
    }

}
