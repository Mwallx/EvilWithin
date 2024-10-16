package slimebound.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.BuffSecondarySlimeEffectsPower;

import java.util.ArrayList;

public class CommandAction extends AbstractGameAction {
    public void update() {
        isDone = true;
        BaseMod.logger.info("CommandAction update called");
        SpawnedSlime oldestUnstunnedSlime = findOldestUnstunnedSlime();
        BaseMod.logger.info("Oldest unstunned slime: " + (oldestUnstunnedSlime != null ? oldestUnstunnedSlime.getClass().getSimpleName() : "null"));
        if (oldestUnstunnedSlime != null) {
            addToTop(new TrigggerSpecificSlimeAttackAction(oldestUnstunnedSlime));
            BaseMod.logger.info("TrigggerSpecificSlimeAttackAction added for " + oldestUnstunnedSlime.getClass().getSimpleName());
        } else {
            BaseMod.logger.info("No unstunned slimes available to command");
        }
    }

    private SpawnedSlime findOldestUnstunnedSlime() {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
        for (int i = orbs.size() - 1; i >= 0; i--) {
            AbstractOrb orb = orbs.get(i);
            if (orb instanceof SpawnedSlime) {
                SpawnedSlime slime = (SpawnedSlime) orb;
                if (!slime.beingStunned) {
                    return slime;
                }
            }
        }
        return null;
    }
}