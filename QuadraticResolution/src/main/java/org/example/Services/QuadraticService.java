package org.example.Services;

import org.example.Models.QuadraticResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuadraticService {

    private static final String INVALID_DOMAIN_MSG = "Le domaine doit être 'real' ou 'complex'.";
    private static final String NON_QUADRATIC_MSG = "Le coefficient 'a' ne peut pas être nul pour une équation quadratique.";
    private static final String NO_REAL_SOLUTIONS_MSG = "Pas de solution dans le domaine réel";
    private static final double EPSILON = 1e-10;  // For floating-point comparisons

    public QuadraticResponse solveQuadratic(double a, double b, double c, String domain) {
        validateInput(a, domain);

        double discriminant = calculateDiscriminant(a, b, c);

        if ("real".equalsIgnoreCase(domain)) {
            return solveInRealDomain(a, b, discriminant);
        } else if ("complex".equalsIgnoreCase(domain)) {
            return solveInComplexDomain(a, b, discriminant);
        } else {
            throw new IllegalArgumentException(INVALID_DOMAIN_MSG);
        }
    }

    private void validateInput(double a, String domain) {
        if (Math.abs(a) < EPSILON) {
            throw new IllegalArgumentException(NON_QUADRATIC_MSG);
        }
        if (domain == null || (!domain.equalsIgnoreCase("real") && !domain.equalsIgnoreCase("complex"))) {
            throw new IllegalArgumentException(INVALID_DOMAIN_MSG);
        }
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return (b * b) - (4 * a * c);
    }

    private QuadraticResponse solveInRealDomain(double a, double b, double discriminant) {
        List<String> roots = new ArrayList<>();

        if (discriminant < -EPSILON) {
            roots.add(NO_REAL_SOLUTIONS_MSG);
            return new QuadraticResponse(roots, "Le discriminant est négatif: " + formatNumber(discriminant));
        }

        if (Math.abs(discriminant) < EPSILON) {
            double root = -b / (2 * a);
            roots.add(formatNumber(root));
            String factorizedForm = String.format("%s(x - %s)²", formatNumber(a), formatNumber(root));
            return new QuadraticResponse(roots, factorizedForm);
        } else {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double root1 = (-b + sqrtDiscriminant) / (2 * a);
            double root2 = (-b - sqrtDiscriminant) / (2 * a);
            roots.add(formatNumber(root1));
            roots.add(formatNumber(root2));
            String factorizedForm = String.format("%s(x - %s)(x - %s)", formatNumber(a), formatNumber(root1), formatNumber(root2));
            return new QuadraticResponse(roots, factorizedForm);
        }
    }

    private QuadraticResponse solveInComplexDomain(double a, double b, double discriminant) {
        List<String> roots = new ArrayList<>();
        double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(Math.abs(discriminant)) / (2 * a);

        roots.add(formatComplexNumber(realPart, imaginaryPart));
        roots.add(formatComplexNumber(realPart, -imaginaryPart));

        String factorizedForm = String.format("%s(x - (%s))(x - (%s))",
                formatNumber(a), roots.get(0), roots.get(1));

        return new QuadraticResponse(roots, factorizedForm);
    }

    private String formatNumber(double number) {
        if (Math.abs(number) < EPSILON) {
            return "0.00";
        }
        return String.format("%.2f", number);
    }

    private String formatComplexNumber(double real, double imaginary) {
        if (Math.abs(imaginary) < EPSILON) {
            return formatNumber(real);
        }
        String sign = imaginary < 0 ? " - " : " + ";
        return String.format("%s%s%si", formatNumber(real), sign, formatNumber(Math.abs(imaginary)));
    }
}
