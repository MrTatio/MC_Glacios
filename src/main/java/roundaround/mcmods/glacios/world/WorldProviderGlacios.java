package roundaround.mcmods.glacios.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IRenderHandler;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosConfig;
import roundaround.mcmods.glacios.world.biome.WorldChunkManagerGlacios;
import roundaround.mcmods.glacios.world.gen.ChunkProviderGlacios;
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
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerGlacios(this.worldObj);
        this.dimensionId = GlaciosConfig.dimID;
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderGlacios(worldObj, worldObj.getSeed(), true);
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
        double angle = (this.worldObj.getWorldTime() % 24000L) / 24000D;
        
        return (float)(((Math.sin(angle * 2 * Math.PI) + 1) * 96D) + 96D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
        float angle = this.worldObj.getCelestialAngle(partialTicks);
        float brightness = MathHelper.cos(angle * (float) Math.PI * 2.0F) * 2.0F + 0.5F;

        if (brightness < 0.0F) {
            brightness = 0.0F;
        }

        if (brightness > 1.0F) {
            brightness = 1.0F;
        }

        int posX = MathHelper.floor_double(cameraEntity.posX);
        int posY = MathHelper.floor_double(cameraEntity.posY);
        int posZ = MathHelper.floor_double(cameraEntity.posZ);
        
        int colorMask = ForgeHooksClient.getSkyBlendColour(this.worldObj, posX, posY, posZ);
        float red = (colorMask >> 16 & 255) / 255.0F;
        float green = (colorMask >> 8 & 255) / 255.0F;
        float blue = (colorMask & 255) / 255.0F;
        // 0.545, 0.467, 0.820
        
        red *= brightness;
        green *= brightness;
        blue *= brightness;
        float rainStrength = this.worldObj.getRainStrength(partialTicks);

        if (rainStrength > 0.0F) {
            float f8 = (red * 0.3F + green * 0.59F + blue * 0.11F) * 0.6F;
            float rainModifier = 1.0F - rainStrength * 0.75F;
            red = red * rainModifier + f8 * (1.0F - rainModifier);
            green = green * rainModifier + f8 * (1.0F - rainModifier);
            blue = blue * rainModifier + f8 * (1.0F - rainModifier);
        }

        return this.worldObj.getWorldVec3Pool().getVecFromPool(red, green, blue);
    }

}
