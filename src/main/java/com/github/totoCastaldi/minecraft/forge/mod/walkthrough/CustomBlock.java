package com.github.totoCastaldi.minecraft.forge.mod.walkthrough;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

/**
 * Created by toto on 19/01/16.
 */
public class CustomBlock extends Block {

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

}