package youyihj.collision.item;

public class SingleNucleus {
    SingleNucleus(String name, String color, int chance) {
        this.name = name;
        this.color = color;
        this.chance = chance;
    }

    public final String name;
    public final String color;
    public final int chance;

    public int getColorToInt() {
        return Integer.parseInt(color, 16);
    }
}
