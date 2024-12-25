package org.example.basecase1resolution.Services;

import org.example.basecase1resolution.Models.LinearResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinearService {

    private static final String INVALID_EQUATION_MSG = "Invalid linear equation: coefficient 'a' cannot be zero.";
    private static final double EPSILON = 1e-10; // Precision for floating-point comparison

    public LinearResponse solveLinear(double a, double b) {
        List<String> roots = new ArrayList<>();

        if (Math.abs(a) < EPSILON) {
            if (Math.abs(b) < EPSILON) {
                roots.add("Infinite solutions (all x are valid)");
                return new LinearResponse(roots, "0 = 0");
            } else {
                roots.add("No solution");
                return new LinearResponse(roots, "Inconsistent equation");
            }
        }

        double root = -b / a;
        roots.add(formatNumber(root));
        return new LinearResponse(roots, String.format("(x - %s)", formatNumber(root)));
    }

    private String formatNumber(double number) {
        return String.format("%.2f", number);
    }
}
