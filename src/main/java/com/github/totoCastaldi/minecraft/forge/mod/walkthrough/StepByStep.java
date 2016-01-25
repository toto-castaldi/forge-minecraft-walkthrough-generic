package com.github.totoCastaldi.minecraft.forge.mod.walkthrough;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.util.Random;

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
                .setQuantityDropped(4)
                .setItemDropped(Items.diamond)
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeGlass)
                .setCreativeTab(CreativeTabs.tabFood)
                ;
        GameRegistry.registerBlock(customBlock, customBlock.getUnlocalizedName());

        final CustomWorldGenerator customWorldGenerator = new CustomWorldGenerator(customBlock);
        GameRegistry.registerWorldGenerator(customWorldGenerator, 10); //min weight -> starts firts

        GameRegistry.addRecipe(new ItemStack(customBlock), new Object[]{
                "A  ",
                " A ",
                "  A",
                'A', new ItemStack(Items.diamond)
        });
    }

    /**
     * Created by toto on 19/01/16.
     */
    public static class CustomBlock extends Block {

        private String topBlockTextureName;
        private String bottomBlockTextureName;
        private String northBlockTextureName;
        private String southBlockTextureName;
        private String westBlockTextureName;
        private String eastBlockTextureName;
        private IIcon topIcon;
        private IIcon bottomIcon;
        private IIcon northIcon;
        private IIcon southIcon;
        private IIcon westIcon;
        private IIcon eastIcon;
        private int quantityDropped;
        private Item itemDropped;

        public CustomBlock(Material material, String name) {
            super(material);

            setBlockName(name);
            setCreativeTab(CreativeTabs.tabBlock);
            setHardness(2.0F);
            setResistance(10.0F);
            setStepSound(stepSound);

        }

        public CustomBlock setTopBlockTextureName(String name) {
            this.topBlockTextureName = name;
            return this;
        }

        public CustomBlock setBottomBlockTextureName(String name) {
            this.bottomBlockTextureName = name;
            return this;
        }

        public CustomBlock setNorthBlockTextureName(String name) {
            this.northBlockTextureName = name;
            return this;
        }

        public CustomBlock setSouthBlockTextureName(String name) {
            this.southBlockTextureName = name;
            return this;
        }

        public CustomBlock setWestBlockTextureName(String name) {
            this.westBlockTextureName = name;
            return this;
        }

        public CustomBlock setEastBlockTextureName(String name) {
            this.eastBlockTextureName = name;
            return this;
        }

        public CustomBlock setQuantityDropped(int quantityDropped) {
            this.quantityDropped = quantityDropped;
            return this;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void registerBlockIcons(IIconRegister iconRegister) {
            this.topIcon = iconRegister.registerIcon(this.topBlockTextureName);
            this.bottomIcon = iconRegister.registerIcon(this.bottomBlockTextureName);
            this.northIcon = iconRegister.registerIcon(this.northBlockTextureName);
            this.southIcon = iconRegister.registerIcon(this.southBlockTextureName);
            this.westIcon = iconRegister.registerIcon(this.westBlockTextureName);
            this.eastIcon = iconRegister.registerIcon(this.eastBlockTextureName);
        }



        @SideOnly(Side.CLIENT)
        @Override
        public IIcon getIcon(int side, int meta){
            switch (side) {
                case 0: return this.bottomIcon;
                case 1: return this.topIcon;
                case 2: return this.westIcon;
                case 3: return this.eastIcon;
                case 4: return this.southIcon;
                case 5: return this.northIcon;
                default: return null;
            }
        }

        public CustomBlock setItemDropped(Item itemDropped) {
            this.itemDropped = itemDropped;
            return this;
        }

        public int quantityDropped(Random random) {
            return this.itemDropped == null ? 0 : this.quantityDropped ;
        }

        public Item getItemDropped(int x, Random random, int y)
        {
            return this.itemDropped;
        }

    }

    private class CustomWorldGenerator implements IWorldGenerator {

        private Block block;

        public CustomWorldGenerator(Block coderdojomiBlock) {
            this.block = coderdojomiBlock;
        }

        @Override
        public void generate(Random random, int chunkX, int chunkZ, World world,
                             IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

            int x = chunkX * 16;
            int z = chunkZ * 16;
            int y = 256;

            // La superficie solida solo al di sopra del livello del mare
            while (!world.doesBlockHaveSolidTopSurface(world, x, y, z) && y > 62) {
                --y;
            }

            //http://www.minecraftforge.net/wiki/Adding_World_Generation
            BiomeGenBase b = world.getBiomeGenForCoords(chunkX, chunkZ);

            //trovata coordinata y corretta ?
            if (world.doesBlockHaveSolidTopSurface(world,x, y, z))
            {
                world.setBlock(x, y, z, block); //punta
            }

        }
    }
}
