//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import slimebound.actions.MakeTempCardInHandActionReduceCost;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class NopeAction extends AbstractGameAction {
    private static final String[] EXTENDED_DESCRIPTION = CardCrawlGame.languagePack.getCardStrings(SneckoMod.makeID("Nope")).EXTENDED_DESCRIPTION;
    private AbstractPlayer p;
    private boolean reduction;

    public NopeAction(boolean reduceCost) {
        this.actionType = ActionType.CARD_MANIPULATION;// 22
        this.p = AbstractDungeon.player;// 23
        this.duration = Settings.ACTION_DUR_FAST;// 24
        this.reduction = false;
    }// 26

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 30

            if (this.p.hand.group.size() > 1) {// 74
                AbstractDungeon.handCardSelectScreen.open(EXTENDED_DESCRIPTION[0], 1, false, false);// 75
                this.tickDuration();// 76
                return;// 77
            }

            if (this.p.hand.group.size() == 1) {// 78
                AbstractCard c = p.hand.getTopCard();
                p.hand.moveToExhaustPile(c);
                AbstractCard.CardType q = c.type;
                AbstractCard card;

                if (c.type == AbstractCard.CardType.CURSE) {
                    card = AbstractDungeon.returnRandomCurse();
                } else if (c.type == AbstractCard.CardType.STATUS) {
                    card = SneckoMod.getRandomStatus().makeCopy();
                } else {
                    card = SneckoMod.getSpecificClassCard(c.color);
                }

                if (reduction) {
                    this.addToBot(new MakeTempCardInHandActionReduceCost(card));// 34
                } else
                {
                    this.addToBot(new MakeTempCardInHandAction(card));
                }

                this.isDone = true;// 82
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {// 87
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.moveToExhaustPile(c);
                AbstractCard card = null;
                if (c.type == AbstractCard.CardType.CURSE) {
                    card = AbstractDungeon.returnRandomCurse();
                } else if (c.type == AbstractCard.CardType.STATUS) {
                    card = SneckoMod.getRandomStatus().makeCopy();
                } else {
                    card = SneckoMod.getSpecificClassCard(c.color);
                }

                if (reduction) {
                    this.addToBot(new MakeTempCardInHandActionReduceCost(card));// 34
                } else {
                    this.addToBot(new MakeTempCardInHandAction(card));
                }
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;// 96
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();// 97
            this.isDone = true;// 98
        }
        this.tickDuration();// 101
    }// 102

}
