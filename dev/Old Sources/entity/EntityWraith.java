package glacios.entity;

import glacios.core.Glacios;
import glacios.item.GlaciosItems;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityWraith extends EntityMob {

    private PathEntity entitypath;

    public EntityWraith(World par1World) {
        super(par1World);
        texture = "/mods/Glacios/mob/wraith.png";
        moveSpeed = 0.8F;
        isCollidedVertically = false;
        isImmuneToFire = false;
        setSize(1.5F, 2.5F);
    }

    @Override
    protected int func_96121_ay() {
        return 40;
    }

    @Override
    public int getMaxHealth() {
        return 20;
    }

    /*
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (worldObj.isDaytime()) {
            float f = getBrightness(1.0F);

            if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
                    && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
                poofAway();
        }
        for (int i = 0; i < 2; ++i) {
            double x = posX + (rand.nextDouble() - 0.5D) * width;
            double y = posY + rand.nextDouble() * height - 0.25D;
            double z = posZ + (rand.nextDouble() - 0.5D) * width;
            double motX = (rand.nextDouble() - 0.5D) * 2.0D;
            double motY = -rand.nextDouble();
            double motZ = (rand.nextDouble() - 0.5D) * 2.0D;
            EntityFX entityFX = new EntityPortalFX(worldObj, x, y, z, motX, motY, motZ);
            float f = rand.nextFloat() * 0.6F + 0.4F;
            entityFX.setRBGColorF(1.0F * f, 1.0F * f, 1.0F * f);
            Glacios.mc.effectRenderer.addEffect(entityFX);
        }

        super.onLivingUpdate();
    }

    public void poofAway() {
        worldObj.spawnParticle("largeexplode", posX, posY, posZ, 1.0D, 0.0D, 0.0D);
        for (int i = 0; i < 4; ++i) {
            double x = posX + (rand.nextDouble() - 0.5D) * width;
            double y = posY + rand.nextDouble() * height - 0.25D;
            double z = posZ + (rand.nextDouble() - 0.5D) * width;
            double motX = (rand.nextDouble() - 0.5D) * 2.0D;
            double motY = -rand.nextDouble();
            double motZ = (rand.nextDouble() - 0.5D) * 2.0D;
            EntityFX entityFX = new EntityPortalFX(worldObj, x, y, z, motX, motY, motZ);
            float f = rand.nextFloat() * 0.6F + 0.4F;
            entityFX.setRBGColorF(1.0F * f, 1.0F * f, 1.0F * f);
            Glacios.mc.effectRenderer.addEffect(entityFX);
        }
        setDead();
        despawnEntity();
    }

    @Override
    public void moveEntityWithHeading(float f, float f1) {
        if (handleWaterMovement()) {
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.8;
            motionY *= 0.8;
            motionZ *= 0.8;
        } else if (handleLavaMovement()) {
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5;
            motionY *= 0.5;
            motionZ *= 0.5;
        } else {
            float f2 = 0.91F;
            if (onGround) {
                f2 = 0.5460001F;
                int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (i > 0)
                    f2 = Block.blocksList[i].slipperiness * 0.91F;
            }
            float f3 = 0.162771F / (f2 * f2 * f2);
            moveFlying(f, f1, onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (onGround) {
                f2 = 0.5460001F;
                int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (j > 0)
                    f2 = Block.blocksList[j].slipperiness * 0.91F;
            }
            moveEntity(motionX, motionY, motionZ);
            motionX *= f2;
            motionY *= f2;
            motionZ *= f2;
            if (isCollidedHorizontally)
                motionY = 0.2D;
            if (entityToAttack != null && entityToAttack.posY < posY && rand.nextInt(30) == 0)
                motionY = -0.25D;
        }

        prevLimbYaw = limbYaw;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4.0F;
        if (f4 > 1.0F)
            f4 = 1.0F;

        limbYaw += (f4 - limbYaw) * 0.4F;
        limbSwing += limbYaw;
    }

    @Override
    protected void updateEntityActionState() {
        hasAttacked = false;
        float f = 16.0F;
        if (entityToAttack == null) {
            entityToAttack = findPlayerToAttack();
            if (entityToAttack != null)
                entitypath = worldObj.getPathEntityToEntity(this, entityToAttack, f, true, false, false, true);

        } else if (!entityToAttack.isEntityAlive())
            entityToAttack = null;
        else {
            float f1 = entityToAttack.getDistanceToEntity(this);
            if (canEntityBeSeen(entityToAttack))
                attackEntity(entityToAttack, f1);
        }

        if (!hasAttacked && entityToAttack != null && (entitypath == null || rand.nextInt(10) == 0))
            entitypath = worldObj.getPathEntityToEntity(this, entityToAttack, f, true, false, false, true);
        else if (entitypath == null && rand.nextInt(80) == 0 || rand.nextInt(80) == 0) {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999.0F;
            for (int i1 = 0; i1 < 10; i1++) {
                int j1 = MathHelper.floor_double(posX + rand.nextInt(13) - 6.0D);
                int k1 = MathHelper.floor_double(posY + rand.nextInt(7) - 3.0D);
                int l1 = MathHelper.floor_double(posZ + rand.nextInt(13) - 6.0D);
                float f3 = getBlockPathWeight(j1, k1, l1);
                if (f3 > f2) {
                    f2 = f3;
                    j = j1;
                    k = k1;
                    l = l1;
                    flag = true;
                }
            }

            if (flag)
                entitypath = worldObj.getEntityPathToXYZ(this, j, k, l, 10.0F, true, false, false, true);
        }
        int i = MathHelper.floor_double(boundingBox.minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = handleLavaMovement();
        rotationPitch = 0.0F;
        if (entitypath == null || rand.nextInt(100) == 0) {
            super.updateEntityActionState();
            entitypath = null;
            return;
        }

        Vec3 vec3d = entitypath.getPosition(this);

        for (double d = width * 2.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;) {
            entitypath.incrementPathIndex();
            if (entitypath.isFinished()) {
                vec3d = null;
                entitypath = null;
            } else
                vec3d = entitypath.getPosition(this);
        }

        isJumping = false;
        if (vec3d != null) {
            double d1 = vec3d.xCoord - posX;
            double d2 = vec3d.zCoord - posZ;
            double d3 = vec3d.yCoord - i;
            float f4 = (float) (Math.atan2(d2, d1) * 180.0D / 3.141592741012573D) - 90.0F;
            float f5 = f4 - rotationYaw;
            moveForward = moveSpeed;
            while (f5 < -180.0F)
                f5 += 360.0F;

            while (f5 >= 180.0F)
                f5 -= 360.0F;

            if (f5 > 30.0F)
                f5 = 30.0F;
            if (f5 < -30.0F)
                f5 = -30.0F;
            rotationYaw += f5;
            if (hasAttacked && entityToAttack != null) {
                double d4 = entityToAttack.posX - posX;
                double d5 = entityToAttack.posZ - posZ;
                float f6 = rotationYaw;
                rotationYaw = (float) (Math.atan2(d5, d4) * 180.0D / 3.141592741012573D) - 90.0F;
                float f7 = (f6 - rotationYaw + 90.0F) * 3.141593F / 180.0F;
                moveStrafing = -MathHelper.sin(f7) * moveForward * 1.0F;
                moveForward = MathHelper.cos(f7) * moveForward * 1.0F;
            }
            if (d3 > 0.0D)
                isJumping = true;
        }
        if (entityToAttack != null)
            faceEntity(entityToAttack, 30.0F, 30.0F);
        if (isCollidedHorizontally)
            isJumping = true;
        if (rand.nextFloat() < 0.8F && (flag1 || flag2))
            isJumping = true;
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 20.0D);
        if (entityplayer != null && canEntityBeSeen(entityplayer))
            return entityplayer;

        return null;
    }

    @Override
    protected void fall(float f) {
        // Nothing happens.
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean flag = super.attackEntityAsMob(par1Entity);

        // TODO: Possibly freeze?

        return flag;
    }

    /*
     * Returns the sound this mob makes while it's alive.
     */
    @Override
    protected String getLivingSound() {
        return "mob.wraith.living";
    }

    /*
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound() {
        return "mob.wraith.hurt";
    }

    /*
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound() {
        return "mob.wraith.death";
    }

    /*
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4) {
        // Doesn't make sound when moving
    }

    /*
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected int getDropItemId() {
        return GlaciosItems.glaciteCrystal.itemID;
    }

    /*
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    /*
     * This method gets called when the entity kills another one.
     */
    @Override
    public void onKillEntity(EntityLiving par1EntityLiving) {
        super.onKillEntity(par1EntityLiving);

        // TODO Consume soul animation/sound.
        setEntityHealth(getMaxHealth());
    }
}