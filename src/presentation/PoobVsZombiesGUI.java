package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;

public class PoobVsZombiesGUI extends JFrame {
    private PoobVsZombies game;
    private JPanel gridPanel;
    private JLabel sunLabel;
    private JButton selectedPlant;
    private JButton shovelButton;
    private JButton[][] boardButtons;
    private boolean isShovelActive = false;

    public PoobVsZombiesGUI() {

        setTitle("Plants vs Zombies");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1650,1080);
        setLocationRelativeTo(null);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        showInitialPanel();
        setVisible(true);
    }

    private void showInitialPanel() {
        JPanel initialPanel = getIntroBackground();
        initialPanel.setLayout(new BorderLayout());

        JPanel modPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        modPanel.setOpaque(false);

        String[] mods = {"Player Vs Machine", "Machine Vs Machine", "Player Vs Player"};
        for (String modo : mods) {
            JButton levelButton = crearBotonPersonalizado(modo);
            levelButton.addActionListener(e -> startPoobVsZombies(modo));
            modPanel.add(levelButton);
        }

        initialPanel.add(modPanel, BorderLayout.CENTER);

        setContentPane(initialPanel);
        revalidate();
        repaint();
    }

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
        backgroundPanel.setBackground(new Color(0, 0, 0, 0)); // Completamente transparente
        return backgroundPanel;
    }

    private JButton crearBotonPersonalizado(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(173, 216, 230));
        button.setFocusPainted(false);
        return button;
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

        Plant[] availablePlants = {new Sunflower(game), new Peashooter(game), new WallNut(game), new PotatoMine(game)};

        for (Plant plant : availablePlants) {
            JButton plantButton = createPlantButton(plant);
            plantsPanel.add(plantButton);
        }
        return plantsPanel;
    }

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

        elementButton.setContentAreaFilled(false);
        elementButton.setOpaque(false);
        elementButton.setBorderPainted(false);
        elementButton.setFocusPainted(false);

        putPlantAction(elementButton, row, column);

        return elementButton;
    }

    private void putPlantAction(JButton elementButton, int row, int column) {
        elementButton.addActionListener(e -> {

            if (isShovelActive) {
                if (game.getBoard().getElement(row, column).getContent() instanceof Plant plant) {
                    game.shovelAction(row, column);
                    elementButton.setIcon(null);
                    elementButton.setBackground(new Color(240, 240, 240));
                    isShovelActive = false;
                }
            } else if (selectedPlant != null) {

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


    private void shovelAction() {
        shovelButton.addActionListener(e -> {
            isShovelActive = !isShovelActive;
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
        if (selectedPlant != null) {
            String plantName = selectedPlant.getText();
            return switch (plantName) {
                case "Sunflower" -> new Sunflower(game);
                case "Peashooter" -> new Peashooter(game);
                case "WallNut" -> new WallNut(game);
                case "PotatoMine" -> new PotatoMine(game);
                default -> null;
            };
        }
        return null;
    }

    private void updateIconPlant(JButton elementButton, Plant plant) {
        ImageIcon icon = new ImageIcon(plant.getImagePath());
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
        sunLabel.setText(String.valueOf(game.getSuns()));
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
                            ImageIcon icon = new ImageIcon(plant.getImagePath());
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
