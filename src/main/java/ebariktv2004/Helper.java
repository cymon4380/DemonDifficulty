package ebariktv2004;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Helper {
    static int difficulty = demondifficulty.DIFFICULTY;

    public static void NeutralsAreHostile() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();

            if(demondifficulty.IsInSurvival(player)) {
                if(difficulty >= 2) {
                    int attackRadius = Chances.neutralAttackRadius[difficulty];
                    for (Entity entity : world.getNearbyEntities(player.getLocation(), attackRadius, attackRadius, attackRadius)) {
                        switch (entity.getType()) {
                            case SPIDER:
                                ((Spider) entity).setTarget(player);
                                break;
                            case CAVE_SPIDER:
                                ((CaveSpider) entity).setTarget(player);
                                break;
                            case DOLPHIN:
                                ((Dolphin) entity).setTarget(player);
                                break;
                            case BEE:
                                ((Bee) entity).setTarget(player);
                                ((Bee) entity).setAnger(200 * difficulty);
                                break;
                            case ENDERMAN:
                                ((Enderman) entity).setTarget(player);
                                break;
                            case PANDA:
                                ((Panda) entity).setTarget(player);
                                break;
                            case LLAMA:
                                ((Llama) entity).setTarget(player);
                                break;
                            case TRADER_LLAMA:
                                ((TraderLlama) entity).setTarget(player);
                                break;
                            case WOLF:
                                ((Wolf) entity).setTarget(player);
                                break;
                            case GOAT:
                                ((Goat) entity).setTarget(player);
                                break;
                            case ZOMBIFIED_PIGLIN:
                                ((PigZombie) entity).setTarget(player);
                                break;
                            case IRON_GOLEM:
                                if(difficulty >= 3)
                                    ((IronGolem) entity).setTarget(player);
                                break;
                        }
                    }
                }
            }
        }
    }

    public static void OnNoIAmDrowning() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if (demondifficulty.IsInSurvival(player)) {
                if (difficulty >= 1
                    && !(player.hasPotionEffect(PotionEffectType.WATER_BREATHING)
                    || player.hasPotionEffect(PotionEffectType.CONDUIT_POWER))) {
                    Location blockLoc = player.getLocation();
                    blockLoc.setY(blockLoc.getY() + 2);
                    if (blockLoc.getBlock().isLiquid() && player.getRemainingAir() > 1
                        && player.getLocation().getBlock().isLiquid()) {
                        Bukkit.getScheduler().runTaskLater(demondifficulty.INSTANCE, () -> {
                            player.setRemainingAir(player.getRemainingAir() - 2 * difficulty);
                        }, Chances.oxygenTime[difficulty] / 3);
                    }
                }
            }
        }
    }
}
