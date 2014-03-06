package roundaround.mcmods.glacios.world.biome;


// Quite obviously biome centered around large volcano or series of volcanos.

public class BiomeGenGlaciosVolcano extends BiomeGenGlacios {

    public BiomeGenGlaciosVolcano(int biomeId) {
        this(biomeId, true);
    }

    public BiomeGenGlaciosVolcano(int biomeId, boolean register) {
        super(biomeId, register);
        //this.topBlock = GlaciosBlocks.ash;
        //this.fillerBlock = GlaciosBlocks.ashStone;
        this.theGlaciosBiomeDecorator.volcanosPerChunk = 1;
    }

}
