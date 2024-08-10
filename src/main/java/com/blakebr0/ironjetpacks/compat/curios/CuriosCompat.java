package com.blakebr0.ironjetpacks.compat.curios;

import com.blakebr0.ironjetpacks.init.ModItems;
import com.blakebr0.ironjetpacks.util.JetpackUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class CuriosCompat {
    public static void onInterModEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
    }

    public static Optional<ItemStack> findJetpackCurio(LivingEntity entity) {
        return findJetpackCurio(entity, null);
    }

    public static Optional<ItemStack> findJetpackCurio(LivingEntity entity, @Nullable Predicate<SlotResult> predicate) {
        var optional = CuriosApi.getCuriosHelper().findFirstCurio(entity, ModItems.JETPACK.get());
        
        if (predicate != null) {
        	optional = optional.filter(predicate);
        }
        return optional.map(SlotResult::stack)
                .filter(stack -> JetpackUtils.getJetpack(stack).curios);
    }

//    @SubscribeEvent
//    public void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
//        var stack = event.getObject();
//
//        if (stack.getItem() instanceof JetpackItem) {
//            var curio = new JetpackCurio(stack);
//
//            event.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
//                final LazyOptional<ICurio> capability = LazyOptional.of(() -> curio);
//
//                @Override
//                public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
//                    return CuriosCapability.ITEM.orEmpty(cap, capability);
//                }
//            });
//        }
//    }
}
