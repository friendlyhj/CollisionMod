package youyihj.collision.fluid;

import net.minecraft.util.ResourceLocation;

public class FluidRegistrar {
    public static void registerAll() {
        new CollisionFluid("collision_proton",
                new ResourceLocation("collision:fluids/molten"),
                new ResourceLocation("collision:fluids/molten_flowing"), 0xffaa0000)
                .setLava()
                .setTemperature(1000)
                .setDensity(3000)
                .setViscosity(3000)
                .register();

        new CollisionFluid("collision_neutron",
                new ResourceLocation("collision:fluids/molten"),
                new ResourceLocation("collision:fluids/molten_flowing"), 0xff0000aa)
                .setLava()
                .setTemperature(1000)
                .setDensity(3000)
                .setViscosity(3000)
                .register();
    }
}
