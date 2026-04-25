# Documentación de UI - Aplicación de Coworking

## Estructura de la Interfaz Implementada

La aplicación cuenta con una arquitectura de navegación simple basada en dos pantallas principales:

### Pantalla Principal (SpaceListScreen)
- **TopAppBar** con título "Espacios de Coworking"
- **LazyColumn** con lista de espacios de coworking
- **BottomNavigationBar** con 3 pestañas (Inicio, Explorar, Perfil)

### Pantalla de Detalles (SpaceDetailScreen)
- **TopAppBar** con título "Detalles del Espacio" y botón de retorno
- **Column** con scroll vertical mostrando información completa del espacio
- **Button** para reservar espacio
- **BottomNavigationBar** consistente con la pantalla principal

## Lista de Composables Creados

### Pantallas
- `SpaceListScreen` - Pantalla principal con lista de espacios
- `SpaceDetailScreen` - Pantalla de detalles de un espacio específico

### Componentes
- `CoworkingSpaceCard` - Tarjeta individual para cada espacio de coworking
- `BottomNavBar` - Barra de navegación inferior

## Identificación de Componentes Reutilizables

### CoworkingSpaceCard
**Reutilizable:** Sí
**Uso:** En SpaceListScreen para mostrar cada espacio en la lista
**Características:**
- Layout consistente con imagen placeholder
- Información estructurada (nombre, ubicación, capacidad, precio, disponibilidad)
- IconButton de favorito funcional
- Clickable para navegación

### BottomNavBar
**Reutilizable:** Sí
**Uso:** En ambas pantallas (SpaceListScreen y SpaceDetailScreen)
**Características:**
- 3 pestañas fijas (Inicio, Explorar, Perfil)
- Estado de selección mantenido
- Callback para manejo de navegación
- Iconos de Material Design consistentes

## Justificación de la Organización de la Interfaz

### Principios de Diseño Aplicados

1. **Simplicidad:** Interfaz minimalista con navegación clara de 2 niveles (lista → detalles)

2. **Consistencia:** BottomNavBar presente en ambas pantallas para navegación coherente

3. **Reutilización:** Componentes modulares (CoworkingSpaceCard, BottomNavBar) para mantener consistencia

4. **Jerarquía Visual:** TopAppBar para títulos, LazyColumn para listas, Cards para elementos individuales

5. **Accesibilidad:** Texto en español, iconos descriptivos, navegación intuitiva

### Organización por Capas

- **Pantallas (screens/):** Lógica de navegación y layout principal
- **Componentes (components/):** Elementos reutilizables y modulares
- **Tema (theme/):** Estilos y colores consistentes

### Navegación Justificada

- **BottomNavigation:** Mantiene contexto de aplicación en todas las pantallas
- **Click en tarjetas:** Navegación natural de lista a detalles
- **Botón de retorno:** Navegación jerárquica estándar

Esta organización permite escalabilidad, mantenibilidad y una experiencia de usuario coherente.
