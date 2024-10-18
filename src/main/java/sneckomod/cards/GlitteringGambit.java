package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;
import sneckomod.actions.ChangeGoldAction;
@Deprecated
@CardIgnore

public class GlitteringGambit extends AbstractSneckoCard {
    public final static String ID = makeID("GlitteringGambit");

    //stupid intellij stuff SKILL, SELF, RARE

    private static final int MAGIC = -10;
    private static final int UPG_MAGIC = 10;

    public GlitteringGambit() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        //        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.SNEKPROOF);
        exhaust = true;
        tags.add(CardTags.HEALING);
        tags.add(SneckoMod.RNG);
        SneckoMod.loadJokeCardImage(this, "GlitteringGambit.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ChangeGoldAction((int) Math.ceil((getRandomNum(magicNumber, 30, this)))));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}