package slimebound.cards;


import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.RotateAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Collections;


public class SplitSpecialist extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SplitSpecialist";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/splitspecialist.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    private static final CardStrings cardStrings;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SplitSpecialist() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.baseDamage = 9;
        this.magicNumber = this.baseMagicNumber = 0;
        SlimeboundMod.loadJokeCardImage(this, "SplitSpecialist.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = 0;
        AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.HungrySlime(), false, true, bonus, 0));
        AbstractDungeon.actionManager.addToBottom(new RotateAction(AttackSlime.class));

        if (this.upgraded) {
            addToBot(new CommandAction());
        }

        checkMinionMaster();
    }

    public AbstractCard makeCopy() {
        return new SplitSpecialist();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}