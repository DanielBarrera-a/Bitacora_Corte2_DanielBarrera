# NOTAS DE ESTUDIO — DOSW Corte 2 (Wok System + Parcial T2)

## Material del curso (carpeta `Material/`)

| Archivo | Temas clave |
|---|---|
| `DOSW 1 - S07.pptx` | Cliente-servidor, MVC, capas Controller/Service/Repository (diapo 35), DTOs, records, enums, validadores, utilidades, mappers, REST, verbos HTTP, idempotencia, inversión de dependencias, IoC |
| `DOSW 1 - S08.pptx` | Dominio con Lombok, estándares REST y códigos HTTP (diapos 15-18), Services con Streams, MapStruct, Swagger, `@Slf4j`, validación input vs negocio, excepciones + `GlobalExceptionHandler`, versionado `/api/v1`, Mockito |
| `DOSW 1  - S09.pptx` | BD relacional, ACID, normalización, JPA, relaciones, LAZY/EAGER, MongoDB (`@Document`, `MongoRepository`), tipos de ID |
| `DOSW 1 - S010.pptx` | Seguridad (autenticación, autorización, JWT, CORS, OWASP), Docker, ambientes, CI/CD, diagrama de despliegue |
| `guia-turtwig-implementacion-controller.html` | Paso a paso: Dominio → DTOs → Mapper → Service → Validator → Controller → Excepciones → Swagger → Pruebas |
| `guia-turtwig-s10-seguridad.html` | Roles, HTTP Basic, JWT, autorización, CORS, OWASP |
| `guia-turtwig-s10-docker.html` | Imagen vs contenedor, Dockerfile multietapa, docker-compose |
| `guia-turtwig-s10-cicd.html` | GitHub Actions, secrets, QA/PROD, Azure |
| `parcial-t2-prerequisitos.html` | Prerrequisitos del parcial práctico T2 (proyecto base desplegado) |

Pendiente: enunciado de Wok System (`material/enunciado/`).

## Plan acordado

**Prioridad 1 — Prerrequisitos del Parcial T2** (repo aparte `DOSW_ParcialT2_PrimerNombrePrimerApellido`):

1. Repositorio y rama `develop` (lo hace el estudiante en GitHub)
2. Proyecto Maven + estructura MVC con `.gitkeep`
3. `pom.xml` con dependencias y plugins
4. Configuración: `application.yaml`, clase principal, `HealthController`, `SecurityConfig`
5. Dockerfile + `.dockerignore`
6. Pipeline GitHub Actions
7. Despliegue en Azure (cuentas y secrets: los hace el estudiante)
8. README con 11 secciones
9. Evidencias de cuentas (modelado + Figma)

**Prioridad 2 — Bitácora Wok System** (este repo): Plato y Menú primero y luego el resto de dominios.
Etapas: proyecto base → dominio → DTOs → mappers → services → validaciones/excepciones →
controllers/Swagger → pruebas → persistencia → seguridad → Docker/CI-CD → README.

## Registro de sesiones

### 2026-10-12 — Sesión 1
- Etapa: inicio. Lectura del material y definición del plan.
- Aprendido: todavía nada (solo resumen del material).
- Hecho por el estudiante: —
- Hecho por Claude: resumen del material, plan, este archivo.
- Parcial, paso 1 (repo) ✅: repo `DOSW_ParcialT2_DanielBarrera`, rama `develop`, README con nombre y grupo, `.gitignore` con `.env`, `*.p12`, `target/`.
  - Hecho por el estudiante: repo, rama develop, `.gitignore`, commit y push. Hecho por Claude: datos en el README.
  - Error: puse las reglas en `.idea/.gitignore` en vez del `.gitignore` de la raíz. Lección: cada `.gitignore` solo aplica a su carpeta y subcarpetas. Se verifica con `git check-ignore -v <archivo>`.
  - Error: olvidé hacer commit y push. Lección: flujo add (staging) → commit (historial local) → push (GitHub).
  - Pendiente: agregar al profesor como colaborador (falta su usuario de GitHub).
