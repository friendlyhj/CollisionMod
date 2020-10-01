package youyihj.collision.compact.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.collision.block.absorber.EnumAbsorber;

@ZenClass("mods.collision.Absorber")
@ZenRegister
public class CrTEnumAbsorber {
    private EnumAbsorber absorber;

    public CrTEnumAbsorber(EnumAbsorber absorber) {
        this.absorber = absorber;
    }

    @ZenMethod
    public static CrTEnumAbsorber neutron() {
        return new CrTEnumAbsorber(EnumAbsorber.NEUTRON);
    }

    @ZenMethod
    public static CrTEnumAbsorber neutronEmpty() {
        return new CrTEnumAbsorber(EnumAbsorber.NEUTRON_EMPTY);
    }

    @ZenMethod
    public static CrTEnumAbsorber proton() {
        return new CrTEnumAbsorber(EnumAbsorber.PROTON);
    }

    @ZenMethod
    public static CrTEnumAbsorber protonEmpty() {
        return new CrTEnumAbsorber(EnumAbsorber.PROTON_EMPTY);
    }

    public EnumAbsorber getInternal() {
        return absorber;
    }

    public static EnumAbsorber getInternalStatic(CrTEnumAbsorber absorber) {
        return absorber == null ? null : absorber.getInternal();
    }
}
