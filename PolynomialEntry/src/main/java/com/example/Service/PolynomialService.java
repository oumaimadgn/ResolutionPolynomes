package com.example.Service;

import com.example.Model.PolynomialResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PolynomialService {

    private static final String POLYNOMIAL_REGEX = "(-?\\d+\\.?\\d*)x\\^?(\\d+)?(\\s*[+-]\\s*(\\d+\\.?\\d*)x\\^?(\\d+)?)*";

    public void validatePolynomial(String polynomial) {
        if (polynomial == null || polynomial.isBlank()) {
            throw new IllegalArgumentException("The polynomial cannot be empty.");
        }
        if (!polynomial.matches(".*x.*")) {
            throw new IllegalArgumentException("The polynomial must contain the variable 'x'.");
        }
    }

    public PolynomialResponse processPolynomial(String polynomial, String domain) {
        validatePolynomial(polynomial);

        Map<Integer, Double> coefficientsMap = extractCoefficients(polynomial);

        // Determine polynomial order
        int order = coefficientsMap.keySet().stream().max(Integer::compareTo).orElse(0);

        // Build coefficient array
        String[] coefficients = new String[order + 1];
        Arrays.fill(coefficients, "0");

        for (Map.Entry<Integer, Double> entry : coefficientsMap.entrySet()) {
            coefficients[order - entry.getKey()] = String.valueOf(entry.getValue());
        }

        return new PolynomialResponse(coefficients, order, domain);
    }

    private Map<Integer, Double> extractCoefficients(String polynomial) {
        Map<Integer, Double> coefficients = new HashMap<>();
        Pattern pattern = Pattern.compile(POLYNOMIAL_REGEX);
        Matcher matcher = pattern.matcher(polynomial.replaceAll("\\s+", ""));

        while (matcher.find()) {
            String coefficientStr = matcher.group(1);
            String exponentStr = matcher.group(2);
            String constantStr = matcher.group(3);

            double coefficient = 1.0; // Default coefficient
            int exponent = 0; // Default exponent

            if (coefficientStr != null) {
                coefficient = coefficientStr.isEmpty() || coefficientStr.equals("+") ? 1.0 :
                        coefficientStr.equals("-") ? -1.0 : Double.parseDouble(coefficientStr);
                exponent = (exponentStr != null) ? Integer.parseInt(exponentStr) : 1;
            } else if (constantStr != null) {
                coefficient = Double.parseDouble(constantStr);
                exponent = 0;
            }

            coefficients.put(exponent, coefficients.getOrDefault(exponent, 0.0) + coefficient);
        }

        return coefficients;
    }
}
