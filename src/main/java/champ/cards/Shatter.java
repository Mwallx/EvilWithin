package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class Shatter extends AbstractChampCard {
    public final static String ID = makeID("Shatter");

    public Shatter() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 1;
    }

    public void upp() {
        upgradeDamage(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RemoveAllBlockAction(m, p));
        if (upgraded) atb(new RemoveSpecificPowerAction(m, p, ArtifactPower.POWER_ID));
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);

    }
}