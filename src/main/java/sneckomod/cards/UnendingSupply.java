package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;
import sneckomod.powers.UnendingSupplyPower;
@Deprecated
@CardIgnore
public class UnendingSupply extends AbstractSneckoCard {

    public final static String ID = makeID("UnendingSupply");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public UnendingSupply() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        //   super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "UnendingSupply.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new UnendingSupplyPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}