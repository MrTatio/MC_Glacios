package roundaround.mcmods.glacios.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelArcticFox extends ModelBase {

    private ModelRenderer Head;
    private ModelRenderer Body;
    private ModelRenderer Leg1;
    private ModelRenderer Leg2;
    private ModelRenderer Leg3;
    private ModelRenderer Leg4;
    private ModelRenderer Tail;
    private ModelRenderer Ear1;
    private ModelRenderer Ear2;
    private ModelRenderer Nose;

    public ModelArcticFox() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-2.5F, -3.5F, -3.5F, 5, 5, 4);
        this.Head.setRotationPoint(0F, 16F, -6F);
        this.Head.setTextureSize(this.textureWidth, this.textureHeight);

        this.Body = new ModelRenderer(this, 18, 0);
        this.Body.addBox(-3F, -3F, -6F, 6, 6, 13);
        this.Body.setRotationPoint(0F, 17F, 0F);
        this.Body.setTextureSize(this.textureWidth, this.textureHeight);

        this.Leg1 = new ModelRenderer(this, 0, 23);
        this.Leg1.addBox(-1F, 0F, -1F, 2, 5, 2);
        this.Leg1.setRotationPoint(-1.5F, 19F, 5F);
        this.Leg1.setTextureSize(this.textureWidth, this.textureHeight);
        this.Leg1.mirror = true;

        this.Leg2 = new ModelRenderer(this, 0, 23);
        this.Leg2.addBox(-1F, 0F, -1F, 2, 5, 2);
        this.Leg2.setRotationPoint(1.5F, 19F, 5F);
        this.Leg2.setTextureSize(this.textureWidth, this.textureHeight);

        this.Leg3 = new ModelRenderer(this, 0, 23);
        this.Leg3.addBox(-1F, 0F, -1F, 2, 5, 2);
        this.Leg3.setRotationPoint(-1.5F, 19F, -4F);
        this.Leg3.setTextureSize(this.textureWidth, this.textureHeight);
        this.Leg3.mirror = true;

        this.Leg4 = new ModelRenderer(this, 0, 23);
        this.Leg4.addBox(-1F, 0F, -1F, 2, 5, 2);
        this.Leg4.setRotationPoint(1.5F, 19F, -4F);
        this.Leg4.setTextureSize(this.textureWidth, this.textureHeight);

        this.Tail = new ModelRenderer(this, 0, 9);
        this.Tail.addBox(-2F, 0F, -2F, 4, 10, 4);
        this.Tail.setRotationPoint(0F, 16.5F, 6F);
        this.Tail.setTextureSize(this.textureWidth, this.textureHeight);

        this.Ear1 = new ModelRenderer(this, 18, 6);
        this.Ear1.addBox(-3F, -5.5F, -1F, 2, 2, 1);
        this.Ear1.setRotationPoint(0F, 16F, -6F);
        this.Ear1.setTextureSize(this.textureWidth, this.textureHeight);

        this.Ear2 = new ModelRenderer(this, 18, 6);
        this.Ear2.addBox(1F, -5F, 0F, 2, 2, 1);
        this.Ear2.setRotationPoint(0F, 15.5F, -7F);
        this.Ear2.setTextureSize(this.textureWidth, this.textureHeight);
        this.Ear2.mirror = true;

        this.Nose = new ModelRenderer(this, 18, 0);
        this.Nose.addBox(-1F, -1F, -6F, 2, 2, 4);
        this.Nose.setRotationPoint(0F, 16F, -6F);
        this.Nose.setTextureSize(this.textureWidth, this.textureHeight);
    }

    @Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

        this.Head.render(par7);
        this.Ear1.render(par7);
        this.Ear2.render(par7);
        this.Nose.render(par7);
        this.Body.render(par7);
        this.Leg1.render(par7);
        this.Leg2.render(par7);
        this.Leg3.render(par7);
        this.Leg4.render(par7);
        this.Tail.render(par7);
    }

    @Override
    public void setRotationAngles(float partialTime, float amplitude, float par3, float viewY, float viewX, float par6, Entity par7Entity) {
        this.Head.rotateAngleX = viewX / (180F / (float) Math.PI);
        this.Head.rotateAngleY = viewY / (180F / (float) Math.PI);
        this.Ear1.rotateAngleX = viewX / (180F / (float) Math.PI);
        this.Ear1.rotateAngleY = viewY / (180F / (float) Math.PI);
        this.Ear2.rotateAngleX = viewX / (180F / (float) Math.PI);
        this.Ear2.rotateAngleY = viewY / (180F / (float) Math.PI);
        this.Nose.rotateAngleX = viewX / (180F / (float) Math.PI);
        this.Nose.rotateAngleY = viewY / (180F / (float) Math.PI);
        this.Leg1.rotateAngleX = MathHelper.cos(partialTime * 0.6662F) * 1.4F * amplitude;
        this.Leg2.rotateAngleX = MathHelper.cos(partialTime * 0.6662F + (float) Math.PI) * 1.4F * amplitude;
        this.Leg3.rotateAngleX = MathHelper.cos(partialTime * 0.6662F + (float) Math.PI) * 1.4F * amplitude;
        this.Leg4.rotateAngleX = MathHelper.cos(partialTime * 0.6662F) * 1.4F * amplitude;
        this.Tail.rotateAngleX = (float) Math.PI / 3.0F;
    }

}
