package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;
import sneckomod.powers.TyphoonPlusPower;
import sneckomod.powers.TyphoonPower;

public class TyphoonFang extends AbstractSneckoCard {

    public final static String ID = makeID("TyphoonFang");

    //stupid intellij stuff ATTACK, ENEMY, BASIC

    // this card was a herculean effort to code

    private static final int DAMAGE = 10;

    public TyphoonFang() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
     //   baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "TyphoonFang.png");
        this.tags.add(SneckoMod.SNEKPROOF);
        this.tags.add(SneckoMod.OVERFLOW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// reused snek bite animation
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
            if (isOverflowActive() && !this.purgeOnUse) {
                if (!upgraded) {
                    applyToSelf(new TyphoonPower(1));
                }
                if (upgraded) {
                    applyToSelf(new TyphoonPlusPower(1));
                }
            }
        }

    //Glowverflow - make the card glow if overflow is active~
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isOverflowActive()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
