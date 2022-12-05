package champ.cards;

import charbosses.bosses.Merchant.CharBossMerchant;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.WallopEffect;

public class CheapShot extends AbstractChampCard {
    public final static String ID = makeID("CheapShot");

    public CheapShot() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 5;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new WallopEffect(30, m.hb.cX, m.hb.cY)));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (m.type != AbstractMonster.EnemyType.BOSS || m instanceof CharBossMerchant) {
            atb(new StunMonsterAction(m, p));
        } else {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        }
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}