- Parcial, paso 2A (proyecto Maven) ✅: Spring Initializr, Boot 4.1.1 (no había 3.x estable), Java 21, Web/MongoDB/Validation/Security/Lombok. Compila con `./mvnw compile`.
  - Error: copié el zip en el repo de la bitácora. Lección: revisar el destino antes de copiar.
  - Error en IntelliJ "Unable to create injector": el wrapper usa Maven 3.10.0 y el IDE no lo soporta. Solución: Settings → Build Tools → Maven → Maven home path = Bundled (Maven 3).
  - Sin "New → Package": IntelliJ no había cargado el proyecto Maven → "Add as Maven Project"; `src/main/java` debe verse azul.
- Parcial, paso 2B (paquetes) ✅
  - Hecho por el estudiante: `controller/dtos/request` y los 4 de `core/` con `.gitkeep`.
  - Hecho por Claude: los 14 restantes (`controller` 3, `persistence` 3, `config`, `main`, test 3, `test/resources`, `docs/uml`, `docs/images`).
  - Error: creé los paquetes dentro de `DOSW_ParcialT2` (ruta duplicada `edu/dosw/parcial/.../edu/dosw/parcial`). Lección: "New → Package" crea debajo del paquete donde haces clic; crear desde la carpeta `java` o desactivar "Compact Middle Packages".
- Paso 2 subido a develop (commit `82354c4`). `HELP.md` se dejó por decisión del estudiante.
- Parcial, paso 3, grupo 1 (Swagger + JWT) ✅
  - Hecho por el estudiante: propiedades `springdoc.version` y `jjwt.version`. Hecho por Claude: dependencias (el estudiante se atascó).
  - **Atasco**: al pegar en el `pom.xml` se rompió el XML dos veces (bloques `<dependency>` sin cierre → "Error reading file"). Regla: cada `<dependency>` abre y cierra y contiene UNA librería. Detectarlo: en IntelliJ el cursor sobre una etiqueta resalta su cierre; las etiquetas sin cerrar salen en rojo.
  - No sabía artifactId/versión de JJWT: salen de la tabla de la sección 3 del HTML; la versión es `${jjwt.version}`.
  - Verificado: compila; `jjwt-api` = compile, `jjwt-impl`/`jjwt-jackson` = runtime, springdoc 3.1.1.
- Parcial, paso 3, grupos 2 y 3 ✅ (MapStruct, Jacoco, Sonar)
  - Hecho por el estudiante: propiedad `mapstruct.version` (1.6.3). Hecho por Claude: el resto (pidió ayuda por tiempo).
  - Verificado: `./mvnw clean verify` pasa y genera `target/site/jacoco/index.html`. Los logs "Connection refused" de Mongo son normales sin Mongo local; la prueba pasa igual.
- Paso 3 subido (commit `02769f0`).
- **Decisión del estudiante (2026-10-13):** Claude hace el resto de los prerrequisitos del parcial; el estudiante aprende esos conceptos en la bitácora.
- Parcial, pasos 4, 5, 6 y 8 (Claude): `application.yaml`, clase principal en `main/` (`ParcialApplication`), `SecurityConfig`, `HealthController`, `Dockerfile`, `.dockerignore`, `.github/workflows/ci-cd.yml` y README con las 11 secciones.
  - Verificado: `mvn clean verify` pasa; la app arranca, `/api/v1/health` → `{"status":"UP",...}`, Swagger → 200, otra ruta → 403.
  - Sin verificar: `docker build` (Docker no instalado) y el pipeline (faltan secrets).
- Infraestructura del parcial ✅ (2026-10-13): Docker Desktop (activar "Plataforma de máquina virtual" con `wsl --install --no-distribution` como admin + reinicio), imagen en Docker Hub `danielbarreraaraque/dosw-parcial-t2`, MongoDB Atlas (clúster M0 `parcial`), App Service `dosw-parcial-danielbarrera` (North Central US, F1), variables `MONGODB_URI`/`JWT_SECRET`/`WEBSITES_PORT`, Service Principal `github-actions-parcial-danielbarrera`, 5 secrets en GitHub. Pipeline test → docker → deploy en verde.
  - Lecciones: Azure for Students solo permite ciertas regiones (Policy → "Allowed resource deployment regions": germanywestcentral, switzerlandnorth, spaincentral, belgiumcentral, northcentralus). El nombre del Service Principal debe ser único en el tenant de la universidad. Los tokens y secretos se muestran una sola vez y nunca se comparten; si se filtran, se cambian.
- Pendiente (solo el estudiante): capturas del README (pruebas/Jacoco, Docker, pipeline, cuentas de modelado y Figma), profesor colaborador.

