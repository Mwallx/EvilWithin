package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.powers.Bruise;
import slimebound.SlimeboundMod;
import slimebound.actions.ExhumeLickAction;
import slimebound.actions.ReturnRandom0Cost;
import slimebound.actions.TackleSelfDamageAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PreventTackleDamagePower;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;


public class HungryTackle extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:HungryTackle";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/tackle.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    public HungryTackle() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.TACKLE);


        this.baseDamage = 9;
        baseSelfDamage = this.selfDamage = 3;


        this.magicNumber = this.baseMagicNumber = 1;
        SlimeboundMod.loadJokeCardImage(this, "HungryTackle.png");

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (!AbstractDungeon.player.hasPower(PreventTackleDamagePower.POWER_ID))
            this.addToBot(new ApplyPowerAction(p, p, new Bruise(p, baseSelfDamage), baseSelfDamage, true, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new MakeTempCardInHandAction(new Lick(), this.magicNumber));
    }

    public AbstractCard makeCopy() {

        return new HungryTackle();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(3);

             this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
            upgradeMagicNumber(1);

        }

    }
}

