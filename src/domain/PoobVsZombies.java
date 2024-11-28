package domain;

import presentation.PoobVsZombiesGUI;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.ArrayList;

public class PoobVsZombies {
    private final PoobVsZombiesGUI poobVsZombiesGUI;
    private int suns;
    private final Board board;
    private final ScheduledExecutorService scheduler;
    private List<LawnMower> lawnMowers;

    public PoobVsZombies(PoobVsZombiesGUI poobVsZombiesGUI) {
        this.board = new Board(5, 8);
        this.poobVsZombiesGUI = poobVsZombiesGUI;
        this.suns = 5000;
        this.scheduler = Executors.newScheduledThreadPool(10);
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
                break;

            case "Player Vs Player":
                break;
            default:
        }
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
                    Zombie zombie = chooseZombie();
                    board.addZombi(zombie, rowRandom, column);
                    poobVsZombiesGUI.updateElementZombie(rowRandom, column, zombie);
                    addedZombie = true;
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::moveZombie, 5, 5, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(this::updateEstate, 0, 1, TimeUnit.SECONDS);
    }

    public Zombie chooseZombie(){
        Random random = new Random();
        int zombieType = random.nextInt(3);

        return switch (zombieType) {
            case 0 -> new Basic();
            case 1 -> new Conehead();
            case 2 -> new Buckethead();
            default -> throw new IllegalStateException("Error Interno.");
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

    public void activateLawnMower(int row){
        for (int col = 0; col < board.getColumns(); col++) {
            board.eraseElement(row, col);
        }
    }

    public void startPlantAction(Plant plant) {
        if(plant instanceof Peashooter peashooter){
            int row = board.getRowObject(plant);
            peashooter.startActions(scheduler, poobVsZombiesGUI, board, row);
        } else {
            plant.startActions(scheduler, poobVsZombiesGUI, board);
        }
    }

    public boolean putPlant(Plant plant, int row, int column) {
        if (plant.getCost() <= suns && board.isEmpty(row,column)) {
            board.addPlant(plant,row,column);
            subSuns(plant.getCost());
            return true;
        }
        return false;
    }

    public void moveZombie() {
        List<int[]> movements = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int column = board.getColumns() - 1; column >= 0; column--) {
                Object content = board.getZombie(row, column);

                if (content instanceof Zombie) {
                    int newColumn = column - 1;

                    if (newColumn == 1) {
                        activateLawnMower(row);
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


    public void updateEstate() {
        poobVsZombiesGUI.updateView(board);
    }


    public void finish() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    public int getSuns() {
        return suns;
    }

    public void addSuns(int amount) {
        suns += amount;
    }

    public void subSuns(int amount) {
        suns = Math.max(0, suns - amount);
    }

    public Board getBoard() {
        return board;
    }
}