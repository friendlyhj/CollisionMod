package youyihj.collision.block.absorber;

/**
 * @author youyihj
 */
public class Neutron extends Absorber {
    public static final Neutron INSTANCE = new Neutron();

    private Neutron() {
        super("neutron", false, false, Type.NEUTRON);
    }

    public static class Refined extends Absorber {

        public static final Refined INSTANCE = new Refined();

        public Refined() {
            super("neutron_refined", false, true, Type.NEUTRON);
        }
    }
}
