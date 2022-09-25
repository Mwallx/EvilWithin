package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import static com.megacrit.cardcrawl.cards.red.PerfectedStrike.countCards;

public class SnekBeam extends AbstractSneckoCard {
    public final static String ID = makeID("SnekBeam");

    public SnekBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 0;
        isMultiDamage = true;
        this.exhaust = true;
    }

    public static int countCards() {
        int i = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractUnknownCard) {
                i++;
            }
        }
        return i;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.rawDescription = upgraded ? UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1] :
                UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
        this.rawDescription = upgraded ? UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1] :
                UPGRADE_DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal)));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}