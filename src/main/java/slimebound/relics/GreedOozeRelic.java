package slimebound.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import downfall.patches.EvilModeCharacterSelect;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;
import downfall.relics.HeartBlessingBlue;
import downfall.relics.HeartBlessingGreen;
import downfall.relics.HeartBlessingRed;
import downfall.vfx.campfire.BustKeyEffect;
import expansioncontent.util.DownfallAchievementUnlocker;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.GreedOozeSlime;

import java.util.ArrayList;

public class GreedOozeRelic extends CustomRelic {
    public static final String ID = "Slimebound:GreedOozeRelic";
    public static final String IMG_PATH = "relics/greedOoze.png";
    public static final String OUTLINE_IMG_PATH = "relics/greedOozeOutline.png";
    private boolean used = false;
    private static boolean keyBreakPending = false;
    private static boolean keyBrokenByGreedOoze = false;
    private static ArrayList<AbstractRelic> relicsToObtain = new ArrayList<>();

    public GreedOozeRelic() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.CLINK);
        this.counter = 4;
    }

    public String getUpdatedDescription() {
        return EvilModeCharacterSelect.evilMode ? DESCRIPTIONS[1] : DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new slimebound.orbs.GreedOozeSlime(), false, false));
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (!(room instanceof RestRoom)){
            used = false;
            keyBrokenByGreedOoze = false;
        }
    }


    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof GreedOozeSlime) {
                ((GreedOozeSlime) o).stopShiny = true;
            }
        }
    }

    public void onEnterRestRoom() {
        if (!used) {
            AbstractPlayer p = AbstractDungeon.player;
            used = true;
            if (p.gold >= 50) {
                this.counter += 1;
                this.flash();
                p.loseGold(50);
                for (int i = 0; i < 20; i++) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY, this.hb.cX, this.hb.cY, false));
                }
                if (this.counter >= 12) {
                    DownfallAchievementUnlocker.unlockAchievement("PAYMENT_RECEIVED");
                }

                // Set flag to break keys on next update
                if (EvilModeCharacterSelect.evilMode) {
                    keyBreakPending = true;
                }
            }
        }
        BaseMod.logger.info("Rest site logic for GREED OOZE has finished successfully");
    }

    // This method will be called by our postfix patch
    public static void updateGreedOoze() {
        if (keyBreakPending) {
            breakUnbrokenKeys(AbstractDungeon.player);
            keyBreakPending = false;
        }

        if (!relicsToObtain.isEmpty()) {
            for (AbstractRelic relic : relicsToObtain) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, relic);
            }
            relicsToObtain.clear();
        }
    }

    private static void breakUnbrokenKeys(AbstractPlayer p) {
        boolean brokeAKey = false;
        if (Settings.hasRubyKey && !AddBustKeyButtonPatches.KeyFields.bustedRuby.get(p)) {
            AddBustKeyButtonPatches.KeyFields.bustedRuby.set(p, true);
            relicsToObtain.add(new HeartBlessingRed());
            brokeAKey = true;
        }
        if (Settings.hasSapphireKey && !AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(p)) {
            AddBustKeyButtonPatches.KeyFields.bustedSapphire.set(p, true);
            relicsToObtain.add(new HeartBlessingBlue());
            brokeAKey = true;
        }
        if (Settings.hasEmeraldKey && !AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(p)) {
            AddBustKeyButtonPatches.KeyFields.bustedEmerald.set(p, true);
            relicsToObtain.add(new HeartBlessingGreen());
            brokeAKey = true;
        }

        if (brokeAKey) {
            keyBrokenByGreedOoze = true;
            AbstractDungeon.effectList.add(new BustKeyEffect());
        }

        BaseMod.logger.info("Keys broken by GREED OOZE: " + (brokeAKey ? "Yes" : "No"));
    }

    public static boolean wasKeyBrokenByGreedOoze() {
        return keyBrokenByGreedOoze;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GreedOozeRelic();
    }

}