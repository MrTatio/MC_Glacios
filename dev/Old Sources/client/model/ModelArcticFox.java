package glacios.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelArcticFox extends ModelBase {
	ModelRenderer head;
	ModelRenderer body;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer tail;
	ModelRenderer ear1;
	ModelRenderer ear2;
	ModelRenderer nose;

	public ModelArcticFox() {
		textureWidth = 128;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, 0);
		head.addBox(-2.5F, -3.5F, -3.5F, 5, 5, 4);
		head.setRotationPoint(0F, 16F, -6F);
		head.setTextureSize(textureWidth, textureHeight);
		body = new ModelRenderer(this, 18, 0);
		body.addBox(-3F, -3F, -6F, 6, 6, 13);
		body.setRotationPoint(0F, 17F, 0F);
		body.setTextureSize(textureWidth, textureHeight);
		leg1 = new ModelRenderer(this, 0, 23);
		leg1.addBox(-1F, 0F, -1F, 2, 5, 2);
		leg1.setRotationPoint(-1.5F, 19F, 5F);
		leg1.setTextureSize(textureWidth, textureHeight);
		leg2 = new ModelRenderer(this, 0, 23);
		leg2.addBox(-1F, 0F, -1F, 2, 5, 2);
		leg2.setRotationPoint(1.5F, 19F, 5F);
		leg2.setTextureSize(textureWidth, textureHeight);
		leg2.mirror = true;
		leg3 = new ModelRenderer(this, 0, 23);
		leg3.addBox(-1F, 0F, -1F, 2, 5, 2);
		leg3.setRotationPoint(-1.5F, 19F, -4F);
		leg3.setTextureSize(textureWidth, textureHeight);
		leg4 = new ModelRenderer(this, 0, 23);
		leg4.addBox(-1F, 0F, -1F, 2, 5, 2);
		leg4.setRotationPoint(1.5F, 19F, -4F);
		leg4.setTextureSize(textureWidth, textureHeight);
		leg4.mirror = true;
		tail = new ModelRenderer(this, 0, 9);
		tail.addBox(-2F, 0F, -2F, 4, 10, 4);
		tail.setRotationPoint(0F, 16.5F, 6F);
		tail.setTextureSize(textureWidth, textureHeight);
		tail.rotateAngleX = 1.130069F;
		ear1 = new ModelRenderer(this, 18, 6);
		ear1.addBox(-3F, -5.5F, -1F, 2, 2, 1);
		ear1.setRotationPoint(0F, 16F, -6F);
		ear1.setTextureSize(textureWidth, textureHeight);
		ear2 = new ModelRenderer(this, 18, 6);
		ear2.addBox(1F, -5F, 0F, 2, 2, 1);
		ear2.setRotationPoint(0F, 15.5F, -7F);
		ear2.setTextureSize(textureWidth, textureHeight);
		ear2.mirror = true;
		nose = new ModelRenderer(this, 18, 0);
		nose.addBox(-1F, -1F, -6F, 2, 2, 4);
		nose.setRotationPoint(0F, 16F, -6F);
		nose.setTextureSize(textureWidth, textureHeight);
	}

	/*
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		if (isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 5.0F * par7, 2.0F * par7);
			head.renderWithRotation(par7);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			leg3.render(par7);
			leg4.render(par7);
			tail.renderWithRotation(par7);
			ear1.render(par7);
			ear2.render(par7);
			nose.render(par7);
			GL11.glPopMatrix();
		} else {
			head.render(par7);
			body.render(par7);
			leg1.render(par7);
			leg2.render(par7);
			leg3.render(par7);
			leg4.render(par7);
			tail.renderWithRotation(par7);
			ear1.render(par7);
			ear2.render(par7);
			nose.render(par7);
		}
	}

	/*
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	@Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        head.rotateAngleX = par5 / (180F / (float)Math.PI);
        head.rotateAngleY = par4 / (180F / (float)Math.PI);
        ear1.rotateAngleX = par5 / (180F / (float)Math.PI);
        ear1.rotateAngleY = par4 / (180F / (float)Math.PI);
        ear2.rotateAngleX = par5 / (180F / (float)Math.PI);
        ear2.rotateAngleY = par4 / (180F / (float)Math.PI);
        nose.rotateAngleX = par5 / (180F / (float)Math.PI);
        nose.rotateAngleY = par4 / (180F / (float)Math.PI);
        leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
	}
}
