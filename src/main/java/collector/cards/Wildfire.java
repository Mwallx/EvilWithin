package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Wildfire extends AbstractCollectorCard {
    public final static String ID = makeID(Wildfire.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 16, 5, , , , 

    public Wildfire() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 16;
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.cost == -2 || card.hasTag(expansionContentMod.UNPLAYABLE)) {
                        att(new GainBlockAction(p, block));
                        att(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
                    }
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(5);
        upgradeBlock(1);
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cost == -2) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}