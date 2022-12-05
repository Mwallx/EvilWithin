package champ.cards;

import champ.ChampMod;
import champ.actions.ModifyBlockAndMagicAction;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class SkillfulDodge extends AbstractChampCard {
    public final static String ID = makeID("SkillfulDodge");

    public SkillfulDodge() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 4;
        baseMagicNumber = magicNumber = 4;
        baseDownfallMagic = downfallMagic = 3;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        loadJokeCardImage(this, "SkillfulDodge.png");
    }

    public void upp() {
        upgradeBlock(1);
        upgradeMagicNumber(1);
        upgradeDownfall(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new CounterPower(magicNumber));
        if (dcombo())
            atb(new ModifyBlockAndMagicAction(uuid, downfallMagic));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}