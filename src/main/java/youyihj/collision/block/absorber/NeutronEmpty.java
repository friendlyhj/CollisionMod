package youyihj.collision.block.absorber;

public class NeutronEmpty extends Absorber {
    private NeutronEmpty() {
        super("neutron_empty", true);
    }

    public static final NeutronEmpty INSTANCE = new NeutronEmpty();

    @Override
    public Absorber getTransformAbsorber() {
        return Neutron.INSTANCE;
    }
}
