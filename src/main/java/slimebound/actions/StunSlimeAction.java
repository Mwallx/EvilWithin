package slimebound.actions;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.StunStarEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.BronzeSlime;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.PreventTackleDamagePower;
import slimebound.relics.SelfDamagePreventRelic;

import java.util.ArrayList;


public class StunSlimeAction extends AbstractGameAction {
    private static final float DURATION = 0.5f;

    public StunSlimeAction() {
        this.duration = DURATION;
        BaseMod.logger.info("StunSlimeAction created");
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            BaseMod.logger.info("StunSlimeAction update started");

            // Check if the player has the SelfDamagePreventRelic
            if (AbstractDungeon.player.hasRelic(SelfDamagePreventRelic.ID) || (AbstractDungeon.player.hasPower(PreventTackleDamagePower.POWER_ID))) {
                BaseMod.logger.info("SelfDamagePreventRelic found, skipping stun action");
                this.isDone = true;
                return;
            }

            SpawnedSlime slimeToStun = findFirstUnstunnedSlime();

            if (slimeToStun != null) {
                slimeToStun.beingStunned = true;
                slimeToStun.updateStunnedState(true);
                BaseMod.logger.info("Slime stunned: " + slimeToStun.getClass().getSimpleName());

                // Visual effect for stunning
                for (int i = 0; i < 5; i++) {
                    AbstractDungeon.effectsQueue.add(new StunStarEffect(slimeToStun.cX, slimeToStun.cY));
                }
                BaseMod.logger.info("Stun effects added");
            } else {
                BaseMod.logger.info("No unstunned slimes to stun");
            }
        }

        this.tickDuration();
        if (this.isDone) {
            BaseMod.logger.info("StunSlimeAction completed");
        }
    }

    private SpawnedSlime findFirstUnstunnedSlime() {
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
