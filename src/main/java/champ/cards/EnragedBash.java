package champ.cards;

import champ.ChampMod;
import champ.actions.ModifyMagicAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class EnragedBash extends AbstractChampCard {
    public final static String ID = makeID("EnragedBash");

    public EnragedBash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 1;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void upp() {
        upgradeDamage(3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int chosen;
        for (int i = 0; i < magicNumber; i++) {
            chosen = AbstractDungeon.cardRng.random(0,2);
            switch(chosen) {
                case 0: {
                    dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
                    break;
                }
                case 1: {
                    dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
                    break;
                }
                case 2: {
                    dmg(m, AbstractGameAction.AttackEffect.SMASH);
                    break;
                }
            }
        }
        if (bcombo()){
            atb(new ModifyMagicAction(this.uuid, 1));
            this.rawDescription = this.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}