package roundaround.mcmods.glacios.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelIceElemental extends ModelBase {
    
    private ModelRenderer Head;
    private ModelRenderer ArmR;
    private ModelRenderer ArmL;
    private ModelRenderer Torso;
    private ModelRenderer Waist;
    private ModelRenderer Ankle;
    private ModelRenderer SpikeR;
    private ModelRenderer SpikeL;
    private ModelRenderer SpikeM;

    public ModelIceElemental() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-3F, -8F, -4F, 6, 8, 8);
        this.Head.setRotationPoint(0F, 1F, 2F);
        this.Head.setTextureSize(this.textureWidth, this.textureHeight);
        
        this.ArmR = new ModelRenderer(this, 48, 16);
        this.ArmR.addBox(-3F, -2F, -2F, 4, 12, 4);
        this.ArmR.setRotationPoint(-5F, 3F, 2F);
        this.ArmR.setTextureSize(this.textureWidth, this.textureHeight);
        
        this.ArmL = new ModelRenderer(this, 48, 16);
        this.ArmL.addBox(-1F, -2F, -2F, 4, 12, 4);
        this.ArmL.setRotationPoint(5F, 3F, 2F);
        this.ArmL.setTextureSize(this.textureWidth, this.textureHeight);
        this.ArmL.mirror = true;
        
        this.Torso = new ModelRenderer(this, 20, 16);
        this.Torso.addBox(-4F, 1F, -2F, 8, 10, 6);
        this.Torso.setRotationPoint(0F, 0F, 1F);
        this.Torso.setTextureSize(this.textureWidth, this.textureHeight);
        
        this.Waist = new ModelRenderer(this, 0, 16);
        this.Waist.addBox(-3F, 0F, -2F, 6, 10, 4);
        this.Waist.setRotationPoint(0F, 10F, 3F);
        this.Waist.setTextureSize(this.textureWidth, this.textureHeight);
        
        this.Ankle = new ModelRenderer(this, 28, 0);
        this.Ankle.addBox(-2F, 3F, -10F, 4, 8, 4);
        this.Ankle.setRotationPoint(0F, 9F, 1F);
        this.Ankle.setTextureSize(this.textureWidth, this.textureHeight);
        
        this.SpikeR = new ModelRenderer(this, 44, 0);
        this.SpikeR.addBox(0F, 0F, 0F, 1, 6, 1);
        this.SpikeR.setRotationPoint(1.5F, 4F, 4F);
        this.SpikeR.setTextureSize(this.textureWidth, this.textureHeight);
        
        this.SpikeL = new ModelRenderer(this, 44, 0);
        this.SpikeL.addBox(-1F, 0F, 0F, 1, 6, 1);
        this.SpikeL.setRotationPoint(-1.5F, 4F, 4F);
        this.SpikeL.setTextureSize(this.textureWidth, this.textureHeight);
        this.SpikeL.mirror = true;
        
        this.SpikeM = new ModelRenderer(this, 44, 7);
        this.SpikeM.addBox(-0.5F, 0F, 1F, 1, 6, 1);
        this.SpikeM.setRotationPoint(0F, 4F, 3F);
        this.SpikeM.setTextureSize(this.textureWidth, this.textureHeight);
    }

    @Override
    public void setRotationAngles(float partialTime, float amplitude, float par3, float viewY, float viewX, float par6, Entity par7Entity) {
        this.Head.rotateAngleX = viewX / (180F / (float) Math.PI);
        this.Head.rotateAngleY = viewY / (180F / (float) Math.PI);

        this.ArmR.rotateAngleX = (float) -Math.PI / 10.0F;
        this.ArmL.rotateAngleX = (float) -Math.PI / 10.0F;
        this.Waist.rotateAngleX = (float) -Math.PI / 5.0F;
        this.Ankle.rotateAngleX = (float) Math.PI / 4.0F;
        this.SpikeR.rotateAngleX = (float) Math.PI * 13.0F / 20.0F;
        this.SpikeR.rotateAngleY = (float) Math.PI * 2.0F / 15.0F;
        this.SpikeL.rotateAngleX = (float) Math.PI * 13.0F / 20.0F;
        this.SpikeL.rotateAngleY = (float) -Math.PI * 2.0F / 15.0F;
        this.SpikeM.rotateAngleX = (float) Math.PI * 19.0F / 90.0F;

        this.Waist.rotateAngleY = MathHelper.cos(partialTime * 0.6662F) * 0.75F * amplitude;
        this.Ankle.rotateAngleY = MathHelper.cos(partialTime * 0.6662F + (float) Math.PI / 12.0F) * 0.75F * amplitude;
    }

}
