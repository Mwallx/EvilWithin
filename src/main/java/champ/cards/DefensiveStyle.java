package champ.cards;

import champ.ChampMod;
import champ.powers.DefensiveStylePower;
import champ.powers.FocusedDefPower;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class DefensiveStyle extends AbstractChampCard {
    public final static String ID = makeID("DefensiveStyle");

    public DefensiveStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        baseMagicNumber = magicNumber = 1;
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        applyToSelf(new DefensiveStylePower(magicNumber));
    }
}