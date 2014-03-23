package roundaround.mcmods.glacios.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.entity.EntityArcticFox;

public class RenderArcticFox extends RenderLiving {

    private static final ResourceLocation foxTexture = new ResourceLocation(Glacios.MODID, "textures/entityarcticfox/arcticfox1.png");

    public RenderArcticFox(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityArcticFox entity) {
        return foxTexture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityArcticFox) entity);
    }

}
