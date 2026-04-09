# 🎮 Maze Race — Juego Multijugador en Tiempo Real por UDP

Un juego de carreras de laberintos en tiempo real para **dos jugadores** que se comunican a través de **sockets UDP**. Los jugadores compiten para cruzar 4 laberintos consecutivos: el primero en completar cada mapa avanza al siguiente. Si un jugador toca una pared, regresa al inicio del mapa actual.

---

## 📋 Tabla de Contenidos

- [Descripción del juego](#-descripción-del-juego)
- [Arquitectura](#-arquitectura)
- [Patrones de diseño](#-patrones-de-diseño)
- [Árbol de directorios](#-árbol-de-directorios)
- [Requisitos](#-requisitos)
- [Configuración](#-configuración)
- [Cómo ejecutar](#-cómo-ejecutar)
- [Comunicación UDP](#-comunicación-udp)

---

## 🕹️ Descripción del juego

- **Modo**: 2 jugadores en red local (o internet con IP pública)
- **Objetivo**: Cruzar los 4 laberintos antes que el oponente
- **Mecánica de movimiento**: Se mantiene presionada una tecla direccional (↑ ↓ ← →) para desplazarse continuamente
- **Penalización**: Al colisionar con una pared, el jugador vuelve a la posición inicial del mapa
- **Progresión**: El primer jugador en alcanzar la salida (puerta) avanza al siguiente laberinto
- **Elementos del mapa**: Paredes, monedas coleccionables y puertas de salida

---

## 🏗️ Arquitectura

El proyecto está dividido en dos módulos Maven independientes:

| Módulo       | Rol                                                                 |
|--------------|---------------------------------------------------------------------|
| `GameServer` | Servidor UDP centralizado. Recibe posiciones, gestiona el estado y hace broadcast a todos los clientes  |
| `GameClient` | Cliente con interfaz gráfica Swing. Captura input, renderiza el juego y envía/recibe posiciones por UDP |

---

## 🧩 Patrón de Diseño

Ambos patrones fueron implementados siguiendo exclusivamente las definiciones y estructuras de **[Refactoring Guru](https://refactoring.guru/design-patterns/)**.

### 👁️ Observer

**Propósito**: Notificar automáticamente a los componentes interesados cuando el estado del juego cambia, sin acoplar el emisor a los receptores.

| Elemento              | Clase / Interfaz                        | Rol                                               |
|-----------------------|-----------------------------------------|---------------------------------------------------|
| Interfaz del Observer | `IStateListener`                        | Define `onStateChanged(GameState state)`          |
| Subject (Observable)  | `NetworkManager` / `UdpConnectionManager` | Recibe datos UDP y notifica a los listeners     |
| Observers concretos   | `SwingRenderer`, `ConsoleRenderer`      | Reaccionan al nuevo estado actualizando la UI     |

GameState cambia
│
▼
IStateListener.onStateChanged(state)
│
├──► SwingRenderer   → repinta la ventana Swing
└──► ConsoleRenderer → imprime en consola (debug)

---

## Estructura del Proyecto (Juego Multijugador en Tiempo Real por UDP)

```
JuegoSoftware/
├── GameClient/                          # Módulo del cliente (interfaz gráfica + red)
│   ├── pom.xml
│   └── src/main/java/org/kami/
│       ├── Main.java                    # Punto de entrada del cliente
│       ├── audio/                       # Capa de audio
│       │   ├── IMusicPlayer.java
│       │   ├── ISoundEffect.java
│       │   ├── MusicPlayer.java         # Música de fondo en bucle
│       │   └── SoundEffect.java         # Efectos: moneda, pared, colisión
│       ├── client/                      # Núcleo de red y renderizado
│       │   ├── AppConfig.java
│       │   ├── ConsoleRenderer.java     # Renderer de debug por consola (Observer)
│       │   ├── NetworkManager.java      # Gestiona envío/recepción UDP y notifica listeners
│       │   ├── SwingRenderer.java       # Renderer gráfico Swing (Observer)
│       │   └── UdpConnection.java       # Abstracción del socket UDP
│       ├── config/                      # Configuración y lectura de propiedades
│       │   ├── element/
│       │   │   ├── CharacterStatus.java
│       │   │   ├── ConfigPlayer.java
│       │   │   └── Player.java          # Modelo del jugador local
│       │   ├── maps/
│       │   │   ├── IMapsHandler.java
│       │   │   └── MapReader.java       # Lee y parsea los archivos de mapa
│       │   ├── IConfigReader.java
│       │   ├── ILayoutConfig.java
│       │   ├── IUDPConfig.java
│       │   ├── LayoutConfig.java
│       │   ├── PropertiesManager.java   # Lee application.properties
│       │   └── UDPConfig.java
│       ├── factory/                     # Creación de personajes
│       │   ├── CharacterBuilder.java
│       │   └── ImageBallCreator.java
│       ├── shared/                      # Contratos compartidos cliente↔servidor
│       │   ├── GameState.java           # Estado global del juego (posiciones, nivel)
│       │   ├── IGameRenderer.java
│       │   ├── INetworkConnection.java
│       │   └── IStateListener.java      # Interfaz Observer
│       └── view/                        # Capa de presentación
│           ├── CollisionManager.java    # Lógica de colisiones con paredes
│           ├── Layout.java              # Panel principal; gestiona niveles y movimiento
│           ├── MainWindow.java          # Ventana Swing raíz
│           └── maps/
│               ├── elements/            # Elementos visuales del mapa
│               │   ├── BackgroundLoader.java
│               │   ├── Coin.java
│               │   ├── CoinAnimator.java
│               │   ├── CoinService.java
│               │   ├── Door.java        # Puerta de salida del laberinto
│               │   ├── GameMap.java     # Representa un laberinto completo
│               │   ├── IBackgroundLoader.java
│               │   ├── ICoinAnimator.java
│               │   ├── ICoinService.java
│               │   ├── ITexturedElement.java
│               │   ├── UpdateCoins.java
│               │   └── Wall.java
│               └── mapelementsfactory/  # Factory Method
│                   ├── IMapElement.java
│                   ├── IMapElementFactory.java
│                   ├── MapElementFactory.java  # Fábrica concreta
│                   └── MapElementType.java     # Enum: WALL, COIN, DOOR
│
└── GameServer/                          # Módulo del servidor UDP
├── pom.xml
└── src/main/java/org/kami/
├── Main.java                    # Punto de entrada del servidor
├── config/
│   ├── IConfigReader.java
│   └── PropertiesManager.java
├── server/
│   ├── IMessageHandler.java     # Contrato para handlers de mensajes UDP
│   ├── MoveHandler.java         # Procesa mensajes de movimiento
│   ├── UdpBroadcaster.java      # Reenvía el estado a todos los clientes
│   └── UdpConnectionManager.java # Escucha en el puerto UDP y despacha handlers
└── shared/
├── GameState.java
├── IGameRenderer.java
├── INetworkConnection.java
└── IStateListener.java
```
---

## ✅ Requisitos

- **Java 17+**
- **Maven 3.8+**
- Ambas máquinas deben poder comunicarse por **UDP** (misma red local o con puertos abiertos)

---

## ⚙️ Configuración

### Servidor — `GameServer/src/main/resources/application.properties`

```properties
port=5000
```

### Cliente — `GameClient/src/main/resources/application.properties`

```properties
# Conexión UDP
udp.server.host=<IP_DEL_SERVIDOR>
udp.server.port=5000
udp.local.port=<PUERTO_LOCAL_DEL_CLIENTE>

# Identificador del jugador (1 o 2)
player.id=1

# Recursos de audio
music.path=sounds/background.wav
sound.coin=sounds/coin_sound.wav
sound.wall=sounds/wall_collision_sound.wav
sound.player=sounds/player_collision_sound.wav
```

> Cada jugador debe configurar su propio `player.id` y `udp.local.port` distinto.

---

## ▶️ Cómo ejecutar

**1. Compilar ambos módulos:**

```bash
cd GameServer && mvn package
cd ../GameClient && mvn package
```

**2. Iniciar el servidor (una sola instancia):**

```bash
cd GameServer
java -jar target/GameServer-*.jar
```

**3. Iniciar el cliente en cada máquina:**

```bash
cd GameClient
java -jar target/GameClient-*.jar
```

---

## 📡 Comunicación UDP

- El servidor escucha en el puerto configurado (`5000` por defecto).
- Cada cliente envía su posición `(x, y, level)` al servidor en cada movimiento.
- El servidor recibe el paquete, actualiza el `GameState` y hace **broadcast** de ese estado a todos los clientes conectados.
- Los clientes reciben el broadcast y el `SwingRenderer` (Observer) repinta la pantalla con la posición actualizada del oponente.
- No hay handshake ni confirmación de entrega — la naturaleza de UDP prioriza la **velocidad** sobre la fiabilidad, adecuada para un juego en tiempo real.