## Guía de sustentación

### Parcial, paso 1: Repositorio
- `main` = versión estable/final; `develop` = rama de trabajo diario. Lo nuevo se integra en develop y pasa a main cuando está listo.
- `.gitignore`: evita subir secretos (`.env`, `*.p12`) y archivos generados (`target/`). S10 diapo 64: "las llaves no van en el código expuesto"; si una llave se filtra, borrar el archivo no la invalida.
- Pregunta probable: "¿Por qué no subes el `.env`?" → Porque tiene credenciales (MONGODB_URI, JWT_SECRET); se pasan como variables de entorno o secrets.

### Parcial, paso 2: Proyecto Maven y estructura
- Maven: gestiona dependencias y compilación desde `pom.xml`. `groupId` = organización (`edu.dosw.parcial`, también el paquete base); `artifactId` = nombre del proyecto (`DOSW-ParcialT2`).
- Capas (S07 diapo 35): `controller` = mesero (HTTP, DTOs, Mapper IN, handler de errores); `core` = cocina (servicios, dominio, validadores, utils); `persistence` = despensa (documentos Mongo, repositorios, Mapper OUT); `config` = seguridad/Swagger; `main` = clase `@SpringBootApplication`.
- `.gitkeep`: Git no versiona carpetas vacías; el archivo vacío hace que la carpeta se suba.
- Preguntas probables:
  - "¿Por qué dos mappers?" → Mapper IN (controller): DTO ↔ dominio. Mapper OUT (persistence): dominio ↔ Document. El Controller nunca ve un Document y el Service nunca ve un DTO.
  - "¿Por qué el Service no recibe DTOs?" → Para no acoplar la lógica de negocio a la capa de presentación (guía controller, "Las 4 reglas").

### Parcial, paso 3: `pom.xml`
- Dependencia = librería que Maven descarga. `<properties>` centraliza versiones (`${jjwt.version}`).
- Starters de Spring Boot no llevan `<version>`: la administra el `parent` (4.1.1). Librerías externas (springdoc, jjwt, mapstruct) sí la llevan.
- Scopes: sin scope = compilar y ejecutar; `runtime` = solo al ejecutar (`jjwt-impl`, `jjwt-jackson`); `test` = solo pruebas.
- Procesadores de anotaciones (corren al compilar y generan código): Lombok (getters/setters) → `lombok-mapstruct-binding` → `mapstruct-processor` (implementación de los mappers). Lombok primero porque MapStruct usa los getters/setters que Lombok genera.
- Jacoco: mide cobertura de pruebas (`prepare-agent` + `report` en fase `test`). Sonar: análisis de calidad, lee el XML de Jacoco.
- Versiones distintas al HTML: springdoc 3.1.1 (Boot 4), Jacoco 0.8.15 (soporte Java 24 local), MapStruct 1.6.3 (última estable).
- Preguntas probables:
  - "¿Qué pasa si `mapstruct-processor` va antes que Lombok?" → MapStruct no encuentra getters/setters y falla la compilación.
  - "¿Para qué Jacoco?" → Reporte de cobertura (`target/site/jacoco/index.html`) que se muestra en el README y usa Sonar.

### Parcial, pasos 4 a 8: qué hace cada archivo (resumen para defender)
- `application.yaml`: `${MONGODB_URI:valor}` = usa la variable de entorno y, si no existe, el valor por defecto. `JWT_SECRET` sin valor por defecto para no dejar secretos en el código.
- `main/ParcialApplication`: `@SpringBootApplication(scanBasePackages = "edu.dosw.parcial")` porque Spring escanea desde el paquete de la clase principal hacia abajo; en `main/` no encontraría `controller/` ni `config/`. `@EnableMongoRepositories` indica dónde están los repositorios.
- `config/SecurityConfig`: con Spring Security todo queda bloqueado; se dejan públicos `/api/v1/health` y Swagger. CSRF desactivado y sesión STATELESS porque se usará JWT (S10).
- `controller/HealthController`: `@RestController` (responde JSON), `@RequestMapping("/api/v1/health")` (ruta con versión), `@GetMapping` (lectura). Sirve para verificar que el despliegue responde.
- `Dockerfile` multietapa: etapa 1 compila con Maven; etapa 2 solo copia el JAR a una imagen JRE liviana (sin Maven ni código fuente). `.dockerignore` evita copiar `.git`, `target/`, `.env`, etc.
- `ci-cd.yml`: en cada push a main/develop → job `test` (mvn verify) → `docker` (build y push a Docker Hub) → `deploy` (Azure). `needs:` encadena los jobs. Los secretos van en `${{ secrets.X }}`, nunca en el código (S10 diapo 63).
- Preguntas probables:
  - "¿Qué pasa sin `scanBasePackages`?" → Spring no encuentra los controllers; `/api/v1/health` daría 404.
  - "¿Por qué multietapa?" → La imagen final es más pequeña y segura: no lleva Maven ni el código fuente.
  - "¿Imagen vs contenedor?" → La imagen es el molde (kit de cocina); el contenedor es una instancia corriendo (la cocina funcionando) (S10 diapo 39).


