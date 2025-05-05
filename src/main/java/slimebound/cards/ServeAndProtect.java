package slimebound.cards;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.BlurPower;
import downfall.util.SelectCardsCenteredAction;
import slimebound.SlimeboundMod;
import slimebound.actions.TendrilFlailAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.HashSet;


public class ServeAndProtect extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ServeAndProtect";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/formablockade.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ServeAndProtect() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.baseBlock = 3;
        this.magicNumber = this.baseMagicNumber = 1;
        SlimeboundMod.loadJokeCardImage(this, "ServeAndProtect.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        HashSet<String> uniqueSlimeTypes = new HashSet<>();

        // Count unique Slime types
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                uniqueSlimeTypes.add(((SpawnedSlime) o).ID);
            }
        }

        int uniqueSlimeCount = uniqueSlimeTypes.size();

        // Gain Block for each unique Slime
        if (uniqueSlimeCount > 0) {
            addToBot(new GainBlockAction(p, p, this.block * uniqueSlimeCount));
        }

        // Gain 1 Blur
        addToBot(new ApplyPowerAction(p, p, new BlurPower(p, this.magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new ServeAndProtect();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
            initializeDescription();
        }
    }
}
