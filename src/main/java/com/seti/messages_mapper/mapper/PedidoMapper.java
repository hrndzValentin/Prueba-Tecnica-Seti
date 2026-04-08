package com.seti.messages_mapper.mapper;

import com.seti.messages_mapper.dto.PedidoRequest;
import com.seti.messages_mapper.dto.SoapRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(source = "numPedido", target = "pedido")
    @Mapping(source = "cantidadPedido", target = "cantidad")
    @Mapping(source = "codigoEAN", target = "ean")
    @Mapping(source = "nombreProducto", target = "producto")
    @Mapping(source = "numDocumento", target = "cedula")
    @Mapping(source = "direccion", target = "direccion")
    SoapRequest toSoapRequest(PedidoRequest request);
}