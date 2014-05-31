package net.shadowmage.ancientwarfare.automation.tile.torque;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;


public class TileTorqueStorageFlywheel extends TileTorqueStorageBase
{

private List<TileTorqueStorageFlywheel> wheelsToBalance = new ArrayList<TileTorqueStorageFlywheel>();

@Override
public void updateEntity()
  {
  super.updateEntity();
  if(!worldObj.isRemote)
    {
    tryBalancingFlywheels();    
    }
  }

private void tryBalancingFlywheels()
  {
  TileEntity te = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
  if((te instanceof TileTorqueStorageFlywheel))
    {
    return;
    }
  int y = yCoord-1;
  te = worldObj.getTileEntity(xCoord, y, zCoord);
  while(y<156 && y>=1 && te instanceof TileTorqueStorageFlywheel)
    {
    wheelsToBalance.add((TileTorqueStorageFlywheel) te);
    y--;
    te = worldObj.getTileEntity(xCoord, y, zCoord);
    }
  wheelsToBalance.add(this);
  double totalPower = 0.d;
  double average;
  for(TileTorqueStorageFlywheel wheel : wheelsToBalance)
    {
    totalPower+=wheel.getEnergyStored();
    }
  average = totalPower / (double)wheelsToBalance.size();
  for(TileTorqueStorageFlywheel wheel : wheelsToBalance)
    {
    wheel.setEnergy(average);
    }
  wheelsToBalance.clear();
  }

@Override
public boolean canInput(ForgeDirection from)
  {
  return !canOutput(from);
  }

@Override
public boolean canOutput(ForgeDirection towards)
  {
  return towards==ForgeDirection.getOrientation(getBlockMetadata());
  }

}