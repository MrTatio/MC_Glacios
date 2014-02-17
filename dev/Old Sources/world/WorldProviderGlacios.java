package glacios.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import glacios.client.renderer.CloudRendererGlacios;
import glacios.client.renderer.SkyRendererGlacios;
import glacios.core.Glacios;
import glacios.world.biome.WorldChunkManagerGlacios;
import glacios.world.gen.ChunkProviderGlacios;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderGlacios extends WorldProvider {

	/*
	 * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
	 */
	@Override
	public String getDimensionName() {
		return "Glacios";
	}

//	/*
//	 * A message to display to the user when they transfer to this dimension.
//	 */
//	@Override
//	public String getWelcomeMessage() {
//		return "Entering Glacios";
//	}
//
//	/*
//	 * A message to display to the user when they transfer out of this dimension.
//	 */
//	@Override
//	public String getDepartMessage() {
//		return "Leaving Glacios";
//	}

	/*
	 * Creates a new world chunk manager for WorldProvider
	 */
	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new WorldChunkManagerGlacios(worldObj);
		dimensionId = Glacios.dimID;

		isHellWorld = false;
		hasNoSky = false;

		setCloudRenderer(new CloudRendererGlacios());
		setSkyRenderer(new SkyRendererGlacios());
	}

    /*
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		int j = (int) (worldTime % 24000L);
		float f1 = (j + partialTicks) / 24000.0F - 0.2083F;

		if (f1 < 0.0F) {
			++f1;
		}
		if (f1 > 1.0F) {
			--f1;
		}

        return (1.0F / 4.5F) * ((float)Math.pow((2.0D * f1) - 1.0D, 3) + 1.0F + 2.5F * f1);
	}

	/*
	 * Returns the chunk provider back for the world provider
	 */
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderGlacios(worldObj, worldObj.getSeed());
	}

	/*
	 * True if the player can respawn in this dimension.
	 */
	@Override
	public boolean canRespawnHere() {
		return false;
	}

	/*
	 * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
	 */
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	/*
	 * Will check if the x, z position specified is alright to be set as the map spawn point
	 */
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return false;
	}

	/*
	 * Gets the hard-coded portal location to use when entering this dimension.
	 */
	@Override
	public ChunkCoordinates getEntrancePortalLocation() {
		return new ChunkCoordinates(-30, 100, 30);
	}

//	@Override
//	public int getAverageGroundLevel() {
//		return Glacios.groundHeight;
//	}

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

	/*
	 * Creates the light to brightness table
	 */
	@Override
	protected void generateLightBrightnessTable() {
		float f = 0.8F;
		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - i / 15.0F;
			lightBrightnessTable[i] = ((1.0F - f1) / (f1 * 3.0F + 1.0F)) * f;
		}
	}

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

	/*
	 * Return Vec3D with biome specific fog color
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float celestialAngle, float partialTicks) {
		float intensity = (1.0F - MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F)) * 0.2F + 0.4F;
		
		if (intensity < 0.4F) {
			intensity = 0.4F;
		}
		if (intensity > 0.8F) {
			intensity = 0.8F;
		}

		float cR = 0.7529412F;
		float cG = 0.84705883F;
		float cB = 1.0F;
		return this.worldObj.getWorldVec3Pool().getVecFromPool((double) cR * intensity, (double) cG * intensity, (double) cB * intensity);
	}

	/*
	 * Returns true if the given X,Z coordinate should show environmental fog.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return true;
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

}
