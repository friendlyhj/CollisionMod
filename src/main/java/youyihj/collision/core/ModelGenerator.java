package youyihj.collision.core;

import java.util.ArrayList;
import java.util.List;

public class ModelGenerator {
    public static List<IHasGeneratedModel> needGenerateModels = new ArrayList<>();

    public static void generate() {
        needGenerateModels.stream().filter(IHasGeneratedModel::isGenerating).forEach(IHasGeneratedModel::generate);
    }
}
