package roundaround.mcmods.glacios.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import roundaround.mcmods.glacios.Glacios;

public class RenderArcticFox extends RenderLiving {

    private static final ResourceLocation foxTexture = new ResourceLocation(Glacios.MODID, "textures/entity/arcticfox/arcticfox1.png");

    public RenderArcticFox(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return foxTexture;
    }

}
