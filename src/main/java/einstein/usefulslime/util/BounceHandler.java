package einstein.usefulslime.util;

import java.util.IdentityHashMap;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BounceHandler
{
    private static final IdentityHashMap<Entity, BounceHandler> bouncingEntities = new IdentityHashMap<Entity, BounceHandler>();
    public final LivingEntity entityLiving;
    private int timer;
    private boolean wasInAir;
    private double bounce;
    private int bounceTick;
    private double lastMovX;
    private double lastMovZ;
    
    public BounceHandler(final LivingEntity entityLiving, final double bounce) {
        this.entityLiving = entityLiving;
        this.timer = 0;
        this.wasInAir = false;
        this.bounce = bounce;
        if (bounce != 0.0) {
            this.bounceTick = entityLiving.tickCount;
        }
        else {
            this.bounceTick = 0;
        }
        BounceHandler.bouncingEntities.put(entityLiving, this);
    }
    
    @SubscribeEvent
    public void playerTickPost(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == this.entityLiving && !event.player.isFallFlying()) {
            if (event.player.tickCount == this.bounceTick) {
                final Vec3 vec3d = event.player.getDeltaMovement();
                event.player.setDeltaMovement(vec3d.x, this.bounce, vec3d.z);
                this.bounceTick = 0;
            }
            if (!this.entityLiving.isOnGround() && this.entityLiving.tickCount != this.bounceTick && (this.lastMovX != this.entityLiving.getDeltaMovement().x || this.lastMovZ != this.entityLiving.getDeltaMovement().z)) {
                final double f = 0.935;
                final Vec3 vec3d2 = this.entityLiving.getDeltaMovement();
                event.player.setDeltaMovement(vec3d2.x / f, vec3d2.y, vec3d2.z / f);
                this.entityLiving.hasImpulse = true;
                this.lastMovX = this.entityLiving.getDeltaMovement().x;
                this.lastMovZ = this.entityLiving.getDeltaMovement().z;
            }
            if (this.wasInAir && this.entityLiving.isOnGround()) {
                if (this.timer == 0) {
                    this.timer = this.entityLiving.tickCount;
                }
                else if (this.entityLiving.tickCount - this.timer > 5) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                    BounceHandler.bouncingEntities.remove(this.entityLiving);
                }
            }
            else {
                this.timer = 0;
                this.wasInAir = true;
            }
        }
    }
    
    public static void addBounceHandler(final LivingEntity entity) {
        addBounceHandler(entity, 0.0);
    }
    
    public static void addBounceHandler(final LivingEntity entity, final double bounce) {
        if (!(entity instanceof Player) || entity instanceof FakePlayer) {
            return;
        }
        final BounceHandler handler = BounceHandler.bouncingEntities.get(entity);
        if (handler == null) {
            MinecraftForge.EVENT_BUS.register(new BounceHandler(entity, bounce));
        }
        else if (bounce != 0.0) {
            handler.bounce = bounce;
            handler.bounceTick = entity.tickCount;
        }
    }
}
