package org.example.bisection.Controller;

import org.example.bisection.Models.PolynomialEntry;
import org.example.bisection.Models.PolynomialResponse;
import org.example.bisection.Services.BisectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bisection")
public class BisectionController {

    private final BisectionService bisectionService;

    public BisectionController(BisectionService bisectionService) {
        this.bisectionService = bisectionService;
    }

    /**
     * Résout un polynôme en utilisant la méthode de la bisection.
     *
     * @param polynomialEntry Les détails du polynôme (coefficients, ordre, domaine)
     * @param tolerance La tolérance pour l'approximation de la racine
     * @param maxIterations Le nombre maximal d'itérations
     * @return La réponse contenant les racines et la forme factorisée
     */
    @PostMapping("/solve")
    public ResponseEntity<PolynomialResponse> solvePolynomial(
            @RequestBody PolynomialEntry polynomialEntry,
            @RequestParam(defaultValue = "0.001") double tolerance,
            @RequestParam(defaultValue = "100") int maxIterations) {

        PolynomialResponse response = bisectionService.findRoots(polynomialEntry, tolerance, maxIterations);
        return ResponseEntity.ok(response);
    }
}
