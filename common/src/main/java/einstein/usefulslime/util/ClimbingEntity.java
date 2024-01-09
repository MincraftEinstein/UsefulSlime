package einstein.usefulslime.util;

public interface ClimbingEntity {

    boolean usefulSlime$verticalCollisionAbove();

    boolean usefulSlime$canHangClimb();

    boolean usefulSlime$canWallClimb();

    void usefulSlime$setHangClimbing(boolean hangClimbing);

    void usefulSlime$setWallClimbing(boolean wallClimbing);
}
