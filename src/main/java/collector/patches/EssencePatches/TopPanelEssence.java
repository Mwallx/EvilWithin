package collector.patches.EssencePatches;

import basemod.TopPanelItem;
import collector.CollectorChar;
import collector.CollectorMod;
import collector.util.EssenceSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

public class TopPanelEssence extends TopPanelItem {

    private static final float tipYpos = Settings.HEIGHT - (120.0f * Settings.scale);

    public static final String ID = CollectorMod.makeID("EssenceCount");

    private static final Texture ICON = ImageMaster.ENDLESS_ICON; //TODO: Special icon
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public TopPanelEssence() {
        super(ICON, ID);
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass == CollectorChar.Enums.THE_COLLECTOR) {
            render(sb, Color.WHITE);
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelAmountFont, Integer.toString(EssenceSystem.essenceCount()), this.x + 58.0F * Settings.scale, this.y + 25.0F * Settings.scale, Color.WHITE.cpy());
            if (getHitbox().hovered) {
                TipHelper.renderGenericTip(getHitbox().x, tipYpos, uiStrings.TEXT[0], uiStrings.TEXT[1] + EssenceSystem.essenceCount() + uiStrings.TEXT[2]);
            }
        }
    }

    @Override
    protected void onClick() {

    }
}
