package com.seti.messages_mapper.dto;

import lombok.Data;

@Data
public class PedidoRequest {
    private String numPedido;
    private String cantidadPedido;
    private String codigoEAN;
    private String nombreProducto;
    private String numDocumento;
    private String direccion;
}
