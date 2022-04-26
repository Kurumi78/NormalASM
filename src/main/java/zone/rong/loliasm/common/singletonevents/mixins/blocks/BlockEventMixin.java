package zone.rong.loliasm.common.singletonevents.mixins.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.*;
import zone.rong.loliasm.common.singletonevents.IRefreshEvent;

@Mixin(value = BlockEvent.class, remap = false)
public class BlockEventMixin extends Event implements IRefreshEvent {

    @Shadow @Final @Mutable private World world;
    @Shadow @Final @Mutable private BlockPos pos;
    @Shadow @Final @Mutable private IBlockState state;

    @Override
    public void refreshBlockEvent(World world, BlockPos pos, IBlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
    }

}
