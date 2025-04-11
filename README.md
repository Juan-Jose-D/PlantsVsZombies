# Plants vs Zombies

Este es un proyecto académico inspirado en el clásico juego Plants vs Zombies, desarrollado completamente en Java con interfaz gráfica y utilizando una arquitectura organizada en capas. El objetivo es simular la dinámica del juego original, integrando lógica de juego, animaciones básicas y estructuras de datos.

El juego se hizo para aprender programación orientada a objetos.

## 🛠️ Tecnologías y herramientas

### Lenguaje: Java

<img src="https://github.com/user-attachments/assets/390db7d6-5815-42fa-86f3-d097c13514a2" width="300"/>

### IDE: IntelliJ IDEA

<img src="https://github.com/user-attachments/assets/1ab57c18-579a-451a-8f64-a070ef03e0f4" width="300"/>



### Interfaz gráfica: Java Swing


<img src="https://github.com/user-attachments/assets/4975a6ca-02e6-49d4-b088-c654f6da96cd" width="300"/>


Arquitectura: Separación en capas domain y presentation

Estructura de datos: Matrices para modelar el tablero del juego y la lógica de ubicación de plantas y zombies

## 🧱 Estructura del proyecto

```
/src
│
├── domain/             # Lógica
│   ├── Plant.java
│   ├── PoobVsZombies.java
│   ├── Zombie.java
│   ├── Board.java
│   └── ...
│
├── presentation/       # Interfaz gráfica
│   ├── PoobVSZombiesGUI.java
│   └── 
│── resources/          # Recursos
│   ├── 
└──
```

## 🎮 Características principales

🌻 Colocación de plantas en un tablero de matriz

🧟‍♂️ Aparecimiento y movimiento automático de zombies

💥 Interacción entre plantas y zombies (ataques, colisiones)

⏱️ Temporizador para aparecimiento de los zombies

🖱️ Interfaz gráfica con eventos por clic



## 📌 Notas de diseño

El tablero del juego está implementado como una matriz bidimensional, donde cada celda puede contener una planta, un zombie o estar vacía.

La lógica del juego reside en el paquete domain, permitiendo separar claramente la presentación de la lógica de negocio.

La interfaz gráfica, construida con Java Swing, permite al usuario interactuar con el juego de forma visual e intuitiva.


## 👨‍💻 Autor

Juan José Díaz Gómez

Estudiante de Ingeniería de Sistemas

Escuela Colombiana de Ingeniería Julio Garavito

GitHub: @https://github.com/Juan-Jose-D

