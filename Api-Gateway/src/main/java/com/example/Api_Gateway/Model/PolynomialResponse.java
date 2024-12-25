package com.example.Api_Gateway.Model;

import java.util.Arrays;

public class PolynomialResponse {

    private double[] coefficients;  // Changed to double[]
    private int order;
    private String domain;
    private String method;

    // Constructor with method
    public PolynomialResponse(double[] coefficients, int order, String domain) {
        this.coefficients = coefficients;
        this.order = order;
        this.domain = domain;
    }

    public PolynomialResponse() {
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public int getOrder() {
        return order;
    }

    public String getDomain() {
        return domain;
    }

    public void setCoefficients(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "PolynomialResponse{" +
                "coefficients=" + Arrays.toString(coefficients) + // Use Arrays.toString for better output
                ", order=" + order +
                ", domain='" + domain + '\'' +
                '}';
    }
}
