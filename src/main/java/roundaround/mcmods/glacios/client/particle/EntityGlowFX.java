package roundaround.mcmods.glacios.client.particle;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

public class EntityGlowFX extends EntityFX {
    
    private static Minecraft mc = FMLClientHandler.instance().getClient();

    class LitBLock {
        public int x, y, z, light;

        LitBLock(int x0, int y0, int z0) {
            x = x0;
            y = y0;
            z = z0;
        }

        LitBLock(int x0, int y0, int z0, int light0) {
            x = x0;
            y = y0;
            z = z0;
            light = light0;
        }
    }

    public static Set<LitBLock> lights = new HashSet<LitBLock>();
    public static Set<LitBLock> resets = new HashSet<LitBLock>();

    public EntityGlowFX(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
        motionX = 0;
        motionZ = 0;
        motionY = -0.01D;
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        setParticleTextureIndex((int) (Math.random() * 8.0D));
    }

    /*
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) {
            setDead();
            return;
        }

        moveEntity(motionX, motionY, motionZ);
        lights.add(new LitBLock((int) posX, (int) posY, (int) posZ, 4 - (int) (particleAge * 4.0D / particleMaxAge)));
    }

    /*
     * Will get destroyed next tick.
     */
    @Override
    public void setDead() {
        super.setDead();
        resets.add(new LitBLock((int) posX, (int) posY, (int) posZ));
    }

    public static void clearLights() {
        if (resets.size() == 0) {
            return;
        }
        for (LitBLock block : resets) {
            mc.theWorld.markBlocksDirtyVertical(block.x, block.z, block.y - 2, block.y + 2);
        }
    }

    public static void updateLights() {
        if (lights.size() == 0) {
            return;
        }
        for (LitBLock block : lights) {
            int light = (int) (15.0F * mc.theWorld.getLightBrightness(block.x, block.y, block.z));
            light += light <= 15 - block.light ? block.light : 15 - light;
            mc.theWorld.setLightValue(EnumSkyBlock.Sky, block.x, block.y, block.z, light);
            mc.theWorld.updateLightByType(EnumSkyBlock.Sky, block.x - 1, block.y, block.z);
            mc.theWorld.updateLightByType(EnumSkyBlock.Sky, block.x + 1, block.y, block.z);
            mc.theWorld.updateLightByType(EnumSkyBlock.Sky, block.x, block.y - 1, block.z);
            mc.theWorld.updateLightByType(EnumSkyBlock.Sky, block.x, block.y + 1, block.z);
            mc.theWorld.updateLightByType(EnumSkyBlock.Sky, block.x, block.y, block.z - 1);
            mc.theWorld.updateLightByType(EnumSkyBlock.Sky, block.x, block.y, block.z + 1);
        }
    }

}
