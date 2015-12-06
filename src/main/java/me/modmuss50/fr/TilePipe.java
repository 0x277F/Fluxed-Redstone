package me.modmuss50.fr;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import reborncore.common.misc.vecmath.Vecs3dCube;


public class TilePipe extends TileEntity implements ITickable {

    public Vecs3dCube[] boundingBoxes = new Vecs3dCube[7];
    public float center = 0.6F;
    public float offset = 0.10F;

    public void refreshBounding() {
        float centerFirst = center - offset;
        double w = 1.0F / 2;
        boundingBoxes[6] = new Vecs3dCube(centerFirst - w - 0.03, centerFirst
                - w - 0.08, centerFirst - w - 0.03, centerFirst + w + 0.08,
                centerFirst + w + 0.04, centerFirst + w + 0.08);

        boundingBoxes[6] = new Vecs3dCube(centerFirst - w, centerFirst - w,
                centerFirst - w, centerFirst + w, centerFirst + w, centerFirst
                + w);

        int i = 0;
        for (EnumFacing dir : EnumFacing.values()) {
            double xMin1 = (dir.getFrontOffsetX() < 0 ? 0.0
                    : (dir.getFrontOffsetX() == 0 ? centerFirst - w : centerFirst + w));
            double xMax1 = (dir.getFrontOffsetX() > 0 ? 1.0
                    : (dir.getFrontOffsetX() == 0 ? centerFirst + w : centerFirst - w));

            double yMin1 = (dir.getFrontOffsetY() < 0 ? 0.0
                    : (dir.getFrontOffsetY() == 0 ? centerFirst - w : centerFirst + w));
            double yMax1 = (dir.getFrontOffsetY() > 0 ? 1.0
                    : (dir.getFrontOffsetY() == 0 ? centerFirst + w : centerFirst - w));

            double zMin1 = (dir.getFrontOffsetZ() < 0 ? 0.0
                    : (dir.getFrontOffsetZ() == 0 ? centerFirst - w : centerFirst + w));
            double zMax1 = (dir.getFrontOffsetZ() > 0 ? 1.0
                    : (dir.getFrontOffsetZ() == 0 ? centerFirst + w : centerFirst - w));

            boundingBoxes[i] = new Vecs3dCube(xMin1, yMin1, zMin1, xMax1,
                    yMax1, zMax1);
            i++;
        }
    }


    @Override
    public void onLoad() {
        super.onLoad();
        refreshBounding();
    }


    public boolean connects(EnumFacing facing){
        BlockPos newPos = getPos().add(facing.getFrontOffsetX(), facing.getFrontOffsetY(), facing.getFrontOffsetZ());
        TileEntity entity = worldObj.getTileEntity(newPos);
        return entity instanceof TilePipe;
    }

    @Override
    public void update() {

    }
}
