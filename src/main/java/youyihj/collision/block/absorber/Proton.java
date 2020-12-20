package youyihj.collision.block.absorber;

/**
 * @author youyihj
 */
public class Proton extends Absorber {

    public static final Proton INSTANCE = new Proton();

    private Proton() {
        super("proton", false, false, Type.PROTON);
    }

    public static class Refined extends Absorber {

        public static final Refined INSTANCE = new Refined();

        public Refined() {
            super("proton_refined", false, true, Type.PROTON);
        }
    }
}
