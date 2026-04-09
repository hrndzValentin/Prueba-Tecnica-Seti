package com.seti.messages_mapper.util;

import com.seti.messages_mapper.dto.PedidoResponse;
import com.seti.messages_mapper.dto.SoapRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;

@Slf4j
public class XmlUtil {

    public static String toXml(SoapRequest request) {
        try {
            log.debug("Convirtiendo objeto SoapRequest a XML. Pedido={}", request.getPedido());
            JAXBContext context = JAXBContext.newInstance(SoapRequest.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter writer = new StringWriter();
            marshaller.marshal(request, writer);

            String xml = writer.toString();
            log.debug("XML generado correctamente para pedido={}", request.getPedido());
            return xml;

        } catch (Exception e) {
            log.error("Error convirtiendo a XML para pedido={}", request.getPedido(), e);
            throw new RuntimeException("Error convirtiendo a XML", e);
        }
    }

    public static String wrap(String bodyXml) {
        log.debug("Envolviendo XML en SOAP Envelope");
        String envelope = """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                 xmlns:env="http://WSDLs/EnvioPedidos/EnvioPedidosAcme">
                   <soapenv:Header/>
                   <soapenv:Body>
                      <env:EnvioPedidoAcme>
                         %s
                      </env:EnvioPedidoAcme>
                   </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(bodyXml);
        log.debug("SOAP Envelope construido correctamente");
        return envelope;
    }

    public static PedidoResponse parse(String xml) {

        try {
            log.debug("Iniciando parseo de respuesta SOAP");

            String codigo = extract(xml, "Codigo");
            String mensaje = extract(xml, "Mensaje");
            log.info("Respuesta SOAP procesada. CodigoEnvio={}, Estado={}", codigo, mensaje);
            return new PedidoResponse(codigo, mensaje);

        } catch (Exception e) {
            log.error("Error parsing SOAP response. XML recibido={}", xml, e);
            throw new RuntimeException("Error parsing SOAP response", e);
        }
    }

    private static String extract(String xml, String tag) {
        return xml.split("<" + tag + ">")[1]
                .split("</" + tag + ">")[0];
    }
}
