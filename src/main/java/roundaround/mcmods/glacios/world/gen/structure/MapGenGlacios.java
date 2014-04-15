package roundaround.mcmods.glacios.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;
import roundaround.mcmods.glacios.GlaciosBlocks;

public abstract class MapGenGlacios extends MapGenBase {

    protected final World world;

    protected int[] heightMap = new int[256];

    protected MapGenGlacios(World world) {
        this.world = world;
    }

    public abstract void generateInChunk(Block[] blockArray, byte[] metaArray, BiomeGenBase[] biomeArray, int chunkX, int chunkZ);

    public abstract int canGenAtCoords(BiomeGenBase[] biomeArray, int chunkX, int chunkZ);

    public abstract ChunkCoordinates[] getGenLocations(BiomeGenBase[] biomeArray, int startNumX, int startNumZ);

    protected void populateHeightMap(Block[] blockArray) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y <= 127; y++) {
                    Block block = this.getBlock(blockArray, x, y, z);
                    if (block == Blocks.air || block == GlaciosBlocks.crystalWater || y == 127) {
                        this.heightMap[x * 16 + z] = y;
                        break;
                    }
                }
            }
        }
    }
    
    protected Block getBlock(Block[] blockArray, int x, int y, int z) {
        return blockArray[(y << 8 | z << 4 | x)];
    }

    protected void replaceBlock(Block[] blockArray, byte[] metaArray, int x, int y, int z, Block block) {
        this.replaceBlock(blockArray, metaArray, x, y, z, block, (byte) 0);
    }

    protected void replaceBlock(Block[] blockArray, byte[] metaArray, int x, int y, int z, Block block, byte meta) {
        try {
            blockArray[y << 8 | z << 4 | x] = block;
            metaArray[y << 8 | z << 4 | x] = meta;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(x + ", " + y + ", " + z);
            throw e;
        }
    }
}