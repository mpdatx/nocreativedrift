package net.krilion.minecraft.nocreativedrift.mixins;

import com.mojang.logging.LogUtils;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.slf4j.Logger;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer {
    @Shadow
    public Input input;
    @Unique
    private boolean isApplyingMovement;
    @Unique
    private int diminishTicks;

    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject(method = "serverAiStep", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (this.isApplyingMovement && this.input.getMoveVector().length() == 0.0F)
        {
            LocalPlayer player = (LocalPlayer)(Object)this;
            if (player.isCreative() && !player.onGround())
            {
                float factor = Mth.clamp((float)this.diminishTicks / (float)5, 0.0F, 1.0F);
                player.setDeltaMovement(player.getDeltaMovement().multiply((double)factor, 1.0D, (double)factor));
            }
            if (this.diminishTicks > 0)
            {
                this.diminishTicks--;
                if (this.diminishTicks == 0)
                {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
                    this.isApplyingMovement = false;
                }
            }
        }
        if (this.input.getMoveVector().length() > 0.0F)
        {
            this.diminishTicks = 5;
            this.isApplyingMovement = true;
        }
    }
}
