package sneckomod.cards;

import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class TrashToTreasure extends AbstractSneckoCard {
    public final static String ID = makeID("TrashToTreasure");

    public TrashToTreasure() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        this.exhaust = true;
    }

    public void upp() {
        this.exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RecycleAction());
    }
}