package ebariktv2004;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public final class demondifficulty extends JavaPlugin {

    FileConfiguration config = getConfig();
    public static demondifficulty INSTANCE;
    public static int DIFFICULTY;

    @Override
    public void onEnable() {
        INSTANCE = this;

        config.addDefault("difficulty", 0);
        config.options().copyDefaults(true);
        saveConfig();

        DIFFICULTY = config.getInt("difficulty");
        if (DIFFICULTY < 0)
            DIFFICULTY = 0;
        if(DIFFICULTY > 4)
            DIFFICULTY = 4;

        getServer().getPluginManager().registerEvents(new Listeners(), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(INSTANCE, demondifficulty::EveryTick, 1L, 1L);

        Bukkit.getLogger().info("Welcome! Demon Difficulty is ready. Good luck <3");
        Bukkit.getLogger().info("--< Inspired by Fundy >--");
        String diffString = "Hard Demon";
        switch (DIFFICULTY) {
            case 0: diffString = "Easy Demon"; break;
            case 1: diffString = "Medium Demon"; break;
            case 3: diffString = "Insane Demon"; break;
            case 4: diffString = "Extreme Demon"; break;
        }
        Bukkit.getLogger().info("Current Difficulty: " + diffString);
        Bukkit.getLogger().info("You can change it in config.yml file.");
        Bukkit.getLogger().info("0 is Easy Demon; 4 is Extreme Demon.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static int RandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean Chance(float percent) {
        if (percent >= 1)
            return RandomInt(1, 100) <= percent;
        else
            return RandomInt(1, (int) (100 / percent)) == 1;
    }

    public static boolean IsInSurvival(Player player) {
        return (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE);
    }

    public static void EveryTick() {
        Helper.NeutralsAreHostile();
        Helper.OnNoIAmDrowning();
    }
}
