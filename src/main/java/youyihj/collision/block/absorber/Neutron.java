package youyihj.collision.block.absorber;

public class Neutron extends Absorber {
    private Neutron() {
        super("neutron", false, false);
    }

    public static final Neutron INSTANCE = new Neutron();

    @Override
    public Absorber getTransformAbsorber() {
        return NeutronEmpty.INSTANCE;
    }

    @Override
    public EnumAbsorber getType() {
        return EnumAbsorber.NEUTRON;
    }

    public static class Refined extends Absorber {
        private Refined() {
            super("neutron_refined", false, false);
        }

        public static final Refined INSTANCE = new Refined();

        @Override
        public Absorber getTransformAbsorber() {
            return NeutronEmpty.Refined.INSTANCE;
        }

        @Override
        public EnumAbsorber getType() {
            return EnumAbsorber.NEUTRON;
        }
    }
}
