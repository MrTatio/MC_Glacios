package roundaround.mcmods.glacios.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityArcticFox extends EntityAnimal {

    public EntityArcticFox(World par1World) {
        super(par1World);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

}
