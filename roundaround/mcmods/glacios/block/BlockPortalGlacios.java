package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockPortal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.world.TeleporterGlacios;

public class BlockPortalGlacios extends BlockPortal {

    public BlockPortalGlacios() {
        this.func_149647_a(CreativeTabs.tabBlock);
    }

    @Override
    public void func_149670_a(World worldObj, int x, int y, int z, Entity entity) {
        if ((entity.ridingEntity == null) && (entity.riddenByEntity == null) && ((entity instanceof EntityPlayerMP))) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            MinecraftServer mServer = MinecraftServer.getServer();
            if (player.timeUntilPortal > 0) {
                player.timeUntilPortal = 10;
            } else if (player.dimension != Glacios.DIMID) {
                player.timeUntilPortal = 10;
                player.mcServer.getConfigurationManager().transferPlayerToDimension(player, Glacios.DIMID,
                        new TeleporterGlacios(mServer.worldServerForDimension(Glacios.DIMID)));
            } else {
                player.timeUntilPortal = 10;
                player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0,
                        new TeleporterGlacios(mServer.worldServerForDimension(1)));
            }
        }
    }
}
