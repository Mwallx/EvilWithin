package slimebound.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandRandomSlimesAction;
import slimebound.actions.SlimeGainBlock;
import slimebound.vfx.SlimeFlareEffect;

public class GeneralSlime extends SpawnedSlime {
    public static final String ID = "Slimebound:GeneralSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/slowing2.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltS/skeleton.json";

    public GeneralSlime() {
        super(ID, new Color(0.5f, 0.5f, 0.8f, 1f), atlasString, skeletonString, false, false, 0, 2, false, new Color(0.4F, 0.4F, 0.8F, 1), SlimeFlareEffect.OrbFlareColor.LICKING, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"));
        this.extraFontColor = Color.GOLD;
        this.debuffAmount = 2;
        this.useDebuffForDescription = true;
        spawnVFX();
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + this.debuffAmount + this.descriptions[1];
    }

    @Override
    public void activateEffectUnique() {
        AbstractDungeon.actionManager.addToBottom(new CommandRandomSlimesAction(debuffAmount));
    }

    public void upgrade() {
        this.debuffAmount += 1;
        updateDescription();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new GeneralSlime();
    }
}