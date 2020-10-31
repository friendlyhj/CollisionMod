package youyihj.collision.block.absorber;

public class NeutronEmpty extends Absorber {
    private NeutronEmpty() {
        super("neutron_empty", true, true);
    }

    public static final NeutronEmpty INSTANCE = new NeutronEmpty();

    @Override
    public Absorber getTransformAbsorber() {
        return Neutron.INSTANCE;
    }

    @Override
    public EnumAbsorber getType() {
        return EnumAbsorber.NEUTRON_EMPTY;
    }

    public static class Refined extends Absorber {
        private Refined() {
            super("neutron_empty_refined", true, false);
        }

        public static final Refined INSTANCE = new Refined();

        @Override
        public Absorber getTransformAbsorber() {
            return Neutron.Refined.INSTANCE;
        }

        @Override
        public EnumAbsorber getType() {
            return EnumAbsorber.NEUTRON_EMPTY;
        }
    }
}
