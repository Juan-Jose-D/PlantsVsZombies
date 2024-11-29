package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class PoobVsZombiesGUI extends JFrame {
    private PoobVsZombies game;
    private JPanel gridPanel;
    private JLabel sunLabel;
    private JButton selectedPlant;
    private JButton[][] boardButtons;

    public PoobVsZombiesGUI() {

        setTitle("Plants vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 700);
        setLocationRelativeTo(null);

        showInitialPanel();
        setVisible(true);
    }

    private void showInitialPanel() {
        JPanel initialPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Poob vs Zombies", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        initialPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel modPanel = getModPanel();

        initialPanel.add(modPanel, BorderLayout.CENTER);
        setContentPane(initialPanel);
    }

    private JPanel getModPanel() {
        JPanel modPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        modPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        String[] mods = {"Player Vs Machine", "Machine Vs Machine", "Player Vs Player"};
        for (String mod : mods) {
            JButton levelButton = new JButton(mod);
            levelButton.setFont(new Font("Arial", Font.PLAIN, 25));
            levelButton.addActionListener(e -> startPoobVsZombies(mod));
            modPanel.add(levelButton);
        }
        return modPanel;
    }

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

    private void prepareTopPanel(JPanel principalPanel) {
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/resources/images/bar.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth() - 100, getHeight(), this);
            }
        };

        // Establecer un tamaño preferido para el topPanel
        topPanel.setPreferredSize(new Dimension(600, 100));
        topPanel.setLayout(null);

        // Panel de soles
        JPanel sunsPanel = new JPanel();
        sunsPanel.setOpaque(false); // Hace transparente el fondo del panel
        sunsPanel.setBounds(10, 70, 120, 50);
        sunLabel = new JLabel(String.valueOf(game.getSuns()));
        sunLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sunsPanel.add(sunLabel);

        // Panel de plantas
        JPanel plantsPanel = preparePlantsPanel();
        plantsPanel.setBounds(130, 10, 600, 90);
        plantsPanel.setOpaque(false);

        // Añadir paneles al topPanel
        topPanel.add(sunsPanel);
        topPanel.add(plantsPanel);

        // Añadir topPanel al principalPanel
        principalPanel.add(topPanel, BorderLayout.NORTH);
    }

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

    private JPanel preparePlantsPanel() {
        JPanel plantsPanel = new JPanel();
        plantsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Plant[] availablePlants = {new Sunflower(game), new Peashooter(game), new WallNut(game)};

        for (Plant plant : availablePlants) {
            JButton plantButton = createPlantButton(plant);
            plantsPanel.add(plantButton);
        }
        return plantsPanel;
    }

    private JButton createPlantButton(Plant plant) {
        JButton plantButton = new JButton(plant.getClass().getSimpleName());
        plantButton.setPreferredSize(new Dimension(100, 100));
        plantButton.setBackground(new Color(220, 255, 220));
        plantButton.setFocusPainted(false);

        ImageIcon icon = uploadImage(plant.getImagePath(), 37, 40);
        plantButton.setIcon(icon);

        plantButton.setHorizontalTextPosition(SwingConstants.CENTER);
        plantButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        plantButton.addActionListener(e -> {
            if (selectedPlant != null) {
                selectedPlant.setBackground(new Color(220, 255, 220));
            }
            selectedPlant = plantButton;
            plantButton.setBackground(new Color(150, 255, 150));
        });

        return plantButton;
    }


    private void prepareBottomPanel(JPanel principalPanel) {
        JPanel bottomPanel = new JPanel();

        JButton addSunsButton = new JButton("Agregar Soles");
        addSunsButton.setBackground(new Color(255, 223, 186));
        addSunsButton.setFocusPainted(false);
        addSunsButton.addActionListener(e -> {
            game.addSuns(25);
            updateSuns();
        });

        JButton endPoobVsZombiesButton = new JButton("Finalizar PoobVsZombies");
        endPoobVsZombiesButton.setBackground(new Color(255, 186, 186));
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

    private JButton createButtonElement(int row, int column) {
        JButton elementButton = new JButton();
        elementButton.setPreferredSize(new Dimension(60, 60));

        // Hacer que el botón sea transparente
        elementButton.setContentAreaFilled(false);
        elementButton.setOpaque(false);

        elementButton.setFocusPainted(false);

        // Añadir acción al botón
        putPlantAction(elementButton, row, column);

        return elementButton;
    }

    private void putPlantAction(JButton elementButton, int row, int column) {
        elementButton.addActionListener(e -> {
            if (selectedPlant != null) {

                if (game.getBoard().isEmpty(row, column)) {
                    Plant plant = createPlantAccordSelection();
                    if (plant != null && game.putPlant(plant, row, column)) {
                        updateIconPlant(elementButton, plant);
                        updateSuns();
                        if (plant instanceof Sunflower) {
                            ((Sunflower) plant).setPosition(row, column);
                        }
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



    public void iluminateElementSunflower(int row, int column) {
        if (boardButtons != null && boardButtons[row][column] != null) {
            SwingUtilities.invokeLater(() -> {
                boardButtons[row][column].setBackground(new Color(255, 255, 0));
            });
        }
    }

    public void restoreElementSunflower(int row, int column) {
        if (boardButtons != null && boardButtons[row][column] != null) {
            SwingUtilities.invokeLater(() -> {
                boardButtons[row][column].setBackground(new Color(240, 240, 240));
            });
        }
    }

    private Plant createPlantAccordSelection() {
        if (selectedPlant.getText().equals("Peashooter")) {
            return new Peashooter(game);
        } else if (selectedPlant.getText().equals("WallNut")) {
            return new WallNut(game);
        } else if (selectedPlant.getText().equals("Sunflower")) {
            return new Sunflower(game);
        }
        return null;
    }

    private void updateIconPlant(JButton elementButton, Plant plant) {
        ImageIcon icon = uploadImage(plant.getImagePath(), 60, 60);
        elementButton.setIcon(icon);
        elementButton.setText("");
    }

    public void updateElementZombie(int row, int column, Zombie zombie) {
        if (boardButtons != null && boardButtons[row][column] != null) {
            SwingUtilities.invokeLater(() -> {
                ImageIcon icon = uploadImage(zombie.getImagePath(), 40, 60);
                boardButtons[row][column].setIcon(icon);
            });
        }
    }

    public void updateSuns() {
        sunLabel.setText("Soles: " + game.getSuns());
    }

    public void updateView(Board board) {

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                JButton button = boardButtons[i][j];
                Object content = board.getElement(i, j).getContent();

                SwingUtilities.invokeLater(() -> {

                    button.setIcon(null);
                    button.setBackground(new Color(240, 240, 240));

                    switch (content) {
                        case null -> button.setIcon(null);
                        case Zombie zombie -> {

                            ImageIcon icon = uploadImage(zombie.getImagePath(), 40, 60);
                            button.setIcon(icon);
                        }
                        case Plant plant -> {
                            ImageIcon icon = uploadImage(plant.getImagePath(), 60, 60);
                            button.setIcon(icon);

                            if (plant instanceof Sunflower sunflower) {
                                if (sunflower.sunAvailable()) {
                                    button.setBackground(new Color(255, 255, 0));
                                }
                            }
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


    private ImageIcon uploadImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        if (icon.getIconWidth() == -1) {
            System.err.println("No se pudo cargar la imagen: " + imagePath);
            return null;
        }
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(PoobVsZombiesGUI::new);
    }
}