---

## Resumen teórico: DOSW Corte 2

Basado en S07 a S10, las guías Turtwig y el HTML del parcial.

### 1. Arquitectura y fundamentos de APIs

| Término | Qué es | Fuente |
|---|---|---|
| **Cliente-Servidor** | El cliente **pide** y el servidor **responde**, nunca al revés. Muchos clientes hablan con el mismo servidor | S07 diapos 26-27 |
| **Con estado (stateful)** | El servidor recuerda al cliente entre peticiones (carrito, login con sesión) | S07 diapos 23-24 |
| **Sin estado (stateless)** | Cada petición es independiente y trae toda la información. Las APIs REST con JWT son así | S07 diapo 23 |
| **MVC** | **Modelo** (datos y reglas), **Vista** (presentación), **Controlador** (intermediario que invoca la lógica) | S07 diapo 34 |
| **Arquitectura en capas** | **Controller** = mesero (HTTP, sin lógica). **Service** = cocina (lógica de negocio, no conoce HTTP). **Repository** = despensa (solo guarda y lee) | S07 diapo 35 |
| **API** | Procedimientos para comunicar aplicaciones sin conocer su implementación | S07 diapo 61 |
| **REST** | Estilo basado en **recursos** identificados por URL y manipulados con verbos HTTP. Toda REST es API, pero no toda API es REST | S07 diapo 61 |
| **Recurso / URI** | Sustantivos en plural: `/platos`, `/platos/5` | S07 diapos 68-71 |
| **Path / query params** | El path identifica el recurso (`/platos/{id}`); los query params filtran, buscan o paginan (`?categoria=sushi`) | S07 diapo 71 |
| **Request / Response** | Request: headers + body opcional. Response: headers + body opcional + **código de estado** | S07 diapos 72-73 |
| **JSON vs XML** | JSON es ligero y es el estándar actual; XML usa etiquetas y se usa en sistemas viejos (SOAP) | S07 diapo 74 |

**Verbos HTTP** (S07 diapos 63-65; S08 diapos 15-18)

| Verbo | Uso | ¿Seguro? | ¿Idempotente? | Éxito |
|---|---|---|---|---|
| GET | Leer | Sí | Sí | 200 |
| POST | Crear | No | **No** (N llamadas = N recursos) | **201** |
| PUT | Reemplazar completo | No | Sí | 200 |
| PATCH | Modificar en parte | No | No necesariamente | 200 |
| DELETE | Eliminar | No | Sí | **204** |

- **Seguro** = nunca modifica el recurso.
- **Idempotente** = 1 o N ejecuciones dejan el mismo estado. **No es automática**: hay que implementarla en el servicio (S07 diapos 66-67).

**Códigos que se confunden** (S08 diapo 18):
- 200 = aquí están los datos; 201 = creé algo nuevo.
- 400 = formato mal (campo vacío).
- 404 = el recurso no existe.
- 409 = conflicto (nombre duplicado, mesa ocupada).
- 422 = formato bien, pero **el negocio lo rechaza** (pedido de LISTO a RECIBIDO).
- 500 = error inesperado.

**Versionado** (S08 diapos 67-72): `/api/v1/...` desde el primer endpoint. Solo se crea versión nueva si un cambio **rompe** a los clientes. Agregar un campo opcional no la requiere.

### 2. Spring Boot y principios de diseño

