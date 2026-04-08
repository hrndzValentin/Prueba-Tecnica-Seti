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
            JAXBContext context = JAXBContext.newInstance(SoapRequest.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter writer = new StringWriter();
            marshaller.marshal(request, writer);

            return writer.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo a XML", e);
        }
    }

    public static String wrap(String bodyXml) {

        return """
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
    }

    public static PedidoResponse parse(String xml) {

        try {
            log.debug("Parsing SOAP response");

            String codigo = extract(xml, "Codigo");
            String mensaje = extract(xml, "Mensaje");

            return new PedidoResponse(codigo, mensaje);

        } catch (Exception e) {
            log.error("Error parsing SOAP response", e);
            throw new RuntimeException("Error parsing SOAP response", e);
        }
    }

    private static String extract(String xml, String tag) {
        return xml.split("<" + tag + ">")[1]
                .split("</" + tag + ">")[0];
    }
}
