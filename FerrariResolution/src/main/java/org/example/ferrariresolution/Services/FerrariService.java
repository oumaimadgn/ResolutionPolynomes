package org.example.ferrariresolution.Services;

import org.example.ferrariresolution.Models.FerrariResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FerrariService {

    private static final String INVALID_DOMAIN_MSG = "Le domaine doit être 'real' ou 'complex'.";
    private static final String NON_QUARTIC_MSG = "Le coefficient 'a' ne peut pas être nul pour une équation quartique.";
    private static final double EPSILON = 1e-10; // Pour la précision flottante

    public FerrariResponse solveFerrari(double a, double b, double c, double d, double e, String domain) {
        validateInput(a, domain);

        // Normalisation
        double A = b / a;
        double B = c / a;
        double C = d / a;
        double D = e / a;

        // Étape 1: Réduction de l'équation quartique
        double p = (8 * B - 3 * A * A) / 8;
        double q = (A * A * A - 4 * A * B + 8 * C) / 8;
        double r = (-3 * A * A * A * A + 256 * D - 64 * A * C + 16 * A * A * B) / 256;

        // Étape 2: Résolution par méthode Ferrari
        double discriminant = q * q - 4 * p * r;

        List<String> roots = new ArrayList<>();

        if (discriminant > EPSILON) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double y1 = (-q + sqrtDiscriminant) / 2;
            double y2 = (-q - sqrtDiscriminant) / 2;

            roots.addAll(solveQuadratic(1, Math.sqrt(y1), r / y1));
            roots.addAll(solveQuadratic(1, -Math.sqrt(y1), r / y1));
        } else if (Math.abs(discriminant) < EPSILON) {
            double y = -q / 2;
            roots.addAll(solveQuadratic(1, Math.sqrt(y), r / y));
            roots.addAll(solveQuadratic(1, -Math.sqrt(y), r / y));
        } else {
            // Racines complexes
            roots.add("Complex Roots");
        }

        String factorizedForm = String.format("(x - %s)(x - %s)(x - %s)(x - %s)",
                roots.get(0), roots.get(1), roots.get(2), roots.get(3));

        return new FerrariResponse(roots, factorizedForm);
    }

    private List<String> solveQuadratic(double a, double b, double c) {
        List<String> roots = new ArrayList<>();
        double discriminant = b * b - 4 * a * c;

        if (discriminant > EPSILON) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            roots.add(formatNumber(root1));
            roots.add(formatNumber(root2));
        } else if (Math.abs(discriminant) < EPSILON) {
            double root = -b / (2 * a);
            roots.add(formatNumber(root));
        } else {
            roots.add(formatComplexNumber(-b / (2 * a), Math.sqrt(-discriminant) / (2 * a)));
            roots.add(formatComplexNumber(-b / (2 * a), -Math.sqrt(-discriminant) / (2 * a)));
        }

        return roots;
    }

    private void validateInput(double a, String domain) {
        if (Math.abs(a) < EPSILON) {
            throw new IllegalArgumentException(NON_QUARTIC_MSG);
        }
        if (domain == null || (!domain.equalsIgnoreCase("real") && !domain.equalsIgnoreCase("complex"))) {
            throw new IllegalArgumentException(INVALID_DOMAIN_MSG);
        }
    }

    private String formatNumber(double number) {
        return String.format("%.2f", number);
    }

    private String formatComplexNumber(double real, double imaginary) {
        return String.format("%.2f + %.2fi", real, imaginary);
    }
}
