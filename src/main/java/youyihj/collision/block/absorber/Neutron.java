package youyihj.collision.block.absorber;

public class Neutron extends Absorber {
    private Neutron() {
        super("neutron", false);
    }

    public static final Neutron INSTANCE = new Neutron();

    @Override
    public Absorber getTransformAbsorber() {
        return NeutronEmpty.INSTANCE;
    }
}
