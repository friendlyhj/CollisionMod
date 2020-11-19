package youyihj.collision.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import org.apache.commons.io.FileUtils;
import youyihj.collision.util.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ModelGenerator {
    private static final List<IHasGeneratedModel> NEED_GENERATE_MODELS = new ArrayList<>();

    public static void registerModel(IHasGeneratedModel model) {
        NEED_GENERATE_MODELS.add(model);
    }

    public static void generate() {
        NEED_GENERATE_MODELS.stream().filter(IHasGeneratedModel::isGeneratingModel).forEach(model -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ArrayList<ModelResourceLocation> modelResourceLocations = new ArrayList<>();
            model.getModelRLs(modelResourceLocations);
            Utils.enumerateForEach(modelResourceLocations, (meta, modelRL) -> {
                File file = new File(model.getModelDir(modelRL) + modelRL.getResourcePath() + ".json");
                if (model.isAlwaysOverrideModelFile() || !file.exists()) {
                    JsonObject all = new JsonObject();
                    all.addProperty("forge_marker", 1);
                    switch (model.getModelType()) {
                        case ITEM:
                            JsonObject defaults = new JsonObject();
                            defaults.addProperty("model", "minecraft:builtin/generated");
                            JsonObject textures = new JsonObject();
                            textures.addProperty("layer0", modelRL.getResourceDomain() + ":items/" + modelRL.getResourcePath());
                            defaults.add("textures", textures);
                            all.add("defaults", defaults);
                            JsonObject variants = new JsonObject();
                            JsonArray inventory = new JsonArray();
                            JsonObject transform = new JsonObject();
                            transform.addProperty("transform", "forge:default-item");
                            inventory.add(transform);
                            variants.add("inventory", inventory);
                            all.add("variants", variants);
                            break;
                        case BLOCK:
                            defaults = new JsonObject();
                            textures = new JsonObject();
                            textures.addProperty("all", modelRL.getResourceDomain() + ":blocks/" + modelRL.getResourcePath());
                            textures.addProperty("particle", modelRL.getResourceDomain() + ":blocks/" + modelRL.getResourcePath());
                            defaults.add("textures", textures);
                            defaults.addProperty("model", "minecraft:cube_all");
                            defaults.addProperty("uvlock", true);
                            all.add("defaults", defaults);
                            JsonArray jsonArray = new JsonArray();
                            jsonArray.add(new JsonObject());
                            variants = new JsonObject();
                            variants.add("normal", jsonArray);
                            variants.add("inventory", jsonArray);
                            all.add("variants", variants);
                            break;
                        case FLUID:
                            variants = new JsonObject();
                            defaults = new JsonObject();
                            defaults.addProperty("model", "forge:fluid");
                            JsonObject custom = new JsonObject();
                            custom.addProperty("fluid", modelRL.getResourcePath());
                            defaults.add("custom", custom);
                            variants.add("defaults", defaults);
                            all.add("variants", variants);
                            break;
                    }
                    try {
                        FileUtils.writeStringToFile(file, gson.toJson(all), StandardCharsets.UTF_8, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
}
