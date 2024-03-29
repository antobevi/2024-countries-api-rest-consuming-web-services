# 2024-countries-api-rest-consuming-web-services
Proyecto realizado durante el curso Java Web API de Educación IT en Enero del 2024 sobre el consumo de SOAP y API de terceros, para finalmente exponer los datos resultantes en una API REST de países, personalizada.

## Tecnologías Utilizadas

- **Java 17**
- **Maven**
- **Dockerfile**
- **Deploy en Render.com**
- **Spring Boot Starter Web Services**
- **Filtros CORS**
- **Spring Boot Starter Tomcat**
- **Lombok**
- **Spring Boot Starter WebFlux:** Llamadas a APIs de forma asíncrona.
- **Spring Boot Starter Test**
- **Mockito Core:** Utilizado para realizar pruebas con objetos simulados (**Mocks**).
- **JAX-WS Maven Plugin:** Utilizado para generar clases Java a partir de un archivo WSDL.
- **Marshalling**

## Construcción y Ejecución

Utiliza el siguiente comando Maven:

```bash
mvn clean install
```

## Configuración del Plugin JAX-WS
El proyecto utiliza el plugin jaxws-maven-plugin para generar clases Java a partir de un archivo WSDL. Configurar correctamente la ubicación en el POM.

```xml
<plugins>
    <plugin>
        <groupId>com.sun.xml.ws</groupId>
        <artifactId>jaxws-maven-plugin</artifactId>
        <version>3.0.0</version>
        <executions>
            <execution>
                <goals>
                    <goal>wsimport</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <packageName>localhost.ws</packageName>
            <wsdlUrls>
                <wsdlUrl>https://soap-paises.onrender.com/ws/paises.wsdl</wsdlUrl>
            </wsdlUrls>
            <sourceDestDir>${sourcesDir}</sourceDestDir>
            <destDir>${classesDir}</destDir>
            <extension>true</extension>
        </configuration>
    </plugin>
</plugins>
```

## Endpoint

El endpoint principal de la aplicación se encuentra en:

`/countries-api/obtenerPais?nombre=argentina`

## Ejemplo de Response

```json
{
  "nombre": "Argentina",
  "capital": "Cuidad Autonoma de Buenos Aires",
  "moneda": "ARS",
  "poblacion": 46234830,
  "bandera": "https://flagcdn.com/w2560/ar.png",
  "lenguajes": {
    "spa": "Spanish"
  },
  "mapas": {
    "googleMaps": "https://goo.gl/maps/Z9DXNxhf2o93kvyc6",
    "openStreetMaps": "https://www.openstreetmap.org/relation/286393"
  }
}
```
