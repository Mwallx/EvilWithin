package guardian.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;

public class SphericShield extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SphericShield");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/sphereShieldSkill.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SphericShield() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.baseBlock = 16;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.BorderFlashEffect(com.badlogic.gdx.graphics.Color.GOLD, true));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        addToBot(new ChangeStanceAction(new DefensiveMode()));
    }

    public AbstractCard makeCopy() {
        return new SphericShield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(8);
        }
    }

    public void updateDescription() {
        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }
}


