package roundaround.mcmods.glacios.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityArcticFox extends EntityAnimal {

    public EntityArcticFox(World world) {
        super(world);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

}
