package youyihj.collision.config;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;

import java.awt.Color;

public class NucleusDataEntry {
    NucleusDataEntry(String name, String color, int chance) {
        this.name = name;
        this.color = color;
        this.chance = chance;
        try {
            this.material = new MaterialBuilder().setName(name).setColor(new Color(Integer.parseInt(color, 16))).build();
            MaterialSystem.registerMaterial(material);
        } catch (MaterialException e) {
            e.printStackTrace();
        }
    }

    public final String name;
    public final String color;
    public final int chance;
    public Material material = null;
}
