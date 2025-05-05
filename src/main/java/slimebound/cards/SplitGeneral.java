package slimebound.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.GeneralSlime;
import slimebound.actions.RotateAction;
import slimebound.patches.AbstractCardEnum;

public class SplitGeneral extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SplitGeneral";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/splitgeneral.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;
    private static final CardStrings cardStrings;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitGeneral() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        SlimeboundMod.loadJokeCardImage(this, "SplitGeneral.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = 0;
        AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new GeneralSlime(), false, true, bonus, 0));
        AbstractDungeon.actionManager.addToBottom(new RotateAction(GeneralSlime.class));
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        checkMinionMaster();
    }

    public AbstractCard makeCopy() {
        return new SplitGeneral();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}