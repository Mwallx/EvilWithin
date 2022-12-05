package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.DrawPower;
import sneckomod.powers.MastersEyePower;

public class MasterEye extends AbstractSneckoCard {
    public final static String ID = makeID("MasterEye");

    public MasterEye() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        SneckoMod.loadJokeCardImage(this, "MasterEye.png");
    }

    public void upp() {
        upgradeBaseCost(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ConfusionPower(p));
        applyToSelf(new DrawPower(p, magicNumber));
    }
}