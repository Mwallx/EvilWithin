package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.NoApplyRandomDamageAction;

public class DiceBoulder extends AbstractSneckoCard {
    public final static String ID = makeID("DiceBoulder");

    public DiceBoulder() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1; //Min Damage
        baseDamage = 27; //Max Damage
        tags.add(SneckoMod.RNG);
    }

    public void upgrade() {
        this.upgradeDamage(3 + timesUpgraded);
        upgradeMagicNumber(6 + timesUpgraded);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new NoApplyRandomDamageAction(m, magicNumber, damage, 1, AbstractGameAction.AttackEffect.BLUNT_HEAVY, this, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public void applyPowers() {
        int maxDamage = baseDamage;
        baseDamage = baseMagicNumber;
        super.applyPowers();

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = isDamageModified;

        baseDamage = maxDamage;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        int maxDamage = baseDamage;
        baseDamage = baseMagicNumber;
        super.calculateCardDamage(mo); // takes baseDamage and applies things like Strength or Pen Nib to set damage

        magicNumber = damage; // magic number holds the first condition's modified damage, so !M! will work
        isMagicNumberModified = isDamageModified;

        // repeat so damage holds the second condition's damage
        baseDamage = maxDamage;
        super.calculateCardDamage(mo);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DiceBoulder();
    }
}