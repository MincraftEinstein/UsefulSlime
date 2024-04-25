package einstein.usefulslime.util;

import einstein.usefulslime.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.IdentityHashMap;

public class BounceHandler {

    public static final IdentityHashMap<Entity, BounceHandler> BOUNCING_ENTITIES = new IdentityHashMap<>();

    public final LivingEntity entity;
    private int timer = 0;
    private boolean wasInAir = false;
    private double bounce;
    private int bounceTick;
    private double lastMoveX;
    private double lastMoveZ;

    public BounceHandler(LivingEntity entity, double bounce) {
        this.entity = entity;
        this.bounce = bounce;
        bounceTick = bounce != 0 ? entity.tickCount : 0;
        BOUNCING_ENTITIES.put(entity, this);
    }

    public void onPlayerTick(Player player) {
        if (player == entity) {
            if (player.getAbilities().flying || entity.isSwimming() || entity.isInWaterOrBubble() || entity.onClimbable() || entity.isSpectator() || entity.isFallFlying()) {
                BOUNCING_ENTITIES.remove(entity);
                return;
            }

            if (player.tickCount == bounceTick) {
                Vec3 playerMovement = player.getDeltaMovement();
                player.setDeltaMovement(playerMovement.x, bounce, playerMovement.z);
                bounceTick = 0;
            }

            if (!entity.onGround() && entity.tickCount != bounceTick && (lastMoveX != entity.getDeltaMovement().x || lastMoveZ != entity.getDeltaMovement().z)) {
                double d = 0.935D;
                Vec3 entityMovement = entity.getDeltaMovement();
                player.setDeltaMovement(entityMovement.x / d, entityMovement.y, entityMovement.z / d);
                entity.hasImpulse = true;
                lastMoveX = entity.getDeltaMovement().x;
                lastMoveZ = entity.getDeltaMovement().z;
            }

            if (wasInAir && entity.onGround()) {
                if (timer == 0) {
                    timer = entity.tickCount;
                }
                else if (entity.tickCount - timer > 5) {
                    BOUNCING_ENTITIES.remove(entity);
                }
                return;
            }
            timer = 0;
            wasInAir = true;
        }
    }

    public static void addBounceHandler(LivingEntity entity) {
        addBounceHandler(entity, 0);
    }

    public static void addBounceHandler(LivingEntity entity, double bounce) {
        if (!(entity instanceof Player) || Services.PLATFORM.isFakePlayer(entity)) {
            return;
        }

        BounceHandler handler = BOUNCING_ENTITIES.get(entity);

        if (handler == null) {
            new BounceHandler(entity, bounce);
        }
        else if (bounce != 0) {
            handler.bounce = bounce;
            handler.bounceTick = entity.tickCount;
        }
    }
}
