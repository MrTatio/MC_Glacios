package roundaround.mcmods.glacios.block;

import net.minecraft.block.BlockPortal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import roundaround.mcmods.glacios.GlaciosConfig;
import roundaround.mcmods.glacios.world.TeleporterGlacios;

public class BlockPortalGlacios extends BlockPortal {

    public BlockPortalGlacios() {
        super();
        this.func_149647_a(CreativeTabs.tabBlock);
    }

    @Override
    public void func_149670_a(World worldObj, int x, int y, int z, Entity entity) {
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

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void func_149651_a(IIconRegister iconRegister) {
//        this.field_149761_L = iconRegister.registerIcon(Glacios.MODID + ":" + this.func_149739_a().substring(5));
//    }
//
//    @Override
//    public boolean func_150000_e(World p_150000_1_, int p_150000_2_, int p_150000_3_, int p_150000_4_) {
//        BlockPortalGlacios.Size size = new BlockPortalGlacios.Size(p_150000_1_, p_150000_2_, p_150000_3_, p_150000_4_, 1);
//        BlockPortalGlacios.Size size1 = new BlockPortalGlacios.Size(p_150000_1_, p_150000_2_, p_150000_3_, p_150000_4_, 2);
//
//        if (size.func_150860_b() && size.field_150864_e == 0) {
//            size.func_150859_c();
//            return true;
//        } else if (size1.func_150860_b() && size1.field_150864_e == 0) {
//            size1.func_150859_c();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void func_149695_a(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
//        int l = func_149999_b(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));
//        BlockPortalGlacios.Size size = new BlockPortalGlacios.Size(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 1);
//        BlockPortalGlacios.Size size1 = new BlockPortalGlacios.Size(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 2);
//
//        if (l == 1 && (!size.func_150860_b() || size.field_150864_e < size.field_150868_h * size.field_150862_g)) {
//            p_149695_1_.func_147449_b(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
//        } else if (l == 2 && (!size1.func_150860_b() || size1.field_150864_e < size1.field_150868_h * size1.field_150862_g)) {
//            p_149695_1_.func_147449_b(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
//        } else if (l == 0 && !size.func_150860_b() && !size1.func_150860_b()) {
//            p_149695_1_.func_147449_b(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.air);
//        }
//    }
//
//    public static class Size {
//        private final World field_150867_a;
//        private final int field_150865_b;
//        private final int field_150866_c;
//        private final int field_150863_d;
//        private int field_150864_e = 0;
//        private ChunkCoordinates field_150861_f;
//        private int field_150862_g;
//        private int field_150868_h;
//
//        public Size(World p_i45415_1_, int p_i45415_2_, int p_i45415_3_, int p_i45415_4_, int p_i45415_5_) {
//            this.field_150867_a = p_i45415_1_;
//            this.field_150865_b = p_i45415_5_;
//            this.field_150863_d = BlockPortal.field_150001_a[p_i45415_5_][0];
//            this.field_150866_c = BlockPortal.field_150001_a[p_i45415_5_][1];
//
//            for (int i1 = p_i45415_3_; p_i45415_3_ > i1 - 21 && p_i45415_3_ > 0
//                    && this.func_150857_a(p_i45415_1_.func_147439_a(p_i45415_2_, p_i45415_3_ - 1, p_i45415_4_)); --p_i45415_3_) {
//                ;
//            }
//
//            int j1 = this.func_150853_a(p_i45415_2_, p_i45415_3_, p_i45415_4_, this.field_150863_d) - 1;
//
//            if (j1 >= 0) {
//                this.field_150861_f = new ChunkCoordinates(p_i45415_2_ + j1 * Direction.offsetX[this.field_150863_d], p_i45415_3_,
//                        p_i45415_4_ + j1 * Direction.offsetZ[this.field_150863_d]);
//                this.field_150868_h = this.func_150853_a(this.field_150861_f.posX, this.field_150861_f.posY, this.field_150861_f.posZ,
//                        this.field_150866_c);
//
//                if (this.field_150868_h < 2 || this.field_150868_h > 21) {
//                    this.field_150861_f = null;
//                    this.field_150868_h = 0;
//                }
//            }
//
//            if (this.field_150861_f != null) {
//                this.field_150862_g = this.func_150858_a();
//            }
//        }
//
//        protected int func_150853_a(int p_150853_1_, int p_150853_2_, int p_150853_3_, int p_150853_4_) {
//            int j1 = Direction.offsetX[p_150853_4_];
//            int k1 = Direction.offsetZ[p_150853_4_];
//            int i1;
//            Block block;
//
//            for (i1 = 0; i1 < 22; ++i1) {
//                block = this.field_150867_a.func_147439_a(p_150853_1_ + j1 * i1, p_150853_2_, p_150853_3_ + k1 * i1);
//
//                if (!this.func_150857_a(block)) {
//                    break;
//                }
//
//                Block block1 = this.field_150867_a.func_147439_a(p_150853_1_ + j1 * i1, p_150853_2_ - 1, p_150853_3_ + k1 * i1);
//
//                if (block1 != Blocks.quartz_block) {
//                    break;
//                }
//            }
//
//            block = this.field_150867_a.func_147439_a(p_150853_1_ + j1 * i1, p_150853_2_, p_150853_3_ + k1 * i1);
//            return block == Blocks.quartz_block ? i1 : 0;
//        }
//
//        protected int func_150858_a() {
//            int i;
//            int j;
//            int k;
//            int l;
//            label56:
//
//            for (this.field_150862_g = 0; this.field_150862_g < 21; ++this.field_150862_g) {
//                i = this.field_150861_f.posY + this.field_150862_g;
//
//                for (j = 0; j < this.field_150868_h; ++j) {
//                    k = this.field_150861_f.posX + j * Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]];
//                    l = this.field_150861_f.posZ + j * Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]];
//                    Block block = this.field_150867_a.func_147439_a(k, i, l);
//
//                    if (!this.func_150857_a(block)) {
//                        break label56;
//                    }
//
//                    if (block == GlaciosBlocks.portalGlacios) {
//                        ++this.field_150864_e;
//                    }
//
//                    if (j == 0) {
//                        block = this.field_150867_a.func_147439_a(
//                                k + Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][0]], i, l
//                                        + Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][0]]);
//
//                        if (block != Blocks.quartz_block) {
//                            break label56;
//                        }
//                    } else if (j == this.field_150868_h - 1) {
//                        block = this.field_150867_a.func_147439_a(
//                                k + Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]], i, l
//                                        + Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]]);
//
//                        if (block != Blocks.quartz_block) {
//                            break label56;
//                        }
//                    }
//                }
//            }
//
//            for (i = 0; i < this.field_150868_h; ++i) {
//                j = this.field_150861_f.posX + i * Direction.offsetX[BlockPortal.field_150001_a[this.field_150865_b][1]];
//                k = this.field_150861_f.posY + this.field_150862_g;
//                l = this.field_150861_f.posZ + i * Direction.offsetZ[BlockPortal.field_150001_a[this.field_150865_b][1]];
//
//                if (this.field_150867_a.func_147439_a(j, k, l) != Blocks.quartz_block) {
//                    this.field_150862_g = 0;
//                    break;
//                }
//            }
//
//            if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
//                return this.field_150862_g;
//            } else {
//                this.field_150861_f = null;
//                this.field_150868_h = 0;
//                this.field_150862_g = 0;
//                return 0;
//            }
//        }
//
//        protected boolean func_150857_a(Block block) {
//            return block.func_149688_o() == Material.field_151579_a || block == Blocks.fire || block == GlaciosBlocks.portalGlacios;
//        }
//
//        public boolean func_150860_b() {
//            return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3
//                    && this.field_150862_g <= 21;
//        }
//
//        public void func_150859_c() {
//            for (int i = 0; i < this.field_150868_h; ++i) {
//                int j = this.field_150861_f.posX + Direction.offsetX[this.field_150866_c] * i;
//                int k = this.field_150861_f.posZ + Direction.offsetZ[this.field_150866_c] * i;
//
//                for (int l = 0; l < this.field_150862_g; ++l) {
//                    int i1 = this.field_150861_f.posY + l;
//                    this.field_150867_a.func_147465_d(j, i1, k, GlaciosBlocks.portalGlacios, this.field_150865_b, 2);
//                }
//            }
//        }
//    }
}
