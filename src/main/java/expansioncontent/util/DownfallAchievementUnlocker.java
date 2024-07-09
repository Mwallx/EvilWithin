package expansioncontent.util;

import com.megacrit.cardcrawl.core.Settings;
import downfall.downfallMod;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;

public class DownfallAchievementUnlocker {
    public static void unlockAchievement(String key) {
        String fullKey = downfallMod.makeID(key);
        if (!Settings.isShowBuild && Settings.isStandardRun()) {
            if (!achievementPref.getBoolean(fullKey, false)) {
                achievementPref.putBoolean(fullKey, true);
            }

            achievementPref.flush();
        }
    }
}