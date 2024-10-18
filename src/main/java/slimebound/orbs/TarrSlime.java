package slimebound.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.RotateAction;
import slimebound.vfx.SlimeFlareEffect;
import slimebound.vfx.TarrGlowParticle;

public class TarrSlime extends SpawnedSlime {
    public static final String ID = "Slimebound:TarrSlime";
    public static final String atlasString = SlimeboundMod.getResourcePath("orbs/tarr.atlas");
    public static final String skeletonString = "images/monsters/theBottom/slimeS/skeleton.json";

    public float attachmentX;
    public float attachmentY;
    public Boolean stopShiny = false;
    private float shinyTimer;
    public TarrSlime() {
        super(ID, new Color(0.5f, 0.5f, 0.8f, 1f), atlasString, skeletonString, false, false, 0, 0, false, new Color(0.4F, 0.4F, 0.8F, 1), SlimeFlareEffect.OrbFlareColor.LICKING, new Texture("slimeboundResources/SlimeboundImages/orbs/attackDebuff.png"));
        this.extraFontColor = Color.PURPLE;
        spawnVFX();
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0];
    }

    @Override
    public void activateEffectUnique() {
        AbstractDungeon.actionManager.addToBottom(new RotateAction(TarrSlime.class));
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.attachmentX = (this.skeleton.findBone("eyeback").getX()) * Settings.scale;
        this.attachmentY = (this.skeleton.findBone("eyeback").getY()) * Settings.scale;


        if (!this.stopShiny) updateShiny();
    }

    private void updateShiny() {

        this.shinyTimer -= Gdx.graphics.getDeltaTime();
        if (this.shinyTimer < 0.0F && !Settings.DISABLE_EFFECTS) {
            this.shinyTimer = 0.2F;

            AbstractDungeon.topLevelEffects.add(new TarrGlowParticle(this));

        }

    }

    @Override
    public AbstractOrb makeCopy() {
        return new TarrSlime();
    }
}