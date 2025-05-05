package slimebound.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hermit.powers.Bruise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

public class PainToPowerPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:PainToPowerPower";
    public static final String NAME = "Pain to Power";
    public static final String IMG = "powers/paintopower.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private int bruiseThreshold;

    public PainToPowerPower(AbstractCreature owner, int amount, int bruiseThreshold) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.bruiseThreshold = bruiseThreshold;
        this.img = new Texture(SlimeboundMod.getResourcePath(IMG));
        this.type = POWER_TYPE;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.bruiseThreshold + DESCRIPTIONS[2];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            int bruiseAmount = 0;
            if (owner.hasPower(Bruise.POWER_ID)) {
                bruiseAmount = owner.getPower(Bruise.POWER_ID).amount;
            }
            int strengthGain = (bruiseAmount / bruiseThreshold) * this.amount;
            if (strengthGain > 0) {
                flash();
                addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, strengthGain), strengthGain));
            }
        }
    }
}