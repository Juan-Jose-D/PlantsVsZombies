package domain;

import presentation.PoobVsZombiesGUI;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PoobVsZombies implements Serializable {
    private PoobVsZombiesGUI poobVsZombiesGUI;
    private int suns;
    private final Board board;
    private ScheduledExecutorService scheduler;
    private List<LawnMower> lawnMowers;
    private int puntaje;
    private int brains;
    private Map<Integer, Queue<Zombie>> zombieQueues;

    public PoobVsZombies(PoobVsZombiesGUI poobVsZombiesGUI) {
        this.board = new Board(6, 10);
        this.poobVsZombiesGUI = poobVsZombiesGUI;
        this.suns = 5000;
        this.puntaje = 0;
        this.brains = 5000;
        this.scheduler = Executors.newScheduledThreadPool(10);
        this.zombieQueues = new HashMap<>();
    }

    public void startPoobVsZombies(String mod) {
        configureAccordMod(mod);
    }

    public void configureAccordMod(String mod){
        switch (mod){
            case "Player Vs Machine":
                playerVsMachine();
                break;

            case "Machine Vs Machine":
                machineVsMachine();
                break;

            case "Player Vs Player":
                playerVsPlayer();
                break;
            default:
        }
    }

    public void addZombieToQueue(Zombie zombie, int row) {
        zombieQueues.putIfAbsent(row, new LinkedList<>());
        zombieQueues.get(row).offer(zombie);
    }

    public Queue<Zombie> getZombieQueueForRow(int row) {
        return zombieQueues.getOrDefault(row, new LinkedList<>());
    }


    public void playerVsMachine() {
        createLawnMowers();
        scheduler.scheduleAtFixedRate(() -> {
            Random random = new Random();
            boolean addedZombie = false;

            for (int intentos = 0; intentos < 10 && !addedZombie; intentos++) {
                int rowRandom = random.nextInt(board.getRows());
                int column = board.getColumns() - 1;

                if (board.isEmpty(rowRandom, column)) {
                    Zombie zombie = null;
                    try {
                        zombie = chooseZombie();
                    } catch (PoobVsZombiesException e) {
                        throw new RuntimeException(e);
                    }
                    board.addZombi(zombie, rowRandom, column);
                    poobVsZombiesGUI.updateElementZombie(rowRandom, column, zombie);
                    addZombieToQueue(zombie, rowRandom);
                    addedZombie = true;
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::moveZombie, 7, 7, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::updateEstate, 0, 1, TimeUnit.SECONDS);
    }

    public void playerVsPlayer() {
        createLawnMowers();
        scheduler.scheduleAtFixedRate(this::moveZombie, 7, 7, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::updateEstate, 0, 1, TimeUnit.SECONDS);
    }

    public void machineVsMachine() {
        createLawnMowers();
        scheduler.scheduleAtFixedRate(() -> {
            Random random = new Random();
            boolean addedZombie = false;

            for (int intentos = 0; intentos < 10 && !addedZombie; intentos++) {
                int rowRandom = random.nextInt(board.getRows());
                int column = board.getColumns() - 1;

                if (board.isEmpty(rowRandom, column)) {
                    Zombie zombie;
                    try {
                        zombie = chooseZombie();
                    } catch (PoobVsZombiesException e) {
                        throw new RuntimeException(e);
                    }
                    board.addZombi(zombie, rowRandom, column);
                    poobVsZombiesGUI.updateElementZombie(rowRandom, column, zombie);
                    addZombieToQueue(zombie, rowRandom);
                    addedZombie = true;
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            Random random = new Random();
            boolean addedPlant = false;

            for (int intentos = 0; intentos < 10 && !addedPlant; intentos++) {
                int rowRandom = random.nextInt(board.getRows());
                int columnRandom = random.nextInt(4);

                if (board.isEmpty(rowRandom, columnRandom)) {
                    Plant plant;
                    try {
                        plant = choosePlant();
                    } catch (PoobVsZombiesException e) {
                        throw new RuntimeException(e);
                    }
                    board.addPlant(plant, rowRandom, columnRandom);
                    poobVsZombiesGUI.updateElementPlant(rowRandom, columnRandom, plant);
                    startPlantAction(plant);
                    addedPlant = true;
                }
            }
        }, 0, 10, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::moveZombie, 7, 7, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::updateEstate, 0, 1, TimeUnit.SECONDS);
    }

    public Zombie chooseZombie() throws PoobVsZombiesException {
        Random random = new Random();
        int zombieType = random.nextInt(3);

        return switch (zombieType) {
            case 0 -> new Basic();
            case 1 -> new Conehead();
            case 2 -> new Buckethead();
            default -> throw new PoobVsZombiesException(PoobVsZombiesException.ERROR);
        };
    }

    public Plant choosePlant() throws PoobVsZombiesException {
        Random random = new Random();
        int plantType = random.nextInt(3);

        return switch (plantType) {
            case 0 -> new Peashooter(this);
            case 1 -> new WallNut(this);
            case 2 -> new PotatoMine(this);
            default -> throw new PoobVsZombiesException(PoobVsZombiesException.ERROR);
        };
    }

    public void createLawnMowers() {
        lawnMowers = new ArrayList<>();
        int rows = board.getRows();

        for (int i = 0; i < rows; i++) {
            LawnMower lawnMower = new LawnMower(i);
            board.setElement(i, 0, lawnMower);
            lawnMowers.add(lawnMower);
        }
    }

    public void activateLawnMower(int row) {
        LawnMower lawnMower = null;
        for (LawnMower mower : lawnMowers) {
            if (mower.getRow() == row) {
                lawnMower = mower;
                break;
            }
        }
        if (lawnMower != null && !lawnMower.isActivated()) {

            lawnMower.activate();
            lawnMowers.remove(lawnMower);
            board.eraseElement(row, 0);

            for (int col = 0; col < board.getColumns(); col++) {
                Object content = board.getZombie(row, col);
                if (content instanceof Zombie) {
                    board.eraseElement(row, col);
                }
            }

            poobVsZombiesGUI.updateView(board);
        }
    }

    public void startPlantAction(Plant plant) {
        int row = board.getRowObject(plant);
        int column = board.getColumnObject(plant);
        if(plant instanceof Peashooter peashooter){
            poobVsZombiesGUI.peashooterAttack(scheduler, board, peashooter, row);
        } else if (plant instanceof Sunflower sunflower) {
            poobVsZombiesGUI.manageSunflowerSuns(scheduler, sunflower, row, column);
        } else if (plant instanceof PotatoMine potatoMine) {
            poobVsZombiesGUI.potatoMineAttack(scheduler, board, potatoMine, row, column);
        }
    }

    public void startZombieAction(Zombie zombie, int row) {
        addZombieToQueue(zombie, row);

    }

    public boolean putPlant(Plant plant, int row, int column) {
        if (plant.getCost() <= suns && board.isEmpty(row,column)) {
            board.addPlant(plant,row,column);
            subSuns(plant.getCost());
            return true;
        }
        return false;
    }

    public boolean putZombie(Zombie zombie, int row, int column) {
        if (column >= 8){
            if (zombie.getCost() <= brains && board.isEmpty(row,column)) {
                board.addZombi(zombie,row,column);
                subBrains(zombie.getCost());
                return true;
            }
        }
        return false;
    }

    public void shovelAction(int row, int column){
        if (board.getElement(row, column).getContent() instanceof Plant plant) {
             board.eraseElement(row, column);
        }
    }

    public void moveZombie() {
        List<int[]> movements = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int column = board.getColumns() - 1; column >= 0; column--) {
                Object content = board.getZombie(row, column);

                if (content instanceof Zombie) {
                    int newColumn = column - 1;

                    if (newColumn == 1) {
                        if (board.getPlant(row, 1) == null) {
                            boolean lawnMowerExists = false;
                            for (LawnMower mower : lawnMowers) {
                                if (mower.getRow() == row) {
                                    lawnMowerExists = true;
                                    break;
                                }
                            }

                            if (lawnMowerExists) {
                                activateLawnMower(row);
                                return;
                            }
                        }
                    }



                    if (newColumn < 0) {
                        System.out.println("Fin del juego");
                        finish();
                        return;
                    }

                    if (board.isEmpty(row, newColumn)) {
                        movements.add(new int[]{row, column, newColumn});
                    }
                }
            }
            zombieAttack();
        }

        for (int[] movement : movements) {
            int row = movement[0];
            int column = movement[1];
            int newColumn = movement[2];

            Zombie zombie = (Zombie) board.getZombie(row, column);
            poobVsZombiesGUI.updateElementZombie(row, newColumn, zombie);
            board.moveZombie(row, column, newColumn);
            poobVsZombiesGUI.updateView(board);
        }
    }

    public void ganar(){
        JOptionPane.showMessageDialog(null, "GANASTE!!!");
        finish();
    }
    public void zombieAttack() {
        for (int row = 0; row < board.getRows(); row++) {
            for (int column = board.getColumns() - 1; column >= 0; column--) {
                Object content = board.getZombie(row, column);

                if (content instanceof Zombie zombie) {

                    int newColumn = column - 1;

                    if (newColumn >= 0) {
                        Object plant = board.getPlant(row, newColumn);

                        if (plant instanceof Plant plantObjective) {

                            plantObjective.receiveDamage(zombie.getDamage());

                            if (plantObjective.getHealth() <= 0) {
                                plantObjective.die(row, newColumn, board, poobVsZombiesGUI);
                                poobVsZombiesGUI.updateView(board);
                            }
                        }
                    }
                }
            }
        }
    }

    public Plant createPlantAccordSelection(String plantName) {
            return switch (plantName) {
                case "Sunflower" -> new Sunflower(this);
                case "Peashooter" -> new Peashooter(this);
                case "WallNut" -> new WallNut(this);
                case "PotatoMine" -> new PotatoMine(this);
                default -> null;
            };
    }

    public Zombie createZombieAccordSelection(String zombieName) {
        return switch (zombieName) {
            case "Basic" -> new Basic();
            case "Conehead" -> new Conehead();
            case "Buckethead" -> new Buckethead();
            default -> null;
        };
    }


    public void updateEstate() {
        poobVsZombiesGUI.updateView(board);
    }


    public void finish() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        JOptionPane.showMessageDialog(null, "PoobVsZombies terminado");
    }

    public int getSuns() {
        return suns;
    }

    public void addSuns(int amount) {
        suns += amount;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getBrains() {
        return brains;
    }

    public void addPuntaje(int amount) {
        puntaje += amount;
        poobVsZombiesGUI.updatePuntaje();
        if (puntaje >= 200){
            ganar();
        }
    }

    public void subSuns(int amount) {
        suns = Math.max(0, suns - amount);
    }

    public void subBrains(int amount) {
        brains = Math.max(0, brains - amount);
    }

    public Board getBoard() {
        return board;
    }

    public void save(File file) throws PoobVsZombiesException {
        PoobVsZombiesGUI tempGUI = this.poobVsZombiesGUI;
        this.poobVsZombiesGUI = null;

        if (!file.getName().endsWith(".dat")) {
            file = new File(file.getAbsolutePath() + ".dat");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (Exception e) {
            throw new PoobVsZombiesException(PoobVsZombiesException.ERROR_SAVE + file.getName());
        } finally {
            this.poobVsZombiesGUI = tempGUI;
        }
    }

    public static PoobVsZombies open(File file) throws PoobVsZombiesException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (PoobVsZombies) ois.readObject();
        } catch (Exception e) {
            throw new PoobVsZombiesException(PoobVsZombiesException.ERROR_OPEN + file.getName());
        }
    }


    public void setPoobVsZombiesGUI(PoobVsZombiesGUI gui) {
        this.poobVsZombiesGUI = gui;
    }

}