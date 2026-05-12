package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.ChatMensajeRequest;
import com.upc.perulisto.DTO.ChatMensajeResponse;
import com.upc.perulisto.services.ChatFinanzasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/API")
public class ChatFinanzasController {

    @Autowired
    private ChatFinanzasService chatFinanzasService;

    /**
     * US17 — Chat con IA sobre Finanzas
     * Permite al usuario hacer preguntas sobre finanzas personales
     * y recibir respuestas generadas por inteligencia artificial.
     *
     * POST /API/chat/finanzas
     * Body: { "usuarioId": 1, "mensaje": "¿Cómo puedo ahorrar más?" }
     * Response: { "respuesta": "...", "timestamp": "..." }
     */
    @PostMapping("/chat/finanzas")
    public ResponseEntity<ChatMensajeResponse> consultarChat(
            @RequestBody ChatMensajeRequest request) {
        ChatMensajeResponse response = chatFinanzasService.procesarMensaje(request);
        return ResponseEntity.ok(response);
    }
}