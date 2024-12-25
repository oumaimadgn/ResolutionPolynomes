package org.example.newtonraphsonresolution.Services;

import org.example.newtonraphsonresolution.Models.PolynomialEntry;
import org.example.newtonraphsonresolution.Models.PolynomialResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewtonRaphsonService {

    private static final double TOLERANCE = 1e-6;
    private static final int MAX_ITERATIONS = 1000;

    private double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0.0;
        int degree = coefficients.length - 1;
        for (int i = 0; i <= degree; i++) {
            result += coefficients[i] * Math.pow(x, degree - i);
        }
        return result;
    }

    private double evaluateDerivative(double[] coefficients, double x) {
        double result = 0.0;
        int degree = coefficients.length - 1;
        for (int i = 0; i < degree; i++) {
            result += coefficients[i] * (degree - i) * Math.pow(x, degree - i - 1);
        }
        return result;
    }

    public PolynomialResponse solvePolynomial(PolynomialEntry entry) {
        List<String> roots = new ArrayList<>();
        double[] coefficients = entry.getCoefficients();
        int order = entry.getOrder();

        if (coefficients == null || coefficients.length != order + 1) {
            throw new IllegalArgumentException("The number of coefficients must match the polynomial order + 1.");
        }

        for (int i = 0; i < order; i++) {
            double guess = Math.random() * 10 - 5; // Random initial guess between -5 and 5
            double root = findRootUsingNewtonRaphson(coefficients, guess);
            if (!Double.isNaN(root)) {
                roots.add(String.format("%.6f", root));
                coefficients = deflatePolynomial(coefficients, root);
            }
        }

        return new PolynomialResponse(roots, buildFactorizedForm(roots));
    }

    private double findRootUsingNewtonRaphson(double[] coefficients, double initialGuess) {
        double x = initialGuess;
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double f = evaluatePolynomial(coefficients, x);
            double fPrime = evaluateDerivative(coefficients, x);

            if (Math.abs(fPrime) < TOLERANCE) {
                break;
            }

            double nextX = x - f / fPrime;

            if (Math.abs(nextX - x) < TOLERANCE) {
                return nextX;
            }

            x = nextX;
        }
        return Double.NaN;
    }

    private double[] deflatePolynomial(double[] coefficients, double root) {
        int degree = coefficients.length - 1;
        double[] newCoefficients = new double[degree];
        newCoefficients[0] = coefficients[0];

        for (int i = 1; i < degree; i++) {
            newCoefficients[i] = coefficients[i] + root * newCoefficients[i - 1];
        }

        return newCoefficients;
    }

    private String buildFactorizedForm(List<String> roots) {
        StringBuilder factorizedForm = new StringBuilder();
        for (String root : roots) {
            factorizedForm.append("(x - ").append(root).append(") ");
        }
        return factorizedForm.toString().trim();
    }
}
