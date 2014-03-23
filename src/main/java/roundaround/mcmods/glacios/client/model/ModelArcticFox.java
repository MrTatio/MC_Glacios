package roundaround.mcmods.glacios.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

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

    protected float tanslateYScale = 8.0F;
    protected float translateZScale = 4.0F;

    public ModelArcticFox() {
        textureWidth = 128;
        textureHeight = 64;

        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-5F, -7F, -7F, 10, 10, 8);
        Head.setRotationPoint(0F, 31F, -12F);
        Head.setTextureSize(128, 64);

        Body = new ModelRenderer(this, 36, 0);
        Body.addBox(-6F, -6F, -12F, 12, 12, 26);
        Body.setRotationPoint(0F, 32F, 0F);
        Body.setTextureSize(128, 64);

        Leg1 = new ModelRenderer(this, 0, 46);
        Leg1.addBox(-2F, 0F, -2F, 4, 10, 4);
        Leg1.setRotationPoint(-3F, 38F, 10F);
        Leg1.setTextureSize(128, 64);
        Leg1.mirror = true;

        Leg2 = new ModelRenderer(this, 0, 46);
        Leg2.addBox(-2F, 0F, -2F, 4, 10, 4);
        Leg2.setRotationPoint(3F, 38F, 10F);
        Leg2.setTextureSize(128, 64);

        Leg3 = new ModelRenderer(this, 0, 46);
        Leg3.addBox(-2F, 0F, -2F, 4, 10, 4);
        Leg3.setRotationPoint(-3F, 38F, -8F);
        Leg3.setTextureSize(128, 64);
        Leg3.mirror = true;

        Leg4 = new ModelRenderer(this, 0, 46);
        Leg4.addBox(-2F, 0F, -2F, 4, 10, 4);
        Leg4.setRotationPoint(3F, 38F, -8F);
        Leg4.setTextureSize(128, 64);

        Tail = new ModelRenderer(this, 0, 18);
        Tail.addBox(-4F, 0F, -4F, 8, 20, 8);
        Tail.setRotationPoint(0F, 30F, 12F);
        Tail.setTextureSize(128, 64);

        Ear1 = new ModelRenderer(this, 36, 12);
        Ear1.addBox(-6F, -11F, -2F, 4, 4, 2);
        Ear1.setRotationPoint(0F, 31F, -12F);
        Ear1.setTextureSize(128, 64);

        Ear2 = new ModelRenderer(this, 36, 12);
        Ear2.addBox(2F, -11F, -2F, 4, 4, 2);
        Ear2.setRotationPoint(0F, 31F, -12F);
        Ear2.setTextureSize(128, 64);
        Ear2.mirror = true;

        Nose = new ModelRenderer(this, 36, 0);
        Nose.addBox(-2F, -2F, -12F, 4, 4, 8);
        Nose.setRotationPoint(0F, 31F, -12F);
        Nose.setTextureSize(128, 64);
    }

    @Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

        float scale = 2.0F;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, this.tanslateYScale * par7, this.translateZScale * par7);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
        GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
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
        GL11.glPopMatrix();
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        this.Head.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Head.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Ear1.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Ear1.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Ear2.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Ear2.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Nose.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.Nose.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.Body.rotateAngleX = ((float) Math.PI / 2F);
        this.Leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.Leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.Leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.Leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.Tail.rotateAngleX = 1.0F;
    }

}
