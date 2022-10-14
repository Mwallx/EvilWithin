package downfall.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Cleric_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:Cleric";
    public static final String NAME;
    public static final String[] DESC;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    public static boolean heDead = true;
    public static boolean encountered = true;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESC = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurrentScreen curScreen;
    private final int gold;


    public Cleric_Evil() {
        super(NAME, DESC[0], "images/events/cleric.jpg");
        this.curScreen = CurrentScreen.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.gold = 50;
        } else {
            this.gold = 75;
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.gold + OPTIONS[4]);
        this.imageEventText.setDialogOption(OPTIONS[1]);

        heDead = false;
        encountered = true;
    }

    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);
            AbstractCard transCard = AbstractDungeon.getTransformedCard();
            logMetricTransformCard(ID, "Transformed", c, transCard);
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(transCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.curScreen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESC[1]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.gold));
                        AbstractDungeon.player.gainGold(this.gold);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        heDead = true;
                        logMetricGainGold(ID, "Punch", this.gold);
                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESC[2]);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, true, false, false);
                        break;
                }

                this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                this.imageEventText.clearRemainingOptions();
                this.curScreen = CurrentScreen.DONE;
                break;
            case DONE:
            default:
                this.openMap();
        }

    }


    private enum CurrentScreen {
        INTRO,
        DONE;

        CurrentScreen() {
        }
    }
}
