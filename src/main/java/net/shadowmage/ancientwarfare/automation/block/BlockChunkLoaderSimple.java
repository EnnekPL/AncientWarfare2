package net.shadowmage.ancientwarfare.automation.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.shadowmage.ancientwarfare.automation.item.AWAutomationItemLoader;
import net.shadowmage.ancientwarfare.automation.tile.TileChunkLoaderSimple;
import net.shadowmage.ancientwarfare.core.config.AWLog;
import net.shadowmage.ancientwarfare.core.interfaces.IInteractableTile;

public class BlockChunkLoaderSimple extends Block
{

protected BlockChunkLoaderSimple(String regName)
  {
  super(Material.rock);
  this.setBlockName(regName);
  this.setCreativeTab(AWAutomationItemLoader.automationTab);
  }

@Override
public boolean hasTileEntity(int metadata)
  {
  return true;
  }

@Override
public TileEntity createTileEntity(World world, int metadata)
  {
  return new TileChunkLoaderSimple();
  }

@Override
public void breakBlock(World world, int x, int y, int z, Block block, int wtf)
  {
  if(!world.isRemote)
    {
    AWLog.logDebug("breaking chunkloader block!!");
    TileChunkLoaderSimple chunkLoader = (TileChunkLoaderSimple) world.getTileEntity(x, y, z);
    if(chunkLoader!=null)
      {
      chunkLoader.releaseTicket();
      }    
    }
  super.breakBlock(world, x, y, z, block, wtf);
  }

@Override
public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
  {
  TileEntity te = world.getTileEntity(x, y, z);
  if(te instanceof IInteractableTile)
    {
    return ((IInteractableTile)te).onBlockClicked(player);
    }
  return false;
  }

@Override
public void onPostBlockPlaced(World world, int x, int y, int z, int blockMeta)
  {
  super.onPostBlockPlaced(world, x, y, z, blockMeta);
  if(!world.isRemote)
    {
    TileChunkLoaderSimple chunkLoader = (TileChunkLoaderSimple) world.getTileEntity(x, y, z);
    if(chunkLoader!=null)
      {
      chunkLoader.setupInitialTicket();
      }
    }
  }

}