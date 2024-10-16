package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import slimebound.relics.GreedOozeRelic;

@SpirePatch(clz = OverlayMenu.class, method = "update")
public class OverlayMenuUpdatePatch {
    @SpirePostfixPatch
    public static void Postfix(OverlayMenu __instance) {
        // Call our custom update method after OverlayMenu's update
        GreedOozeRelic.updateGreedOoze();
    }
}