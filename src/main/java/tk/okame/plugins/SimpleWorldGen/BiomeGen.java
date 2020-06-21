package tk.okame.plugins.SimpleWorldGen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.util.noise.PerlinNoiseGenerator;

public class BiomeGen {
	private PerlinNoiseGenerator gen;
	public BiomeGen(long worldSeed) {
		gen = new PerlinNoiseGenerator(worldSeed);
		//gen.setScale(0.05D);
	}
	
	public Biome getBiomeAt(int chunkX, int chunkZ, int x, int z) {
		int worldX = chunkX * 16 + x;
		int worldZ = chunkZ * 16 + z;
		int factor = getBiomeFactor(worldX, worldZ);
		return getBiomeFromFactor(factor);
	}
	
	public int getBiomeFactor(int x, int z) {
		int factor = (int) (gen.noise(x / 200D, z / 200D, 0) * 100D);
		return factor;
	}
	
	public int getBiomeFactor(int chunkX, int chunkZ, int x, int z) {
		int worldX = chunkX * 16 + x;
		int worldZ = chunkZ * 16 + z;
		return getBiomeFactor(worldX, worldZ);
	}
	
	public Biome getBiomeFromFactor(int factor) {
		if (factor < -33) return Biome.OCEAN;
		if (factor < -30) return Biome.BEACH;
		return Biome.PLAINS;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onChunkLoad(ChunkLoadEvent event) {
		if (!event.isNewChunk())
			return;
		Chunk chunk = event.getChunk();
		int chunkX = chunk.getX();
		int chunkZ = chunk.getZ();
		World world = chunk.getWorld();
		BiomeGen gen = new BiomeGen(world.getSeed());
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++) {
				int worldX = chunkX * 16 + x;
				int worldZ = chunkZ * 16 + z;
				for (int y = 0; y < world.getMaxHeight() / 2; y++)
					world.setBiome(worldX, y, worldZ, gen.getBiomeAt(chunkX, chunkZ, x, z));
			}
	}
}
