package theHexaghost.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.DivinityStance;
import curatedchallenges.CuratedChallenges;
import expansioncontent.Clashtastic;

@SpirePatch(
        clz = Clash.class,
        method = "use"
)
public class ClashtasticPatch {
    @SpirePostfixPatch
    public static void Postfix(Clash __instance, AbstractPlayer p, AbstractMonster m) {
        if (Clashtastic.ID.equals(CuratedChallenges.currentChallengeId)) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(DivinityStance.STANCE_ID));
        }
    }
}