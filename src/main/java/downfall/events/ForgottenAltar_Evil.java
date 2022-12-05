package downfall.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;

public class ForgottenAltar_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:ForgottenAltar";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    public static final String[] DESCRIPTIONSALT;
    public static final String[] OPTIONSALT;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Forgotten Altar");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DESCRIPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS;
        OPTIONSALT = CardCrawlGame.languagePack.getEventString(ID).OPTIONS;
    }

    private final int healAmount;
    private final int hpLoss;
    private final int goldCost = 50;
    private CurScreen screen;

    public ForgottenAltar_Evil() {
        super(NAME, DESCRIPTIONS[0], "images/events/forgottenAltar.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;
        this.healAmount = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.25F) + 5;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLoss = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.35F);
        } else {
            this.hpLoss = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.25F);
        }

        if (AbstractDungeon.player.gold >= goldCost) {
            this.imageEventText.setDialogOption(OPTIONSALT[0] + this.goldCost + OPTIONSALT[1] + this.healAmount + OPTIONSALT[2]);
        } else {
            this.imageEventText.setDialogOption(OPTIONSALT[3] + this.goldCost + OPTIONSALT[4], true);
        }

        this.imageEventText.setDialogOption(OPTIONS[2] + 5 + OPTIONS[3] + this.hpLoss + OPTIONS[4]);
        this.imageEventText.setDialogOption(OPTIONSALT[5]);
    }

    protected void buttonEffect(int buttonPressed) {
        if (this.screenNum == 0) {
            switch (buttonPressed) {
                case 0:
                    this.imageEventText.clearAllDialogs();
                    AbstractDungeon.player.loseGold(this.goldCost);
                    AbstractDungeon.player.heal(this.healAmount + 10, true);
                    this.imageEventText.updateBodyText(DESCRIPTIONSALT[0]);
                    this.imageEventText.setDialogOption(OPTIONSALT[5]);
                    CardCrawlGame.sound.play("HEAL_1");
                    this.screenNum = 1;
                    logMetricHealAtCost(ID, "Offer Souls", goldCost, healAmount + 10);
                    return;
                case 1:

                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    this.imageEventText.setDialogOption(OPTIONSALT[5]);
                    AbstractDungeon.player.increaseMaxHp(5, false);
                    AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss));
                    CardCrawlGame.sound.play("HEAL_3");
                    this.screenNum = 1;
                    logMetricDamageAndMaxHPGain(ID, "Shed Blood", this.hpLoss, 5);
                    return;
                case 2:
                    this.imageEventText.clearAllDialogs();
                    this.imageEventText.updateBodyText(DESCRIPTIONSALT[1]);
                    this.imageEventText.setDialogOption(OPTIONSALT[5]);
                    this.screenNum = 1;
                    logMetricIgnored(ID);
                    return;
                default:
            }
        } else {
            this.openMap();
        }
    }

    private enum CurScreen {
        INTRO,
        END;

        CurScreen() {
        }
    }
}
