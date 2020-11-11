package youyihj.collision.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.util.List;

public interface IHasModel {
    void getModelRLs(List<ModelResourceLocation> list);

    ModelType getModelType();
}
