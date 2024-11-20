package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class PlantsVsZombiesGUI extends JFrame {
    private Juego juego;
    private JPanel gridPanel;
    private JLabel solesLabel;
    private JButton plantaSeleccionada;
    private String dificultad;

    public PlantsVsZombiesGUI(Juego juego) {
        if (juego.getTablero() == null) {
            Tablero tablero = new Tablero(5, 8);
            juego.setTablero(tablero);
        }

        this.juego = juego;
        setTitle("Plantas vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        prepareElements();
        prepareGameElements();
        prepareActions();

        setSize(800, 600);
        setLocationRelativeTo(null);

        mostrarPantallaInicial();
        setVisible(true);
    }

    private void mostrarPantallaInicial() {
        JPanel panelInicial = new JPanel(new BorderLayout());

        // Título del juego
        JLabel tituloLabel = new JLabel("Plantas vs Zombies", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 36));
        panelInicial.add(tituloLabel, BorderLayout.NORTH);

        // Panel de selección de dificultad
        JPanel dificultadPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        dificultadPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        String[] nivelesDificultad = {"Fácil", "Medio", "Difícil"};
        for (String nivel : nivelesDificultad) {
            JButton botonNivel = new JButton(nivel);
            botonNivel.setFont(new Font("Arial", Font.PLAIN, 20));
            botonNivel.addActionListener(e -> iniciarJuego(nivel));
            dificultadPanel.add(botonNivel);
        }

        panelInicial.add(dificultadPanel, BorderLayout.CENTER);

        // Limpiar el frame y mostrar la pantalla inicial
        getContentPane().removeAll();
        getContentPane().add(panelInicial);
        revalidate();
        repaint();
    }

    private void iniciarJuego(String dificultad) {
        this.dificultad = dificultad;

        juego = new Juego();
        Tablero tablero = new Tablero(5, 8);
        juego.setTablero(tablero);

        configurarZombiesPorDificultad(dificultad);

        getContentPane().removeAll();
        setLayout(new BorderLayout());

        prepareElements();
        prepareGameElements();
        prepareActions();

        revalidate();
        repaint();
    }

    private void configurarZombiesPorDificultad(String dificultad) {
        switch (dificultad) {
            case "Fácil":
                // Menos zombies, zombies más lentos

                break;
            case "Medio":
                // Cantidad normal de zombies

                break;
            case "Difícil":
                // Más zombies, zombies más rápidos y fuertes

                break;
        }
    }

    private void prepareElements() {
        prepareTopPanel();
        prepareGridPanel();
        prepareBottomPanel();
    }

    private void prepareTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel solesPanel = new JPanel();
        solesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        solesLabel = new JLabel("Soles: " + juego.getSoles());
        solesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        solesPanel.add(solesLabel);

        JPanel plantasPanel = preparePlantasPanel();

        topPanel.add(solesPanel, BorderLayout.WEST);
        topPanel.add(plantasPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }

    private JPanel preparePlantasPanel() {
        JPanel plantasPanel = new JPanel();
        plantasPanel.setBorder(BorderFactory.createTitledBorder("Plantas Disponibles"));
        plantasPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[] plantas = {"Girasol", "Lanzaguisantes", "Nuez"};
        String[] imagenes = {"D:\\POOB\\PlantsVsZombies\\src\\domain\\girasol.png", "D:\\POOB\\PlantsVsZombies\\src\\domain\\lanzaguisantes.png", "D:\\POOB\\PlantsVsZombies\\src\\domain\\nuez.png"};

        for (int i = 0; i < plantas.length; i++) {
            JButton plantaButton = createPlantaButton(plantas[i], imagenes[i]);
            plantasPanel.add(plantaButton);
        }
        return plantasPanel;
    }

    private JButton createPlantaButton(String planta, String rutaImagen) {
        JButton plantaButton = new JButton(planta);
        plantaButton.setPreferredSize(new Dimension(100, 100));
        plantaButton.setBackground(new Color(220, 255, 220));
        plantaButton.setFocusPainted(false);

        ImageIcon icon = new ImageIcon(rutaImagen);
        if (icon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen: " + rutaImagen);
        } else {

            Image image = icon.getImage();
            Image newImage = image.getScaledInstance(37, 40, Image.SCALE_SMOOTH);
            plantaButton.setIcon(new ImageIcon(newImage));
        }

        plantaButton.setHorizontalTextPosition(SwingConstants.CENTER);
        plantaButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        plantaButton.addActionListener(e -> {
            if (plantaSeleccionada != null) {
                plantaSeleccionada.setBackground(new Color(220, 255, 220));
            }
            plantaSeleccionada = plantaButton;
            plantaButton.setBackground(new Color(150, 255, 150));

            preparePlantsActions(planta);
        });

        return plantaButton;
    }

    private void preparePlantsActions(String plantaTexto) {
        switch (plantaTexto) {
            case "🌻 Girasol (50)":
                System.out.println("Girasol seleccionado - Genera soles adicionales");
                break;
            case "🌱 Lanzaguisantes (100)":
                System.out.println("Lanzaguisantes seleccionado - Dispara guisantes");
                break;
            case "🌵 Cactus (175)":
                System.out.println("Cactus seleccionado - Ataca en todas direcciones");
                break;
            case "🍄 Seta (125)":
                System.out.println("Seta seleccionada - Envenena zombis");
                break;
        }
    }

    private void prepareGridPanel() {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(juego.getTablero().getFilas(), juego.getTablero().getColumnas()));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inicializarTablero();
        add(gridPanel, BorderLayout.CENTER);
    }

    private void prepareBottomPanel() {
        JPanel bottomPanel = new JPanel();

        JButton agregarSolesButton = new JButton("Agregar Soles");
        agregarSolesButton.setBackground(new Color(255, 223, 186));
        agregarSolesButton.setFocusPainted(false);
        agregarSolesButton.addActionListener(e -> {
            juego.agregarSoles(25);
            actualizarSoles();
        });


        JButton finalizarJuegoButton = new JButton("Finalizar Juego");
        finalizarJuegoButton.setBackground(new Color(255, 186, 186));
        finalizarJuegoButton.setFocusPainted(false);
        finalizarJuegoButton.addActionListener(e -> {
            juego.finish();
            JOptionPane.showMessageDialog(null, "Juego terminado");
            System.exit(0);
        });

        bottomPanel.add(agregarSolesButton);
        bottomPanel.add(finalizarJuegoButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void inicializarTablero() {
        Tablero tablero = juego.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                JButton celdaBoton = crearBotonCelda(i, j);
                gridPanel.add(celdaBoton);
            }
        }
    }

    private JButton crearBotonCelda(int fila, int columna) {
        JButton celdaBoton = new JButton();
        celdaBoton.setPreferredSize(new Dimension(60, 60));

        actualizarCelda(celdaBoton, fila, columna);
        accionColocarPlanta(celdaBoton, fila, columna);

        return celdaBoton;
    }

    private void accionColocarPlanta(JButton celdaBoton, int fila, int columna) {
        celdaBoton.addActionListener(e -> {
            if (plantaSeleccionada != null) {
                Planta planta = crearPlantaSegunSeleccion();

                if (planta != null && juego.colocarPlanta(planta, fila, columna)) {
                    actualizarIconoPlanta(celdaBoton);
                    actualizarSoles();
                }
            }
        });
    }

    private Planta crearPlantaSegunSeleccion() {
        if (plantaSeleccionada.getText().equals("Lanzaguisantes")) {
            return new Lanzaguisantes();
        } else if (plantaSeleccionada.getText().equals("Nuez")) {
            return new Nuez();
        } else if (plantaSeleccionada.getText().equals("Girasol")) {
            return new Girasol();
        }
        return null;
    }

    private void actualizarIconoPlanta(JButton celdaBoton) {
        String nombrePlanta = plantaSeleccionada.getText().toLowerCase();
        ImageIcon icon = new ImageIcon("D:\\POOB\\PlantsVsZombies\\src\\domain\\" + nombrePlanta + ".png");
        Image image = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        celdaBoton.setIcon(new ImageIcon(image));
        celdaBoton.setText("");
    }

    private void actualizarCelda(JButton boton, int fila, int columna) {
        Celda celda = juego.getTablero().getCelda(fila, columna);
        if (celda.getContenido() instanceof Planta) {
            boton.setText("🌱");
            boton.setBackground(new Color(200, 255, 200));
        } else if (celda.getContenido() instanceof Zombi) {
            boton.setText("🧟");
            boton.setBackground(new Color(255, 200, 200));
        } else {
            boton.setText("");
            boton.setBackground(new Color(240, 240, 240));
        }
        boton.setFocusPainted(false);
    }

    private void actualizarSoles() {
        solesLabel.setText("Soles: " + juego.getSoles());
    }

    public void actualizarTablero() {
        Component[] componentes = gridPanel.getComponents();
        int index = 0;
        Tablero tablero = juego.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                JButton boton = (JButton) componentes[index++];
                actualizarCelda(boton, i, j);
            }
        }
    }

    private void prepareGameElements() {
    }

    private void prepareActions() {
        // Puedes agregar métodos relacionados con eventos o acciones del juego aquí.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PlantsVsZombiesGUI(new Juego());
        });
    }
}
