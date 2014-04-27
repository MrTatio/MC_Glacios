package roundaround.mcmods.glacios.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import roundaround.mcmods.glacios.Glacios;

public class RenderIceElemental extends RenderLiving {

    private static final ResourceLocation iceElemental = new ResourceLocation(Glacios.MODID, "textures/entity/iceelemental/iceelemental1.png");

    public RenderIceElemental(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return iceElemental;
    }

}
