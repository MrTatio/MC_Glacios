package roundaround.mcmods.glacios.world.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorGlacios extends BiomeDecorator {

    public BiomeDecoratorGlacios() {
        this.treesPerChunk = 0;
        this.grassPerChunk = 0;
    }

    @Override
    public void decorateChunk(World world, Random rand, BiomeGenBase biome, int chunkX, int chunkZ) {
        if (this.currentWorld != null) {
            throw new RuntimeException("Already decorating!!");
        } else {
            this.currentWorld = world;
            this.randomGenerator = rand;
            this.chunk_X = chunkX;
            this.chunk_Z = chunkZ;
            this.genDecorations(biome);
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }

    @Override
    protected void genDecorations(BiomeGenBase biome) {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));

        boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, TREE);
        for (int i = 0; doGen && i < this.treesPerChunk; ++i) {
            int posX = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int posZ = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int posY = this.currentWorld.getHeightValue(posX, posZ);

            WorldGenAbstractTree worldgenabstracttree = biome.func_150567_a(this.randomGenerator);
            worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);

            if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, posX, posY, posZ)) {
                worldgenabstracttree.func_150524_b(this.currentWorld, this.randomGenerator, posX, posY, posZ);
            }
        }

        doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, GRASS);
        for (int i = 0; doGen && i < this.grassPerChunk; ++i) {
            int posX = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int posZ = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int posY = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(posX, posZ) * 2);
            WorldGenerator worldgenerator = biome.getRandomWorldGenForGrass(this.randomGenerator);
            worldgenerator.generate(this.currentWorld, this.randomGenerator, posX, posY, posZ);
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }
}
