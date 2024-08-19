package com.blakebr0.ironjetpacks.network.payloads;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.util.JetpackUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record IncrementThrottlePayload() implements CustomPacketPayload {
    public static final Type<IncrementThrottlePayload> TYPE = new Type<>(IronJetpacks.resource("increment_throttle"));

    public static final StreamCodec<ByteBuf, IncrementThrottlePayload> STREAM_CODEC = StreamCodec.unit(new IncrementThrottlePayload());

    @Override
    public Type<IncrementThrottlePayload> type() {
        return TYPE;
    }

    public static void handleServer(IncrementThrottlePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            var stack = JetpackUtils.getEquippedJetpack(player);
            var item = stack.getItem();

            if (item instanceof JetpackItem) {
                JetpackUtils.incrementThrottle(stack);
            }
        });
    }
}