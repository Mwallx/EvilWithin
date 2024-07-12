package expansioncontent.achievements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;

import java.util.ArrayList;

public class DownfallAchievementGrid {
    public ArrayList<DownfallAchievementItem> items = new ArrayList<>();
    private static final float SPACING = 200.0F * Settings.scale;
    private static final int ITEMS_PER_ROW = 5;

    public DownfallAchievementGrid() {
        DownfallAchievementItem.atlas = new TextureAtlas(Gdx.files.internal("downfallResources/images/achievements/DownfallAchievements.atlas"));
        loadAchievement("CORRUPTED");
        loadAchievement("UPRISE");
    }

    private void loadAchievement(String id) {
        String fullId = downfallMod.makeID(id);
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(fullId);
        String name = uiStrings.TEXT[0];
        String description = uiStrings.TEXT[1];
        TextureAtlas.AtlasRegion AchievementImageUnlocked = DownfallAchievementItem.atlas.findRegion("unlocked/" + id);
        TextureAtlas.AtlasRegion AchievementImageLocked = DownfallAchievementItem.atlas.findRegion("locked/" + id);
        items.add(new DownfallAchievementItem(name, description, fullId, AchievementImageUnlocked, AchievementImageLocked));
    }

    public void updateAchievementStatus() {
        for (DownfallAchievementItem item : items) {
            String achievementKey = item.getKey();
            boolean isUnlocked = UnlockTracker.isAchievementUnlocked(achievementKey);
            item.isUnlocked = isUnlocked;
            item.reloadImg();
        }
    }

    public void render(SpriteBatch sb, float renderY) {
        for (int i = 0; i < items.size(); ++i) {
            items.get(i).render(sb, 560.0F * Settings.scale + (i % ITEMS_PER_ROW) * SPACING, renderY - (i / ITEMS_PER_ROW) * SPACING + 680.0F * Settings.yScale);
        }
    }

    public float calculateHeight() {
        int numRows = (items.size() + ITEMS_PER_ROW - 1) / ITEMS_PER_ROW;
        return numRows * SPACING + 50.0F * Settings.scale;
    }

    public void update() {
        updateAchievementStatus();
        for (DownfallAchievementItem item : items) {
            item.update();
        }
    }

    public boolean areAllAchievementsUnlockedExceptEvilOne() {
        for (DownfallAchievementItem item : items) {
            if (!item.isUnlocked && !item.getKey().equals(downfallMod.makeID("EVIL_ONE"))) {
                return false;
            }
        }
        return true;
    }

}