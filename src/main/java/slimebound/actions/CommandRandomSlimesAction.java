package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.orbs.GeneralSlime;
import slimebound.orbs.SpawnedSlime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CommandRandomSlimesAction extends AbstractGameAction {
    private int numberOfSlimesToCommand;

    public CommandRandomSlimesAction(int numberOfSlimes) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.numberOfSlimesToCommand = numberOfSlimes;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            ArrayList<AbstractOrb> commandableSlimes = new ArrayList<>();

            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof SpawnedSlime && !(orb instanceof GeneralSlime)) {
                    commandableSlimes.add(orb);
                }
            }

            if (commandableSlimes.size() > 0) {
                int slimesToCommand = Math.min(this.numberOfSlimesToCommand, commandableSlimes.size());
                Collections.shuffle(commandableSlimes, new Random(AbstractDungeon.miscRng.randomLong()));

                for (int i = 0; i < slimesToCommand; i++) {
                    AbstractOrb slimeToCommand = commandableSlimes.get(i);
                    AbstractDungeon.actionManager.addToTop(new TriggerSpecificSlimeAttackAction((SpawnedSlime)slimeToCommand));
                }
            }
        }

        this.tickDuration();
    }
}