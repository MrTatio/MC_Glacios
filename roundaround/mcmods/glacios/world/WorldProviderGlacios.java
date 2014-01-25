package roundaround.mcmods.glacios.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IRenderHandler;
import roundaround.mcmods.glacios.Glacios;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderGlacios extends WorldProvider {

    @SideOnly(Side.CLIENT)
    private IRenderHandler skyRenderer;

    public static WorldProviderGlacios instance;

    public WorldProviderGlacios() {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT) {
            this.skyRenderer = Glacios.skyRenderer;
        }
    }

    @Override
    public String getDimensionName() {
        return "Galcios";
    }

    @Override
    public String getWelcomeMessage() {
        return "Entering the Glacios";
    }

    @Override
    public String getDepartMessage() {
        return "Leaving the Glacios";
    }
    
    @Override
    public double getMovementFactor() {
        return 0.5;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IRenderHandler getSkyRenderer() {
        return this.skyRenderer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setSkyRenderer(IRenderHandler skyRenderer) {
        this.skyRenderer = skyRenderer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return super.getCloudHeight();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
        float f1 = this.worldObj.getCelestialAngle(partialTicks);
        float f2 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        int i = MathHelper.floor_double(cameraEntity.posX);
        int j = MathHelper.floor_double(cameraEntity.posY);
        int k = MathHelper.floor_double(cameraEntity.posZ);
        int l = ForgeHooksClient.getSkyBlendColour(this.worldObj, i, j, k);
        float f4 = (l >> 16 & 255) / 255.0F;
        float f5 = (l >> 8 & 255) / 255.0F;
        float f6 = (l & 255) / 255.0F;
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float f7 = this.worldObj.getRainStrength(partialTicks);
        float f8;
        float f9;

        if (f7 > 0.0F)
        {
            // 0.545, 0.467, 0.820
            f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            f9 = 1.0F - f7 * 0.75F;
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
            f6 = f6 * f9 + f8 * (1.0F - f9);
        }

        return this.worldObj.getWorldVec3Pool().getVecFromPool(f4, f5, f6);
    }

}
