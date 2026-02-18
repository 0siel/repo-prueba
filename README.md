# Veterinaria Patitas

Sistema de gestion de citas para la clinica veterinaria Patitas. Arquitectura de microservicios con Spring Boot, Oracle Database, Spring Cloud Gateway, Eureka y un frontend en AngularJS.

---

## Tabla de Contenidos

1. [Arquitectura](#arquitectura)
2. [Requisitos Previos](#requisitos-previos)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Configuracion](#configuracion)
5. [Ejecucion Local](#ejecucion-local)
6. [Servicios](#servicios)
7. [API REST](#api-rest)
8. [Frontend](#frontend)
9. [Despliegue en AWS](#despliegue-en-aws)

---

## Arquitectura

```
                    Puerto 4200
                   +-----------+
  Usuario ------> |  Frontend  |  (nginx:alpine)
                   +-----------+
                        |
                  /api/  |  /auth/
                        v
                   +-----------+
                   |    API    |  Puerto 9090
                   |  Gateway  |  (Spring Cloud Gateway)
                   +-----------+
                     /        \
                    v          v
           +---------+    +---------+
           | Backend |    |  Auth   |  Puertos 8080 / 9000
           | (Citas) |    | Server  |
           +---------+    +---------+
                \            /
                 v          v
              +---------------+
              |   Oracle DB   |  Puerto 1521
              | (Oracle XE 21)|
              +---------------+
                      |
              +---------------+
              | Eureka Server |  Puerto 8761
              | (Descubrimiento)
              +---------------+
```

Todos los servicios se registran en **Eureka Server** para descubrimiento automatico. El **API Gateway** enruta las peticiones usando los nombres de servicio registrados en Eureka.

---

## Requisitos Previos

- Docker y Docker Compose
- Git

No se requiere instalar Java, Maven, Node.js ni Oracle de forma local. Todo corre dentro de contenedores Docker.

---

## Estructura del Proyecto

```
patitas-project/
|-- docker-compose.yml
|-- .env                          # Variables de entorno (no versionado)
|-- .github/workflows/deploy.yml  # CI/CD con GitHub Actions
|
|-- eureka-server/                # Servidor de descubrimiento
|-- api-gateway/                  # Enrutador centralizado
|-- auth-server/                  # Autenticacion JWT
|-- backend/                      # API REST de citas
|
|-- frontend/
|   |-- index.html                # SPA shell
|   |-- app.js                    # Definicion del modulo AngularJS
|   |-- app.routes.js             # Configuracion de rutas
|   |-- app.constants.js          # Constantes (endpoints, estados)
|   |-- nginx.conf                # Proxy inverso
|   |-- Dockerfile
|   |-- styles.css
|   |-- controllers/
|   |   |-- home.controller.js
|   |   |-- cita.controller.js
|   |   |-- consultar.controller.js
|   |   |-- login.controller.js
|   |   |-- dashboard.controller.js
|   |   |-- navbar.controller.js
|   |-- services/
|   |   |-- auth.service.js
|   |   |-- auth.interceptor.js
|   |   |-- estado.helper.js
|   |   |-- error.helper.js
|   |-- views/
|       |-- home.html
|       |-- cita.html
|       |-- consultar.html
|       |-- login.html
|       |-- dashboard.html
```

---

## Configuracion

Crear un archivo `.env` en la raiz del proyecto con las siguientes variables:

```env
DB_ROOT_PASSWORD=<contrasena_root_oracle>
DB_USER=<usuario_aplicacion>
DB_PASSWORD=<contrasena_aplicacion>
```

Ejemplo:

```env
DB_ROOT_PASSWORD=Oracle123
DB_USER=patitas_user
DB_PASSWORD=patitas_pass
```

---

## Ejecucion Local

```bash
# Levantar todos los servicios
docker-compose up -d --build

# Verificar que todos los contenedores estan corriendo
docker-compose ps

# Ver logs de un servicio especifico
docker-compose logs -f backend

# Detener todos los servicios
docker-compose down
```

| Servicio       | URL                          |
|----------------|------------------------------|
| Frontend       | http://localhost:4200         |
| API Gateway    | http://localhost:9090         |
| Backend        | http://localhost:8080         |
| Auth Server    | http://localhost:9000         |
| Eureka Server  | http://localhost:8761         |
| Oracle DB      | localhost:1521 (XEPDB1)      |

---

## Servicios

### Oracle Database

Base de datos Oracle XE 21 Slim. Almacena tanto las citas como los usuarios del sistema de autenticacion.

### Eureka Server

Servidor de descubrimiento de servicios (Spring Cloud Netflix Eureka). Los microservicios se registran automaticamente al iniciar.

### Auth Server

Servicio de autenticacion basado en JWT.

- **Framework**: Spring Boot 3 + Spring Security
- **Puerto**: 9000
- **Endpoint**: `POST /auth/login`
- **Cifrado de contrasenas**: BCrypt

**Flujo de autenticacion**:

1. El cliente envia `username` y `password` a `/auth/login`
2. El servidor valida las credenciales contra la base de datos
3. Si son correctas, retorna un token JWT, tipo (`Bearer`) y nombre de usuario
4. El cliente incluye el token en el header `Authorization` de las peticiones posteriores

### API Gateway

Enrutador centralizado basado en Spring Cloud Gateway.

- **Puerto**: 9090
- Rutas configuradas:
  - `/api/citas/**` y `/api/hola/**` -> `citas-service` (backend)
  - `/auth/**` -> `auth-server`

### Backend (Servicio de Citas)

API REST para la gestion de citas veterinarias.

- **Framework**: Spring Boot 3 + Spring Data JPA
- **Puerto**: 8080
- **Nombre de servicio**: `citas-service`

### Frontend

Aplicacion de pagina unica (SPA) construida con AngularJS 1.8.2.

- **Servidor**: nginx:alpine
- **Puerto**: 4200 (mapeado al puerto 80 del contenedor)
- Nginx actua como proxy inverso para las rutas `/api/` y `/auth/`

---

## API REST

### Autenticacion

| Metodo | Ruta           | Descripcion                  | Autenticacion |
|--------|----------------|------------------------------|---------------|
| POST   | `/auth/login`  | Iniciar sesion               | No            |

**Cuerpo de la peticion:**

```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta exitosa (200):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin"
}
```

### Citas

| Metodo | Ruta                       | Descripcion              | Autenticacion |
|--------|----------------------------|--------------------------|---------------|
| GET    | `/api/citas`               | Listar todas las citas   | Si (JWT)      |
| GET    | `/api/citas/{id}`          | Obtener cita por ID      | No            |
| POST   | `/api/citas`               | Registrar nueva cita     | No            |
| PUT    | `/api/citas/{id}`          | Actualizar cita          | Si (JWT)      |
| PATCH  | `/api/citas/{id}/estado`   | Cambiar estado de cita   | Si (JWT)      |
| DELETE | `/api/citas/{id}`          | Eliminar cita            | Si (JWT)      |

**Crear cita (POST /api/citas):**

```json
{
  "nombreCliente": "Juan Perez",
  "nombreMascota": "Firulais",
  "numeroTelefono": "5512345678",
  "razonCita": "Vacunacion anual",
  "fechaCita": "2026-03-15T10:00:00"
}
```

**Respuesta (201):**

```json
{
  "id": 1,
  "nombreCliente": "Juan Perez",
  "nombreMascota": "Firulais",
  "numeroTelefono": "5512345678",
  "razonCita": "Vacunacion anual",
  "fechaCita": "2026-03-15T10:00:00",
  "estadoCita": "PENDIENTE"
}
```

**Cambiar estado (PATCH /api/citas/{id}/estado):**

```json
{
  "estadoCita": "ATENDIDA"
}
```

**Estados disponibles:**

| Estado      | Descripcion                              |
|-------------|------------------------------------------|
| PENDIENTE   | Cita registrada, pendiente de atencion   |
| ATENDIDA    | Cita atendida y finalizada               |
| CANCELADA   | Cita cancelada por un administrador      |

---

## Frontend

### Flujos de Usuario

#### Dueno de Mascota (Publico)

1. **Agendar cita**: Accede al formulario desde la pagina principal, llena los datos y recibe un numero de folio
2. **Consultar cita**: Ingresa su numero de folio para ver el estado actual de su cita

#### Personal de la Clinica (Protegido)

1. **Iniciar sesion**: Accede a `/login` e ingresa sus credenciales
2. **Panel de citas**: Visualiza todas las citas en una tabla ordenada por fecha
3. **Cambiar estado**: Usa el selector en cada fila para cambiar entre PENDIENTE, ATENDIDA y CANCELADA
4. **Editar cita**: Abre un modal con los datos pre-llenados para modificar la informacion o reprogramar la fecha

### Estructura del Frontend

| Archivo                          | Responsabilidad                                |
|----------------------------------|------------------------------------------------|
| `app.js`                         | Definicion del modulo AngularJS                |
| `app.routes.js`                  | Configuracion de rutas (ngRoute)               |
| `app.constants.js`               | Endpoints de API y estados de cita             |
| `services/auth.service.js`       | Gestion de token JWT en localStorage           |
| `services/auth.interceptor.js`   | Inyeccion automatica del header Authorization  |
| `services/estado.helper.js`      | Mapeo de estado a clase CSS de badge           |
| `services/error.helper.js`       | Extraccion de mensajes de error del servidor   |
| `controllers/home.controller.js` | Pagina principal y lista de servicios          |
| `controllers/cita.controller.js` | Formulario de agendado de citas                |
| `controllers/consultar.controller.js` | Consulta publica de cita por folio        |
| `controllers/login.controller.js`| Autenticacion del personal                     |
| `controllers/dashboard.controller.js` | Panel de gestion de citas               |
| `controllers/navbar.controller.js` | Estado de autenticacion en la barra de navegacion |

### Practicas Implementadas

- **IIFE** (Immediately Invoked Function Expression) en cada archivo para evitar contaminacion del scope global
- **Modo estricto** (`'use strict'`) en todo el codigo
- **Inyeccion de dependencias explicita** (array notation) para compatibilidad con minificacion
- **Constantes centralizadas** para endpoints de API y estados de cita
- **Interceptor HTTP** para manejo automatico del token JWT
- **Proteccion de rutas** en el lado del cliente con redireccion a login

---

## Despliegue en AWS

El proyecto incluye un workflow de GitHub Actions (`.github/workflows/deploy.yml`) que automatiza el despliegue a una instancia EC2.

### Requisitos en AWS

1. Instancia EC2 con Docker y Docker Compose instalados
2. Archivo `.env` configurado en `/home/ubuntu/patitas-project/`
3. Puerto 4200 abierto en el Security Group de la instancia

### Secretos de GitHub Requeridos

| Secreto         | Descripcion                        |
|-----------------|------------------------------------|
| `EC2_HOST`      | IP publica de la instancia EC2     |
| `EC2_USERNAME`  | Usuario SSH (normalmente `ubuntu`) |
| `EC2_SSH_KEY`   | Llave privada SSH para conexion    |

### Flujo de Despliegue

1. Se hace push a la rama `osiel-rubio`
2. GitHub Actions copia el repositorio completo a la instancia EC2 via SCP
3. Se ejecuta `docker-compose down`, `docker system prune -f` y `docker-compose up -d --build` en el servidor
4. La aplicacion queda disponible en `http://<IP_PUBLICA_EC2>:4200`
