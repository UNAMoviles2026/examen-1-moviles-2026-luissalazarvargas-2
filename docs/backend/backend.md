# Backend — CoworkingSpaces

Pensado en C#
## 1. Arquitectura en Capas

El backend estaría estructurado en capas claramente separadas, cada una con una responsabilidad.

```text
┌──────────────────────────────────────┐
│         Capa de Presentación          │
│         (Controllers + DTOs)          │
└──────────────────┬───────────────────┘
                   │ llama a
┌──────────────────▼───────────────────┐
│          Capa de Aplicación           │
│         (Services + Mappers)          │
└──────────────────┬───────────────────┘
                   │ llama a
┌──────────────────▼───────────────────┐
│        Capa de Infraestructura        │
│            (Repositories)             │
└──────────────────┬───────────────────┘
                   │ consulta a
┌──────────────────▼───────────────────┐
│            Capa de Datos              │
│     (Entity Framework Core + DB)      │
└──────────────────────────────────────┘
```

---

## 2. Rol de Cada Capa

### 2.1 Capa de Presentación — Controllers & DTOs

Los Controllers son el punto de entrada de cada solicitud HTTP. Su única responsabilidad es recibir la solicitud, validar la entrada a nivel superficial, delegar al Service apropiado y devolver una respuesta HTTP con el DTO correspondiente.
Es decir, los controllers deben estar libres de lógica de negocio. Son orquestadores.

Los DOTs son clases de C# utilizadas exclusivamente para la comunicación entre el cliente y la API.
Dentro de los DTOs tenemos los Requests y los Responses. Los Request modelan los datos que el cliente envía y los Responses los datos que el servidor devuelve.

### 2.2 Capa de Aplicación — Services & Mappers

Los services contienen toda la lógica de negocio. Coordinan entre el repositorio y cualquier operación adicional.

Un servicio recibe un Request DTO, aplica reglas, llama al repositorio y devuelve un Response DTO.

Los Mappers son responsables de convertir entre entidades de dominio y DTOs. Para así evitar que el servicio asigne campos manualmente y mantiene la lógica de transformación centralizada.

### 2.3 Capa de Infraestructura — Repositories

Los Repositories se ocupan de abstraer todo el acceso a datos. Implementan interfaces definidas en la capa de aplicación, manteniendo la capa de servicios independiente de cualquier tecnología de base de datos específica.

El repositorio es la única capa a la que se le permite interactuar con `DbContext` (Entity Framework Core en este caso).

### 2.4 Capa de Datos — Entity Framework Core

Las entidades de dominio son clases planas de C# mapeadas a tablas de la base de datos a través de EF Core. Representan el estado real del sistema y no deben exponerse directamente al cliente.

## 3. Flujo de la Solicitud: Cliente → Base de datos

Lo siguiente describe el ciclo de vida completo de una solicitud `POST /api/spaces/{id}/reservations`:

```text
Android App
       │
       │  HTTP POST /api/spaces/3/reservations
       │  Body: { userId, startTime, endTime }
       ▼
SpacesController
       │  Recibe HttpRequest, deserializa el body en el DTO CreateReservationRequest
       │  Llama al servicio
       ▼
SpaceService
       │  Valida reglas de negocio:
       │    - ¿Existe el espacio?
       │    - ¿Está disponible para el horario solicitado?
       ▼
SpaceRepository
       │  Pasa a guardar la entidad Reservation
       ▼
Base de Datos
       ▼
SpaceRepository → SpaceService
       │  Devuelve la entidad Reservation guardada
       │  El Mapper convierte la entidad → DTO ReservationResponse
       ▼
SpacesController
       │  Devuelve HTTP 201 Created
       │  Body: DTO ReservationResponse
       ▼
Android App
       Recibe la respuesta, actualiza el estado de la UI
```

---

## 4. Justificación de Decisiones de Diseño

| Decisión                                          | Justificacion                                                                                                                               |
|---------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| **ASP.NET Core (.NET 8)**                         | Framework maduro, con alto rendimiento con tipado fuerte, amplia adopción en la industria.                                                  |
| **Patrón Repository**                             | Desacopla el acceso a datos de la lógica de negocio, para una fácil sustitución de las fuentes de datos o motores, sin tocar los servicios. |
| **DTOs en lugar de entidades directas**           | Oculta detalles del modelo y permite que el API contract pueda cambiar de manera independiente al esquema de la base de datos.              |
| **Services y repositories basados en interfaces** | Permite la inyección de dependencias y alta escalabilidad                                                                                   |