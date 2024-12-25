package org.example.geminiresolution.Controller;

import org.example.geminiresolution.Models.GeminiResponse;
import org.example.geminiresolution.Models.PolynomialEntry;
import org.example.geminiresolution.Services.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Gemini")
public class GeminiController {

    private final GeminiService GeminiService;

    public GeminiController(GeminiService GeminiService) {
        this.GeminiService = GeminiService;
    }

    @PostMapping("/solve")
    public ResponseEntity<GeminiResponse> solveGemini(@RequestBody PolynomialEntry polynomialEntry) {
        if (polynomialEntry.getCoefficients() == null || polynomialEntry.getOrder() < 1) {
            return ResponseEntity.badRequest().body(
                    new GeminiResponse(
                            "Invalid polynomial input: ensure coefficients are provided and order is valid."
                    )
            );
        }

        try {
            GeminiResponse response = GeminiService.solvePolynomial(polynomialEntry);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new GeminiResponse(
                            e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GeminiResponse(
                            "An unexpected error occurred while solving the polynomial."
                    )
            );
        }
    }
}
