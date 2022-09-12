package ebariktv2004;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
public class Listeners implements Listener {
    int difficulty = demondifficulty.DIFFICULTY;

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        LivingEntity entity = e.getEntity();

        switch(entity.getType()) {
            case CREEPER:
                if(demondifficulty.Chance(Chances.poweredCreeperSpawn[difficulty])) {
                    ((Creeper) entity).setPowered(true);
                }
                ((Creeper) entity).setMaxFuseTicks(Chances.creeperFuseTime[difficulty]);
                break;
            case SKELETON: case STRAY:
                ItemStack bow = new ItemStack(Material.BOW);
                bow.addEnchantment(Enchantment.ARROW_DAMAGE, difficulty + 1);
                if(difficulty >= 2)
                    bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
                entity.getEquipment().setItemInMainHand(bow);
                break;
            case ZOMBIE: case HUSK:
                if(entity.getType().equals(EntityType.ZOMBIE)) {
                    ((Zombie) entity).setBaby();
                    entity.setCanPickupItems(true);

                    if(difficulty >= 1)
                        ((Zombie) entity).setCanBreakDoors(true);
                }
                else {
                    ((Husk) entity).setBaby();
                    entity.setCanPickupItems(true);

                    if(difficulty >= 1)
                        ((Husk) entity).setCanBreakDoors(true);
                }

                ItemStack helmet = null;
                ItemStack chestplate = null;
                ItemStack leggings = null;
                ItemStack boots = null;
                ItemStack sword = null;

                if(demondifficulty.Chance(.1f)) {
                    entity.setCustomName("влад");
                    entity.setCustomNameVisible(true);
                }

                switch (difficulty) {
                    case 1:
                        helmet = new ItemStack(Material.LEATHER_HELMET);
                        chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                        leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                        boots = new ItemStack(Material.LEATHER_BOOTS);
                        sword = new ItemStack(Material.WOODEN_SWORD);
                        break;
                    case 2:
                        helmet = new ItemStack(Material.GOLDEN_HELMET);
                        chestplate = new ItemStack(Material.GOLDEN_CHESTPLATE);
                        leggings = new ItemStack(Material.GOLDEN_LEGGINGS);
                        boots = new ItemStack(Material.GOLDEN_BOOTS);
                        sword = new ItemStack(Material.GOLDEN_SWORD);
                        break;
                    case 3:
                        helmet = new ItemStack(Material.IRON_HELMET);
                        chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                        leggings = new ItemStack(Material.IRON_LEGGINGS);
                        boots = new ItemStack(Material.IRON_BOOTS);
                        sword = new ItemStack(Material.DIAMOND_SWORD);

                        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                        break;
                    case 4:
                        helmet = new ItemStack(Material.NETHERITE_HELMET);
                        chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
                        leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
                        boots = new ItemStack(Material.NETHERITE_BOOTS);
                        sword = new ItemStack(Material.NETHERITE_SWORD);

                        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                        sword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                        sword.addEnchantment(Enchantment.KNOCKBACK, 2);
                        sword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                        break;
                }
                if(helmet != null)
                    entity.getEquipment().setHelmet(helmet);
                if(chestplate != null)
                    entity.getEquipment().setChestplate(chestplate);
                if(leggings != null)
                    entity.getEquipment().setLeggings(leggings);
                if(boots != null)
                    entity.getEquipment().setBoots(boots);
                if(sword != null)
                    entity.getEquipment().setItemInMainHand(sword);

                if(difficulty >= 3)
                    entity.getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                break;
            case DROWNED:
                if(demondifficulty.Chance(Chances.tridentDrowned[difficulty])) {
                    entity.getEquipment().setItemInMainHand(new ItemStack(Material.TRIDENT));
                }
                break;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        try {
            Entity entity = e.getEntity();
            EntityDamageEvent.DamageCause cause = e.getCause();

            if((cause == EntityDamageEvent.DamageCause.LAVA
                    || cause == EntityDamageEvent.DamageCause.DROWNING)
                    && entity.getType() == EntityType.PLAYER
                    && difficulty >= 2)
                ((LivingEntity) entity).damage(((LivingEntity) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            else if(cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                switch(entity.getType()) {
                    case PLAYER:
                        if(difficulty >= 3)
                            entity.setFireTicks(99999);
                        break;
                    case ZOMBIE: case SKELETON: case PHANTOM: case DROWNED:
                        if(difficulty >= 1) {
                            entity.setVisualFire(false);
                            entity.setFireTicks(0);
                            e.setCancelled(true);
                        }
                        break;
                }
            }
        }
        catch(Exception ex) {
            Bukkit.getLogger().info("Crappy junk! Something went wrong.");
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        ItemMeta meta = item.getItemMeta();

        if(demondifficulty.IsInSurvival(player)) {
            if(demondifficulty.Chance(Chances.itemBreakChance[difficulty])) {
                ((Damageable) meta).setDamage(1000000);
                item.setItemMeta(meta);
                if(item.containsEnchantment(Enchantment.MENDING)
                        && item.containsEnchantment(Enchantment.DURABILITY)) {
                    player.sendMessage("Удачной починки >:)");
                    player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE,
                            2.0f,
                            .6f);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(e.getKeepInventory())
            return;

        for(ItemStack item : e.getDrops()) {
            if(demondifficulty.Chance(Chances.itemVanishChance[difficulty]))
                item.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        String itemType = item.getType().toString().toLowerCase();

        if(!itemType.contains("cooked") && !itemType.contains("golden") && !itemType.contains("baked")
        && !itemType.contains("bread") && !itemType.contains("melon") && !itemType.contains("potion")
        && !itemType.contains("milk") && !itemType.contains("honey")) {
            if(demondifficulty.Chance(Chances.rawFoodPoisonChance[difficulty])) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.CONFUSION, 300 * (difficulty + 1), 0)
                );
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.HUNGER, 300 * (difficulty + 1), 1)
                );
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.POISON, 300 * difficulty, 0)
                );

                player.sendMessage("Не вся еда чистая, особенно сырая.");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();

        if(block.getType().toString().toLowerCase().contains("torch")) {
            Bukkit.getScheduler().runTaskLater(demondifficulty.INSTANCE, () -> {
                block.setType(Material.AIR);
                ItemStack stick = new ItemStack(Material.STICK);
                ItemMeta meta = stick.getItemMeta();
                meta.setDisplayName("Потухший факел ¯\\_(ツ)_/¯");
                stick.setItemMeta(meta);

                block.getWorld().dropItemNaturally(block.getLocation(), stick);
            }, demondifficulty.RandomInt(
                    Chances.minTorchBurningTime[difficulty],
                    Chances.maxTorchBurningTime[difficulty]
            ));
        }
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent e) {
        Player player = e.getPlayer();

        if((player.getWorld().getFullTime() % 24000) < 1500 && difficulty >= 1) {
            player.setFoodLevel(player.getFoodLevel() - Chances.playerHunger[difficulty]);
            player.setSaturation(player.getSaturation() - Chances.playerHunger[difficulty]);
            player.sendMessage("Доброе утро! Ты проголодался. Самое время найти что-нибудь съестное.");
        }
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!demondifficulty.IsInSurvival(player))
            return;
        if(e.getAction() != Action.LEFT_CLICK_BLOCK)
            return;
        if(player.getEquipment().getItemInMainHand().getType() != Material.AIR)
            return;
        if(e.getClickedBlock().getType().toString().toLowerCase().contains("log")
            && difficulty >= 1) {
            player.damage(Chances.playerLogDamage[difficulty]);
            player.sendMessage("Ай, больно! Возьми что-нибудь в руку, чтобы не раниться.");
        }
    }

