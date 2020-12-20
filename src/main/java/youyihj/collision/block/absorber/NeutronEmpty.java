package youyihj.collision.block.absorber;

/**
 * @author youyihj
 */
public class NeutronEmpty extends Absorber {
    public static final NeutronEmpty INSTANCE = new NeutronEmpty();

    public NeutronEmpty() {
        super("neutron_empty", true, false, Type.NEUTRON_EMPTY);
    }

    public static class Refined extends Absorber {
        public static final Refined INSTANCE = new Refined();

        public Refined() {
            super("neutron_empty_refined", false, true, Type.NEUTRON_EMPTY);
        }
    }
}
