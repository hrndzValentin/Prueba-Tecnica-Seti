package com.seti.messages_mapper.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoapClient {

    private final WebClient webClient;

    private static final String URI = "/v3/19217075-6d4e-4818-98bc-416d1feb7b84";

    public String sendSoapRequest(String xml) {

        long start = System.currentTimeMillis();

        log.info("Iniciando consumo de servicio SOAP. endpoint={}", URI);
        log.debug("SOAP Request payload (truncado a 500 chars): {}",
                xml.length() > 500 ? xml.substring(0, 500) : xml);

        try {
            String response = webClient.post()
                    .uri(URI)
                    .bodyValue(xml)
                    .retrieve()
                    .onStatus(
                            status -> status.isError(),
                            clientResponse -> {
                                log.error("Error HTTP en servicio SOAP. status={}", clientResponse.statusCode());
                                return clientResponse.createException();
                            }
                    )
                    .bodyToMono(String.class)
                    .doOnSuccess(res -> log.debug("Respuesta SOAP recibida correctamente"))
                    .doOnError(e -> log.error("Error en consumo SOAP", e))
                    .block();

            long duration = System.currentTimeMillis() - start;

            log.info("Consumo SOAP finalizado exitosamente. tiempo={} ms", duration);
            log.debug("SOAP Response payload (truncado a 500 chars): {}",
                    response != null && response.length() > 500
                            ? response.substring(0, 500)
                            : response);

            return response;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            log.error("Fallo en consumo SOAP. tiempo={} ms, endpoint={}", duration, URI, e);
            throw e;
        }
    }

    public static String sendMockedSoapRequest(String xml) {

        log.info("Usando respuesta SOAP mockeada");
        log.debug("SOAP Request recibido para mock (truncado a 300 chars): {}",
                xml.length() > 300 ? xml.substring(0, 300) : xml);

        String response = """
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

        log.debug("SOAP Mock Response generado");

        return response;
    }
}