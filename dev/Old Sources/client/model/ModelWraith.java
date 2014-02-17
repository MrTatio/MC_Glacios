package glacios.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWraith extends ModelBase {
    ModelRenderer hood;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer head;
    ModelRenderer rightwing;
    ModelRenderer leftwing;
    ModelRenderer legs;
    ModelRenderer tail;
    ModelRenderer shoulders;

    public ModelWraith() {
        textureWidth = 128;
        textureHeight = 64;

        hood = new ModelRenderer(this, 0, 0);
        hood.addBox(-4F, -8F, -4F, 8, 8, 8);
        hood.setRotationPoint(0F, -8F, 0F);
        hood.setTextureSize(textureWidth, textureHeight);

        body = new ModelRenderer(this, 32, 0);
        body.addBox(-5F, 8F, -2F, 10, 10, 8);
        body.setRotationPoint(0F, -8F, -1F);
        body.setTextureSize(textureWidth, textureHeight);

        rightarm = new ModelRenderer(this, 0, 16);
        rightarm.addBox(-3F, 0F, -2F, 4, 16, 4);
        rightarm.setRotationPoint(-6.9F, -3F, 2F);
        rightarm.setTextureSize(textureWidth, textureHeight);

        leftarm = new ModelRenderer(this, 0, 16);
        leftarm.addBox(-1F, 0F, -2F, 4, 16, 4);
        leftarm.setRotationPoint(6.9F, -3F, 2F);
        leftarm.setTextureSize(textureWidth, textureHeight);
        leftarm.mirror = true;

        head = new ModelRenderer(this, 0, 36);
        head.addBox(-3F, -6F, -3F, 6, 6, 6);
        head.setRotationPoint(0F, -8F, 0F);
        head.setTextureSize(textureWidth, textureHeight);

        rightwing = new ModelRenderer(this, 72, 0);
        rightwing.addBox(-1F, -3F, 0F, 1, 12, 16);
        rightwing.setRotationPoint(-2F, -6F, 6F);
        rightwing.setTextureSize(textureWidth, textureHeight);
        setRotation(rightwing, 0.3490659F, -0.4363323F, 0F);

        leftwing = new ModelRenderer(this, 72, 0);
        leftwing.addBox(0F, -3F, 0F, 1, 12, 16);
        leftwing.setRotationPoint(2F, -6F, 6F);
        leftwing.setTextureSize(textureWidth, textureHeight);
        leftwing.mirror = true;
        setRotation(leftwing, 0.3490659F, 0.4363323F, 0F);

        tail = new ModelRenderer(this, 32, 18);
        tail.addBox(-4F, 1F, -2F, 8, 6, 4);
        tail.setRotationPoint(0F, 16F, 2F);
        tail.setTextureSize(textureWidth, textureHeight);
        setRotation(tail, 0.9599311F, 0F, 0F);

        legs = new ModelRenderer(this, 0, 48);
        legs.addBox(-4F, -4F, -4F, 8, 8, 6);
        legs.setRotationPoint(0F, 13F, 3F);
        legs.setTextureSize(textureWidth, textureHeight);
        setRotation(legs, 0.2617994F, 0F, 0F);

        shoulders = new ModelRenderer(this, 32, 28);
        shoulders.addBox(-10F, 0F, -3F, 20, 8, 8);
        shoulders.setRotationPoint(0F, -8F, 0F);
        shoulders.setTextureSize(128, 64);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        hood.render(f5);
        body.render(f5);
        rightarm.render(f5);
        leftarm.render(f5);
        head.render(f5);
        rightwing.render(f5);
        leftwing.render(f5);
        tail.render(f5);
        legs.render(f5);
        shoulders.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
