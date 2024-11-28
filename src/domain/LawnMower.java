package domain;

public record LawnMower(int row) {

    public String getImagePath() {
        return "src/resources/images/lawnMower.png";
    }
}
