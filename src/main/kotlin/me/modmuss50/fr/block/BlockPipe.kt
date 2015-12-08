package me.modmuss50.fr.block

import me.modmuss50.fr.PipeTypeEnum
import me.modmuss50.fr.WorldState
import me.modmuss50.fr.caps.TestCap
import me.modmuss50.fr.tile.TilePipe
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumWorldBlockLayer
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


class BlockPipe(val  type: PipeTypeEnum) : BlockContainer(Material.iron) {

    init {
        this.setCreativeTab(CreativeTabs.tabRedstone)
        defaultState = this.blockState.baseState
        this.setBlockBounds(0.25f, 0.25f, 0.25f, 0.75f, 0.75f, 0.75f)
    }

    override fun onBlockActivated(worldIn: World?, pos: BlockPos?, state: IBlockState?, playerIn: EntityPlayer?, side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if(playerIn!!.heldItem != null && playerIn!!.heldItem.item == Items.apple){
            var pipe = worldIn!!.getTileEntity(pos) as TilePipe;
            if(!pipe.hasCap(side!!)){
                return pipe.addCap(side!!, TestCap())
            } else {
                return pipe.removeCap(side!!)
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ)
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
        return TilePipe()
    }

    override fun getRenderType(): Int {
        return 3
    }

    override fun isBlockNormalCube(): Boolean {
        return false
    }

    override fun isOpaqueCube(): Boolean {
        return false
    }

    override fun isFullBlock(): Boolean {
        return false
    }

    override fun isFullCube(): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): EnumWorldBlockLayer {
        return EnumWorldBlockLayer.CUTOUT
    }

    override fun getExtendedState(state: IBlockState?, world: IBlockAccess?, pos: BlockPos?): IBlockState? {
        return WorldState(world, pos, type);
    }
}