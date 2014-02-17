package glacios.world.biome;

import glacios.block.GlaciosBlocks;
import glacios.entity.EntityArcticFox;
import glacios.entity.EntityWraith;
import glacios.world.gen.feature.WorldGenGlaciosTrees;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;

public class BiomeGenGlacios extends BiomeGenBase {
    public static final BiomeGenBase glaciosOcean = new BiomeGenOceanGlacios(30).setBiomeName("Glacios Ocean").setMinMaxHeight(-1.5F, -0.2F);
    public static final BiomeGenBase glaciosRiver = new BiomeGenRiverGlacios(31).setBiomeName("Glacios River").setMinMaxHeight(-0.8F, 0F);
    public static final BiomeGenBase glaciosPlains = new BiomeGenPlainsGlacios(32).setBiomeName("Glacios Plains");
    public static final BiomeGenBase glaciosMountains = new BiomeGenMountainsGlacios(33).setBiomeName("Glacios Mountains").setMinMaxHeight(0.4F, 1.8F);
    public static final BiomeGenBase glaciosForest = new BiomeGenForestGlacios(34).setBiomeName("Glacios Forest").setMinMaxHeight(0.2F, 0.5F);
    public static final BiomeGenBase glaciosLake = new BiomeGenLakeGlacios(35).setBiomeName("Glacios Lake").setMinMaxHeight(-0.8F, 0.2F);
    public static final BiomeGenBase glaciosIslands = new BiomeGenIslandsGlacios(36).setBiomeName("Glacios Lake").setMinMaxHeight(-0.8F, 0.2F);

    protected WorldGenGlaciosTrees genGlaciosTrees;

    @SuppressWarnings("unchecked")
    public BiomeGenGlacios(int par1) {
        super(par1);

        biomeList[par1] = this;

        setMinMaxHeight(0.1F, 0.3F);
        setTemperatureRainfall(0.0F, 0.0F);
        setEnableSnow();

        genGlaciosTrees = new WorldGenGlaciosTrees();
        theBiomeDecorator = new BiomeDecoratorGlacios(this);

        topBlock = (byte) Block.blockSnow.blockID;
        fillerBlock = (byte) GlaciosBlocks.glacite.blockID;

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableCaveCreatureList.clear();
        spawnableWaterCreatureList.clear();

        spawnableMonsterList.add(new SpawnListEntry(EntityWraith.class, 2, 1, 1));
        spawnableCreatureList.add(new SpawnListEntry(EntityArcticFox.class, 2, 2, 5));
    }

}
