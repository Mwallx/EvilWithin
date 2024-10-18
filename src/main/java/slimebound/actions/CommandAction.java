package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;

public class CommandAction extends AbstractGameAction {

    public void update() {
        isDone = true;
        AbstractOrb oldestOrb = SlimeboundMod.getLeadingSlime();
        if (oldestOrb != null) {
            addToTop(new TriggerSpecificSlimeAttackAction(oldestOrb));

        }
    }
}
