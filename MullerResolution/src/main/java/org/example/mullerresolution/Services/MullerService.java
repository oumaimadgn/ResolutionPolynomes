package org.example.mullerresolution.Services;

import org.example.mullerresolution.Models.PolynomialEntry;
import org.example.mullerresolution.Models.MullerResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MullerService {

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

    public MullerResponse solvePolynomial(PolynomialEntry entry) {
        List<String> roots = new ArrayList<>();
        double[] coefficients = entry.getCoefficients();
        int order = entry.getOrder();

        if (coefficients == null || coefficients.length != order + 1) {
            throw new IllegalArgumentException("The number of coefficients must match the polynomial order + 1.");
        }

        for (int i = 0; i < order; i++) {
            double root = findRootUsingMuller(coefficients, Math.random(), Math.random(), Math.random());
            if (!Double.isNaN(root)) {
                roots.add(String.format("%.6f", root));
                coefficients = deflatePolynomial(coefficients, root);
            }
        }

        return new MullerResponse(roots, buildFactorizedForm(roots));
    }

    private double findRootUsingMuller(double[] coefficients, double x0, double x1, double x2) {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double f0 = evaluatePolynomial(coefficients, x0);
            double f1 = evaluatePolynomial(coefficients, x1);
            double f2 = evaluatePolynomial(coefficients, x2);

            double h1 = x1 - x0;
            double h2 = x2 - x1;
            double delta1 = (f1 - f0) / h1;
            double delta2 = (f2 - f1) / h2;
            double a = (delta2 - delta1) / (h2 + h1);
            double b = a * h2 + delta2;
            double c = f2;

            double discriminant = Math.sqrt(b * b - 4 * a * c);
            double denominator = (b > 0) ? (b + discriminant) : (b - discriminant);

            if (denominator == 0) {
                return Double.NaN;
            }

            double dx = -2 * c / denominator;
            double root = x2 + dx;

            if (Math.abs(dx) < TOLERANCE) {
                return root;
            }

            x0 = x1;
            x1 = x2;
            x2 = root;
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