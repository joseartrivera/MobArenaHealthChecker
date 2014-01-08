package org.pvpzone.shanko.mobarenahealthchecker;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.garbagemule.MobArena.framework.Arena;
import com.garbagemule.MobArena.waves.MABoss;
import com.garbagemule.MobArena.waves.types.BossWave;

/**
 * Updates the players in the arena with the current Boss Health
 * 
 * @author Jose Rivera
 * 
 */
public class BossUpdater implements Runnable {
	private Arena arena;
	private BossWave wave;

	public BossUpdater(Arena arena, BossWave wave) {
		this.arena = arena;
		this.wave = wave;
	}

	@Override
	public void run() {
		//Whether we should continue displaying the health
		boolean keepGoing = true;
		
		//Loop through each player and send them the health
		for (Player player : arena.getPlayersInArena()) {
			for (MABoss boss : wave.getMABosses()) {
				if (boss.isDead()) {
					keepGoing = false;
				} else {
					player.sendMessage(ChatColor.GREEN + "[MobArena]"
							+ ChatColor.RED + " Boss HP:" + boss.getHealth()
							+ "/" + boss.getMaxHealth());
				}
			}

		}

		//Schedule the updater to display the boss health again
		if (keepGoing && arena.isRunning()) {
			arena.scheduleTask(this, 200);
		}
	}

}