package org.example.fixedpointresolution.Services;

import org.example.fixedpointresolution.Models.FixedPointResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FixedPointService {

    private static final double TOLERANCE = 1e-6; // Tolérance pour convergence
    private static final int MAX_ITERATIONS = 1000; // Nombre maximum d'itérations

    public FixedPointResponse solveFixedPoint(double initialGuess, String domain) {
        List<String> roots = new ArrayList<>();
        double x0 = initialGuess;
        double x1;

        int iterations = 0;
        boolean converged = false;

        try {
            while (iterations < MAX_ITERATIONS) {
                x1 = g(x0); // Appliquer la fonction g(x)
                if (Math.abs(x1 - x0) < TOLERANCE) {
                    converged = true;
                    roots.add(String.format("%.6f", x1));
                    break;
                }
                x0 = x1;
                iterations++;
            }

            if (converged) {
                return new FixedPointResponse(roots, "✅ Convergence atteinte après " + iterations + " itérations.");
            } else {
                return new FixedPointResponse(roots, "❌ La méthode n'a pas convergé après " + MAX_ITERATIONS + " itérations.");
            }

        } catch (Exception e) {
            return new FixedPointResponse(roots, "❌ Une erreur est survenue : " + e.getMessage());
        }
    }

    /**
     * Fonction g(x) définie pour la méthode des points fixes.
     * Vous pouvez personnaliser cette fonction selon votre polynôme.
     */
    private double g(double x) {
        // Exemple : g(x) = sqrt((x + 1) / 2)
        return Math.sqrt((x + 1) / 2);
    }
}
