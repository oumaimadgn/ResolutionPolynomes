package com.example.Model;

public class PolynomialResponse {

    private String[] coefficients;
    private int order;
    private String domain;

    public PolynomialResponse(String[] coefficients, int order, String domain) {
        this.coefficients = coefficients;
        this.order = order;
        this.domain = domain;
    }

    public PolynomialResponse() {

    }

    public String[] getCoefficients() {
        return coefficients;
    }

    public int getOrder() {
        return order;
    }

    public String getDomain() {
        return domain;
    }

    public void setCoefficients(String[] coefficients) {
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
                "coefficients=" + String.join(", ", coefficients) +
                ", order=" + order +
                ", domain='" + domain + '\'' +
                '}';
    }
}
