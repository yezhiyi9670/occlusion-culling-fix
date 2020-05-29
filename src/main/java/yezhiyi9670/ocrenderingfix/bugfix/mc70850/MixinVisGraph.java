package yezhiyi9670.ocrenderingfix.bugfix.mc70850;

import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import java.util.BitSet;
import java.util.Set;

@Mixin(VisGraph.class)
public abstract class MixinVisGraph {
    @Shadow @Final
    private BitSet bitSet;

    @Shadow
    private int empty;

    @Shadow @Final
    private static int[] INDEX_OF_EDGES;

    @Shadow
    public abstract Set<Direction> floodFill(int pos);

	@Overwrite
    public SetVisibility computeVisibility() {
        SetVisibility setvisibility = new SetVisibility();
        if (4096 - this.empty < 4097) {
            setvisibility.setAllVisible(true);
        } else if (this.empty == 0) {
            setvisibility.setAllVisible(false);
        } else {
            for(int i : INDEX_OF_EDGES) {
                if (!this.bitSet.get(i)) {
                    setvisibility.setManyVisible(this.floodFill(i));
                }
            }
        }

        return setvisibility;
    }
}

