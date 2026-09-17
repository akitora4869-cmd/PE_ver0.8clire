package jp.evolvegame.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.scoreboard.Team;

public final class EvolveClientState {
    private static MonsterStage stage = MonsterStage.NONE;
    private static boolean hunter = false;
    private static boolean evolving = false;
    private static boolean perspectiveApplied = false;

    private EvolveClientState() {}

    public static MonsterStage stage() { return stage; }
    public static boolean isMonster() { return stage != MonsterStage.NONE; }
    public static boolean isHunter() { return hunter; }
    public static boolean isEvolving() { return evolving; }

    public static int stageNumber() {
        return switch (stage) {
            case STAGE_1 -> 1;
            case STAGE_2 -> 2;
            case STAGE_3 -> 3;
            default -> 0;
        };
    }

    public static float maxMonsterHealth() {
        return switch (stage) {
            case STAGE_2 -> 140.0f;
            case STAGE_3 -> 180.0f;
            case STAGE_1 -> 100.0f;
            default -> 20.0f;
        };
    }

    public static float maxMonsterArmor() {
        return switch (stage) {
            case STAGE_2 -> 80.0f;
            case STAGE_3 -> 120.0f;
            case STAGE_1 -> 50.0f;
            default -> 0.0f;
        };
    }

    public static void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            stage = MonsterStage.NONE;
            hunter = false;
            evolving = false;
            perspectiveApplied = false;
            return;
        }

        MonsterStage nextStage = MonsterStage.NONE;
        boolean nextHunter = false;
        boolean nextEvolving = false;
        Team team = player.getScoreboardTeam();
        if (team != null) {
            String name = team.getName();
            if (name.startsWith("evolve_monster_s1")) nextStage = MonsterStage.STAGE_1;
            else if (name.startsWith("evolve_monster_s2")) nextStage = MonsterStage.STAGE_2;
            else if (name.startsWith("evolve_monster_s3")) nextStage = MonsterStage.STAGE_3;
            nextEvolving = name.endsWith("_e") && nextStage != MonsterStage.NONE;
            nextHunter = "evolve_hunter".equals(name);
        }

        boolean roleChanged = (nextStage != stage) || (nextHunter != hunter);
        stage = nextStage;
        hunter = nextHunter;
        evolving = nextEvolving;

        if (roleChanged) perspectiveApplied = false;
        if (!perspectiveApplied) {
            if (isMonster()) {
                client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                perspectiveApplied = true;
            } else if (isHunter()) {
                client.options.setPerspective(Perspective.FIRST_PERSON);
                perspectiveApplied = true;
            }
        }
    }
}
