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
import slimebound.orbs.*;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PotencyPower;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.HashSet;


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
        SlimeboundMod.loadJokeCardImage(this, "MassRepurpose.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        HashSet<String> uniqueSlimeTypes = new HashSet<>();

        // Collect unique Slime types
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                uniqueSlimeTypes.add(((SpawnedSlime) o).ID);
            }
        }

        // Spawn one of each unique Slime type
        spawnUniqueSlimes(uniqueSlimeTypes);

        // Repeat the process if upgraded
        if (upgraded) {
            spawnUniqueSlimes(uniqueSlimeTypes);
        }
    }

    private void spawnUniqueSlimes(HashSet<String> slimeTypes) {
        for (String slimeID : slimeTypes) {
            switch (slimeID) {
                case AttackSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new AttackSlime(), false, true));
                    break;
                case ShieldSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true));
                    break;
                case SlimingSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new SlimingSlime(), false, true));
                    break;
                case PoisonSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new PoisonSlime(), false, true));
                    break;
                case GeneralSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new GeneralSlime(), false, true));
                    break;
                case RecklessSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new RecklessSlime(), false, true));
                    break;
                case HungrySlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new HungrySlime(), false, true));
                    break;
                case GreedOozeSlime.ID:
                    AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new GreedOozeSlime(), false, true));
                    break;
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