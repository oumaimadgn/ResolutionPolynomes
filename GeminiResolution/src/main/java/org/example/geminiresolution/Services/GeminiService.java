package org.example.geminiresolution.Services;

import org.example.geminiresolution.Models.GeminiResponse;
import org.example.geminiresolution.Models.PolynomialEntry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeminiService {

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyDXO4KxNq5VkcigPfyTgAkA3YD6m-dPNnM";

    private final RestTemplate restTemplate;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeminiResponse solvePolynomial(PolynomialEntry entry) {
        String polynomial = buildPolynomialString(entry.getCoefficients());
        String requestBody = buildRequestBody(polynomial, entry.getDomain(), entry.getMethod());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                GEMINI_API_URL,
                HttpMethod.POST,
                request,
                Map.class
        );

        String resultText = extractResultText(response.getBody());
        return new GeminiResponse(resultText);
    }

    private String buildPolynomialString(double[] coefficients) {
        StringBuilder polynomial = new StringBuilder();
        int degree = coefficients.length - 1;
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                if (polynomial.length() > 0 && coefficients[i] > 0) {
                    polynomial.append("+");
                }
                polynomial.append(coefficients[i]);
                if (degree - i > 0) {
                    polynomial.append("x^").append(degree - i);
                }
            }
        }
        return polynomial.toString();
    }

    private String buildRequestBody(String polynomial, String domain, String method) {
        return String.format(
                "{\"contents\":[{\"parts\":[{\"text\":\"explique et detaille la resolution du polynome aux details suivants: { 'polynomial': '%s', 'domain': '%s', 'method': '%s' }\"}]}]}",
                polynomial, domain, method
        );
    }

    private String extractResultText(Map responseBody) {
        if (responseBody != null && responseBody.containsKey("candidates")) {
            Object candidates = responseBody.get("candidates");
            if (candidates instanceof Iterable) {
                for (Object candidate : (Iterable<?>) candidates) {
                    if (candidate instanceof Map && ((Map<?, ?>) candidate).containsKey("content")) {
                        Object content = ((Map<?, ?>) candidate).get("content");
                        if (content instanceof Map && ((Map<?, ?>) content).containsKey("parts")) {
                            Object parts = ((Map<?, ?>) content).get("parts");
                            if (parts instanceof Iterable) {
                                for (Object part : (Iterable<?>) parts) {
                                    if (part instanceof Map && ((Map<?, ?>) part).containsKey("text")) {
                                        return (String) ((Map<?, ?>) part).get("text");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "No valid response received from Gemini API.";
    }

}
