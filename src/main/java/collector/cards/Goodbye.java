package collector.cards;

import collector.powers.DoomPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import expansioncontent.util.DownfallAchievementUnlocker;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemyTop;
import static collector.util.Wiz.atb;

public class Goodbye extends AbstractCollectorCard {
    public final static String ID = makeID(Goodbye.class.getSimpleName());

    public Goodbye() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(m, new VerticalAuraEffect(Color.BLACK, m.hb.cX, m.hb.cY), 0.1F));
        this.addToBot(new SFXAction("ATTACK_FIRE"));
        this.addToBot(new VFXAction(m, new VerticalAuraEffect(Color.PURPLE, m.hb.cX, m.hb.cY), 0.1F));
        this.addToBot(new VFXAction(m, new VerticalAuraEffect(Color.CYAN, m.hb.cX, m.hb.cY), 0.0F));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (m.hasPower(DoomPower.POWER_ID)) {
                    int initialDoom = m.getPower(DoomPower.POWER_ID).amount;
                    int doomToApply = initialDoom * magicNumber;
                    applyToEnemyTop(m, new DoomPower(m, doomToApply));

                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            if (m.hasPower(DoomPower.POWER_ID)) {
                                int finalDoom = m.getPower(DoomPower.POWER_ID).amount;
                                if (finalDoom - initialDoom >= 100) {
                                    DownfallAchievementUnlocker.unlockAchievement("SAYONARA");
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}