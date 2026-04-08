package com.seti.messages_mapper.service;

import com.seti.messages_mapper.client.SoapClient;
import com.seti.messages_mapper.dto.PedidoRequest;
import com.seti.messages_mapper.dto.PedidoResponse;
import com.seti.messages_mapper.dto.SoapRequest;
import com.seti.messages_mapper.mapper.PedidoMapper;
import com.seti.messages_mapper.util.XmlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final SoapClient soapClient;
    private final PedidoMapper mapper;

    public PedidoResponse procesarPedido(PedidoRequest request) {

        log.info("Procesando pedido {}", request.getNumPedido());

        // 1. MapStruct
        SoapRequest soapRequest = mapper.toSoapRequest(request);

        // 2. JAXB → XML
        String bodyXml = XmlUtil.toXml(soapRequest);

        // 3. Envelope
        String fullXml = XmlUtil.wrap(bodyXml);

        // 4. Enviar
        String responseXml = soapClient.sendMockedSoapRequest(fullXml);

        // 5. Parse response
        return XmlUtil.parse(responseXml);
    }
}
