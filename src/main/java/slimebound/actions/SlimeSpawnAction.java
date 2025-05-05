package slimebound.actions;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import slimebound.SlimeboundMod;
import slimebound.cards.OneTwoCombo;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.SlimeboundEnum;
import slimebound.powers.DuplicatedFormNoHealPower;
import slimebound.relics.TarBlob;


public class SlimeSpawnAction extends AbstractGameAction {
    private AbstractOrb orbType;
    private boolean SelfDamage = true;
    private boolean upgraded = false;
    private int currentAmount;
    private int upgradedamount;
    private int count;

    private int bonusUniqueFocus;
    private int bonusSecondary;

    public SlimeSpawnAction(AbstractOrb newOrbType, boolean upgraded, boolean SelfDamage, int bonusUniqueFocus, int bonusSecondary) {

        this(newOrbType, upgraded, false);

        this.bonusUniqueFocus = bonusUniqueFocus;
        this.bonusSecondary = bonusSecondary;

    }


    public SlimeSpawnAction(AbstractOrb newOrbType, boolean upgraded, boolean SelfDamage) {

        this.duration = Settings.ACTION_DUR_FAST;

        if (newOrbType != null) {
            this.orbType = newOrbType;
            SpawnedSlime s = (SpawnedSlime) newOrbType;

            this.upgradedamount = s.upgradedInitialBoost;
        }

        this.upgraded = upgraded;
        this.SelfDamage = false;
        this.currentAmount = 4;
        if (AbstractDungeon.player.hasRelic(TarBlob.ID)) {
            currentAmount++;
        }


    }


    public void update() {
        ////SlimeboundMod.logger.info("Starting slime spawn action");

        if (AbstractDungeon.player.maxOrbs > 0 || (AbstractDungeon.player.masterMaxOrbs == 0 && AbstractDungeon.player.maxOrbs == 0)) {

            int currentHealth = AbstractDungeon.player.currentHealth;
            if (this.orbType != null) {
                // Check if the player already has this type of Slime
                boolean slimeExists = false;
                AbstractOrb existingSlime = null;
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    if (orb.getClass().equals(this.orbType.getClass())) {
                        slimeExists = true;
                        existingSlime = orb;
                        break;
                    }
                }

                if (slimeExists && existingSlime instanceof SpawnedSlime) {
                    // Upgrade existing Slime
                    ((SpawnedSlime) existingSlime).upgrade();
                } else {
                    // Spawn new Slime as before
                    if (this.bonusUniqueFocus > 0) {
                        ((SpawnedSlime) this.orbType).applyUniqueFocus(bonusUniqueFocus);
                    }
                    AbstractDungeon.player.channelOrb(this.orbType);
                }
                for (AbstractCard q : AbstractDungeon.player.exhaustPile.group) {
                    if (q instanceof OneTwoCombo) {
                        ((OneTwoCombo) q).onSplit();
                    }
                }
                for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
                    if (q instanceof OneTwoCombo) {
                        ((OneTwoCombo) q).onSplit();
                    }
                }
                for (AbstractCard q : AbstractDungeon.player.discardPile.group) {
                    if (q instanceof OneTwoCombo) {
                        ((OneTwoCombo) q).onSplit();
                    }
                }
            }


            if (this.upgraded) {
                SlimeboundMod.bumpnextlime = true;

                AbstractDungeon.actionManager.addToTop(new SlimeBuffUpgraded(this.upgradedamount, SlimeboundMod.mostRecentSlime));
            }
            tickDuration();


            this.isDone = true;


        }
        this.isDone = true;
    }

}



