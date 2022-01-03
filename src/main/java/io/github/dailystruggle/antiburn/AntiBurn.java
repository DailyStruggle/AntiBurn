package io.github.dailystruggle.antiburn;

import io.github.dailystruggle.antiburn.configs.Materials;
import io.github.dailystruggle.antiburn.spigotEventListeners.EntityDamageEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiBurn extends JavaPlugin {
    private static AntiBurn instance;

    public static AntiBurn getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //get materials from configuration
        Materials.reload();

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EntityDamageEventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
