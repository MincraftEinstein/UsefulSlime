package einstein.usefulslime.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;

public class BounceHandler {
    private static final IdentityHashMap<Entity, BounceHandler> bouncingEntities = new IdentityHashMap<>();
    public final LivingEntity entity;
    private int timer;
    private boolean wasInAir;
    private double bounce;
    private int bounceTick;
    private double lastMoveX;
    private double lastMoveZ;

    public BounceHandler(final LivingEntity entity, final double bounce) {
        this.entity = entity;
        this.timer = 0;
        this.wasInAir = false;
        this.bounce = bounce;

        if (bounce != 0) {
            bounceTick = entity.tickCount;
        } else {
            bounceTick = 0;
        }

        BounceHandler.bouncingEntities.put(entity, this);
    }

    @SubscribeEvent
    public void playerTick(final TickEvent.@NotNull PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == entity && !event.player.isFallFlying()) {
            if (event.player.tickCount == bounceTick) {
                final Vec3 vec3 = event.player.getDeltaMovement();
                event.player.setDeltaMovement(vec3.x, bounce, vec3.z);
                bounceTick = 0;
            }
            if (!entity.isOnGround() && entity.tickCount != bounceTick && (lastMoveX != entity.getDeltaMovement().x || lastMoveZ != entity.getDeltaMovement().z)) {
                final double d = 0.935D;
                final Vec3 vec32 = entity.getDeltaMovement();
                event.player.setDeltaMovement(vec32.x / d, vec32.y, vec32.z / d);
                entity.hasImpulse = true;
                lastMoveX = entity.getDeltaMovement().x;
                lastMoveZ = entity.getDeltaMovement().z;
            }
            if (wasInAir && entity.isOnGround()) {
                if (timer == 0) {
                    timer = entity.tickCount;
                } else if (entity.tickCount - timer > 5) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                    BounceHandler.bouncingEntities.remove(entity);
                }
            } else {
                timer = 0;
                wasInAir = true;
            }
        }
    }

    public static void addBounceHandler(final LivingEntity entity) {
        addBounceHandler(entity, 0);
    }

    public static void addBounceHandler(final LivingEntity entity, final double bounce) {
        if (!(entity instanceof Player) || entity instanceof FakePlayer) {
            return;
        }

        final BounceHandler handler = BounceHandler.bouncingEntities.get(entity);

        if (handler == null) {
            MinecraftForge.EVENT_BUS.register(new BounceHandler(entity, bounce));
        } else if (bounce != 0) {
            handler.bounce = bounce;
            handler.bounceTick = entity.tickCount;
        }
    }
}
