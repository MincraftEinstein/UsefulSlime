package einstein.usefulslime.util;

import java.util.IdentityHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
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
            this.bounceTick = entityLiving.ticksExisted;
        }
        else {
            this.bounceTick = 0;
        }
        BounceHandler.bouncingEntities.put(entityLiving, this);
    }
    
    @SubscribeEvent
    public void playerTickPost(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == this.entityLiving && !event.player.isElytraFlying()) {
            if (event.player.ticksExisted == this.bounceTick) {
                final Vector3d vec3d = event.player.getMotion();
                event.player.setMotion(vec3d.x, this.bounce, vec3d.z);
                this.bounceTick = 0;
            }
            if (!this.entityLiving.isOnGround() && this.entityLiving.ticksExisted != this.bounceTick && (this.lastMovX != this.entityLiving.getMotion().x || this.lastMovZ != this.entityLiving.getMotion().z)) {
                final double f = 0.935;
                final Vector3d vec3d2 = this.entityLiving.getMotion();
                event.player.setMotion(vec3d2.x / f, vec3d2.y, vec3d2.z / f);
                this.entityLiving.isAirBorne = true;
                this.lastMovX = this.entityLiving.getMotion().x;
                this.lastMovZ = this.entityLiving.getMotion().z;
            }
            if (this.wasInAir && this.entityLiving.isOnGround()) {
                if (this.timer == 0) {
                    this.timer = this.entityLiving.ticksExisted;
                }
                else if (this.entityLiving.ticksExisted - this.timer > 5) {
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
        if (!(entity instanceof PlayerEntity) || entity instanceof FakePlayer) {
            return;
        }
        final BounceHandler handler = BounceHandler.bouncingEntities.get(entity);
        if (handler == null) {
            MinecraftForge.EVENT_BUS.register(new BounceHandler(entity, bounce));
        }
        else if (bounce != 0.0) {
            handler.bounce = bounce;
            handler.bounceTick = entity.ticksExisted;
        }
    }
}