    @EventHandler
    public void onCreatureDeath(EntityDeathEvent e) {
        try {
            LivingEntity entity = e.getEntity();
            EntityType type = e.getEntityType();
            LivingEntity killer = entity.getKiller();
            if(killer.getType() != EntityType.PLAYER)
                return;

            if(
                    type == EntityType.CREEPER
                    || type == EntityType.PILLAGER
                    || type == EntityType.ZOMBIE
                    || type == EntityType.SKELETON
                    || type == EntityType.SPIDER
                    || type == EntityType.PHANTOM
                    || type == EntityType.EVOKER
                    || type == EntityType.CAVE_SPIDER
                    || type == EntityType.SLIME
                    || type == EntityType.MAGMA_CUBE
                    || type == EntityType.PIGLIN
                    || type == EntityType.PIGLIN_BRUTE
                    || type == EntityType.HUSK
                    || type == EntityType.STRAY
            ) {
                if(demondifficulty.Chance(Chances.ghostChance[difficulty])) {
                    Entity ghost = entity.getWorld().spawnEntity(entity.getLocation(),
                            EntityType.VEX);
                    ghost.setCustomName("Призрак " + entity.getName());
                    ghost.setCustomNameVisible(true);
                }
            }
        }
        catch (Exception ex){}
    }

}
