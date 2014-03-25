package roundaround.mcmods.glacios;

import net.minecraft.entity.EnumCreatureType;
import roundaround.mcmods.glacios.client.model.ModelArcticFox;
import roundaround.mcmods.glacios.client.renderer.entity.RenderArcticFox;
import roundaround.mcmods.glacios.entity.EntityArcticFox;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GlaciosEntities {
    
    public static void init() {
        EntityRegistry.registerModEntity(EntityArcticFox.class, "arcticFox", 1, Glacios.instance, 80, 3, true);
        
        EntityRegistry.addSpawn(EntityArcticFox.class, 10, 3, 6, EnumCreatureType.creature, GlaciosBiomes.hills, GlaciosBiomes.plateau, GlaciosBiomes.taiga);
        
        registerRenderers();
    }

    @SideOnly(Side.CLIENT)
    private static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityArcticFox.class, new RenderArcticFox(new ModelArcticFox(), 0.5F));
    }
}
