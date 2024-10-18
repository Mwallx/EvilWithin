package slimebound.orbs;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeApplyDebuff;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;


public class SlimingSlime extends SpawnedSlime {
    public static final String ID = "Slimebound:SlimingSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/sliming.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltS/skeleton.json";

    public SlimingSlime() {
        super(ID, new Color(1.0F, .5F, 1.0F, 100F), atlasString, skeletonString, false, true, 0, 5, false, new Color(.6F, .47F, .59F, 1), SlimeFlareEffect.OrbFlareColor.SLIMING, new Texture("slimeboundResources/SlimeboundImages/orbs/debuff2.png"));
        this.extraFontColor = new Color(.7F, .3F, .7F, 1F);
        this.debuffAmount = 5;
        this.useDebuffForDescription = true;
        spawnVFX();
    }

    @Override
    public void postSpawnEffects() {
        super.postSpawnEffects();
        updateSlimedNumber();
    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.debuffAmount + this.descriptions[1];
    }

    public void updateSlimedNumber() {
        this.slimeBonus = SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player);
    }

    public void activateEffectUnique() {
        AbstractDungeon.actionManager.addToBottom(new SlimeApplyDebuff(AbstractDungeon.player, this.debuffAmount + SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player), this));
    }

    public void upgrade() {
        this.debuffAmount += 3;
        updateDescription();
    }

    public AbstractOrb makeCopy() {
        return new SlimingSlime();
    }
}