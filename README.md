# EmployeeHub

**Descripción:**  
EmployeeHub es una aplicación basada en microservicios para la gestión de empleados, departamentos y ciudades. Incluye autenticación JWT centralizada mediante un API Gateway y permite registrar usuarios, autenticarse y consultar información de empleados de manera segura.

## Microservicios
- **Auth Service:** Gestión de usuarios, registro, login y tokens JWT.  
- **Employee Service:** CRUD de empleados, asociados a departamentos.  
- **Department Service:** CRUD de departamentos.  
- **City Service:** Información de ciudades.  
- **API Gateway:** Punto único de entrada, maneja autenticación JWT y enruta peticiones a microservicios.  
- **Config Server & Eureka:** Gestión de configuración centralizada y descubrimiento de servicios.  

## Tecnologías usadas
- **Backend:** Spring Boot 3, Spring WebFlux (API Gateway), Spring Security, JWT, Spring Data JPA, Hibernate.  
- **Microservicios:** Arquitectura distribuida con Eureka Discovery Server.  
- **Autenticación:** JWT con roles de usuario.  

## Endpoints principales

**Auth Service**
| Método | Endpoint | Descripción |
|--------|----------|------------|
| POST | `/api/v1/auth/register` | Registrar usuario |
| POST | `/api/v1/auth/login` | Login y obtención de access y refresh token |
| POST | `/api/v1/auth/refresh` | Renovar token de acceso |

**Employee Service**
| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | `/api/v1/employees` | Listar empleados (requiere token válido) |
| POST | `/api/v1/employees` | Crear empleado |
| PUT | `/api/v1/employees/{id}` | Actualizar empleado |
| DELETE | `/api/v1/employees/{id}` | Eliminar empleado |

## Notas importantes
- Todos los microservicios están protegidos mediante JWT; la autenticación la gestiona el API Gateway.  
- Para probar endpoints seguros, enviar **header Authorization: `Bearer <accessToken>`** obtenido del login.  
- Los microservicios pueden ejecutarse localmente con `docker-compose up` o por separado.  

## Ejemplo de uso con cURL
```bash
# Login
curl -X POST http://localhost:8060/api/v1/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"Teresa","password":"123456abC"}'

# Consultar empleados
curl -X GET http://localhost:8060/api/v1/employees \
-H "Authorization: Bearer <accessToken>"
