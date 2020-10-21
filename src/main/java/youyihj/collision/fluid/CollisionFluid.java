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
import youyihj.collision.core.IHasGeneratedModel;
import youyihj.collision.core.IRegistryObject;
import youyihj.collision.core.ModelGenerator;
import youyihj.collision.core.ModelType;
import youyihj.collision.item.ItemRegistryHandler;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;

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
    public HashMap<Integer, ModelResourceLocation> getModelRLs() {
        HashMap<Integer, ModelResourceLocation> temp = new HashMap<>();
        temp.put(0, new ModelResourceLocation(new ResourceLocation(Collision.MODID, this.fluidName), "defaults"));
        return temp;
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
        ItemRegistryHandler.fluids.add(this);
        Block blockFluid = new BlockFluidClassic(this, (isLava ? Material.LAVA : Material.WATER)).setRegistryName(this.fluidName);
        BlockRegistryHandler.blocks.add(blockFluid);
        ModelGenerator.needGenerateModels.add(this);
    }

    @Override
    public boolean isAlwaysOverrideModelFile() {
        return true;
    }
}
