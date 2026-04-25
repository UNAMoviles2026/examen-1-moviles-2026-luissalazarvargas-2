# Arquitectura — CoworkingSpaces

## 1. Tipo de Aplicación

La aplicación es nativa de Android, por lo que sería desarrollada directamente para plataforma Android usando **Kotlin** y **Jetpack Compose**.

### Justificación

Elegí un tipo de aplicación nativa y no una híbrida o demás (utilizando Flutter o React Native), debido a que:

- Acceso completo a funcines nativas: El desarrollo nativo otorga acceso las funcionalidades de Android, incluyendo sensores, notificaciones y componentes de hardware — todo esto relevante para posibles integraciones futuras como geolocalización y notificaciones push.
- Rendimiento: Las aplicaciones nativas se ejecutan sin un puente de JavaScript o sin compilaciones externas, lo que resulta en una mejor renderizacion y fluidez.
- Alineación con Jetpack Compose: Al utilizar Jetpack Compose, el cual es un kit de herramientas de interfaz de usuario nativo de Android de primer nivel, se mantendría de mejor manera el ecosistema android, es un uso optimo.
- Madurez del ecosistema: El ecosistema brinda muchas librerias ya desarrolladas que facilitarian la implementacion de la aplicacion.

---

## 2. Patrón de Arquitectura — MVVM

El patrón arquitectónico que utilizaría es **Model-View-ViewModel MVVM**.

### Justificación

- **Separación de responsabilidades**: Es un patron muy organizado y claro, la lógica de negocio y la lógica de la UI están claramente desacopladas.
- **Escalabilidad**: La aplicación necesita de escalabilidad, por lo que a medida que la aplicación crece, cada característica puede introducir su propio ViewModel y repositorio sin afectar a las demás.
- **Estándar de la industria**: MVVM es el patrón recomendado de manera oficial por Google para el desarrollo en Android con Jetpack Compose.

---

## 3. Diagrama de Arquitectura

```text
┌─────────────────────────────────────────────────────┐
│                      Capa de UI                      │
│                  (Jetpack Compose)                   │
│                                                      │
│   ┌──────────────────┐   ┌────────────────────────┐ │
│   │  SpaceListScreen │   │  SpaceDetailScreen     │ │
│   └────────┬─────────┘   └──────────┬─────────────┘ │
│            │ observa el estado      │ observa el est.│
└────────────┼────────────────────────┼────────────────┘
             │                        │
┌────────────▼────────────────────────▼────────────────┐
│                 Capa de ViewModel                     │
│                                                       │
│   ┌───────────────────────────────────────────────┐  │
│   │            SpaceViewModel                     │  │
│   │  - uiState: StateFlow<SpaceUiState>           │  │
│   │  - onSpaceSelected(id)                        │  │
│   │  - onReservationRequested(spaceId)            │  │
│   └──────────────────┬────────────────────────────┘  │
└──────────────────────┼────────────────────────────────┘
                       │ llama a
┌──────────────────────▼────────────────────────────────┐
│                 Capa de Repositorio                    │
│                                                        │
│   ┌────────────────────────────────────────────────┐  │
│   │         ISpaceRepository (interface)            │  │
│   └──────────────────┬─────────────────────────────┘  │
│                      │ implementado por                │
│   ┌──────────────────▼─────────────────────────────┐  │
│   │        SpaceRepository                         │  │
│   │  - devuelve List<CoworkingSpace>               │  │
│   └────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────┘
                       │ devuelve
┌──────────────────────▼────────────────────────────────┐
│                  Capa de Modelo                        │
│                                                        │
│   ┌──────────────────────────────────────────────┐    │
│   │  data class CoworkingSpace(                  │    │
│   │    id, name, imageUrl, description,          │    │
│   │    location, capacity, pricePerHour,         │    │
│   │    isAvailable                               │    │
│   │  )                                           │    │
│   └──────────────────────────────────────────────┘    │
└────────────────────────────────────────────────────────┘
```

---

## 4. Flujo General del Sistema

El sistema sigue un flujo de datos unidireccional, consistente con las mejores prácticas de MVVM:

1. **El usuario abre la aplicación** → El `NavHost` resuelve hacia `SpaceListScreen` como el destino inicial.
2. **SpaceListScreen** observa `SpaceViewModel.uiState`, lo que desencadena `SpaceRepository.getSpaces()` durante la inicialización.
3. **MockSpaceRepository** devuelve una lista de objetos `CoworkingSpace` (datos simulados estáticos).
4. El **ViewModel** actualiza su `StateFlow`, y el composable se recompone para mostrar la lista.
5. **El usuario toca la tarjeta de un espacio** → Se llama a `SpaceViewModel.onSpaceSelected(id)` → Se desencadena la navegación hacia `SpaceDetailScreen` enviando el ID del espacio seleccionado.
6. **SpaceDetailScreen** recupera el espacio seleccionado desde el estado del `ViewModel` y renderiza los detalles completos.
7. **El usuario toca "Reservar"** → Se envía un evento de reserva al `ViewModel` (en este PoC, actualiza un flag local o muestra un diálogo de confirmación).

En un escenario de producción, los pasos 3 y 7 implicarían llamadas HTTP a una API REST, con el repositorio utilizando Retrofit y manejando las respuestas asíncronas a través de Kotlin Coroutines.