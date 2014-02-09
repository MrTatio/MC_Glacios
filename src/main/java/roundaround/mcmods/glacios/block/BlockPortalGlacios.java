package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockPortal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import roundaround.mcmods.glacios.Glacios;
import roundaround.mcmods.glacios.GlaciosConfig;
import roundaround.mcmods.glacios.world.TeleporterGlacios;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPortalGlacios extends BlockPortal {

    public BlockPortalGlacios() {
        super();
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldObj, int x, int y, int z, Entity entity) {
        if ((entity.ridingEntity == null) && (entity.riddenByEntity == null) && ((entity instanceof EntityPlayerMP))) {
            EntityPlayerMP player = (EntityPlayerMP) entity;
            MinecraftServer mServer = MinecraftServer.getServer();
            if (player.timeUntilPortal > 0) {
                player.timeUntilPortal = 10;
            } else if (player.dimension != GlaciosConfig.dimID) {
                player.timeUntilPortal = 10;
                player.mcServer.getConfigurationManager().transferPlayerToDimension(player, GlaciosConfig.dimID,
                        new TeleporterGlacios(mServer.worldServerForDimension(GlaciosConfig.dimID)));
            } else {
                player.timeUntilPortal = 10;
                player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0,
                        new TeleporterGlacios(mServer.worldServerForDimension(1)));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(Glacios.MODID + ":" + this.getUnlocalizedName().substring(5));
    }
}
