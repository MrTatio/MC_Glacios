package roundaround.mcmods.glacios;

import net.minecraft.entity.EnumCreatureType;
import roundaround.mcmods.glacios.client.model.ModelArcticFox;
import roundaround.mcmods.glacios.client.model.ModelIceElemental;
import roundaround.mcmods.glacios.client.renderer.entity.RenderArcticFox;
import roundaround.mcmods.glacios.client.renderer.entity.RenderIceElemental;
import roundaround.mcmods.glacios.entity.EntityArcticFox;
import roundaround.mcmods.glacios.entity.EntityIceElemental;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GlaciosEntities {
    
    public static void init(Glacios instance) {
        EntityRegistry.registerModEntity(EntityArcticFox.class, "arcticFox", 0, instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityIceElemental.class, "iceElemental", 1, instance, 80, 3, true);
        
        EntityRegistry.addSpawn(EntityArcticFox.class, 10, 3, 6, EnumCreatureType.creature, GlaciosBiomes.hills, GlaciosBiomes.plateau, GlaciosBiomes.taiga);
        EntityRegistry.addSpawn(EntityIceElemental.class, 2, 1, 1, EnumCreatureType.monster, GlaciosBiomes.hills, GlaciosBiomes.plateau);
        
        registerRenderers();
    }

    @SideOnly(Side.CLIENT)
    private static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityArcticFox.class, new RenderArcticFox(new ModelArcticFox(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityIceElemental.class, new RenderIceElemental(new ModelIceElemental(), 0.4F));
    }
}
