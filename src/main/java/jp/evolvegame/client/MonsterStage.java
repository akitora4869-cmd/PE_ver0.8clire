package jp.evolvegame.client;

public enum MonsterStage {
    NONE(0), STAGE_1(1), STAGE_2(2), STAGE_3(3);
    private final int id;
    MonsterStage(int id) { this.id = id; }
    public int id() { return id; }
}
