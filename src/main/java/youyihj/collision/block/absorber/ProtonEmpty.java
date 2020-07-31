package youyihj.collision.block.absorber;

public class ProtonEmpty extends Absorber {
    private ProtonEmpty() {
        super("proton_empty", true);
    }

    public static final ProtonEmpty INSTANCE = new ProtonEmpty();

    @Override
    public Absorber getTransformAbsorber() {
        return Proton.INSTANCE;
    }
}
