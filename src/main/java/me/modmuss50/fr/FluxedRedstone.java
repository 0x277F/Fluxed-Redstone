package me.modmuss50.fr;

import reborncore.mcmultipart.multipart.MultipartRegistry;
import me.modmuss50.fr.client.PipeModelBakery;
import me.modmuss50.fr.mutlipart.ItemMultipartPipe;
import me.modmuss50.fr.mutlipart.PipeStateHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import reborncore.RebornCore;
import reborncore.common.util.CraftingHelper;

import java.util.HashMap;

@Mod(modid = "fluxedredstone", name = "FluxedRedstone", version = "@MODVERSION@", dependencies = "required-after:reborncore;required-after:reborncore-mcmultipart;required-after:tesla")
public class FluxedRedstone {

    public static HashMap<PipeTypeEnum, Item> itemMultiPipe = new HashMap<>();

    public static PipeStateHelper stateHelper;

    public static FluxedRedstoneCreativeTab creativeTab;

    public static Config config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
        config.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        stateHelper = new PipeStateHelper();
        creativeTab = new FluxedRedstoneCreativeTab();


        for (PipeTypeEnum typeEnum : PipeTypeEnum.values()) {
            MultipartRegistry.registerPart(typeEnum.getClassType(), "fluxedredstone:fluxedPipe." + typeEnum.getFriendlyName());
            itemMultiPipe.put(typeEnum, new ItemMultipartPipe(typeEnum).setCreativeTab(creativeTab).setUnlocalizedName("fluxedredstone.itemFluxedPipe." + typeEnum.getFriendlyName()));
            GameRegistry.registerItem(itemMultiPipe.get(typeEnum), "itemFluxedPipe." + typeEnum.getFriendlyName());
            RebornCore.jsonDestroyer.registerObject(itemMultiPipe.get(typeEnum));
        }

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) { //Lazy man's proxy
            MinecraftForge.EVENT_BUS.register(new PipeModelBakery());
        }

        CraftingHelper.addShapedOreRecipe(new ItemStack(itemMultiPipe.get(PipeTypeEnum.REDSTONE), 6),
                "GRG", "RSR", "GRG",
                'G', new ItemStack(Blocks.GLASS_PANE),
                'R', new ItemStack(Items.REDSTONE),
                'S', new ItemStack(Blocks.STONE));

        CraftingHelper.addShapedOreRecipe(new ItemStack(itemMultiPipe.get(PipeTypeEnum.GOLD), 6),
                "GRG", "RSR", "GRG",
                'G', new ItemStack(Blocks.GLASS_PANE),
                'R', new ItemStack(Items.GOLD_INGOT),
                'S', new ItemStack(itemMultiPipe.get(PipeTypeEnum.REDSTONE)));

        CraftingHelper.addShapedOreRecipe(new ItemStack(itemMultiPipe.get(PipeTypeEnum.BALZE), 4),
                "GRG", "RSR", "GRG",
                'G', new ItemStack(Blocks.IRON_BARS),
                'R', new ItemStack(Items.BLAZE_ROD),
                'S', new ItemStack(itemMultiPipe.get(PipeTypeEnum.GOLD)));

        CraftingHelper.addShapedOreRecipe(new ItemStack(itemMultiPipe.get(PipeTypeEnum.ENDER), 4),
                "GRG", "RSR", "GRG",
                'G', new ItemStack(Items.GOLD_INGOT),
                'R', new ItemStack(Items.ENDER_PEARL),
                'S', new ItemStack(Items.GHAST_TEAR));


    }

    public static class FluxedRedstoneCreativeTab extends CreativeTabs {

        public FluxedRedstoneCreativeTab() {
            super("fluxedredstone");
        }

        @Override
        public Item getTabIconItem() {
            return itemMultiPipe.get(PipeTypeEnum.ENDER);
        }
    }

}
