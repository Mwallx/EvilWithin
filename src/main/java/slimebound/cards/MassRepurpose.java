package slimebound.cards;


import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.MassRepurposeAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.actions.TriggerSlimeAttacksAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PotencyPower;
import sneckomod.SneckoMod;

import java.util.ArrayList;


public class MassRepurpose extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:MassRepurpose";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/morpheverything.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public MassRepurpose() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "MassRepurpose.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<String> uniqueSlimes = new ArrayList<>();

        // Identify unique slimes
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                String slimeType = ((SpawnedSlime) o).getSlimeType();
                if (!uniqueSlimes.contains(slimeType)) {
                    uniqueSlimes.add(slimeType);
                }
            }
        }

        // Spawn one of each unique slime
        for (String slimeType : uniqueSlimes) {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(slimeType));
        }

        // Repeat the process if upgraded
        if (upgraded) {
            for (String slimeType : uniqueSlimes) {
                AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(slimeType));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}