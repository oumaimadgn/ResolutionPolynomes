package com.example.Api_Gateway.Controller;

import com.example.Api_Gateway.Model.PolynomialRequest;
import com.example.Api_Gateway.Model.PolynomialResponse;
import com.example.Api_Gateway.Service.PolynomialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/polynomial")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}
)
public class GatewayController {

    private final PolynomialService polynomialService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public GatewayController(PolynomialService polynomialService, WebClient.Builder webClientBuilder) {
        this.polynomialService = polynomialService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/evaluate")
    public Mono<String> processPolynomial(@RequestBody PolynomialRequest request) {
        try {
            PolynomialResponse response = polynomialService.processPolynomial(request);

            if (response.getOrder() == 1 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToLinearService(request, response);
            } else if (response.getOrder() == 2 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToQuadraticService(request, response);
            } else if (response.getOrder() == 3 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToCardanosService(request, response);
            } else if (response.getOrder() == 4 && "numerical".equalsIgnoreCase(request.getMethod())) {
                return routeToFerrariService(request, response);
            } else if ("newton".equalsIgnoreCase(request.getMethod())) {
                return routeToNewtonRaphsonService(request, response);
            } else if ("bisection".equalsIgnoreCase(request.getMethod())) {
                return routeToBisectionService(request, response);
            } else if ("bairstow".equalsIgnoreCase(request.getMethod())) {
                return routeToBairstowService(request, response);
            } else if ("muller".equalsIgnoreCase(request.getMethod())) {
                return routeToMullerService(request, response);
            } else if ("fixedpoint".equalsIgnoreCase(request.getMethod())) {
                return routeToFixedPointService(request, response);
            } else {
                return Mono.just("❌ Veuillez fournir un polynôme valide avec une méthode et un ordre supportés.");
            }

        } catch (Exception e) {
            return Mono.just("❌ Une erreur est survenue lors du traitement du polynôme : " + e.getMessage());
        }
    }

    /**
     * Redirige la requête vers le service LinearResolution.
     */
    private Mono<String> routeToLinearService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8089/api/v1/linear/solve", buildPayload(request, response), "LinearResolution");
    }

    /**
     * Redirige la requête vers le service QuadraticResolution.
     */
    private Mono<String> routeToQuadraticService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8082/api/v1/quadratic/solve", buildPayload(request, response), "QuadraticResolution");
    }

    /**
     * Redirige la requête vers le service CardanosResolution.
     */
    private Mono<String> routeToCardanosService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8083/api/v1/cardanos/solve", buildPayload(request, response), "CardanosResolution");
    }

    /**
     * Redirige la requête vers le service FerrariResolution.
     */
    private Mono<String> routeToFerrariService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8090/api/v1/ferrari/solve", buildPayload(request, response), "FerrariResolution");
    }

    /**
     * Redirige la requête vers le service NewtonRaphsonResolution.
     */
    private Mono<String> routeToNewtonRaphsonService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8084/api/v1/newtonraphson/solve", buildPayload(request, response), "NewtonRaphsonResolution");
    }

    /**
     * Redirige la requête vers le service BisectionResolution.
     */
    private Mono<String> routeToBisectionService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8085/api/v1/bisection/solve", buildPayload(request, response), "BisectionResolution");
    }

    /**
     * Redirige la requête vers le service BairstowResolution.
     */
    private Mono<String> routeToBairstowService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8086/api/v1/bairstow/solve", buildPayload(request, response), "BairstowResolution");
    }

    /**
     * Redirige la requête vers le service MullerResolution.
     */
    private Mono<String> routeToMullerService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8087/api/v1/muller/solve", buildPayload(request, response), "MullerResolution");
    }

    /**
     * Redirige la requête vers le service FixedPointResolution.
     */
    private Mono<String> routeToFixedPointService(PolynomialRequest request, PolynomialResponse response) {
        return routeToService("http://localhost:8091/api/v1/fixedpoint/solve?initialGuess=1.0&domain=real", buildPayload(request, response), "FixedPointResolution");
    }

    /**
     * Envoie une requête générique vers un service donné.
     */
    private Mono<String> routeToService(String uri, Map<String, Object> payload, String serviceName) {
        try {
            System.out.println("🔄 Sending JSON to " + serviceName + ":\n" + payload);
            return webClientBuilder.build()
                    .post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(error -> {
                        System.err.println("❌ Erreur avec " + serviceName + ": " + error.getMessage());
                        return Mono.just("❌ Erreur avec " + serviceName + ": " + error.getMessage());
                    });
        } catch (Exception e) {
            return Mono.just("❌ Erreur critique avec " + serviceName + ": " + e.getMessage());
        }
    }

    /**
     * Construit le payload pour les services.
     */
    private Map<String, Object> buildPayload(PolynomialRequest request, PolynomialResponse response) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("coefficients", response.getCoefficients());
        payload.put("order", response.getOrder());
        payload.put("domain", response.getDomain());
        payload.put("method", request.getMethod());
        return payload;
    }
}
