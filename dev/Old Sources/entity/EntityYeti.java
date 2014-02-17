package glacios.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityYeti extends EntityMob {

    public EntityYeti(World par1World) {
        super(par1World);
    }

    @Override
    public int getMaxHealth() {
        return 30;
    }

    /*
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (worldObj.isDaytime()) {
            float f = getBrightness(1.0F);

            //if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
            //        && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
        }

        super.onLivingUpdate();
    }

}
