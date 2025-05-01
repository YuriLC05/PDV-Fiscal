package com.pdvfiscal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ReceitaFederalService {
    /**
     * Consulta CNPJ na Receita Federal usando API pública (exemplo: receitaws.com.br)
     * @param cnpj CNPJ a ser consultado
     * @return JSON com dados cadastrais ou mensagem de erro
     */
    public String consultarCNPJ(String cnpj) {
        try {
            String url = "https://www.receitaws.com.br/v1/cnpj/" + cnpj;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Erro ao consultar CNPJ: " + e.getMessage();
        }
    }
}
