package com.example.Api_Gateway.Service;

import com.example.Api_Gateway.Model.PolynomialRequest;
import com.example.Api_Gateway.Model.PolynomialResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PolynomialService {

    private static final String POLYNOMIAL_REGEX = "([+-]?\\d*\\.?\\d*)x(?:\\^(\\d+))?|([+-]?\\d*\\.?\\d*)";  // Regex to match polynomial terms

    public PolynomialResponse processPolynomial(PolynomialRequest request) {
        validatePolynomial(request.getPolynomial());  // Validate the polynomial
        Map<Integer, Double> coefficientsMap = extractCoefficients(request.getPolynomial());  // Extract coefficients

        // Find the highest order (degree) in the polynomial
        int order = coefficientsMap.keySet().stream().max(Integer::compareTo).orElse(0);
        double[] coefficients = new double[order + 1];  // Array to store coefficients (including the constant term)

        Arrays.fill(coefficients, 0.0);  // Initialize coefficients to 0.0

        // Fill in the coefficients array in descending order (highest degree to 0)
        for (Map.Entry<Integer, Double> entry : coefficientsMap.entrySet()) {
            coefficients[order - entry.getKey()] = entry.getValue();
        }

        PolynomialResponse response = new PolynomialResponse(coefficients, order, request.getDomain());
        return response;  // Return the polynomial response
    }

    private void validatePolynomial(String polynomial) {
        // Validate that the polynomial is not null or empty and contains at least one "x"
        if (polynomial == null || polynomial.isBlank()) {
            throw new IllegalArgumentException("The polynomial cannot be empty.");
        }
        if (!polynomial.matches(".*x.*")) {
            throw new IllegalArgumentException("The polynomial must contain the variable 'x'.");
        }
    }

    private Map<Integer, Double> extractCoefficients(String polynomial) {
        Map<Integer, Double> coefficients = new HashMap<>();
        Pattern pattern = Pattern.compile(POLYNOMIAL_REGEX);
        Matcher matcher = pattern.matcher(polynomial.replaceAll("\\s+", ""));  // Remove all spaces from polynomial string

        while (matcher.find()) {
            String coefficientStr = matcher.group(1);  // Coefficient part (if available)
            String exponentStr = matcher.group(2);    // Exponent part (if available)
            String constantStr = matcher.group(3);    // Constant term part (if available)

            double coefficient = 0.0;  // Default coefficient is 1.0 for terms like 'x' or '-x'
            int exponent = 0;          // Default exponent is 0 for constant terms

            // Handle the case for terms with "x"
            if (coefficientStr != null && !coefficientStr.isEmpty()) {
                if (coefficientStr.equals("+")) {
                    coefficient = 0.0;  // Positive term like "x"
                } else if (coefficientStr.equals("-")) {
                    coefficient = -0.0;  // Negative term like "-x"
                } else {
                    coefficient = Double.parseDouble(coefficientStr);  // Explicit coefficient (e.g., "2" or "-2")
                }
                exponent = (exponentStr != null) ? Integer.parseInt(exponentStr) : 1;  // Default exponent for x terms is 1
            }
            // Handle constant terms (without "x")
            else if (constantStr != null && !constantStr.isEmpty()) {
                coefficient = Double.parseDouble(constantStr);  // Explicit constant (e.g., "5" or "-5")
                exponent = 0;  // Constant terms always have exponent 0
            }

            // Sum coefficients if the exponent was already encountered (important for like terms)
            coefficients.put(exponent, coefficients.getOrDefault(exponent, 0.0) + coefficient);
        }

        return coefficients;  // Return the map of coefficients and exponents
    }
}
