package fr.utaria.utariadatabase;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class UtariaDatabaseBungee extends Plugin implements UtariaDatabasePlugin {

	public void onEnable() {
		InstanceManager.useInstance(this);
		this.init();
	}

	public void onDisable() {
	}

	@Override
	public void log(Level logLevel, String message) {
		this.getLogger().log(logLevel, message);
	}

	@Override
	public void runTimerTask(Runnable runnable, int delay, int timer) {
		ProxyServer.getInstance().getScheduler().schedule(this, runnable, delay / 20, timer / 20, TimeUnit.SECONDS);
	}

}
