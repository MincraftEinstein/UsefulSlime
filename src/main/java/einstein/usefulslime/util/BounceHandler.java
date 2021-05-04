package einstein.usefulslime.util;

import java.util.IdentityHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BounceHandler
{
    private static final IdentityHashMap<Entity, BounceHandler> bouncingEntities = new IdentityHashMap<Entity, BounceHandler>();
    public final EntityLivingBase entityLiving;
    private int timer;
    private boolean wasInAir;
    private double bounce;
    private int bounceTick;
    private double lastMovX;
    private double lastMovZ;
    
    public BounceHandler(final EntityLivingBase entityLiving, final double bounce) {
        this.entityLiving = entityLiving;
        timer = 0;
        wasInAir = false;
        this.bounce = bounce;
        if (bounce != 0.0) {
            bounceTick = entityLiving.ticksExisted;
        }
        else {
            bounceTick = 0;
        }
        bouncingEntities.put(entityLiving, this);
    }
    
    @SubscribeEvent
    public void playerTickPost(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player == entityLiving && !event.player.isElytraFlying()) {
            if (event.player.ticksExisted == bounceTick) {
                event.player.motionY = bounce;
                this.bounceTick = 0;
            }
            if (!entityLiving.onGround && entityLiving.ticksExisted != bounceTick && (lastMovX != entityLiving.motionX || lastMovZ != entityLiving.motionZ)) {
                final double f = 0.935;
                entityLiving.motionX /= f;
                entityLiving.motionZ /= f;
                entityLiving.isAirBorne = true;
                lastMovX = entityLiving.motionX;
                lastMovZ = entityLiving.motionZ;
            }
            if (wasInAir && entityLiving.onGround) {
                if (timer == 0) {
                    timer = entityLiving.ticksExisted;
                }
                else if (entityLiving.ticksExisted - timer > 5) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                    bouncingEntities.remove(entityLiving);
                }
            }
            else {
                timer = 0;
                wasInAir = true;
            }
        }
    }
    
    public static void addBounceHandler(final EntityLivingBase entity) {
        addBounceHandler(entity, 0);
    }
    
    public static void addBounceHandler(final EntityLivingBase entity, final double bounce) {
        if (!(entity instanceof EntityPlayer) || entity instanceof FakePlayer) {
            return;
        }
        final BounceHandler handler = bouncingEntities.get(entity);
        if (handler == null) {
            MinecraftForge.EVENT_BUS.register(new BounceHandler(entity, bounce));
        }
        else if (bounce != 0) {
            handler.bounce = bounce;
            handler.bounceTick = entity.ticksExisted;
        }
    }
}