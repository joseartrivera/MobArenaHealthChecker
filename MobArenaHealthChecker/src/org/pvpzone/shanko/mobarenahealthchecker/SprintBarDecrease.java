package org.pvpzone.shanko.mobarenahealthchecker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Decreases the player health as he sprints
 * 
 * @author Jose Rivera
 * 
 */
public class SprintBarDecrease implements Runnable {
	private Player player;

	public SprintBarDecrease(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		if (player.isValid() && player.isOnline()) {
			if (player.getInventory().getChestplate() != null
					&& player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {

				if (player.isSprinting()) {
					player.setFoodLevel(player.getFoodLevel() - 2);
					Bukkit.getScheduler().scheduleSyncDelayedTask(
							MobArenaHealthChecker.plugin,
							new SprintBarDecrease(player), 10);
				}

			}
		}

	}

}