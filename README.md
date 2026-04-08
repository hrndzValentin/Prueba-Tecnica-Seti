# 🚀 API de Envío de Pedidos ACME

## 📌 Descripción

API REST en Spring Boot que recibe pedidos en JSON, los transforma a formato SOAP (XML), simula el consumo de un servicio externo y retorna la respuesta en JSON.

---

## 🛠️ Tecnologías

* Java 21
* Spring Boot (MVC)
* WebClient (Spring WebFlux)
* MapStruct
* JAXB
* Lombok
* Docker

---

## 📡 Endpoint

**POST** `/api/pedidos`

### Request

```json
{
  "numPedido": "75630275",
  "cantidadPedido": "1",
  "codigoEAN": "00110000765191002104587",
  "nombreProducto": "Armario INVAL",
  "numDocumento": "1113987400",
  "direccion": "CR 72B 45 12 APT 301"
}
```

### Response

```json
{
  "codigoEnvio": "80375472",
  "estado": "Entregado exitosamente al cliente"
}
```

---

## ⚠️ Nota

El endpoint externo proporcionado no está disponible (404), por lo que se mockea la respuesta SOAP para garantizar el flujo completo.

---

## ▶️ Ejecución

```bash
mvn clean package
docker build -t pedidos-app .
docker run -p 8080:8080 pedidos-app
```

---

## 🧠 Decisiones técnicas

* Uso de **WebClient** (cliente HTTP moderno)
* **MapStruct** para mapeo desacoplado
* **JAXB** para generación de XML
* Parsing XML simple por contrato fijo
* Validaciones con Bean Validation

---

## 👨‍💻 Autor

Valentín Sánchez
