package slimebound.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.orbs.SpawnedSlime;

public class UnstunSlimesAction extends AbstractGameAction {
    public UnstunSlimesAction() {
        BaseMod.logger.info("UnstunSlimesAction created");
    }

    @Override
    public void update() {
        BaseMod.logger.info("UnstunSlimesAction update started");
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof SpawnedSlime) {
                SpawnedSlime slime = (SpawnedSlime) orb;
                if (slime.beingStunned) {
                    slime.beingStunned = false;
                   // slime.updateIntentImage(); // Assuming you have a method to update the intent image
                    BaseMod.logger.info("Slime unstunned: " + slime.getClass().getSimpleName());
                }
            }
        }
        this.isDone = true;
        BaseMod.logger.info("UnstunSlimesAction completed");
    }
}
