package com.github.totoCastaldi.minecraft.forge.mod.walkthrough;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

@Mod(modid = StepByStep.modid, name = StepByStep.name, version = StepByStep.version)
public class StepByStep
{
    static final String modid = "step_by_step";
    static final String version = "step-1";
    static final String name = "${name}";

    private Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        Configuration configs = new Configuration(event.getSuggestedConfigurationFile());
        try {
            configs.load();
        } catch (RuntimeException e) {
            logger.warn(e);
        }
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        final Block customBlock = new CustomBlock( Material.rock, "stepbystepcustomblock")
                .setTopBlockTextureName(modid + ":finn")
                .setBottomBlockTextureName(modid + ":blue")
                .setNorthBlockTextureName(modid + ":green")
                .setEastBlockTextureName(modid + ":red")
                .setSouthBlockTextureName(modid + ":orange")
                .setWestBlockTextureName(modid + ":purple")
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeGlass)
                .setCreativeTab(CreativeTabs.tabFood)
                ;

        GameRegistry.registerBlock(customBlock, customBlock.getUnlocalizedName());
    }
}
