package tk.okame.plugins.SimpleWorldGen;

import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
//import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
/*
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion.Target;
import org.bukkit.plugin.java.annotation.plugin.LoadOrder;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
*/

//@Plugin(name = "SimpleWorldGenerator", version = "1.0.0")
//@Author(value = "Okame")
//@LoadOrder(value = PluginLoadOrder.STARTUP)
//@ApiVersion(value = Target.v1_13)
//@Command(name = "getfactor", desc = "Get internal biome factor of player's current position.", permission = "swg.getfactor", usage = "/<command>")
public class Main extends JavaPlugin {
	private static Logger logger;
	@Override
	public void onEnable() {
		logger = getLogger();
		logger.info("Enabled!");
	}
	
	@Override
	public void onDisable() {
		logger.info("Disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("getfactor")) {
			if (!(sender instanceof Player))
				sender.sendMessage("This command can only be used by a player.");
			else {
				Player player = (Player) sender;
				World world = player.getWorld();
				BiomeGen gen = new BiomeGen(world.getSeed());
				int x = player.getLocation().getBlockX();
				int z = player.getLocation().getBlockZ();
				int factor = gen.getBiomeFactor(x, z);
				Biome biome = gen.getBiomeFromFactor(factor);
				sender.sendMessage("X: " + x);
				sender.sendMessage("Z: " + z);
				sender.sendMessage("Factor: " + factor);
				sender.sendMessage("Biome: " + biome.toString());
			}
			return true;
		}
		return false;
	}
	
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ChunkGen();
	}
	
	public static Logger myLogger() {
		return logger;
	}
}
