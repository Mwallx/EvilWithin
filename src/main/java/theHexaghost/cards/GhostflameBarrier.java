package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.GhostFlameBarrierPower;
import theHexaghost.powers.SoulburnOnExhaustPower;
import theHexaghost.vfx.SpookyFlameBarrier;

public class GhostflameBarrier extends AbstractHexaCard {

    public final static String ID = makeID("Whisper");

    //

    private static final int DAMAGE = 10;
//    private static final int UPG_BLOCK = 4;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public GhostflameBarrier() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
//        baseBlock = BLOCK;
        baseDamage = DAMAGE;
//        baseMagicNumber = magicNumber = MAGIC;
        baseBurn = burn = MAGIC;
        HexaMod.loadJokeCardImage(this, "GhostflameBarrier.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new ApplyPowerAction(m, p, new SoulburnOnExhaustPower(m, burn)));

//        if (Settings.FAST_MODE) {// 38
//            this.addToBot(new VFXAction(p, new SpookyFlameBarrier(p.hb.cX, p.hb.cY), 0.1F));// 39
//        } else {
//            this.addToBot(new VFXAction(p, new SpookyFlameBarrier(p.hb.cX, p.hb.cY), 0.5F));// 41
//        }
//        blck();

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
//            upgradeBlock(UPG_BLOCK);
            upgradeDamage(2);
            upgradeBurn(UPG_MAGIC);
        }
    }
}