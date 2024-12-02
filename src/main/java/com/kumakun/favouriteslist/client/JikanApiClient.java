package com.kumakun.favouriteslist.client;

import com.kumakun.favouriteslist.client.dto.JikanResponseDTO;
import com.kumakun.favouriteslist.exception.JikanApiException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JikanApiClient {

    private final WebClient webClient;

    public JikanApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<JikanResponseDTO> getAnimeData(String param) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/anime")
                        .queryParam("q", param)
                        .queryParam("limit", 1)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new JikanApiException("Client error occurred")))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new JikanApiException("Internal server error occurred")))
                .bodyToMono((JikanResponseDTO.class));
    }
}
