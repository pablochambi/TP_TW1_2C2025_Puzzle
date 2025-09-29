package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.SudokuResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SudokuApiService {


    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "3e+ZWhN56BTMcpGdK4xoKA==2fLfeI43Pl8oOlAs";

    public SudokuResponse getSudoku() {
        // URL de la API Sudoku Ninja (puedes cambiar la dificultad: easy, medium, hard)
        String url = "https://api.api-ninjas.com/v1/sudokugenerate?difficulty=easy";

        // Headers con la API Key
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        // Envolvemos los headers en la request
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Ejecutamos la request
        ResponseEntity<SudokuResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                SudokuResponse.class
        );

        // Retornamos el body ya convertido en SudokuResponse
        return response.getBody();
    }
}
