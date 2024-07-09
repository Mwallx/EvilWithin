package expansioncontent.util;

public class DownfallAchievementVariables {
    public static boolean threeShapesFought = false;
    public static int slimesEvoked = 0;
    public static int gemsSocketed = 0;
    public static int cardsReturnedFromStasis = 0;
    public static int enteredUltimateStance = 0;
    private static final String INT_SAVE_ID = "downfall_achievements_int";
    private static final String BOOL_SAVE_ID = "downfall_achievements_bool";

    public static void resetAchievementVariables() {
    }

    public static void resetBattleAchievementVariables() {
        slimesEvoked = 0;
    }

    public static void resetTurnAchievementVariables() {
        cardsReturnedFromStasis = 0;
        enteredUltimateStance = 0;
    }

    public static void resetFloorAchievementVariables() {
        gemsSocketed = 0;
    }

}
