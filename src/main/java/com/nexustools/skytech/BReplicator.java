package com.nexustools.skytech; /// TAKEN DIRECTLY FROM MINECRAFT DECOMPILE

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BReplicator extends BlockContainer { // code taken from BlockFurnace

    @SideOnly(Side.CLIENT)
    private Icon[] icons = new Icon[8];
    
    
    public static final int IDX_FRONT = 4;//0 // top is left   /// left is right /// front is top // bottom is front
    public static final int IDX_LEFT = 2;//1
    public static final int IDX_RIGHT = 3;//2
    public static final int IDX_TOP = 1;//3
    public static final int IDX_BOTTOM = 0;//4
    public static final int IDX_BACK = 5;//5
    

    protected BReplicator(int par1) {
        super(par1, Material.iron);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
//    @SideOnly(Side.CLIENT)
//    this

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    /**
     * set a blocks direction
     */
    private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
        if (!par1World.isRemote) {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1]) {
                b0 = 3;
            }

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l]) {
                b0 = 2;
            }

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1]) {
                b0 = 5;
            }

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1]) {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture.
     * Args: side, metadata
     */
    public Icon getIcon(int par1, int par2) {
        return icons[par1];
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it
     * needs with the given IconRegister. This is the only chance you get to
     * register icons.
     */
    public void registerIcons(IconRegister r) {
        this.blockIcon = r.registerIcon("skytech:replicator");
        icons[IDX_TOP] = icons[IDX_BOTTOM] = blockIcon;
        icons[IDX_FRONT] = r.registerIcon("skytech:replicator-front");
        icons[IDX_BACK] = r.registerIcon("skytech:replicator-rear");
        icons[IDX_LEFT] = r.registerIcon("skytech:replicator-left");
        icons[IDX_RIGHT] = r.registerIcon("skytech:replicator-right");

    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        player.openGui(SkyTech.instance, 0, world, x, y, z);
        TEReplicator rep = ((TEReplicator)tileEntity);
        rep.watchedBy.clear(); // temporary hackfix
        rep.watchedBy.add(player);
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    public TileEntity createNewTileEntity(World par1World) {
        return new TEReplicator();
    }

}
