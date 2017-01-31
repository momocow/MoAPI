package me.momocow.moapi.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoBlockHorizontalFacing extends MoBlock
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public MoBlockHorizontalFacing()
	{
		this(Material.ROCK);
	}
	
	public MoBlockHorizontalFacing(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public MoBlockHorizontalFacing(Material material, MapColor color)
    {
        super(material, color);
    }
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) 
	{
		if(!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		}
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) 
	{
		//North, South, West, East start at index 2 so 2 is added here.
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }
	
	@Override
    public int getMetaFromState(IBlockState state) {
		//getIndex(): D-U-N-S-W-E
        //North, South, West, East start at index 2 so 2 is subtracted here.
        return state.getValue(FACING).getIndex()-2;
    }
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }
}
