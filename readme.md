# API REST con Spring Boot

Este proyecto implementa una API REST utilizando Spring Boot, diseñada para gestionar operaciones de un supermercado. Proporciona endpoints para administrar productos, inventario, ventas y clientes.

## Características

- CRUD completo para productos, categorías, inventario, ventas y clientes
- Autenticación y autorización con Spring Security y JWT
- Documentación con Swagger/OpenAPI
- Testing unitario e integración
- Base de datos relacional con JPA/Hibernate
- Manejo de excepciones personalizado
- Validación de datos
- Paginación y filtrado en consultas

## Requisitos previos

- Java 17 o superior
- Maven 3.8.1 o superior
- MySQL/PostgreSQL (según preferencia)
- IDE compatible con Spring (IntelliJ IDEA, Eclipse, etc.)

## Configuración e instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/JoseTorrealba/Api-Rest-Spring-Boot.git
   cd Api-Rest-Spring-Boot
   ```

2. Configurar la base de datos en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/supermercado_db
   spring.datasource.username=root
   spring.datasource.password=tu_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Compilar y ejecutar la aplicación:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. La API estará disponible en: `http://localhost:8080`
   Documentación Swagger: `http://localhost:8080/swagger-ui.html`

## Estructura del proyecto

```
src/
├── main/
│   ├── java/com/supermercado/api/
│   │   ├── config/         # Configuraciones de la aplicación y seguridad
│   │   ├── controller/     # Controladores REST
│   │   ├── dto/            # Objetos de transferencia de datos
│   │   ├── exception/      # Manejo de excepciones personalizado
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositorios JPA
│   │   ├── security/       # Configuración de seguridad y JWT
│   │   ├── service/        # Capa de servicios
│   │   └── SupermercadoApiApplication.java  # Clase principal
│   └── resources/
│       ├── application.properties  # Configuraciones de la aplicación
│       └── data.sql                # Script inicial de datos (opcional)
├── test/                  # Tests unitarios e integración
```

## Endpoints principales

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar nuevo usuario

### Productos
- `GET /api/productos` - Listar todos los productos
- `GET /api/productos/{id}` - Obtener detalles de un producto
- `POST /api/productos` - Crear nuevo producto
- `PUT /api/productos/{id}` - Actualizar producto existente
- `DELETE /api/productos/{id}` - Eliminar producto

### Ventas
- `GET /api/ventas` - Listar ventas
- `POST /api/ventas` - Registrar nueva venta
- `GET /api/ventas/reporte` - Generar reportes de ventas

### Inventario
- `GET /api/inventario` - Consultar inventario
- `PUT /api/inventario/actualizar` - Actualizar stock

### Clientes
- `GET /api/clientes` - Listar clientes
- `POST /api/clientes` - Registrar nuevo cliente
- `GET /api/clientes/{id}/historico` - Ver historial de compras

## Seguridad

La API utiliza Spring Security con JWT para la autenticación. Todos los endpoints, excepto `/api/auth/**` y algunos endpoints públicos, requieren un token JWT válido incluido en el encabezado de la solicitud:

```
Authorization: Bearer {token}
```

## Contribuir

1. Hacer fork del repositorio
2. Crear una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Hacer commit de tus cambios (`git commit -m 'Añadir nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

José Torrealba - [@JoseTorrealba](https://github.com/JoseTorrealba)