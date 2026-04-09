package com.seti.messages_mapper.controller;

import com.seti.messages_mapper.dto.PedidoRequest;
import com.seti.messages_mapper.dto.PedidoResponse;
import com.seti.messages_mapper.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> enviarPedido(
            @Valid @RequestBody PedidoRequest request) {

        log.info("POST /api/pedidos - Inicio. numPedido={}", request.getNumPedido());
        log.debug("Request recibido: {}", request);

        try {
            PedidoResponse response = pedidoService.procesarPedido(request);

            log.info("POST /api/pedidos - Exitoso. numPedido={}, codigoEnvio={}",
                    request.getNumPedido(), response.getCodigoEnvio());

            log.debug("Response enviado: {}", response);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("POST /api/pedidos - Error procesando pedido. numPedido={}",
                    request.getNumPedido(), e);
            throw e;
        }
    }
}