package org.example.mullerresolution.Controller;

import org.example.mullerresolution.Models.PolynomialEntry;
import org.example.mullerresolution.Models.MullerResponse;
import org.example.mullerresolution.Services.MullerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/muller")
public class MullerController {

    private final MullerService mullerService;

    public MullerController(MullerService mullerService) {
        this.mullerService = mullerService;
    }

    @PostMapping("/solve")
    public ResponseEntity<MullerResponse> solveMuller(@RequestBody PolynomialEntry polynomialEntry) {
        if (polynomialEntry.getCoefficients() == null || polynomialEntry.getOrder() < 1) {
            return ResponseEntity.badRequest().body(
                    new MullerResponse(
                            null,
                            "Invalid polynomial input: ensure coefficients are provided and order is valid."
                    )
            );
        }

        try {
            MullerResponse response = mullerService.solvePolynomial(polynomialEntry);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new MullerResponse(
                            null,
                            e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new MullerResponse(
                            null,
                            "An unexpected error occurred while solving the polynomial."
                    )
            );
        }
    }
}
