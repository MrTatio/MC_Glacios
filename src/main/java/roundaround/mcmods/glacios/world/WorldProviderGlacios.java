package roundaround.mcmods.glacios.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import roundaround.mcmods.glacios.GlaciosConfig;
import roundaround.mcmods.glacios.client.renderer.CloudRendererGlacios;
import roundaround.mcmods.glacios.client.renderer.SkyRendererGlacios;
import roundaround.mcmods.glacios.world.biome.WorldChunkManagerGlacios;
import roundaround.mcmods.glacios.world.gen.ChunkProviderGlacios;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderGlacios extends WorldProvider {

    @SideOnly(Side.CLIENT)
    private IRenderHandler skyRenderer;

    @SideOnly(Side.CLIENT)
    private IRenderHandler cloudRenderer;

    public static WorldProviderGlacios instance;

    public WorldProviderGlacios() {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT) {
            this.skyRenderer = new SkyRendererGlacios();
            this.cloudRenderer = new CloudRendererGlacios();
        }

        this.isHellWorld = false;
        this.hasNoSky = false;
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
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
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
    public IRenderHandler getCloudRenderer() {
        return this.cloudRenderer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setCloudRenderer(IRenderHandler cloudRenderer) {
        this.cloudRenderer = cloudRenderer;
    }

    // To be reviewed.
//    @Override
//    public float calculateCelestialAngle(long worldTime, float partialTicks) {
//        int timeOfDay = (int) (worldTime % 24000L);
//        float angleMagnitude = (timeOfDay + partialTicks) / 24000.0F - 0.2083F;
//
//        if (angleMagnitude < 0.0F) {
//            ++angleMagnitude;
//        }
//        if (angleMagnitude > 1.0F) {
//            --angleMagnitude;
//        }
//
//        return (1.0F / 4.5F) * ((float) Math.pow((2.0D * angleMagnitude) - 1.0D, 3) + 1.0F + 2.5F * angleMagnitude);
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        double angle = (this.worldObj.getWorldTime() % 24000L) / 24000D;

        return (float) (((Math.sin(angle * 2 * Math.PI) + 1) * 48D) + 96D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
        float intensity = MathHelper.cos(worldObj.getCelestialAngle(partialTicks) * (float) Math.PI * 2.0F) * 0.3F + 0.4F;

        if (intensity < 0.1F) {
            intensity = 0.1F;
        }
        if (intensity > 0.7F) {
            intensity = 0.7F;
        }
        return worldObj.getWorldVec3Pool().getVecFromPool(0.5 * intensity, 0.4 * intensity, 0.9 * intensity);
    }

    @SideOnly(Side.CLIENT)
    public float getCloudAlpha(float partialTicks) {
        float intensity = (1.0F - MathHelper.cos(worldObj.getCelestialAngle(partialTicks) * (float) Math.PI * 2.0F)) * 0.1F + 0.5F;

        if (intensity < 0.5F) {
            intensity = 0.5F;
        }
        if (intensity > 0.7F) {
            intensity = 0.7F;
        }

        return intensity;
    }

//    @Override
//    protected void generateLightBrightnessTable() {
//        float f = 0.8F;
//        for (int i = 0; i <= 15; ++i) {
//            float f1 = 1.0F - i / 15.0F;
//            lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F)) * f;
//        }
//    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float partialTicks) {
        float intensity = (1.0F - MathHelper.cos(worldObj.getCelestialAngle(partialTicks) * (float) Math.PI * 2.0F)) * 0.2F + 0.6F;

        if (intensity < 0.6F) {
            intensity = 0.6F;
        }
        if (intensity > 1.0F) {
            intensity = 1.0F;
        }

        return intensity * intensity * 0.8F;
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public Vec3 getFogColor(float celestialAngle, float partialTicks) {
//        float intensity = (1.0F - MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F)) * 0.2F + 0.4F;
//
//        if (intensity < 0.4F) {
//            intensity = 0.4F;
//        }
//        if (intensity > 0.8F) {
//            intensity = 0.8F;
//        }
//
//        float cR = 0.7529412F;
//        float cG = 0.84705883F;
//        float cB = 1.0F;
//        return this.worldObj.getWorldVec3Pool().getVecFromPool((double) cR * intensity, (double) cG * intensity, (double) cB * intensity);
//    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean doesXZShowFog(int x, int z) {
//        return true;
//    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

}
