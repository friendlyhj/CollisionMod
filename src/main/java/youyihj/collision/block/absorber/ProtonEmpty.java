package youyihj.collision.block.absorber;

public class ProtonEmpty extends Absorber {
    private ProtonEmpty() {
        super("proton_empty", true, true);
    }

    public static final ProtonEmpty INSTANCE = new ProtonEmpty();

    @Override
    public Absorber getTransformAbsorber() {
        return Proton.INSTANCE;
    }

    @Override
    public EnumAbsorber getType() {
        return EnumAbsorber.PROTON_EMPTY;
    }

    public static class Refined extends Absorber {
        private Refined() {
            super("proton_empty_refined", true, false);
        }

        public static final Refined INSTANCE = new Refined();

        @Override
        public Absorber getTransformAbsorber() {
            return Proton.Refined.INSTANCE;
        }

        @Override
        public EnumAbsorber getType() {
            return EnumAbsorber.PROTON_EMPTY;
        }
    }
}
