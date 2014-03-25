package roundaround.mcmods.glacios.client.renderer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModelRendererTechne extends ModelRenderer {

    public float textureWidth;
    public float textureHeight;
    private int textureOffsetX;
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled;
    private int displayList;
    public boolean mirror;
    public boolean showModel;
    public boolean isHidden;
    public List cubeList;
    public List childModels;
    public final String boxName;
    private ModelBase modelBase;
    public float offsetX;
    public float offsetY;
    public float offsetZ;

    public ModelRendererTechne(ModelBase modelBase, String boxName) {
        super(modelBase, boxName);
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = new ArrayList();
        this.modelBase = modelBase;
        modelBase.boxList.add(this);
        this.boxName = boxName;
        this.setTextureSize(modelBase.textureWidth, modelBase.textureHeight);
    }

    public ModelRendererTechne(ModelBase modelBase) {
        this(modelBase, null);
    }

    public ModelRendererTechne(ModelBase modelBase, int textureOffsetX, int textureOffsetY) {
        this(modelBase);
        this.setTextureOffset(textureOffsetX, textureOffsetY);
    }

    @Override
    public void addChild(ModelRenderer modelRenderer) {
        if (this.childModels == null) {
            this.childModels = new ArrayList();
        }

        this.childModels.add(modelRenderer);
    }

    @Override
    public ModelRenderer setTextureOffset(int textureOffsetX, int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        return this;
    }

    @Override
    public ModelRenderer addBox(String name, float offsetX, float offsetY, float offsetZ, int sizeX, int sizeY, int sizeZ) {
        name = this.boxName + "." + name;
        TextureOffset textureoffset = this.modelBase.getTextureOffset(name);
        this.setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
        this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, 0.0F)).func_78244_a(name));
        return this;
    }

    @Override
    public ModelRenderer addBox(float offsetX, float offsetY, float offsetZ, int sizeX, int sizeY, int sizeZ) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, 0.0F));
        return this;
    }

    @Override
    public void addBox(float offsetX, float offsetY, float offsetZ, int sizeX, int sizeY, int sizeZ, float par7) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, par7));
    }

    @Override
    public void setRotationPoint(float rotationPointX, float rotationPointY, float rotationPointZ) {
        this.rotationPointX = rotationPointX;
        this.rotationPointY = rotationPointY;
        this.rotationPointZ = rotationPointZ;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(float partialTicks) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(partialTicks);
                }

                GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
                int i;

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                        GL11.glCallList(this.displayList);

                        if (this.childModels != null) {
                            for (i = 0; i < this.childModels.size(); ++i) {
                                ((ModelRenderer) this.childModels.get(i)).render(partialTicks);
                            }
                        }
                    } else {
                        GL11.glTranslatef(this.rotationPointX * partialTicks, this.rotationPointY * partialTicks, this.rotationPointZ * partialTicks);
                        GL11.glCallList(this.displayList);

                        if (this.childModels != null) {
                            for (i = 0; i < this.childModels.size(); ++i) {
                                ((ModelRenderer) this.childModels.get(i)).render(partialTicks);
                            }
                        }

                        GL11.glTranslatef(-this.rotationPointX * partialTicks, -this.rotationPointY * partialTicks, -this.rotationPointZ * partialTicks);
                    }
                } else {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(this.rotationPointX * partialTicks, this.rotationPointY * partialTicks, this.rotationPointZ * partialTicks);

                    if (this.rotateAngleY != 0.0F) {
                        GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F) {
                        GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                    
                    if (this.rotateAngleZ != 0.0F) {
                        GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    GL11.glCallList(this.displayList);

                    if (this.childModels != null) {
                        for (i = 0; i < this.childModels.size(); ++i) {
                            ((ModelRenderer) this.childModels.get(i)).render(partialTicks);
                        }
                    }

                    GL11.glPopMatrix();
                }

                GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderWithRotation(float partialTicks) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(partialTicks);
                }

                GL11.glPushMatrix();
                GL11.glTranslatef(this.rotationPointX * partialTicks, this.rotationPointY * partialTicks, this.rotationPointZ * partialTicks);
                
                if (this.rotateAngleY != 0.0F) {
                    GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                }

                if (this.rotateAngleX != 0.0F) {
                    GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                }

                if (this.rotateAngleZ != 0.0F) {
                    GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                }

                GL11.glCallList(this.displayList);
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void postRender(float partialTicks) {
        if (!this.isHidden) {
            if (this.showModel) {
                if (!this.compiled) {
                    this.compileDisplayList(partialTicks);
                }

                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
                        GL11.glTranslatef(this.rotationPointX * partialTicks, this.rotationPointY * partialTicks, this.rotationPointZ * partialTicks);
                    }
                } else {
                    GL11.glTranslatef(this.rotationPointX * partialTicks, this.rotationPointY * partialTicks, this.rotationPointZ * partialTicks);

                    if (this.rotateAngleY != 0.0F) {
                        GL11.glRotatef(this.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleZ != 0.0F) {
                        GL11.glRotatef(this.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleX != 0.0F) {
                        GL11.glRotatef(this.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void compileDisplayList(float partialTicks) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.displayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.instance;

        for (int i = 0; i < this.cubeList.size(); ++i) {
            ((ModelBox) this.cubeList.get(i)).render(tessellator, partialTicks);
        }

        GL11.glEndList();
        this.compiled = true;
    }

    @Override
    public ModelRenderer setTextureSize(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        return this;
    }

}
