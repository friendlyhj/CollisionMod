package youyihj.collision.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import youyihj.collision.core.Utils;

public interface IHasGeneratedModel extends IHasModel {
    default String getDir(ModelResourceLocation location) {
        return "../src/main/resources/assets/" + location.getResourceDomain() + "/blockstates/";
    }

    default boolean isAlwaysOverrideModelFile() {
        return false;
    }

    default boolean isGenerating() {
        return Utils.isDevEnvironment();
    }
}
