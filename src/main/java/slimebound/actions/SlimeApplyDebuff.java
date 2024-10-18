package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeIntentEffect;

public class SlimeApplyDebuff extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private AbstractCreature owner;
    private int debuffAmount;
    private SpawnedSlime slime;

    public SlimeApplyDebuff(AbstractCreature player, int debuffAmount, SpawnedSlime slime) {
        this.owner = player;
        this.actionType = ActionType.DEBUFF;
        this.duration = 0.1F;
        this.debuffAmount = debuffAmount;
        this.slime = slime;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }

        if (this.duration == 0.1F) {
            logger.info("Slime applying debuff");

            AbstractMonster targetMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (targetMonster != null) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(targetMonster, this.owner, new SlimedPower(targetMonster, this.owner, this.debuffAmount), this.debuffAmount));
            }

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
            logger.info("Slime debuff application complete");
        }
    }
}