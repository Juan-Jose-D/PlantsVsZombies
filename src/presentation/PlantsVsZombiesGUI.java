package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class PlantsVsZombiesGUI extends JFrame {
    private Juego juego;
    private JPanel gridPanel;
    private JLabel solesLabel;
    private JButton plantaSeleccionada;
    private JButton[][] botonesTablero;

    public PlantsVsZombiesGUI() {

        setTitle("Plantas vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(null);

        mostrarPantallaInicial();
        setVisible(true);
    }

    private void mostrarPantallaInicial() {
        JPanel panelInicial = new JPanel(new BorderLayout());

        JLabel tituloLabel = new JLabel("Plantas vs Zombies", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 40));
        panelInicial.add(tituloLabel, BorderLayout.NORTH);

        JPanel modoPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        modoPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        String[] modos = {"PvM", "MvM", "PvP"};
        for (String modo : modos) {
            JButton botonNivel = new JButton(modo);
            botonNivel.setFont(new Font("Arial", Font.PLAIN, 20));
            botonNivel.addActionListener(e -> iniciarJuego(modo));
            modoPanel.add(botonNivel);
        }

        panelInicial.add(modoPanel, BorderLayout.CENTER);
        setContentPane(panelInicial);
    }

    private void iniciarJuego(String modo) {
        this.juego = new Juego(this);
        juego.iniciarJuego(modo);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        prepareTopPanel(panelPrincipal);
        prepareGridPanel(panelPrincipal);
        prepareBottomPanel(panelPrincipal);

        setContentPane(panelPrincipal);
        revalidate();
        repaint();
    }


    private void prepareTopPanel(JPanel panelPrincipal) {
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel solesPanel = new JPanel();
        solesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        solesLabel = new JLabel("Soles: " + juego.getSoles());
        solesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        solesPanel.add(solesLabel);

        JPanel plantasPanel = preparePlantasPanel();

        topPanel.add(solesPanel, BorderLayout.WEST);
        topPanel.add(plantasPanel, BorderLayout.CENTER);
        panelPrincipal.add(topPanel, BorderLayout.NORTH);
    }

    private void prepareGridPanel(JPanel panelPrincipal) {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(juego.getTablero().getFilas(), juego.getTablero().getColumnas()));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inicializarTablero();
        panelPrincipal.add(gridPanel, BorderLayout.CENTER);
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

        });

        return plantaButton;
    }



    private void prepareBottomPanel(JPanel panelPrincipal) {
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
        panelPrincipal.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void inicializarTablero() {
        Tablero tablero = juego.getTablero();
        botonesTablero = new JButton[tablero.getFilas()][tablero.getColumnas()];
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                JButton celdaBoton = crearBotonCelda(i, j);
                botonesTablero[i][j] = celdaBoton;
                gridPanel.add(celdaBoton);
            }
        }
    }

    private JButton crearBotonCelda(int fila, int columna) {
        JButton celdaBoton = new JButton();
        celdaBoton.setPreferredSize(new Dimension(60, 60));

        celdaBoton.setBackground(new Color(240, 240, 240));
        celdaBoton.setFocusPainted(false);
        accionColocarPlanta(celdaBoton, fila, columna);

        return celdaBoton;
    }

    private void accionColocarPlanta(JButton celdaBoton, int fila, int columna) {
        celdaBoton.addActionListener(e -> {
            if (plantaSeleccionada != null) {

                if (juego.getTablero().isEmpty(fila, columna)) {
                    Planta planta = crearPlantaSegunSeleccion();
                    if (planta != null && juego.colocarPlanta(planta, fila, columna)) {
                        actualizarIconoPlanta(celdaBoton);
                        actualizarSoles();
                        if (planta instanceof Girasol) {
                            ((Girasol) planta).setPosition(fila, columna);
                        }
                        juego.iniciarAccionPlanta(planta);
                    }
                }

                else if (juego.getTablero().getPlanta(fila, columna) instanceof Girasol) {
                    Girasol girasol = (Girasol) juego.getTablero().getPlanta(fila, columna);
                    if (girasol.tieneSolDisponible()) {
                        girasol.recolectarSol();
                        restaurarCeldaGirasol(fila, columna);
                        actualizarSoles();
                    }
                }
            }
        });
    }

    public void iluminarCeldaGirasol(int fila, int columna) {
        if (botonesTablero != null && botonesTablero[fila][columna] != null) {
            SwingUtilities.invokeLater(() -> {
                botonesTablero[fila][columna].setBackground(new Color(255, 255, 0));
            });
        }
    }

    public void restaurarCeldaGirasol(int fila, int columna) {
        if (botonesTablero != null && botonesTablero[fila][columna] != null) {
            SwingUtilities.invokeLater(() -> {
                botonesTablero[fila][columna].setBackground(new Color(240, 240, 240));
            });
        }
    }

    private Planta crearPlantaSegunSeleccion() {
        if (plantaSeleccionada.getText().equals("Lanzaguisantes")) {
            return new Lanzaguisantes();
        } else if (plantaSeleccionada.getText().equals("Nuez")) {
            return new Nuez();
        } else if (plantaSeleccionada.getText().equals("Girasol")) {
            return new Girasol(juego);
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

    public void actualizarCeldaZombi(int fila, int columna, Zombi zombi) {
        if (botonesTablero != null && botonesTablero[fila][columna] != null) {
            SwingUtilities.invokeLater(() -> {
                ImageIcon icon = new ImageIcon(zombi.getImagePath());
                Image scaledImage = icon.getImage().getScaledInstance(40, 60, Image.SCALE_SMOOTH);
                botonesTablero[fila][columna].setIcon(new ImageIcon(scaledImage));
            });
        }
    }

    public void actualizarSoles() {
        solesLabel.setText("Soles: " + juego.getSoles());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlantsVsZombiesGUI::new);
    }
}
