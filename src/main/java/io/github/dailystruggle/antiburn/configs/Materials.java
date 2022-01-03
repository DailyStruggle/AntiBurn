package io.github.dailystruggle.antiburn.configs;

import io.github.dailystruggle.antiburn.AntiBurn;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;

public class Materials {
    public static ConcurrentHashMap<EntityDamageEvent.DamageCause, ConcurrentSkipListSet<Material>> itemLists;

    public static void reload() {
        itemLists = new ConcurrentHashMap<>();

        //create if not exists
        File f = new File(AntiBurn.getInstance().getDataFolder(), "materials.yml");
        if(!f.exists())
        {
            AntiBurn.getInstance().saveResource("materials.yml", false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);

        for(String key : config.getKeys(false)) {
            if(key == null || key.equalsIgnoreCase("version")) continue;
            String finalKey = key;
            switch (key.toLowerCase(Locale.ROOT)) {
                case "lava" -> finalKey = "LAVA";
                case "fire" -> finalKey = "FIRE";
                case "cactus" -> finalKey = "CONTACT";
            }

            EntityDamageEvent.DamageCause damageCause;
            try {
                damageCause = EntityDamageEvent.DamageCause.valueOf(finalKey.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                Bukkit.getLogger().log(Level.WARNING, "AntiBurn - invalid DamageCause:" + finalKey);
                continue;
            }
            if(itemLists.containsKey(damageCause)) continue;
            ConcurrentSkipListSet<Material> set = new ConcurrentSkipListSet<>();
            List<String> itemStringList = config.getStringList(finalKey);
            Objects.requireNonNull(itemStringList);
            for(String itemName : itemStringList) {
                Material material = Material.getMaterial(itemName.toUpperCase(Locale.ROOT));
                if(material == null) {
                    Bukkit.getLogger().log(Level.WARNING, "AntiBurn - invalid Material:" + itemName);
                    continue;
                }
                set.add(material);
            }
            itemLists.put(damageCause,set);
        }
    }
}
