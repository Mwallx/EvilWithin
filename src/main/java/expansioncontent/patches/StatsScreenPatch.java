package expansioncontent.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import expansioncontent.achievements.DownfallAchievementGrid;

@SpirePatch(clz = StatsScreen.class, method = SpirePatch.CLASS)
public class StatsScreenPatch {
    private static DownfallAchievementGrid downfallAchievements;

    public static DownfallAchievementGrid getDownfallAchievements() {
        if (downfallAchievements == null) {
            downfallAchievements = new DownfallAchievementGrid();
        }
        return downfallAchievements;
    }
}