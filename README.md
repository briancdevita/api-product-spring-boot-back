# API de Gestión de Productos

Esta es una **API RESTful** desarrollada con **Spring Boot**, diseñada para la gestión de productos. La API incluye funcionalidades avanzadas como autenticación y autorización con **JWT**, roles de usuario, y la capacidad de descargar productos en formato **CSV**.

---

## 🚀 Funcionalidades principales

- CRUD completo para productos.
- Autenticación y autorización mediante **JWT**.
- Roles de usuario (`ADMIN` y `USER`) para acceso controlado.
- Exportación de productos en formato **CSV**.
- Sistema seguro con **Spring Security**.
- Configuración de **CORS** para integración con frontend.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Security** (con JWT)
- **Spring Data JPA** (con Hibernate)
- **MySQL** (como base de datos)
- **OpenCSV** (para generar archivos CSV)
- **Maven** (gestión de dependencias)
- **Postman** (para pruebas de la API)

---

## 📂 Estructura del proyecto

```plaintext
src
├── main
│   ├── java/com/example/api
│   │   ├── controller     # Controladores REST
│   │   ├── model          # Entidades JPA
│   │   ├── repository     # Repositorios JPA
│   │   ├── service        # Lógica de negocio
│   │   ├── config         # Configuraciones (Spring Security, CORS, JWT)
│   │   ├── jwt            # Utilidades para JWT (generación, validación)
│   │   └── dto            # Clases para transferencia de datos
│   ├── resources
│       ├── application.properties  # Configuración del proyecto
└── test                  # Pruebas unitarias


🚀 Cómo ejecutar el proyecto
1. Clonar el repositorio
git clone https://github.com/briancdevita/api-product-spring-boot-back
cd nombre-del-repo

2. Configurar la base de datos
Crea una base de datos MySQL llamada products_db.
Configura las credenciales de acceso en el archivo src/main/resources/application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/products_db
spring.datasource.username=tu-usuario
spring.datasource.password=tu-password
spring.jpa.hibernate.ddl-auto=update

3. Ejecutar el proyecto
Ejecuta la aplicación con Maven:
./mvnw spring-boot:run


La API estará disponible en:
http://localhost:8080


🔑 Autenticación y roles
La API utiliza JWT para autenticar usuarios y controlar el acceso a las rutas protegidas.

Rutas públicas
POST /api/auth/login: Iniciar sesión.
POST /api/users/register: Registrar nuevos usuarios.
Rutas protegidas
Requieren un token JWT válido:

GET /products: Listar productos (solo ADMIN).
POST /products: Crear un producto (solo ADMIN).
PUT /products/{id}: Actualizar un producto (solo ADMIN).
DELETE /products/{id}: Eliminar un producto (solo ADMIN).
GET /products/download: Descargar productos en formato CSV (solo ADMIN).


Generar un token
Envía las credenciales al endpoint /api/auth/login. La respuesta incluirá un token JWT que debes usar en los encabezados de las solicitudes protegidas:

Authorization: Bearer <TOKEN_JWT>


📥 Descargar productos en formato CSV
El endpoint /products/download permite descargar un archivo CSV con todos los productos. Solo es accesible para usuarios con el rol ADMIN.

Ejemplo de uso en Postman
Configura la solicitud como GET.
URL:http://localhost:8080/products/download
Agrega el encabezado de autorización con el token JWT:
Authorization: Bearer <TOKEN_JWT>
Descarga el archivo.


🧪 Pruebas
Pruebas con Postman
El proyecto incluye una colección de Postman para probar todos los endpoints. Importa el archivo postman_collection.json en Postman.
./mvnw test


🛡️ Licencia
Este proyecto está bajo la licencia MIT.




