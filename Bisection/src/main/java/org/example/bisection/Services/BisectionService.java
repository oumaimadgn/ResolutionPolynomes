package org.example.bisection.Services;

import org.example.bisection.Models.PolynomialEntry;
import org.example.bisection.Models.PolynomialResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class BisectionService {
    private static final Logger logger = LoggerFactory.getLogger(BisectionService.class);

    /**
     * Évalue le polynôme à un point donné.
     */
    private double evaluatePolynomial(PolynomialEntry entry, double x) {
        double result = 0.0;
        for (int i = 0; i < entry.getCoefficients().length; i++) {
            result += entry.getCoefficients()[i] * Math.pow(x, entry.getOrder() - i);
        }
        logger.debug("Évaluation du polynôme à x={}: {}", x, result);
        return result;
    }

    /**
     * Détecte automatiquement un intervalle valide où la fonction change de signe.
     */
    private double[] detectInterval(PolynomialEntry entry) {
        logger.info("🔍 Détection automatique d'un intervalle réel...");

        double start = -100.0; // Étendre la recherche
        double end = 100.0;
        double step = 0.1; // Précision du balayage

        double previousValue = evaluatePolynomial(entry, start);

        for (double x = start + step; x <= end; x += step) {
            double currentValue = evaluatePolynomial(entry, x);

            if (previousValue * currentValue < 0) {
                logger.info("✅ Intervalle détecté : [{}, {}]", x - step, x);
                return new double[]{x - step, x};
            }

            previousValue = currentValue;
        }

        throw new IllegalArgumentException("❌ Impossible de détecter un intervalle valide pour la racine réelle.");
    }

    /**
     * Trouve les racines du polynôme à l'aide de la méthode de bisection.
     */
    public PolynomialResponse findRoots(PolynomialEntry entry, double tolerance, int maxIterations) {
        logger.info("📝 Domaine sélectionné : {}", entry.getDomain());

        if ("complex".equalsIgnoreCase(entry.getDomain())) {
            logger.warn("⚠️ Le domaine complexe n'est pas pris en charge par la méthode de bisection.");
            throw new UnsupportedOperationException("La méthode de bisection ne prend pas en charge les domaines complexes. Utilisez Newton-Raphson à la place.");
        }

        // Détection automatique de l'intervalle
        double[] interval = detectInterval(entry);
        double a = interval[0];
        double b = interval[1];

        double fA = evaluatePolynomial(entry, a);
        double fB = evaluatePolynomial(entry, b);

        if (fA * fB > 0) {
            logger.error("❌ La fonction ne change pas de signe sur l'intervalle détecté [{}; {}]: f(a)={}, f(b)={}", a, b, fA, fB);
            throw new IllegalArgumentException(
                    String.format("La fonction ne change pas de signe sur l'intervalle [%f, %f]. f(a)=%f, f(b)=%f", a, b, fA, fB));
        }

        List<String> roots = new ArrayList<>();
        double root = 0;
        int iterations = 0;

        logger.info("🚀 Démarrage de l'algorithme de bisection...");
        while ((b - a) / 2 > tolerance && iterations < maxIterations) {
            root = (a + b) / 2;
            double fRoot = evaluatePolynomial(entry, root);

            logger.debug("🔄 Iteration {}: a={}, b={}, root={}, f(root)={}", iterations, a, b, root, fRoot);

            if (Math.abs(fRoot) < tolerance) {
                roots.add(String.format("%.6f", root));
                break;
            }

            if (fA * fRoot < 0) {
                b = root;
                fB = fRoot;
            } else {
                a = root;
                fA = fRoot;
            }

            iterations++;
        }

        String factorizedForm = roots.isEmpty()
                ? "Pas de factorisation possible"
                : String.format("(x - %.6f)", root);

        logger.info("✅ Racines trouvées : {}, Forme factorisée : {}", roots, factorizedForm);
        return new PolynomialResponse(roots, factorizedForm);
    }
}
