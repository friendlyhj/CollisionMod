package youyihj.collision.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import youyihj.collision.Collision;
import youyihj.collision.block.BlockRegistryHandler;
import youyihj.collision.model.IHasGeneratedModel;
import youyihj.collision.IRegistryObject;
import youyihj.collision.model.ModelGenerator;
import youyihj.collision.model.ModelType;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.List;

public class CollisionFluid extends Fluid implements IHasGeneratedModel, IRegistryObject {

    private boolean isLava = false;

    public CollisionFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, Color color) {
        super(fluidName, still, flowing, color);
    }

    public CollisionFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, @Nullable ResourceLocation overlay, Color color) {
        super(fluidName, still, flowing, overlay, color);
    }

    public CollisionFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int color) {
        super(fluidName, still, flowing, color);
    }

    public CollisionFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, @Nullable ResourceLocation overlay, int color) {
        super(fluidName, still, flowing, overlay, color);
    }

    public CollisionFluid(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
    }

    public CollisionFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, @Nullable ResourceLocation overlay) {
        super(fluidName, still, flowing, overlay);
    }

    @Override
    public void getModelRLs(List<ModelResourceLocation> list) {
        list.add(new ModelResourceLocation(new ResourceLocation(Collision.MODID, this.fluidName), "defaults"));
    }

    @Override
    public ModelType getModelType() {
        return ModelType.FLUID;
    }

    public CollisionFluid setLava() {
        isLava = true;
        return this;
    }

    @Override
    public CollisionFluid setLuminosity(int luminosity) {
        return ((CollisionFluid) super.setLuminosity(luminosity));
    }

    @Override
    public CollisionFluid setTemperature(int temperature) {
        return ((CollisionFluid) super.setTemperature(temperature));
    }

    @Override
    public CollisionFluid setDensity(int density) {
        return ((CollisionFluid) super.setDensity(density));
    }

    @Override
    public CollisionFluid setViscosity(int viscosity) {
        return ((CollisionFluid) super.setViscosity(viscosity));
    }

    @Override
    public void register() {
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
        FluidRegistrar.fluids.add(this);
        Block blockFluid = new BlockFluidClassic(this, (isLava ? Material.LAVA : Material.WATER)).setRegistryName(this.fluidName);
        BlockRegistryHandler.blocks.add(blockFluid);
        ModelGenerator.registerModel(this);
    }

    @Override
    public boolean isAlwaysOverrideModelFile() {
        return true;
    }
}
