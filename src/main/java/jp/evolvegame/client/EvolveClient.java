package jp.evolvegame.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class EvolveClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(EvolveClientState::tick);
        MonsterHudRenderer.register();
        HunterHudRenderer.register();
    }
}
