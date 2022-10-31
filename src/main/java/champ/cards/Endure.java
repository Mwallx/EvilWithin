package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.loadJokeCardImage;

public class Endure extends AbstractChampCard {
    public final static String ID = makeID("Endure");

    public Endure() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 7;
        loadJokeCardImage(this, "Endure.png");
    }

    public void upp() {
        upgradeBlock(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        if (!upgraded && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            baseBlock -= AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        super.applyPowersToBlock();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }
}