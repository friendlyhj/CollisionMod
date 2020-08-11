package youyihj.collision.item;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.parts.Part;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import youyihj.collision.Collision;
import youyihj.collision.config.NucleusDataReader;


public class Nucleus {
    public static void register() {
        final Part part;
        try {
            part = new PartBuilder().setPartType(MaterialSystem.getPartType("item")).setName("nucleus").setOwnerId(Collision.MODID).setOreDictName("nucleus").build();
            if (part != null) {
                NucleusDataReader.nucleusDataEntries.forEach((name, entry) -> {
                    Material material = entry.material;
                    if (material != null) {
                        Collision.instance.getMaterialUser().registerPartsForMaterial(material, "nucleus");
                    }
                });
            }
        } catch (MaterialException e) {
            e.printStackTrace();
        }
    }
}
