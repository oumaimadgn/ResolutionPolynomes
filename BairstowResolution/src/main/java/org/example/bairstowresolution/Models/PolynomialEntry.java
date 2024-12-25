package org.example.bairstowresolution.Models;

public class PolynomialEntry {
    private double[] coefficients;
    private int order;
    private String domain;

    public PolynomialEntry(double[] coefficients, int order, String domain) {
        this.coefficients = coefficients;
        this.order = order;
        this.domain = domain;
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
}
