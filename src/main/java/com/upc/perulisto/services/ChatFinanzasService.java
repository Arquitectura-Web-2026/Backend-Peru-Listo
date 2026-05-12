package com.upc.perulisto.services;

import com.upc.perulisto.DTO.ChatMensajeRequest;
import com.upc.perulisto.DTO.ChatMensajeResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class ChatFinanzasService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    // Usando Gemini 2.5 Flash
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    private static final String SYSTEM_PROMPT = "Eres un asistente financiero personal de la aplicación PeruListo. " +
            "Responde de forma clara y concisa en español sobre finanzas en Perú.";

    public ChatMensajeResponse procesarMensaje(ChatMensajeRequest request) {
        try {
            String finalUrl = BASE_URL + "?key=" + geminiApiKey.trim();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode body = mapper.createObjectNode();

            // 1. Configuración de System Instruction
            ObjectNode systemInstruction = mapper.createObjectNode();
            ArrayNode sParts = mapper.createArrayNode();
            sParts.add(mapper.createObjectNode().put("text", SYSTEM_PROMPT));
            systemInstruction.set("parts", sParts);
            body.set("system_instruction", systemInstruction);

            // 2. Contenido del mensaje del usuario
            ArrayNode contents = mapper.createArrayNode();
            ObjectNode userRole = mapper.createObjectNode();
            userRole.put("role", "user");
            ArrayNode uParts = mapper.createArrayNode();

            // Limpieza para evitar errores de parseo JSON
            String mensajeLimpio = request.getMensaje().replaceAll("[\\n\\t\\r]", " ");
            uParts.add(mapper.createObjectNode().put("text", mensajeLimpio));

            userRole.set("parts", uParts);
            contents.add(userRole);
            body.set("contents", contents);

            // 3. Ejecución de la petición
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // --- VALIDACIÓN DE ERROR 429 (CUOTA EXCEDIDA) ---
            if (httpResponse.statusCode() == 429) {
                return new ChatMensajeResponse(
                        "te quedaste sin tokens para poder seguir chateando con la ia espera que se refresque",
                        LocalDateTime.now()
                );
            }

            // Manejo de otros errores técnicos
            if (httpResponse.statusCode() != 200) {
                System.err.println("DETALLE ERROR GOOGLE: " + httpResponse.body());
                return new ChatMensajeResponse("Error en el servicio (" + httpResponse.statusCode() + ")", LocalDateTime.now());
            }

            // 4. Procesar respuesta exitosa
            JsonNode root = mapper.readTree(httpResponse.body());
            String textoRespuesta = root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText("Respuesta no disponible.");

            return new ChatMensajeResponse(textoRespuesta, LocalDateTime.now());

        } catch (Exception e) {
            e.printStackTrace();
            return new ChatMensajeResponse("Error crítico en el backend del chat.", LocalDateTime.now());
        }
    }
}