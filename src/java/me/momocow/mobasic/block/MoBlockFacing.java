package me.momocow.mobasic.block;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoBlockFacing extends MoBlock
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public MoBlockFacing()
	{
		this(Material.ROCK);
	}
	
	public MoBlockFacing(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public MoBlockFacing(Material material, MapColor color)
    {
        super(material, color);
    }
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) 
	{
		worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)), 2);
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) 
	{
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }
	
	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
