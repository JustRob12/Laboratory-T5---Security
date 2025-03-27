package com.security.lab;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.owasp.encoder.Encode;
import java.util.regex.Pattern;
import java.util.Locale;
import java.util.Arrays;
import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@WebServlet("/greeting")
public class GreetingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s]{1,50}$");
    private static final String DEFAULT_NAME = "Guest";
    private static final String PARAM_NAME = "name";
    private static final List<String> DANGEROUS_STRINGS = Arrays.asList(
        "<script", "javascript:", "onerror=", "onload=", "onmouseover=", "onclick=",
        "onfocus=", "onblur=", "onsubmit=", "onmouseout=", "ondblclick="
    );
    private static final String SAFE_HTML_TEMPLATE = 
        "<!DOCTYPE html>%n" +
        "<html>%n" +
        "<head>%n" +
        "<title>Greeting</title>%n" +
        "<meta charset=\"UTF-8\">%n" +
        "</head>%n" +
        "<body>%n" +
        "<h1>Hello, %s!</h1>%n" +
        "</body>%n" +
        "</html>%n";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Set security headers
        setSecurityHeaders(response);
        
        // Get and validate name parameter
        String name = getSanitizedParameter(request);
        
        // Set content type and encoding
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        // Write response with validated and encoded name
        response.getWriter().write(createSafeHtml(name));
    }
    
    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
    }
    
    @SuppressFBWarnings(value = "SERVLET_PARAMETER", 
        justification = "Parameter is properly validated and sanitized before use")
    private String getSanitizedParameter(HttpServletRequest request) {
        try {
            String value = getParameter(request, PARAM_NAME);
            if (value == null || value.trim().isEmpty()) {
                return Encode.forHtml(DEFAULT_NAME);
            }
            
            value = value.trim();
            return validateInput(value) ? Encode.forHtml(value) : Encode.forHtml(DEFAULT_NAME);
        } catch (Exception e) {
            return Encode.forHtml(DEFAULT_NAME);
        }
    }
    
    private String getParameter(HttpServletRequest request, String paramName) {
        if (request == null || paramName == null || paramName.trim().isEmpty()) {
            return null;
        }
        return request.getParameter(paramName);
    }
    
    private boolean validateInput(String input) {
        if (input == null || input.length() > 50) {
            return false;
        }
        return NAME_PATTERN.matcher(input).matches() && !containsDangerousStrings(input);
    }
    
    private boolean containsDangerousStrings(String input) {
        String normalized = input.toLowerCase(Locale.ENGLISH);
        return DANGEROUS_STRINGS.stream()
            .anyMatch(normalized::contains);
    }
    
    private String createSafeHtml(String name) {
        return String.format(SAFE_HTML_TEMPLATE, name);
    }
} 