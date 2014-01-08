package org.pvpzone.shanko.mobarenahealthchecker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import com.garbagemule.MobArena.MobArena;
import com.garbagemule.MobArena.framework.Arena;

/**
 * This is a Bukkit plugin meant to extend the functionality of the MobArena
 * plugin. It adds features such as a sprint-bar for players wearing Diamond
 * Chests, an update of Boss Health, and a command to skip certain waves
 * 
 * @author Jose Rivera
 * 
 */
public final class MobArenaHealthChecker extends JavaPlugin {
	public static MobArenaHealthChecker plugin;

	public static MobArena ma;

	@Override
	public void onEnable() {
		plugin = this;
		ma = null;
		getServer().getPluginManager().registerEvents(new MobArenaListener(),
				this);
	}

	@Override
	public void onDisable() {
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// Command /manextwave
		if (cmd.getName().equalsIgnoreCase("manextwave") && args.length == 1) {

			// If MobArena is null
			if (ma == null) {
				sender.sendMessage("Arena isn't started");
				return false;
			}

			// Get the arena specified
			Arena arena = ma.getArenaMaster().getArenaWithName(args[0]);

			// Check to make sure Arena is not null and is running
			if (arena == null) {
				sender.sendMessage("Arena doesn't exist");
				return false;
			} else if (!arena.isRunning()) {
				sender.sendMessage("Arena is not running");
				return false;
			}

			// Clear out all monsters
			for (LivingEntity monster : arena.getMonsterManager().getMonsters()) {
				monster.setHealth(0.0f);
			}

			// Increment wave counter
			arena.getWaveManager().next();

			return true;
		}

		return false;
	}
}
