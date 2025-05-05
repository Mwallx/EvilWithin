package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.SlimeIntentEffect;

public class SlimeGainBlock extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private AbstractCreature owner;
    private int blockAmount;
    private SpawnedSlime slime;

    public SlimeGainBlock(AbstractCreature player, int blockAmount, SpawnedSlime slime) {
        this.owner = player;
        this.actionType = ActionType.BLOCK;
        this.duration = 0.1F;
        this.blockAmount = blockAmount;
        this.slime = slime;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }

        if (this.duration == 0.1F) {
            logger.info("Slime gaining block");

            AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, this.blockAmount));

            float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE) {
                speedTime = 0.0F;
            }

            AbstractDungeon.actionManager.addToTop(new VFXAction(new SlimeIntentEffect(slime.intentImage, slime, speedTime), speedTime));

            if (slime.movesToAttack) {
                slime.useFastAttackAnimation();
            }
        }

        tickDuration();

        if (this.isDone) {
            logger.info("Slime block gain complete");
        }
    }
}