# Angle of Attack School

El proyecto que se expone a continuación consiste en el desarrollo de una aplicación. Consta de dos partes: la aplicación en Android y la implementación de una API REST desarrollada en Spring Boot y Kotlin, haciendo uso de Gradle y desplegada en Heroku junto con PostgreSQL como base de datos.           

## Descripción general del sistema

La aplicación simula la gestión de un aeródromo. En este nos encontramos con los pilotos, los administradores y las aeronaves que usan los pilotos (para la obtención de la licencia mediante vuelos de instrucción o para vuelos libres una vez obtenida la licencia). Para registrar los vuelos se hace uso de los registros de vuelo, que consumen las horas pagadas por los pilotos, pudiendo estos comprar más.

Como ya se ha mencionado, la aplicación cuenta con dos tipos de usuarios: los administradores y los pilotos. Los administradores se encargarán de realizar la gestión de usuarios, las aeronaves y los paquetes de horas. También se encargarán de obtener dos listados: un primero con la facturación de los productos y un segundo listado con los registros de vuelo. Los pilotos podrán registrar sus horas de vuelo, comprar paquetes de horas, ver las aeronaves disponibles y consultar sus registros de vuelo y facturas. Ambos, administradores y pilotos, podrán ver la información de su usuario y cambiar su contraseña.

Ambas partes podrán acceder a la información meteorológica actual.


## Documentación relevante

Aquí se presenta un breve resumen, si desea más información es libre de acceder a la documentación.
Para consultar documentación de los servicios web (realizada con Swagger), tendrá que lanzar el proyecto de la API. Si se encuentra en IntelliJ IDEA, tendrá que ejecutar el siguiente comando: gradle bootRun. Al terminar de cargar este, solo tiene que acceder a la siguiente URL: http://localhost:9000/swagger-ui.html

Para consultar la documentación (Dokka) de la API:
1)	puede acceder a la carpeta ```Documentación``` -> ```html``` -> abrir el archivo index.html 
2)	desde el mismo proyecto de la API, ```root``` -> ```build``` -> ```dokka``` -> ```html``` -> abrir el archivo index.html

En la carpeta ```Documentación``` de este repositorio encontrará documentación adicional, como la memoria del mismo o el sketching, entre otras.  

También se encuentra una colección de Postman donde probar las peticiones a la API, haciendo uso de la que se encuentra en Heroku (https://aoa-school.herokuapp.com) 
