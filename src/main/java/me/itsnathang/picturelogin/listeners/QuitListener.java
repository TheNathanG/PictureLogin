package me.itsnathang.picturelogin.listeners;

import me.itsnathang.picturelogin.util.ImageMessage;
import me.itsnathang.picturelogin.PictureLogin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.itsnathang.picturelogin.config.ConfigManager;
import me.itsnathang.picturelogin.util.PictureUtil;

public class QuitListener implements Listener {
	private PictureUtil pictureUtil;
	private ConfigManager config;

	public QuitListener(PictureLogin plugin) {
		this.config = plugin.getConfigManager();
		this.pictureUtil = plugin.getPictureUtil();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (!config.getBoolean("show-leave-message", true))
			return;

		Player player = event.getPlayer();
		
		if(!player.hasPermission("picturelogin.show") && config.getBoolean("require-permission", true))
			return;
		
		if (config.getBoolean("block-leave-message", true))
			event.setQuitMessage(null);

		ImageMessage picture_message = pictureUtil.createPictureMessage(player, config.getStringList("leave-messages"));

		if (picture_message == null) return;

		pictureUtil.sendOutPictureMessage(picture_message);
	}

}
