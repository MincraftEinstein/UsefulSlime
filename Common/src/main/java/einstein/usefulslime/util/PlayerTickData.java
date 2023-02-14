package einstein.usefulslime.util;

import net.minecraft.world.entity.player.Player;

public class PlayerTickData {

    private final Player player;

    public PlayerTickData(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
