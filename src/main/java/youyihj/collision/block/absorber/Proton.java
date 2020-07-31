package youyihj.collision.block.absorber;

public class Proton extends Absorber {
    private Proton() {
        super("proton", false);
    }

    public static final Proton INSTANCE = new Proton();

    @Override
    public Absorber getTransformAbsorber() {
        return ProtonEmpty.INSTANCE;
    }
}
