package slimebound.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimeAutoAttack;
import slimebound.vfx.SlimeFlareEffect;

public class RecklessSlime
        extends SpawnedSlime {
    public static final String ID = "Slimebound:RecklessSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/champ2.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeAltM/skeleton.json";

    public RecklessSlime() {

        super(ID, new Color(1.0F, 100F / 255F, 100F / 255F, 100F), atlasString, skeletonString, false, true, 3, 0, true, new Color(.45F, .58F, .58F, 1), SlimeFlareEffect.OrbFlareColor.AGGRESSIVE, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"));
        spawnVFX();

    }

    public void updateDescription() {
        this.description = this.descriptions[0] + this.passiveAmount + this.descriptions[1];
    }


    public void activateEffectUnique() {


        AbstractDungeon.actionManager.addToBottom(new SlimeAutoAttack(AbstractDungeon.player, this.passiveAmount, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, false, false, false, 0, false, 0, false));

    }

    public void upgrade() {
        this.passiveAmount += 2;
        updateDescription();
    }


    public AbstractOrb makeCopy() {
        return new RecklessSlime();
    }
}

