package youyihj.collision.block.absorber;

import javax.annotation.Nonnull;

public enum EnumAbsorber {
    NEUTRON(Neutron.INSTANCE, Neutron.Refined.INSTANCE),
    NEUTRON_EMPTY(NeutronEmpty.INSTANCE, NeutronEmpty.Refined.INSTANCE),
    PROTON(Proton.INSTANCE, Proton.Refined.INSTANCE),
    PROTON_EMPTY(ProtonEmpty.INSTANCE, ProtonEmpty.Refined.INSTANCE);

    private Absorber instance;
    private Absorber refined;

    EnumAbsorber(Absorber arg, Absorber refined) {
        this.instance = arg;
        this.refined = refined;
    }

    public Absorber getInstance() {
        return instance;
    }

    public Absorber getRefined() {
        return refined;
    }

    public Absorber getInstanceByLevel(int level) {
        return level > 2 ? getRefined() : getInstance();
    }

    @Nonnull
    public EnumAbsorber getTransformAbsorber() {
        switch (this) {
            case PROTON:
                return PROTON_EMPTY;
            case NEUTRON:
                return NEUTRON_EMPTY;
            case PROTON_EMPTY:
                return PROTON;
            case NEUTRON_EMPTY:
                return NEUTRON;
        }
        return null;
    }
}
