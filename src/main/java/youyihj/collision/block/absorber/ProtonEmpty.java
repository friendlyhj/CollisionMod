package youyihj.collision.block.absorber;

/**
 * @author youyihj
 */
public class ProtonEmpty extends Absorber {
    public static final ProtonEmpty INSTANCE = new ProtonEmpty();

    public ProtonEmpty() {
        super("proton_empty", true, false, Type.PROTON_EMPTY);
    }

    public static class Refined extends Absorber {

        public static final Refined INSTANCE = new Refined();

        public Refined() {
            super("proton_empty_refined", false, true, Type.PROTON_EMPTY);
        }
    }
}
