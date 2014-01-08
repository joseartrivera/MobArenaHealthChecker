package org.pvpzone.shanko.mobarenahealthchecker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.garbagemule.MobArena.events.ArenaEndEvent;
import com.garbagemule.MobArena.events.ArenaStartEvent;
import com.garbagemule.MobArena.events.NewWaveEvent;
import com.garbagemule.MobArena.waves.enums.WaveType;
import com.garbagemule.MobArena.waves.types.BossWave;

/**
 * This class listens for Mob Arena events and Player events
 * 
 * @author Jose Rivera
 * 
 */
public class MobArenaListener implements Listener {

	/**
	 * Fires when a boss enters the Arena, starts a Boss Update to display it's
	 * health
	 * 
	 * @param event
	 */
	@EventHandler
	public void BossEvent(NewWaveEvent event) {
		if (event.getWave().getType() == WaveType.BOSS) {
			BossWave wave = (BossWave) event.getWave();
			event.getArena().scheduleTask(
					new BossUpdater(event.getArena(), wave), 200);

		}
	}

	/**
	 * Gives the plugin an instance of the MobArena plugin to handle the
	 * /manextwave command
	 * 
	 * @param event
	 */
	@EventHandler
	public void ArenaStart(ArenaStartEvent event) {
		if (MobArenaHealthChecker.ma == null)
			MobArenaHealthChecker.ma = event.getArena().getPlugin();
	}

	/**
	 * Used to disable specific arenas after they are finished. Currently only
	 * disables "boss" arena.
	 * 
	 * @param event
	 */
	@EventHandler
	public void ArenaEnd(ArenaEndEvent event) {
		if (event.getArena().arenaName().toLowerCase().equals("boss")) {
			event.getArena().setEnabled(false);
		}
	}

	/**
	 * Turns the Food bar into a Sprint bar for players wearing a Diamond
	 * Chestplate
	 * 
	 * @param event
	 */
	@EventHandler
	public void toggleBar(PlayerToggleSprintEvent event) {
		// If we are wearing a diamond chestplate
		if (event.getPlayer().getInventory().getChestplate() != null
				&& event.getPlayer().getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {
			// If we are sprinting decrease the bar
			if (event.isSprinting()) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(
						MobArenaHealthChecker.plugin,
						new SprintBarDecrease(event.getPlayer()), 10);
			}
			// Otherwise increase it as we are not sprinting
			else {
				Bukkit.getScheduler().scheduleSyncDelayedTask(
						MobArenaHealthChecker.plugin,
						new SprintBarIncrease(event.getPlayer()), 10);
			}
		}
	}

	/**
	 * Cancel ender pearl teleportation as regular MobArena allows all
	 * teleportation via EnderPearls to happen
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void enderPearl(PlayerTeleportEvent event) {
		if (event.getCause() == TeleportCause.ENDER_PEARL) {
			event.setCancelled(true);
			return;
		}

	}
}