| Término | Qué es | Fuente |
|---|---|---|
| **Spring Boot** | Framework para apps Java listas para producción: configuración automática y servidor incluido | S07 diapos 86-88 |
| **Inversión de dependencias (DIP)** | Depender de **interfaces**, no de clases concretas (`IPlatoService`, no `PlatoServiceImpl`). Bajo acoplamiento, alta cohesión | S07 diapo 76 |
| **Inversión de control (IoC)** | El **framework crea y conecta los objetos**, no tú con `new`. Se basa en **reflection** y **anotaciones** | S07 diapos 79-85 |
| **Inyección de dependencias** | Spring entrega a cada clase lo que necesita (constructor + `@RequiredArgsConstructor` + campos `private final`) | S07 diapo 88; S08 diapo 33 |
| **Bean** | Objeto creado y administrado por Spring | — |
| **Dependencia circular** | A necesita a B y B a A; Spring no puede resolverla | S08 diapo 34 |
| **Anotación** | Metadato con `@` que le dice a Spring cómo tratar una clase o método | S07 diapo 83 |

- **Anotaciones de capa** (S07 diapos 40-43): `@RestController` (HTTP → JSON, con `@RequestMapping` y `@GetMapping`/`@PostMapping`…), `@Service` (lógica), `@Component` (utilidad genérica, p. ej. Validator), `@Repository` (persistencia).
- **Anotaciones del controller** (guía controller Paso 06): `@PathVariable` (dato de la ruta), `@RequestParam` (query param), `@RequestBody` (JSON del body), `ResponseEntity` (elegir el código HTTP).
- **application.properties / .yml** (S08 diapos 50-51): configuración (puerto, BD, logs), **perfiles** por ambiente, `@Value` / `@ConfigurationProperties`. Nunca contraseñas ahí.

### 3. Los objetos de cada capa

| Término | Qué es | Fuente |
|---|---|---|
| **Dominio** (`Plato`, `Pedido`) | El negocio, con **comportamiento propio** (`estaDisponible()`, `activar()`). No depende de la BD | S07 diapo 49; guía Paso 01 |
| **Entidad de persistencia** (`PlatoEntity` / `PlatoDocument`) | **Cómo se guarda** en BD (`@Entity` SQL, `@Document` Mongo). Sin lógica | S07 diapo 50; S09 diapo 29 |
| **DTO** | Objeto simple para **transportar datos** entre capas; no expone el dominio | S07 diapo 45 |
| **RequestDTO / ResponseDTO** | Request: lo que entra, con validaciones. Response: solo lo que el cliente debe ver | Guía Paso 02 |
| **Record** | Clase inmutable corta: genera constructor, getters, equals, hashCode, toString. Ideal para DTOs | S07 diapo 46 |
| **Enum** | Conjunto fijo de constantes (estados, roles). Puede tener lógica (`puedeTransicionarA()`) | S07 diapo 47; guía Paso 01 |
| **Mapper** | Traduce entre capas: RequestDTO → dominio, dominio → ResponseDTO, dominio ↔ entidad/documento | S07 diapo 54; S09 diapo 37 |
| **MapStruct** | **Genera** los mappers al compilar; tú declaras la interfaz con `@Mapper(componentModel="spring")` y `@Mapping` | S08 diapo 32; guía Paso 03 |
| **Validador** | Verifica **reglas de negocio** ("¿el nombre ya existe?") y lanza excepciones | S07 diapo 52; guía Paso 05 |
| **Utilidad (Utils)** | Métodos **estáticos** genéricos (fechas, redondeo). `final` y constructor privado | S07 diapo 53; guía Paso 05-B |
| **Lombok** | `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`, `@RequiredArgsConstructor`, `@Slf4j` | S08 diapo 10; guía Paso 01 |

**Las 4 reglas** (guía controller): el Mapper vive en el Controller; el Service recibe y devuelve **dominio**; el Controller no tiene lógica; input en el DTO y negocio en el Service/Validator.

### 4. Validaciones y manejo de errores

