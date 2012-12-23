package de.jwhy.fireworksex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class FireworksExUtils {

	public static void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runFirstSetup(Plugin plugin, File configFile) {
		if (!configFile.exists()) {
			// Create folder
			configFile.getParentFile().mkdirs();
			InputStream config_res = plugin.getResource("config.yml");
			if (config_res == null) {
				runEmergency(plugin,
						"Config doesn't exist, sample file not available.");
			}
			plugin.getLogger().log(Level.INFO, "Created new config.yml");
			FireworksExUtils.copy(config_res, configFile);
		}
	}

	public static void saveConfigs(File configFile, FileConfiguration config) {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadConfigs(File configFile, FileConfiguration config) {
		try {
			config.load(configFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runEmergency(Plugin plugin, String reason) {
		plugin.getLogger().log(Level.SEVERE, "Disabling: " + reason);
		plugin.getPluginLoader().disablePlugin(plugin);
	}

}
