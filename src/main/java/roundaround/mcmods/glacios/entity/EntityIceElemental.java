package roundaround.mcmods.glacios.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityIceElemental extends EntityAnimal {

    public EntityIceElemental(World world) {
        super(world);
        this.setSize(0.6f, 1.2f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
    }

}