| Término | Qué es | Fuente |
|---|---|---|
| **Validación de input** | ¿Formato correcto? Bean Validation en el DTO: `@NotNull`, `@NotBlank`, `@Positive`, `@Size`, `@Email`, `@Min`/`@Max`, `@Pattern`, `@Future` | S08 diapos 54-56 |
| **`@Valid`** | Activa la validación del DTO en el Controller: `@RequestBody @Valid PlatoRequestDTO dto` | S08 diapo 56 |
| **Validación de negocio** | ¿Tiene sentido para el restaurante? Va en el Service/Validator con **excepciones propias** | S08 diapos 54 y 57 |
| **Excepción personalizada** | Extiende `RuntimeException`: `RecursoNoEncontradoException`, `ConflictoException`, `EstadoInvalidoException`, `ReglaDeNegocioException` | S08 diapo 60; guía Paso 07 |
| **try-catch** | Captura una excepción para decidir cómo tratarla | S08 diapo 58 |
| **GlobalExceptionHandler** | "El gerente": `@RestControllerAdvice` que intercepta todos los errores y responde uniforme | S08 diapos 59-64 |
| **`@ExceptionHandler`** | Atiende un tipo de excepción. El de `Exception.class` **va al final** (de lo específico a lo general) | S08 diapo 64 |
| **ErrorResponseDTO** | `timestamp`, `status`, `error`, `message`, `path` | S08 diapo 61 |

### 5. Streams, logs y documentación

| Término | Qué es | Fuente |
|---|---|---|
| **Streams** | `filter`, `map`, `findFirst().orElseThrow()`, `anyMatch`, `sorted`, `count`, `collect(groupingBy)`, `toList()` | S08 diapos 28-31; guía Paso 04 |
| **Logging** | Registro estructurado de lo que pasa; aporta **observabilidad** | S08 diapos 43-44 |
| **SLF4J / Logback / Log4j2** | SLF4J = API; Logback = implementación por defecto; Log4j2 = alternativa | S08 diapo 45 |
| **`@Slf4j`** | Lombok crea la variable `log` → `log.info(...)` | S08 diapo 47 |
| **Niveles** | DEBUG (desarrollo), INFO (flujo normal), WARN (algo raro), ERROR (fallo) | S08 diapo 48 |
| **OpenAPI** | El **estándar**: contrato YAML/JSON de la API | S08 diapo 41 |
| **Swagger UI** | La **herramienta** que muestra el contrato como página interactiva | S08 diapos 35-42 |
| **springdoc-openapi** | Genera el contrato desde el código: `@Tag`, `@Operation`, `@ApiResponse` (en una interfaz que implementa el Controller) | S08 diapos 38-40; guía Paso 08 |

### 6. Pruebas

| Término | Qué es | Fuente |
|---|---|---|
| **Prueba unitaria** | Prueba una clase aislada, sin BD ni Controller | S08 diapo 75 |
| **JUnit 5** | `@Test`, `@DisplayName`, `assertEquals`, `assertThrows` | Guía Paso 09 |
| **Mock (Mockito)** | Doble falso que tú controlas: "cuando te llamen con X, devuelve Y" | S08 diapo 76 |
| **`@Mock`** | Crea el doble vacío de una dependencia | S08 diapo 79 |
| **`@InjectMocks`** | Crea la clase **real** bajo prueba (SUT) e inyecta los mocks | S08 diapo 79 |
| **`verify(mock, times(1))`** | Comprueba que el mock sí fue llamado | S08 diapo 79 |
| **5 escenarios** | Éxito, no encontrado, duplicado, estado inválido, lista vacía (nunca `null`) | S08 diapo 79 |
| **Cobertura** | Líneas, funciones, ramas (if/else), condiciones (cada booleano true y false), caminos, ciclos (0 o más veces) | S07 diapos 12-16 |
| **Jacoco** | Mide la cobertura y genera el reporte HTML | HTML parcial sección 3 |
| **SonarQube** | Análisis de calidad: bugs, duplicados, vulnerabilidades, cobertura | S10 diapo 53 |

### 7. Persistencia

**Relacional (SQL)**

| Término | Qué es | Fuente |
|---|---|---|
| **BD relacional** | Datos en **tablas** relacionadas (lógica de predicados y teoría de conjuntos) | S09 diapo 6 |
| **Transacción** | Grupo de operaciones como una sola unidad | S09 diapos 7-9 |
| **ACID** | **A**tomicidad (todo o nada), **C**onsistencia (de estado válido a válido), a**I**slamiento (no se afectan entre sí), **D**urabilidad (lo confirmado persiste) | S09 diapos 8-9 |
| **Índice** | Ordena una columna para buscar rápido. Ocupa espacio y hace lentas las escrituras | S09 diapos 11-15 |
| **Normalización** | 1FN: datos atómicos. 2FN: sin dependencias parciales de la PK. 3FN: sin dependencias transitivas | S09 diapos 16-20 |
| **PK / FK** | La PK identifica cada fila; la FK apunta a la PK de otra tabla | S09 diapo 73 |
| **Agregación vs composición** | Agregación: las partes viven solas y se comparten. Composición: mueren con el todo | S09 diapo 21 |
| **Modelo ER** | Diagrama de tablas, columnas y relaciones | S09 diapo 23 |

