# Simon Says Android Game with Animations

Este proyecto es un juego de "Simon Says" para dispositivos Android desarrollado en Android Studio. El juego sigue el clásico juego de memoria en el que el dispositivo emite una secuencia de colores y sonidos, y el jugador debe repetir esa secuencia presionando los botones en el mismo orden.

## Funcionamiento del Código

### Paquetes y Librerías

El código está organizado en un solo paquete `com.example.simonsaysord` y hace uso de algunas clases predefinidas de Android, como `AppCompatActivity`, `Bundle`, `View`, `Button`, `MediaPlayer`, `Handler`, entre otras.

### Clase MainActivity

La clase `MainActivity` es la actividad principal de la aplicación y contiene la lógica principal del juego.

#### Inicialización y Configuración

- **`onCreate()`**: En este método se inicializan los componentes de la interfaz de usuario, como los botones y el reproductor de medios. También se establecen los listeners para los botones.
- **`onDestroy()`**: Se encarga de liberar recursos al destruir la actividad, como detener el reproductor de medios.

#### Funciones Principales

- **`startSimonSays()`**: Esta función inicia el juego generando la primera secuencia de colores y sonidos y reproduciéndola.
- **`playSimonSequence()`**: Reproduce la secuencia de colores y sonidos para que el usuario la repita.
- **`restartGame()`**: Reinicia el juego, limpiando la secuencia y generando una nueva.

#### Manejo de Eventos

- **`onButtonClick()`**: Maneja el evento de clic en los botones. Comprueba si el botón pulsado corresponde al siguiente en la secuencia y maneja la derrota si no es así.
- **`handleDefeat()`**: Oculta los botones, reproduce el sonido de derrota y reinicia el juego después de un breve retardo.
### Animaciones

Para mejorar la experiencia del usuario, se han añadido animaciones de "shake" (sacudida) a los botones cuando se presionan, tanto por el jugador como por la secuencia de Simon. Estas animaciones hacen que los botones se muevan ligeramente de un lado a otro y roten un poco, proporcionando una retroalimentación visual clara.

#### Detalles de la Animación

La animación se define en un archivo XML `res/anim/shake.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:interpolator="@android:anim/cycle_interpolator">
    <translate
        android:duration="25"
        android:fromXDelta="-5%"
        android:toXDelta="5%"
        android:repeatCount="5"
        android:repeatMode="reverse"/>
    <rotate
        android:duration="25"
        android:fromDegrees="-3"
        android:toDegrees="3"
        android:repeatCount="5"
        android:repeatMode="reverse"/>
</set>
```
### Interfaz de Usuario (XML)

El diseño de la interfaz de usuario se define en el archivo `activity_main.xml`. Consiste en un GridLayout con cuatro botones de colores que representan las opciones para el jugador.

## Instalación y Uso

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en Android Studio.
3. Compila y ejecuta la aplicación en tu dispositivo Android.

¡Disfruta del juego de Simon Says!

El video se ve un poco raro por que el emulador explota mi pc esta en el repositorio en la carpeta VideoApp

