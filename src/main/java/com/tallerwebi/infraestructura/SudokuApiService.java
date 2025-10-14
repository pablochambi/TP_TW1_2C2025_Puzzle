


package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.SudokuResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
        import org.springframework.beans.factory.annotation.Value;

@Service
public class SudokuApiService {

    private static final String API_URL = "https://api.api-ninjas.com/v1/sudokugenerate?difficulty=";


    public SudokuResponse getSudokuFromApi(String difficulty) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        //    @Value("${api.ninjas.key}")
        String apiKey = "3e+ZWhN56BTMcpGdK4xoKA==2fLfeI43Pl8oOlAs";
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<SudokuResponse> response = restTemplate.exchange(
                API_URL + difficulty,
                HttpMethod.GET,
                entity,
                SudokuResponse.class
        );

        return response.getBody();
    }
}
