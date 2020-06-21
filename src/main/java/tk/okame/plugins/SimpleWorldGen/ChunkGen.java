package tk.okame.plugins.SimpleWorldGen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
//import org.bukkit.util.noise.SimplexOctaveGenerator;

public class ChunkGen extends ChunkGenerator {
	private BiomeGen biomeGen = null;
	
	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		//Main.myLogger().info("Generating chunk [" + chunkX + ", " + chunkZ + "]");
		if (biomeGen == null) biomeGen = new BiomeGen(world.getSeed());
		ChunkData chunk = createChunkData(world);
		//Random rand = new Random();
		//rand.setSeed(world.getSeed());
		//SimplexOctaveGenerator gen = new SimplexOctaveGenerator(rand, 8);
		//gen.setScale(0.005D);

		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++) {
				int curHeight;
				
				curHeight = (int) (biomeGen.getBiomeFactor(chunkX, chunkZ, x, z) / 3) + 70;
				Biome newbiome = biomeGen.getBiomeAt(chunkX, chunkZ, x, z);
				//world.setBiome(chunkX * 16 + x, curHeight, chunkZ * 16 + z, newbiome);
				switch(newbiome) {
				
				case OCEAN:
					//chunk.setBlock(x, curHeight, z, Material.DIRT);
					for (int i = 59; i >= curHeight; i--)
						chunk.setBlock(x, i, z, Material.WATER);
					chunk.setBlock(x, curHeight - 1, z, Material.DIRT);
					for (int i = curHeight - 2; i > 0; i--)
						chunk.setBlock(x, i, z, Material.STONE);
					break;
				
				case BEACH:
					chunk.setBlock(x, curHeight, z, Material.SAND);
					chunk.setBlock(x, curHeight - 1, z, Material.SAND);
					chunk.setBlock(x, curHeight - 2, z, Material.DIRT);
					for (int i = curHeight - 3; i > 0; i--)
						chunk.setBlock(x, i, z, Material.STONE);
					break;
				
				default:
					chunk.setBlock(x, curHeight, z, Material.GRASS_BLOCK);
					chunk.setBlock(x, curHeight - 1, z, Material.DIRT);
					for (int i = curHeight - 2; i > 0; i--)
						chunk.setBlock(x, i, z, Material.STONE);
					break;
				}
				chunk.setBlock(x, 0, z, Material.BEDROCK);
				
			}
		
		return chunk;
	}
	
	@Override
	public boolean shouldGenerateCaves() { return true; }
	@Override
	public boolean shouldGenerateDecorations() { return true; }
	@Override
	public boolean shouldGenerateMobs() { return true; }
	@Override
	public boolean shouldGenerateStructures() { return true; }
}
