package com.pdvfiscal.controller;

import com.pdvfiscal.service.FiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fiscal")
public class FiscalController {

    @Autowired
    private FiscalService fiscalService;

    @PostMapping("/emitir-nfe")
    public ResponseEntity<String> emitirNFe() {
        try {
            fiscalService.emitirNFe();
            return ResponseEntity.ok("NFe emitida com sucesso (mock)");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao emitir NFe: " + e.getMessage());
        }
    }

    @PostMapping("/cancelar-nfe")
    public ResponseEntity<String> cancelarNFe() {
        try {
            fiscalService.cancelarNFe();
            return ResponseEntity.ok("NFe cancelada com sucesso (mock)");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao cancelar NFe: " + e.getMessage());
        }
    }

    @GetMapping("/consultar-nfe")
    public ResponseEntity<String> consultarNFe() {
        try {
            fiscalService.consultarNFe();
            return ResponseEntity.ok("Consulta de NFe realizada (mock)");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao consultar NFe: " + e.getMessage());
        }
    }
}
