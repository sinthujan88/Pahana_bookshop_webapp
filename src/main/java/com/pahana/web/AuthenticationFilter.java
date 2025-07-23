package com.pahana.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// --- Teaching Point: @WebFilter Annotation ---
// This annotation tells the server that this class is a Filter.
// The "urlPatterns = {"/*"}" part means this filter will intercept EVERY
// single request coming into our application. This is powerful because it
// creates a single security checkpoint.

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        // Cast the request and response objects to their HTTP-specific types
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the requested URL path
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // --- Teaching Point: Preventing Infinite Loops ---
        // We must allow requests for the login page, register page, and our CSS files
        // to pass through WITHOUT checking for a session. If we don't, trying to
        // access the login page would trigger a redirect to the login page, which
        // would trigger a redirect, creating an infinite loop.
        if (path.startsWith("/login") || path.startsWith("/register") || path.startsWith("/styles/")) {
            chain.doFilter(request, response); // Let the request continue to its destination
            return;
        }

        // Get the current session, but don't create a new one if it doesn't exist
        HttpSession session = httpRequest.getSession(false);

        // Check if the user is logged in (i.e., if the "user" object exists in the session)
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            // If the user is logged in, let the request proceed to the intended page
            chain.doFilter(request, response);
        } else {
            // If the user is NOT logged in, redirect them to the login page
            System.out.println("Blocked unauthorized access to: " + path); // A helpful log message
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
    
    // The init() and destroy() methods are not needed for this simple filter.
    // You can leave their default implementations.
}