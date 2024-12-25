package org.example.cardanosresolution.Controller;

import org.example.cardanosresolution.Models.PolynomialEntry;
import org.example.cardanosresolution.Models.CardanosResponse;
import org.example.cardanosresolution.Services.CardanosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cardanos")
public class CardanosController {

    private final CardanosService cardanosService;

    public CardanosController(CardanosService cardanosService) {
        this.cardanosService = cardanosService;
    }

    @PostMapping("/solve")
    public ResponseEntity<CardanosResponse> solveCardanos(@RequestBody PolynomialEntry polynomialEntry) {
        if (polynomialEntry.getOrder() != 3) {
            return ResponseEntity.badRequest().body(
                    new CardanosResponse(
                            List.of("Invalid polynomial order"),
                            "Only cubic (order 3) polynomials are supported"
                    )
            );
        }

        double[] coefficients = polynomialEntry.getCoefficients();
        if (coefficients == null || coefficients.length != 4) {
            return ResponseEntity.badRequest().body(
                    new CardanosResponse(
                            List.of("Invalid coefficients"),
                            "Cubic equations require exactly 4 coefficients (a, b, c, d)"
                    )
            );
        }

        double a = coefficients[0];
        double b = coefficients[1];
        double c = coefficients[2];
        double d = coefficients[3];

        CardanosResponse response = cardanosService.solveCardanos(a, b, c, d, polynomialEntry.getDomain());
        return ResponseEntity.ok(response);
    }
}
