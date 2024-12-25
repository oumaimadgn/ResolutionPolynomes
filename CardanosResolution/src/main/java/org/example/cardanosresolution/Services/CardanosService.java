package org.example.cardanosresolution.Services;

import org.example.cardanosresolution.Models.CardanosResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardanosService {

    private static final String INVALID_DOMAIN_MSG = "Le domaine doit être 'real' ou 'complex'.";
    private static final String NON_CARDANOS_MSG = "Le coefficient 'a' ne peut pas être nul pour une équation cubique.";
    private static final double EPSILON = 1e-10; // For floating-point comparisons

    public CardanosResponse solveCardanos(double a, double b, double c, double d, String domain) {
        validateInput(a, domain);

        double A = b / a;
        double B = c / a;
        double C = d / a;

        double p = (3 * B - A * A) / 3;
        double q = (2 * A * A * A - 9 * A * B + 27 * C) / 27;
        double discriminant = (q * q / 4) + (p * p * p / 27);

        if ("real".equalsIgnoreCase(domain)) {
            return solveInRealDomain(A, p, q, discriminant);
        } else if ("complex".equalsIgnoreCase(domain)) {
            return solveInComplexDomain(A, p, q, discriminant);
        } else {
            throw new IllegalArgumentException(INVALID_DOMAIN_MSG);
        }
    }

    private void validateInput(double a, String domain) {
        if (Math.abs(a) < EPSILON) {
            throw new IllegalArgumentException(NON_CARDANOS_MSG);
        }
        if (domain == null || (!domain.equalsIgnoreCase("real") && !domain.equalsIgnoreCase("complex"))) {
            throw new IllegalArgumentException(INVALID_DOMAIN_MSG);
        }
    }

    private CardanosResponse solveInRealDomain(double A, double p, double q, double discriminant) {
        List<String> roots = new ArrayList<>();

        if (discriminant > EPSILON) {
            // One real root
            double u = Math.cbrt(-q / 2 + Math.sqrt(discriminant));
            double v = Math.cbrt(-q / 2 - Math.sqrt(discriminant));
            double root = u + v - A / 3;
            roots.add(formatNumber(root));
            return new CardanosResponse(roots, String.format("(x - %s)", formatNumber(root)));
        }
        if (Math.abs(discriminant) < EPSILON) {
            // Three real roots, two are equal
            double root1 = 2 * Math.cbrt(-q / 2) - A / 3;
            double root2 = -Math.cbrt(-q / 2) - A / 3;
            roots.add(formatNumber(root1));
            roots.add(formatNumber(root2));
            roots.add(formatNumber(root2)); // Duplicate root2 for symmetry
            return new CardanosResponse(roots, String.format("(x - %s)(x - %s)", formatNumber(root1), formatNumber(root2)));
        }
        else {
            // Three distinct real roots
            double r = Math.sqrt(-(p * p * p) / 27);
            double phi = Math.acos(-q / (2 * r));
            double root1 = 2 * Math.cbrt(r) * Math.cos(phi / 3) - A / 3;
            double root2 = 2 * Math.cbrt(r) * Math.cos((phi + 2 * Math.PI) / 3) - A / 3;
            double root3 = 2 * Math.cbrt(r) * Math.cos((phi + 4 * Math.PI) / 3) - A / 3;
            roots.add(formatNumber(root1));
            roots.add(formatNumber(root2));
            roots.add(formatNumber(root3));
            return new CardanosResponse(
                    roots,
                    String.format("(x - %s)(x - %s)(x - %s)", formatNumber(root1), formatNumber(root2), formatNumber(root3))
            );
        }
    }

    private CardanosResponse solveInComplexDomain(double A, double p, double q, double discriminant) {
        List<String> roots = new ArrayList<>();
        double u = Math.cbrt(-q / 2 + Math.sqrt(Math.abs(discriminant)));
        double v = Math.cbrt(-q / 2 - Math.sqrt(Math.abs(discriminant)));
        double realPart = (u + v) - A / 3;
        double imaginaryPart = Math.sqrt(3) * (u - v) / 2;

        roots.add(formatNumber(realPart));
        roots.add(formatComplexNumber(-realPart / 2, imaginaryPart));
        roots.add(formatComplexNumber(-realPart / 2, -imaginaryPart));

        return new CardanosResponse(
                roots,
                String.format("(x - %s)(x - %s)(x - %s)", roots.get(0), roots.get(1), roots.get(2))
        );
    }

    private String formatNumber(double number) {
        if (Math.abs(number) < EPSILON) {
            return "0.00";
        }
        return String.format("%.2f", number);
    }

    private String formatComplexNumber(double real, double imaginary) {
        String sign = imaginary < 0 ? " - " : " + ";
        return String.format("%s%s%si", formatNumber(real), sign, formatNumber(Math.abs(imaginary)));
    }
}
