package com.seti.messages_mapper.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoapClient {

    private final WebClient webClient;

    public String sendSoapRequest(String xml) {

        log.info("Enviando request SOAP");

        return webClient.post()
                .uri("/v3/19217075-6d4e-4818-98bc-416d1feb7b84")
                .bodyValue(xml)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Error en consumo SOAP", e))
                .block();
    }

    public static String sendMockedSoapRequest(String xml) {

        log.info("Mockeando respuesta SOAP");

        return """
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                     xmlns:env="http://WSDLs/EnvioPedidos/EnvioPedidosAcme">
       <soapenv:Header/>
       <soapenv:Body>
          <env:EnvioPedidoAcmeResponse>
             <EnvioPedidoResponse>
                <Codigo>75630275</Codigo>
                <Mensaje>Entregado exitosamente al cliente</Mensaje>
             </EnvioPedidoResponse>
          </env:EnvioPedidoAcmeResponse>
       </soapenv:Body>
    </soapenv:Envelope>
    """;
    }
}