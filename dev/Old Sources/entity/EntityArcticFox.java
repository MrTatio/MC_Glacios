package glacios.entity;

import glacios.block.GlaciosBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityArcticFox extends EntityAnimal {

    EntityArcticFox parent;

    public EntityArcticFox(World world) {
        this(world, null);
    }

    public EntityArcticFox(World world, EntityArcticFox parent0) {
        super(world);
        parent = parent0;
        texture = "/mods/Glacios/mob/arcticFox.png";
        setSize(0.4F, 0.4F);
        moveSpeed = 0.3F;
        getNavigator().setAvoidsWater(true);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, moveSpeed, true));
        tasks.addTask(6, new EntityAIMate(this, moveSpeed));
        tasks.addTask(7, new EntityAIWander(this, moveSpeed));
        tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(9, new EntityAILookIdle(this));
        // targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        // targetTasks.addTask(4, new EntityAITarget(this, EntitySheep.class, 16.0F, 200, false));
    }

    /*
     * Returns true if the newer Entity AI code should be run
     */
    @Override
    public boolean isAIEnabled() {
        return true;
    }

    /*
     * Get number of ticks, at least during which the living entity will be silent.
     */
    @Override
    public int getTalkInterval() {
        return 160;
    }

    /*
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return "mob.arcticfox.living";
    }

    /*
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.arcticfox.hurt";
    }

    /*
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "mob.arcticfox.death";
    }

    /*
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityArcticFox spawnBabyAnimal(EntityAgeable entityageable) {
        return new EntityArcticFox(worldObj, this);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return spawnBabyAnimal(entityageable);
    }

    @Override
    public int getMaxHealth() {
        return 6;
    }

    /*
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return (worldObj.getBlockId(i, j - 1, k) == Block.blockSnow.blockID || worldObj.getBlockId(i, j, k) == Block.snow.blockID || worldObj.getBlockId(i, j - 1, k) == GlaciosBlocks.glacite.blockID)
                && /* worldObj.getFullBlockLightValue(i, j, k) > 7 && */worldObj.checkIfAABBIsClear(boundingBox)
                && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty()
                && !worldObj.isAnyLiquid(boundingBox) && getBlockPathWeight(i, j, k) >= 0.0F;
    }

}
