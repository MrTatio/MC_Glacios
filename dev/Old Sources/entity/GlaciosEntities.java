package glacios.entity;

import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GlaciosEntities {

    public static void RegisterEntities() {
        LanguageRegistry.instance().addStringLocalization("entity.Wraith.name", "en_US", "Wraith");
        EntityRegistry.registerGlobalEntityID(EntityWraith.class, "Wraith", ModLoader.getUniqueEntityId());

        LanguageRegistry.instance().addStringLocalization("entity.ArcticFox.name", "en_US", "Arctic Fox");
        EntityRegistry.registerGlobalEntityID(EntityArcticFox.class, "Arctic Fox", ModLoader.getUniqueEntityId());
    }

}
