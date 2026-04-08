package com.seti.messages_mapper.controller;

import com.seti.messages_mapper.dto.PedidoRequest;
import com.seti.messages_mapper.dto.PedidoResponse;
import com.seti.messages_mapper.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    @PostMapping
    public ResponseEntity<PedidoResponse> enviarPedido(
            @Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.procesarPedido(request);

        return ResponseEntity.ok(response);
    }
}
