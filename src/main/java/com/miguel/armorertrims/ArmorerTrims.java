package com.miguel.armorertrims;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ArmorerTrims implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("armorertrims");

    private static final List<Item> ARMOR_TRIM_TEMPLATES = new ArrayList<>();

    @Override
    public void onInitialize() {
        LOGGER.info("¡Iniciando Armorer Trims Mod!");
        fillTrimList();
        registerCustomTrades();
    }

    private void fillTrimList() {
        String[] trimIds = {
            "coast_armor_trim_smithing_template",
            "dune_armor_trim_smithing_template",
            "eye_armor_trim_smithing_template",
            "host_armor_trim_smithing_template",
            "raiser_armor_trim_smithing_template",
            "rib_armor_trim_smithing_template",
            "sentry_armor_trim_smithing_template",
            "shaper_armor_trim_smithing_template",
            "silence_armor_trim_smithing_template",
            "snout_armor_trim_smithing_template",
            "spire_armor_trim_smithing_template",
            "tide_armor_trim_smithing_template",
            "vex_armor_trim_smithing_template",
            "ward_armor_trim_smithing_template",
            "wayfinder_armor_trim_smithing_template",
            "wild_armor_trim_smithing_template",
            "flow_armor_trim_smithing_template",
            "bolt_armor_trim_smithing_template"
        };

        for (String id : trimIds) {
            Item template = Registries.ITEM.get(Identifier.ofVanilla(id));
            if (template != Items.AIR) {
                ARMOR_TRIM_TEMPLATES.add(template);
            } else {
                LOGGER.error("¡No se pudo encontrar la plantilla con ID: {}!", id);
            }
        }
        LOGGER.info("Se han cargado {} plantillas de armadura.", ARMOR_TRIM_TEMPLATES.size());
    }

    private void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 5, factories -> {
            factories.add((entity, random) -> {
                if (ARMOR_TRIM_TEMPLATES.isEmpty()) {
                    return null;
                }
                int randomIndex = random.nextInt(ARMOR_TRIM_TEMPLATES.size());
                ItemStack templateStack = new ItemStack(ARMOR_TRIM_TEMPLATES.get(randomIndex));

                int randomPrice = 15 + random.nextInt(31); // 15 a 45 esmeraldas

                return new TradeOffer(
                        new ItemStack(Items.EMERALD, randomPrice),
                        templateStack,
                        12,    // usos máximos
                        30,    // experiencia para el aldeano
                        0.05F  // multiplicador de precio
                );
            });
        });
        LOGGER.info("¡Intercambio personalizado registrado para el Armero nivel 5!");
    }
}
