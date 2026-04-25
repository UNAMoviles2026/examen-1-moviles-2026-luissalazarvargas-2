# API — CoworkingSpaces

## Endpoint 1 — Crear Reserva

### `POST /spaces/{id}/reservations`

Crea una nueva reserva para un espacio de coworking específico. El sistema valida que el espacio exista y esté disponible para el horario solicitado antes de persistir la reserva.

---

#### Solicitud

**Parámetro de Ruta**

| Parámetro | Tipo | Requerido | Descripción         |
|---|---|-----------|---------------------|
| `id` | `integer` | Si        | Identificador único |

**Header**

```text
Content-Type: application/json
Authorization: Bearer <token>
```

**Body — `CreateReservationRequest`**

```json
{
  "userId": "usr_48a2f1c9",
  "startTime": "2026-05-10T09:00:00Z",
  "endTime": "2026-05-10T12:00:00Z",
  "attendees": 3,
  "notes": "Need projector access"
}
```

| Campo | Tipo      | Requerido | Descripción |
|---|-----------|-----------|---|
| `userId` | `string`  | Si        | Identificador del usuario que realiza la reserva |
| `startTime` | `datetime` | Si        | Hora de inicio de la reserva en UTC |
| `endTime` | `datetime` | Si        | Hora de finalización de la reserva en UTC. Debe ser posterior a `startTime` |
| `attendees` | `integer` | Si        | Número de asistentes. No debe exceder la capacidad del espacio |
| `notes` | `string`  | No        | Instrucciones o solicitudes adicionales opcionales |

---

#### Responses

**`201 Created` — Reserva creada exitosamente**

```json
{
  "reservationId": "res_0091ac",
  "spaceId": 3,
  "spaceName": "Sala Innovación Norte",
  "userId": "usr_48a2f1c9",
  "startTime": "2026-05-10T09:00:00Z",
  "endTime": "2026-05-10T12:00:00Z",
  "totalHours": 3,
  "totalCost": 45.00,
  "currency": "USD",
  "status": "CONFIRMED",
  "createdAt": "2026-04-25T08:30:00Z"
}
```

| Campo | Tipo | Descripción |
|---|---|---|
| `reservationId` | `string` | Identificador único de la nueva reserva |
| `spaceId` | `integer` | ID del espacio reservado |
| `spaceName` | `string` | Nombre a mostrar del espacio reservado |
| `userId` | `string` | ID del usuario que realizó la reserva |
| `startTime` | `datetime` | Hora de inicio confirmada |
| `endTime` | `datetime` | Hora de finalización confirmada |
| `totalHours` | `number` | Duración en horas |
| `totalCost` | `number` | Cargo total basado en `pricePerHour × totalHours` |
| `currency` | `string` | Código de moneda (ej. `"USD"`) |
| `status` | `string` | Estado de la reserva: `CONFIRMED`, `PENDING`, `CANCELLED` |
| `createdAt` | `datetime` | Marca de tiempo de la creación de la reserva |

**`400 Bad Request` — Error de validación**

```json
{
  "error": "VALIDATION_ERROR",
  "message": "endTime must be after startTime.",
  "field": "endTime"
}
```

**`404 Not Found` — El espacio no existe**

```json
{
  "error": "NOT_FOUND",
  "message": "Coworking space with id 3 was not found."
}
```

**`409 Conflict` — Espacio no disponible para el horario solicitado**

```json
{
  "error": "SPACE_UNAVAILABLE",
  "message": "The selected space is already reserved for the requested time slot."
}
```

---

## Endpoint 2 — Registrar Nuevo Espacio de Coworking

### `POST /spaces`

Crea un nuevo listado de espacio de coworking en el sistema. Este endpoint está destinado a administradores o propietarios de espacios que gestionan el inventario de la plataforma.

---

#### Solicitud

**Encabezados**

```text
Content-Type: application/json
Authorization: Bearer <admin-token>
```

**Cuerpo — `CreateSpaceRequest`**

```json
{
  "name": "Sala Innovación Norte",
  "description": "Espacio amplio con iluminación natural, ideal para equipos creativos.",
  "location": "San José, Costa Rica",
  "capacity": 10,
  "pricePerHour": 15.00,
  "imageUrl": "https://cdn.coworkingspaces.com/images/sala-norte.jpg",
  "amenities": ["wifi", "projector", "whiteboard", "coffee"],
  "isAvailable": true
}
```

| Campo | Tipo | Requerido | Descripción |
|---|---|-----------|---|
| `name` | `string` | Si        | Nombre a mostrar del espacio (máx 120 caracteres) |
| `description` | `string` | Si        | Descripción completa mostrada en la vista de detalle |
| `location` | `string` | Si        | Ciudad o dirección del espacio |
| `capacity` | `integer` | Si        | Número máximo de personas que el espacio puede acomodar |
| `pricePerHour` | `number` | Si        | Costo en USD por hora de uso. Debe ser mayor a 0 |
| `imageUrl` | `string` | No        | URL de la imagen principal para el listado del espacio |
| `amenities` | `string[]` | No        | Lista de comodidades disponibles|
| `isAvailable` | `boolean` | Si        | Indica si el espacio está disponible de inmediato para reservas |

---

#### Respuestas

**`201 Created` — Espacio registrado exitosamente**

```json
{
  "spaceId": 12,
  "name": "Sala Innovación Norte",
  "description": "Espacio amplio con iluminación natural, ideal para equipos creativos.",
  "location": "San José, Costa Rica",
  "capacity": 10,
  "pricePerHour": 15.00,
  "currency": "USD",
  "imageUrl": "https://cdn.coworkingspaces.com/images/sala-norte.jpg",
  "amenities": ["wifi", "projector", "whiteboard", "coffee"],
  "isAvailable": true,
  "createdAt": "2026-04-25T08:45:00Z"
}
```

| Campo | Tipo | Descripción |
|---|---|---|
| `spaceId` | `integer` | ID único generado automáticamente del nuevo espacio |
| `name` | `string` | Nombre del espacio tal como está almacenado |
| `description` | `string` | Descripción completa |
| `location` | `string` | Cadena de texto de la ubicación |
| `capacity` | `integer` | Capacidad máxima |
| `pricePerHour` | `number` | Tarifa por hora |
| `currency` | `string` | Moneda del precio |
| `imageUrl` | `string` | URL de la imagen principal |
| `amenities` | `string[]` | Lista de comodidades |
| `isAvailable` | `boolean` | Estado de disponibilidad actual |
| `createdAt` | `datetime` | Marca de tiempo de creación |

**`400 Bad Request` — Campos faltantes o inválidos**

```json
{
  "error": "VALIDATION_ERROR",
  "message": "pricePerHour must be greater than 0.",
  "field": "pricePerHour"
}
```

**`401 Unauthorized` — Token faltante o inválido**

```json
{
  "error": "UNAUTHORIZED",
  "message": "A valid admin token is required to perform this action."
}
```

**`422 Unprocessable Entity` — Fallo de validación semántica**

```json
{
  "error": "INVALID_CAPACITY",
  "message": "Capacity must be between 1 and 500."
}
```

---