**JPA / Spring Data**

| Término | Qué es | Fuente |
|---|---|---|
| **Spring Data JPA** | Mapea objetos a tablas y **genera el SQL** | S09 diapo 25 |
| **`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`** | Tabla, nombre, clave, generación del ID (`IDENTITY` = lo asigna la BD), columna | S09 diapos 28-30 y 44 |
| **`JpaRepository<Entidad, TipoId>`** | Trae `save`, `findById`, `findAll`, `deleteById`, `existsById`, `count`. Métodos por nombre (`existsByNombreIgnoreCase`). Un repositorio por entidad | S09 diapos 31-32 |
| **`@Query`** | Consulta a mano (JPQL o SQL nativo) | S09 diapo 33 |
| **`ddl-auto: update`** | Crea y actualiza tablas; solo desarrollo. `create` las borra; producción usa Flyway/Liquibase | S09 diapo 34 |
| **Relaciones** | `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany` (mejor entidad intermedia) | S09 diapos 40-42 |
| **LAZY vs EAGER** | LAZY carga al usarlo (recomendado); EAGER carga todo de una vez | S09 diapos 42-43 |
| **Problema N+1** | 1 consulta + N consultas extra. Se soluciona con `JOIN FETCH` o `@EntityGraph` | S09 diapo 43 |

**No relacional (NoSQL)**

| Término | Qué es | Fuente |
|---|---|---|
| **NoSQL** | Sin tablas, **esquema flexible** | S09 diapos 46-47 |
| **BASE** | Básicamente disponible, estado suave, eventualmente consistente | S09 diapo 49 |
| **MongoDB** | BD de **documentos** en BSON (clave-valor tipo JSON) | S09 diapo 50 |
| **Embebido vs referenciado** | Embebido: todo en un documento (1 a pocos, se lee junto). Referenciado: separados por `_id` (muchos a muchos, datos grandes) | S09 diapos 51-53 |
| **`@Document` / `MongoRepository`** | Equivalentes de `@Entity` / `JpaRepository`. La lógica no cambia, solo de dónde vienen los datos | S09 diapos 61-67 |

**Tipos de ID** (S09 diapos 72-78): Long secuencial (tablas internas, p. ej. categorías); UUID v4 (URLs públicas, no se adivina, fragmenta índices); UUID v7 (ordenado por tiempo, no fragmenta, pedidos con alto volumen); ObjectId (lo genera Mongo).

**Imágenes** (S09 diapos 69-70): binario (BLOB/BYTEA), GridFS en Mongo, o URL (se rompe si borran la imagen remota).

### 8. Seguridad

| Término | Qué es | Fuente |
|---|---|---|
| **Spring Security** | Framework estándar de autenticación y control de acceso | S10 diapo 6 |
| **Autenticación** | "¿**Quién eres**?" | S10 diapo 7 |
| **Autorización** | "¿**Qué puedes hacer**?": `permitAll()`, `hasRole("CHEF")`, `hasAnyRole(...)`, `@PreAuthorize` | S10 diapos 13-15 |
| **HTTP Basic** | Usuario:contraseña en **Base64** en `Authorization`. Codifica, no cifra; solo con HTTPS | S10 diapo 8; guía seguridad Paso 2 |
| **BCrypt** | Guarda las contraseñas con hash, nunca en texto plano | Guía seguridad Paso 2 |
| **OAuth2** | Login con proveedor externo (Google); la app recibe un token sin conocer tu contraseña | S10 diapo 9 |
| **JWT** | Token firmado y **stateless**: Header (algoritmo) . Payload (datos no sensibles, roles) . Signature (integridad). Con TTL | S10 diapos 11-12 |
| **Integridad** | ¿Llegó tal cual se envió? HTTPS, firmas digitales, HMAC | S10 diapo 16 |
| **SSL/TLS** | Cifra la comunicación; evita *man-in-the-middle* | S10 diapo 17 |
| **CORS** | El navegador bloquea peticiones a otro dominio salvo que el servidor las permita (preflight) | S10 diapos 18-19 |
| **OWASP Top 10** | Control de acceso roto, fallas criptográficas, inyección, diseño inseguro, mala configuración, componentes desactualizados, fallas de autenticación, fallas de integridad, falta de logs/monitoreo, SSRF | S10 diapos 21-23 |
| **CSRF** | Acciones en nombre del usuario sin que lo sepa; las APIs con JWT casi no son vulnerables (se desactiva) | S10 diapo 27 |
| **XSS** | Código del atacante ejecutado en el navegador de otro; se previene escapando HTML y con cabeceras | S10 diapo 28 |
| **Clickjacking** | Iframe invisible sobre un botón engañoso; se previene con `X-Frame-Options` | S10 diapo 29 |
| **DoS / DDoS** | Saturar la API con peticiones | S10 diapo 25 |

