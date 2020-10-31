package youyihj.collision.block.absorber;

public class Proton extends Absorber {
    private Proton() {
        super("proton", false, false);
    }

    public static final Proton INSTANCE = new Proton();

    @Override
    public Absorber getTransformAbsorber() {
        return ProtonEmpty.INSTANCE;
    }

    @Override
    public EnumAbsorber getType() {
        return EnumAbsorber.PROTON;
    }

    public static class Refined extends Absorber {
        private Refined() {
            super("proton_refined", true, false);
        }

        public static final Refined INSTANCE = new Refined();

        @Override
        public Absorber getTransformAbsorber() {
            return ProtonEmpty.Refined.INSTANCE;
        }

        @Override
        public EnumAbsorber getType() {
            return EnumAbsorber.PROTON;
        }
    }
}
