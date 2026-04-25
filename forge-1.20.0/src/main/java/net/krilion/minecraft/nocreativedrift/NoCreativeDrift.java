package net.krilion.minecraft.nocreativedrift;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The mod's actual behavior lives in MixinLocalPlayer; this entry class
// just registers the mod with Forge.
@Mod(NoCreativeDrift.MODID)
public class NoCreativeDrift
{
    public static final String MODID = "nocreativedrift";

    public NoCreativeDrift()
    {
        FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