### 9. DevOps: Docker, ambientes y CI/CD

| Término | Qué es | Fuente |
|---|---|---|
| **Docker** | Empaqueta la app con sus dependencias en **contenedores** | S10 diapos 33-40 |
| **Contenedor vs VM** | El contenedor comparte el kernel: más ligero y rápido. La VM trae un SO completo | S10 diapo 34 |
| **Dockerfile** | La **receta** para construir la imagen | S10 diapos 37 y 39 |
| **Imagen** | El **kit de cocina**: plantilla inmutable | S10 diapo 39 |
| **Contenedor** | **Una cocina funcionando**: instancia de la imagen | S10 diapo 39 |
| **Registry (Docker Hub)** | La **bodega** de imágenes (`push`/`pull`) | S10 diapo 39 |
| **Build multietapa** | Compila en una etapa y copia solo el JAR a la final: imagen pequeña y segura | Guía Docker Paso 2 |
| **docker-compose** | Levanta varios contenedores juntos (API + BD) | Guía Docker Paso 4 |
| **Kubernetes** | **Orquestador**: despliega, escala, balancea y reinicia contenedores | S10 diapos 46-50 |
| **Ambientes** | DEV (local), QA (pruebas, igual a prod), PREPROD (idéntico a prod, aceptación), PROD (siempre estable) | S10 diapos 41-45 |
| **CI** | En cada push: compila, prueba y analiza automáticamente | S10 diapos 52-54 |
| **Delivery vs Deployment** | Delivery: siempre lista, despliegue manual. Deployment: a producción automático | S10 diapo 54 |
| **Pipeline** | Build → Test → Análisis → Deploy → Monitoreo | S10 diapo 57 |
| **GitHub Actions** | Workflow (`.yml` en `.github/workflows`), Events (push, PR), Jobs, Steps, Actions | S10 diapos 59-60 |
| **Secrets** | Credenciales en GitHub usadas como `${{ secrets.NOMBRE }}`. Si se filtra, se cambia | S10 diapos 62-64 |
| **Métricas DORA** | Indicadores del rendimiento del equipo | S10 diapo 55 |
| **DevOps** | **Cultura** que une desarrollo y operaciones. No es producto, servicio ni departamento | S10 diapos 66-68 |
| **CALMS** | Cultura, Automatización, Lean, Medición, Sharing | S10 diapo 70 |
| **IT as a Service** | TI como proveedor interno con catálogo, acuerdos y costos | S10 diapos 71-72 |

### 10. Diagramas

| Diagrama | Muestra | Fuente |
|---|---|---|
| **Clases** | Objetos del **dominio** y relaciones (sin DTOs, anotaciones ni entidades JPA) | S08 diapos 5-9 y 87 |
| **Componentes general** | El sistema y los sistemas externos | S07 diapo 56 |
| **Componentes específico** | Módulos internos: controllers, services, repositories, BD | S07 diapo 57 |
| **Secuencia** | Mensajes **en el tiempo** para un caso de uso: líneas de vida, mensajes, activación, `alt`/`opt`/`loop`/`par` | S08 diapos 81-85 |
| **Entidad-relación** | Tablas y relaciones (SQL) | S09 diapo 23 |
| **Documentos** | Colecciones en Mongo (embebido o referenciado) | S09 diapo 55 |
| **Despliegue** | Nodos (cubos), artefactos (JAR, config), dispositivos, enlaces | S10 diapos 75-78 |
