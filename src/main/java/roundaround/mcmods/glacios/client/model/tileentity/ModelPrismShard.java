package roundaround.mcmods.glacios.client.model.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPrismShard extends ModelBase {

    private ModelRenderer Shaft1;
    private ModelRenderer Shaft2;
    private ModelRenderer Shaft3;
    private ModelRenderer Shaft4;
    private ModelRenderer Shaft5;
    private ModelRenderer Base1;
    private ModelRenderer Base2;
    private ModelRenderer Base3;
    private ModelRenderer Base4;
    private ModelRenderer Base5;

    public ModelPrismShard() {
        textureWidth = 64;
        textureHeight = 32;

        Shaft1 = new ModelRenderer(this, 0, 0);
        Shaft1.addBox(-2F, -13F, -3F, 4, 12, 4);
        Shaft1.setRotationPoint(0F, 24F, 0F);
        Shaft1.setTextureSize(64, 32);
        setRotation(Shaft1, 0.2617994F, 0F, 0F);
        
        Shaft2 = new ModelRenderer(this, 16, 0);
        Shaft2.addBox(0F, -9F, -1F, 3, 8, 3);
        Shaft2.setRotationPoint(0F, 24F, 0F);
        Shaft2.setTextureSize(64, 32);
        setRotation(Shaft2, 0F, -0.7853982F, 0.2617994F);
        
        Shaft3 = new ModelRenderer(this, 16, 0);
        Shaft3.addBox(-3F, -12F, -1F, 3, 10, 3);
        Shaft3.setRotationPoint(0F, 24F, 0F);
        Shaft3.setTextureSize(64, 32);
        setRotation(Shaft3, 0F, 0.5235988F, -0.5235988F);
        
        Shaft4 = new ModelRenderer(this, 28, 0);
        Shaft4.addBox(-2F, -7F, 0F, 2, 5, 2);
        Shaft4.setRotationPoint(0F, 24F, 0F);
        Shaft4.setTextureSize(64, 32);
        setRotation(Shaft4, 0F, -0.5235988F, -0.7853982F);
        
        Shaft5 = new ModelRenderer(this, 28, 0);
        Shaft5.addBox(-1F, -7F, -1F, 2, 5, 2);
        Shaft5.setRotationPoint(0F, 24F, 0F);
        Shaft5.setTextureSize(64, 32);
        setRotation(Shaft5, 0F, 0.1396263F, 1.047198F);
        
        Base1 = new ModelRenderer(this, 0, 0);
        Base1.addBox(-2F, -2F, -3.2F, 4, 2, 4);
        Base1.setRotationPoint(0F, 24F, 0F);
        Base1.setTextureSize(64, 32);
        
        Base2 = new ModelRenderer(this, 16, 0);
        Base2.addBox(0.2F, -2F, -1F, 3, 2, 3);
        Base2.setRotationPoint(0F, 24F, 0F);
        Base2.setTextureSize(64, 32);
        setRotation(Base2, 0F, -0.7853982F, 0F);
        
        Base3 = new ModelRenderer(this, 16, 0);
        Base3.addBox(-3.6F, -2F, -1F, 3, 2, 3);
        Base3.setRotationPoint(0F, 24F, 0F);
        Base3.setTextureSize(64, 32);
        setRotation(Base3, 0F, 0.5235988F, 0F);
        
        Base4 = new ModelRenderer(this, 28, 0);
        Base4.addBox(-3F, -1F, 0F, 2, 1, 2);
        Base4.setRotationPoint(0F, 24F, 0F);
        Base4.setTextureSize(64, 32);
        setRotation(Base4, 0F, -0.5235988F, 0F);
        
        Base5 = new ModelRenderer(this, 28, 0);
        Base5.addBox(0.3F, -1F, -1F, 2, 1, 2);
        Base5.setRotationPoint(0F, 24F, 0F);
        Base5.setTextureSize(64, 32);
        setRotation(Base5, 0F, 0.1396263F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Shaft1.renderWithRotation(f5);
        Shaft2.renderWithRotation(f5);
        Shaft3.renderWithRotation(f5);
        Shaft4.renderWithRotation(f5);
        Shaft5.renderWithRotation(f5);
        Base1.renderWithRotation(f5);
        Base2.renderWithRotation(f5);
        Base3.renderWithRotation(f5);
        Base4.renderWithRotation(f5);
        Base5.renderWithRotation(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
