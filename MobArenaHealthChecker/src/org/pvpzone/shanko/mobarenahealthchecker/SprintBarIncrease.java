package org.pvpzone.shanko.mobarenahealthchecker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Increases the sprint bar if we ar enot sprinting
 * 
 * @author peperivera
 * 
 */
public class SprintBarIncrease implements Runnable {
	private Player player;

	public SprintBarIncrease(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		if (player.isValid() && player.isOnline()) {
			if (player.getInventory().getChestplate() != null
					&& player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {

				if (!player.isSprinting() && player.getFoodLevel() < 20) {
					player.setFoodLevel(player.getFoodLevel() + 1);
					Bukkit.getScheduler().scheduleSyncDelayedTask(
							MobArenaHealthChecker.plugin,
							new SprintBarIncrease(player), 10);
				}

			}
		}

	}

}