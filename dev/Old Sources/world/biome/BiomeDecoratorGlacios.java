package glacios.world.biome;

import glacios.block.GlaciosBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorGlacios extends BiomeDecorator {

    public WorldGenerator silverGen;

    public BiomeDecoratorGlacios(BiomeGenBase biomeGenBase) {
        super(biomeGenBase);
        silverGen = new WorldGenMinable(GlaciosBlocks.oreSilver.blockID, 8, GlaciosBlocks.slate.blockID);
        coalGen = new WorldGenMinable(Block.oreCoal.blockID, 12);
        biome = biomeGenBase;
    }

    /*
     * Decorates the world. Calls code that was formerly (pre-1.8) in ChunkProviderGenerate.populate
     */
    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
        if (this.currentWorld != null) {
            return;
        } else {
            this.currentWorld = par1World;
            this.randomGenerator = par2Random;
            this.chunk_X = par3;
            this.chunk_Z = par4;
            this.decorate();
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }

    /*
     * The method that does the work of actually decorating chunks
     */
    @Override
    protected void decorate() {
        generateOres();
        for (int pass = 0; pass < grassPerChunk; pass++) {
            int x = chunk_X + randomGenerator.nextInt(16) + 8;
            int y = randomGenerator.nextInt(128);
            int z = chunk_Z + randomGenerator.nextInt(16) + 8;
            WorldGenerator worldGen = new WorldGenTallGrass(GlaciosBlocks.iceGrass.blockID, 1);
            worldGen.generate(currentWorld, randomGenerator, x, y, z);
        }
        for (int pass = 0; pass < treesPerChunk; pass++) {
            int x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            WorldGenerator worldGen = this.biome.getRandomWorldGenForTrees(this.randomGenerator);
            worldGen.setScale(1.0D, 1.0D, 1.0D);
            worldGen.generate(this.currentWorld, randomGenerator, x, currentWorld.getHeightValue(x, z), z);
        }
    }

    /*
     * Generates ores in the current chunk
     */
    @Override
    protected void generateOres() {
        genStandardOre1(20, silverGen, 0, 64);
        // if (TerrainGen.generateOre(currentWorld, randomGenerator, coalGen, chunk_X, chunk_Z, COAL))
        // genStandardOre1(20, coalGen, 0, 128);
    }

}
