package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal para la interfaz gráfica del juego PoobVsZombies.
 * Gestiona la visualización y la interacción del usuario con el juego.
 */
public class PoobVsZombiesGUI extends JFrame {
    private PoobVsZombies game;
    private JPanel gridPanel;
    private JLabel sunLabel;
    private JButton selectedPlant;
    private JButton shovelButton;
    private JButton[][] boardButtons;
    private boolean isShovelActive = false;
    private JMenu menuFile;
    private JMenuBar menuBar;
    private JMenuItem openItem, saveItem;
    private JFileChooser fileChooser;

    /**
     * Constructor que inicializa la interfaz gráfica del juego.
     */
    public PoobVsZombiesGUI() {
        setTitle("Plants vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1650, 1080);
        setLocationRelativeTo(null);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        prepareElementsMenu();

        showInitialPanel();
        setVisible(true);
    }

    /**
     * Muestra el panel inicial con las opciones de juego.
     */
    private void showInitialPanel() {
        JPanel initialPanel = getIntroBackground();
        initialPanel.setLayout(null);

        JPanel modPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        modPanel.setOpaque(false);

        String[] mods = {"Player Vs Machine", "Machine Vs Machine", "Player Vs Player"};
        for (String modo : mods) {
            JButton levelButton = new JButton(modo);
            levelButton.setFont(new Font("Rubik One", Font.PLAIN, 18));
            levelButton.setPreferredSize(new Dimension(1650, 1080));
            levelButton.setFocusPainted(false);
            levelButton.addActionListener(e -> startPoobVsZombies(modo));
            modPanel.add(levelButton);
        }
        initialPanel.add(modPanel);
        JLabel imageLabel = getImageLabel();
        imageLabel.setBounds(460, 500, 300, 100);
        initialPanel.add(imageLabel);
        setContentPane(initialPanel);
        revalidate();
        repaint();
    }

    /**
     * Crea y devuelve un JLabel con la imagen de inicio.
     *
     * @return JLabel con la imagen de inicio.
     */
    private JLabel getImageLabel() {
        ImageIcon imageIcon = uploadImage("src/resources/images/play.png", 300, 100);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                showGameModeSelectionPanel();
            }
        });
        return imageLabel;
    }

    /**
     * Crea y devuelve un panel con la imagen de fondo del panel inicial.
     *
     * @return JPanel con la imagen de fondo.
     */
    private JPanel getIntroBackground() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/resources/images/intro.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(500, 120));
        backgroundPanel.setLayout(null);
        backgroundPanel.setBackground(new Color(0, 0, 0, 0));
        return backgroundPanel;
    }

    /**
     * Muestra el panel de selección de modo de juego.
     */
    private void showGameModeSelectionPanel() {
        JPanel modeSelectionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/resources/images/modos.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        modeSelectionPanel.setLayout(null);
        modeSelectionPanel.setPreferredSize(new Dimension(1650, 1080));
        String[] mods = {"Player Vs Machine", "Machine Vs Machine", "Player Vs Player"};
        int yPosition = 210;
        for (String modo : mods) {
            JLabel modeLabel = new JLabel(modo);
            modeLabel.setFont(new Font("CS Globe", Font.BOLD, 35));
            modeLabel.setForeground(Color.WHITE);
            modeLabel.setBounds(490, yPosition, 400, 50);
            modeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            modeLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    startPoobVsZombies(modo);
                }

                @Override
                public void mouseEntered(MouseEvent evt) {
                    modeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });

            modeSelectionPanel.add(modeLabel);
            yPosition += 90;
        }
        setContentPane(modeSelectionPanel);
        revalidate();
        repaint();
    }

    /**
     * Inicia el juego con el modo seleccionado.
     *
     * @param mod El modo de juego seleccionado.
     */
    private void startPoobVsZombies(String mod) {
        this.game = new PoobVsZombies(this);
        game.startPoobVsZombies(mod);

        JPanel principalPanel = new JPanel(new BorderLayout());

        prepareTopPanel(principalPanel);
        prepareGridPanel(principalPanel);
        prepareBottomPanel(principalPanel);

        setContentPane(principalPanel);
        revalidate();
        repaint();
    }

    /**
     * Prepara el panel superior del juego.
     *
     * @param principalPanel El panel principal al que se agrega el panel superior.
     */
    private void prepareTopPanel(JPanel principalPanel) {
        JPanel topPanel = getBackgroundPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel sunsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sunsPanel.setOpaque(false);
        sunsPanel.setBorder(BorderFactory.createEmptyBorder(85, 90, 10, 0));
        sunLabel = new JLabel(String.valueOf(game.getSuns()));
        sunLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sunsPanel.add(sunLabel);

        JPanel plantsPanel = preparePlantsPanel();
        plantsPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 10));
        plantsPanel.setOpaque(false);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        shovelButton = new JButton(new ImageIcon("src/resources/images/shovel.png"));
        shovelButton.setPreferredSize(new Dimension(93, 120));
        shovelButton.setFocusPainted(false);
        shovelButton.setBorderPainted(false);

        rightPanel.add(shovelButton);

        topPanel.add(sunsPanel, BorderLayout.WEST);
        topPanel.add(plantsPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        principalPanel.add(topPanel, BorderLayout.NORTH);
        shovelAction();
    }

    /**
     * Crea y devuelve un panel de fondo para el panel superior.
     *
     * @return JPanel con el fondo configurado.
     */
    private JPanel getBackgroundPanel() {
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/resources/images/bar.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth() - 100, getHeight(), this);
            }
        };

        topPanel.setPreferredSize(new Dimension(500, 120));
        topPanel.setLayout(null);
        return topPanel;
    }

    /**
     * Prepara el panel que contiene el tablero de juego.
     *
     * @param principalPanel El panel principal al que se agrega el tablero.
     */
    private void prepareGridPanel(JPanel principalPanel) {
        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/resources/images/background.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        gridPanel.setLayout(new GridLayout(game.getBoard().getRows(), game.getBoard().getColumns()));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 50));
        startBoard();
        principalPanel.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Prepara el panel de las plantas para seleccionar
     */
    private JPanel preparePlantsPanel() {
        JPanel plantsPanel = new JPanel();
        plantsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Plant[] availablePlants = {new Sunflower(game), new Peashooter(game), new WallNut(game), new PotatoMine(game)};

        for (Plant plant : availablePlants) {
            JButton plantButton = createPlantButton(plant);
            plantsPanel.add(plantButton);
        }
        return plantsPanel;
    }

    /**
     * Crea visiblemente las cartas de las plantas
     * *
     *@param plant Una planta especifica
     */
    private JButton createPlantButton(Plant plant) {
        JButton plantButton = new JButton(plant.getClass().getSimpleName());
        plantButton.setPreferredSize(new Dimension(70, 90));
        plantButton.setFocusPainted(false);
        plantButton.setBorderPainted(false);
        plantButton.setContentAreaFilled(false);

        ImageIcon cardImage = uploadImage(plant.getImageCardPath(), 75, 90);
        if (cardImage != null) {
            plantButton.setIcon(cardImage);
        }

        plantButton.addActionListener(e -> {
            if (selectedPlant != null) {
                selectedPlant.setBorderPainted(false);
            }
            selectedPlant = plantButton;
            plantButton.setBorderPainted(true);
        });

        return plantButton;
    }

    /**
     * Panel de abajo.
     */
    private void prepareBottomPanel(JPanel principalPanel) {
        JPanel bottomPanel = new JPanel();

        JButton addSunsButton = new JButton("Agregar Soles");
        addSunsButton.setFocusPainted(false);
        addSunsButton.addActionListener(e -> {
            game.addSuns(25);
            updateSuns();
        });

        JButton endPoobVsZombiesButton = new JButton("Finalizar PoobVsZombies");
        endPoobVsZombiesButton.setFocusPainted(false);
        endPoobVsZombiesButton.addActionListener(e -> {
            game.finish();
            JOptionPane.showMessageDialog(null, "PoobVsZombies terminado");
            System.exit(0);
        });

        bottomPanel.add(addSunsButton);
        bottomPanel.add(endPoobVsZombiesButton);
        principalPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Inicializa el tablero de juego con botones en cada celda.
     */
    private void startBoard() {
        Board board = game.getBoard();
        boardButtons = new JButton[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                JButton elementButton = createButtonElement(i, j);
                boardButtons[i][j] = elementButton;
                gridPanel.add(elementButton);
            }
        }
    }

    /**
     * Crea y devuelve un botón representando una celda del tablero.
     *
     * @param row Fila de la celda.
     * @param column Columna de la celda.
     * @return JButton configurado para la celda.
     */
    private JButton createButtonElement(int row, int column) {
        JButton elementButton = new JButton();
        elementButton.setPreferredSize(new Dimension(60, 60));

        elementButton.setContentAreaFilled(false);
        elementButton.setOpaque(false);
        elementButton.setBorderPainted(false);
        elementButton.setFocusPainted(false);

        putPlantAction(elementButton, row, column);

        return elementButton;
    }

    /**
     * Configura la acción de colocar plantas en las celdas del tablero.
     *
     * @param elementButton Botón de la celda.
     * @param row Fila de la celda.
     * @param column Columna de la celda.
     */
    private void putPlantAction(JButton elementButton, int row, int column) {
        elementButton.addActionListener(e -> {

            if (isShovelActive) {
                if (game.getBoard().getElement(row, column).getContent() instanceof Plant plant) {
                    game.shovelAction(row, column);
                    elementButton.setIcon(null);
                    isShovelActive = false;
                }
            } else if (selectedPlant != null) {

                if (game.getBoard().isEmpty(row, column)) {
                    Plant plant = createPlantAccordSelection();
                    if (plant != null && game.putPlant(plant, row, column)) {
                        updateIconPlant(elementButton, plant);
                        updateSuns();
                        game.startPlantAction(plant);
                    }
                }

                else if (game.getBoard().getPlant(row, column) instanceof Sunflower sunflower) {
                    if (sunflower.sunAvailable()) {
                        sunflower.gatherSun();
                        restoreElementSunflower(row, column);
                        updateSuns();
                    }
                }
            }
        });
    }

    /**
     * Configura el comportamiento de la pala para eliminar elementos.
     */
    private void shovelAction() {
        shovelButton.addActionListener(e -> {
            isShovelActive = !isShovelActive;
        });
    }

    /**
     * Se encarga del ataque de los lanzaguisantes
     */
    public void peashooterAttack(ScheduledExecutorService scheduler, Board board, Peashooter peashooter, int row) {
        int RATE_OF_FIRE = peashooter.getRATE_OF_FIRE();
        scheduler.scheduleAtFixedRate(() -> {
            peashooter.attack(row, this, board);
        }, RATE_OF_FIRE, RATE_OF_FIRE, TimeUnit.MILLISECONDS);
    }

    /**
     * Se encarga de los soles del girasol
     */
    public void manageSunflowerSuns(ScheduledExecutorService scheduler, Sunflower sunflower, int row, int column) {
        scheduler.scheduleAtFixedRate(() -> {
            sunflower.generateSun();
            iluminateElementSunflower(row, column);
        }, 5, 20, TimeUnit.SECONDS);
    }

    /**
     * Cambia la imagen del girasol cuando tiene sol disponible
     */
    public void iluminateElementSunflower(int row, int column) {
        if (boardButtons != null && boardButtons[row][column] != null) {
            SwingUtilities.invokeLater(() -> {
                Plant plant = (Plant) game.getBoard().getPlant(row, column);
                ImageIcon illuminatedIcon = uploadImage(plant.getImagePath(), 60, 60);
                boardButtons[row][column].setIcon(illuminatedIcon);
            });
        }
    }

    /**
     * Vuelve al girasol normal cuando ya se recoge el sol
     */
    public void restoreElementSunflower(int row, int column) {
        if (boardButtons != null && boardButtons[row][column] != null) {
            SwingUtilities.invokeLater(() -> {
                Plant plant = (Plant) game.getBoard().getPlant(row, column);
                updateIconPlant(boardButtons[row][column], plant);
            });
        }
    }

    /**
     * Crea plantas en el dominio dada una planta seleccionada
     */
    private Plant createPlantAccordSelection() {
        if (selectedPlant != null) {
            String plantName = selectedPlant.getText();
            return game.createPlantAccordSelection(plantName);
        }
        return null;
    }

    /**
     * Actualiza el icono de una planta
     */
    private void updateIconPlant(JButton elementButton, Plant plant) {
        ImageIcon icon = new ImageIcon(plant.getImagePath());
        elementButton.setIcon(icon);
        elementButton.setText("");
    }

    /**
     * Actualiza el icono de un Zombie
     */
    public void updateElementZombie(int row, int column, Zombie zombie) {
        if (boardButtons != null && boardButtons[row][column] != null) {
            SwingUtilities.invokeLater(() -> {
                ImageIcon icon = uploadImage(zombie.getImagePath(), 40, 60);
                boardButtons[row][column].setIcon(icon);
            });
        }
    }

    /**
     * Actualiza la cantidad de soles mostrada en la interfaz.
     */
    public void updateSuns() {
        sunLabel.setText(String.valueOf(game.getSuns()));
    }

    /**
     * Actualiza la vista
     * @param board el tablero
     */
    public void updateView(Board board) {

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                JButton button = boardButtons[i][j];
                Object content = board.getElement(i, j).getContent();
                SwingUtilities.invokeLater(() -> {

                    button.setIcon(null);

                    switch (content) {
                        case null -> button.setIcon(null);
                        case Zombie zombie -> {
                            ImageIcon icon = uploadImage(zombie.getImagePath(), 40, 60);
                            button.setIcon(icon);
                        }
                        case Plant plant -> {
                            ImageIcon icon = new ImageIcon(plant.getImagePath());
                            button.setIcon(icon);

                        }
                        case LawnMower lawnMower -> {
                            ImageIcon icon = uploadImage(lawnMower.getImagePath(), 40, 40);
                            button.setIcon(icon);
                        }
                        default -> {
                        }
                    }
                });
            }
        }
    }


    /**
     * Metodo auxiliar para cargar una imagen
     */
    private ImageIcon uploadImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen: " + imagePath);
            return null;
        }
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    /**
     * Prepara el menu
     */
    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("Archivo");
        fileChooser = new JFileChooser();

        openItem = new JMenuItem("Abrir");
        saveItem = new JMenuItem("Guardar como");

        menuFile.add(openItem);
        menuFile.add(saveItem);

        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        prepareActionsMenu();
    }

    /**
     * Prepara las acciones del menu
     */
    private void prepareActionsMenu(){
        optionSave();
        optionOpen();
    }

    private void optionSave(){
        saveItem.addActionListener(e -> {
            int returnValue = fileChooser.showSaveDialog(presentation.PoobVsZombiesGUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    game.save(selectedFile);
                } catch (PoobVsZombiesException r) {
                    JOptionPane.showMessageDialog(null, r.getMessage());
                }
            }
        });
    }

    private void optionOpen() {
        openItem.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(PoobVsZombiesGUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    PoobVsZombies loadedGame = PoobVsZombies.open(selectedFile);

                    this.game = loadedGame;
                    this.game.setPoobVsZombiesGUI(this);

                    restartGameWithLoadedState();
                } catch (PoobVsZombiesException r) {
                    JOptionPane.showMessageDialog(null, r.getMessage());
                }
            }
        });
    }

    private void restartGameWithLoadedState() {
        JPanel principalPanel = new JPanel(new BorderLayout());

        prepareTopPanel(principalPanel);
        prepareGridPanel(principalPanel);
        prepareBottomPanel(principalPanel);

        setContentPane(principalPanel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PoobVsZombiesGUI::new);
    }
}
