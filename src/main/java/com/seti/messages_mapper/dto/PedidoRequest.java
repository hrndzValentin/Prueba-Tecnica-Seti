package com.seti.messages_mapper.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoRequest {
    @NotBlank
    private String numPedido;
    @NotBlank
    private String cantidadPedido;
    @NotBlank
    private String codigoEAN;
    @NotBlank
    private String nombreProducto;
    @NotBlank
    private String numDocumento;
    @NotBlank
    private String direccion;
}
