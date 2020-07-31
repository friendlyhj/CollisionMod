package youyihj.collision.block.absorber;

public enum EnumAbsorber {
    NEUTRON(Neutron.INSTANCE),
    NEUTRON_EMPTY(NeutronEmpty.INSTANCE),
    PROTON(Proton.INSTANCE),
    PROTON_EMPTY(ProtonEmpty.INSTANCE);

    private Absorber instance;

    EnumAbsorber(Absorber arg) {
        this.instance = arg;
    }

    public Absorber getInstance() {
        return instance;
    }
}
