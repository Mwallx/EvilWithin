package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;

public class StasisSlotReductionRelic extends CustomRelic {
    public static final String ID = "Guardian:StasisSlotReductionRelic";
    public static final String IMG_PATH = "relics/bots.png";
    public static final String OUTLINE_IMG_PATH = "relics/botsOutline.png";

    public StasisSlotReductionRelic() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        --AbstractDungeon.player.masterMaxOrbs;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
        ++AbstractDungeon.player.masterMaxOrbs;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new StasisSlotReductionRelic();
    }
}