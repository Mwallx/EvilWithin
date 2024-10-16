package slimebound.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.StunSlimeAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PotencyPower;

import java.util.ArrayList;

public class Reprimand extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Reprimand";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/reprimand.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Reprimand() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.exhaust = true;
       // SlimeboundMod.loadJokeCardImage(this, "Reprimand.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int stunnedSlimes = 0;
        ArrayList<AbstractOrb> orbs = p.orbs;

        // Stun all Slimes
        for (AbstractOrb orb : orbs) {
            if (orb instanceof SpawnedSlime) {
                addToBot(new StunSlimeAction());
                stunnedSlimes++;
            }
        }

        // Gain PotencyPower for each Stunned Slime
        if (stunnedSlimes > 0) {
            addToBot(new ApplyPowerAction(p, p, new PotencyPower(p, p, stunnedSlimes), stunnedSlimes));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reprimand();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}