package sneckomod.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import downfall.util.TextureLoader;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

import static sneckomod.SneckoMod.costToIdentify;


public class LockInCampfireOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final UIStrings UI_STRINGS;

    static {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString("sneckomod:LockInBonfireOptions");
        DESCRIPTIONS = UI_STRINGS.TEXT;

    }

    //private ArrayList<String> idleMessages;
    public LockInCampfireOption() {
        this.label = DESCRIPTIONS[0];
        boolean active = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            c.update();
            if (c instanceof AbstractUnknownCard){
                active = true;
            }
        }

        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2];
        }


        if (AbstractDungeon.player.gold < costToIdentify) {
            this.usable = false;
            this.description = DESCRIPTIONS[4];
            updateImage(false);
        } else {
            updateImage(active);
        }
    }

    public void updateImage(boolean active) {
        if (active) {
            this.img = TextureLoader.getTexture("sneckomodResources/images/ui/lockincampfire.png");
        } else {
            this.img = TextureLoader.getTexture("sneckomodResources/images/ui/lockincampfiredisabled.png");
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            LockInCampfireEffect e = new LockInCampfireEffect();
            AbstractDungeon.effectList.add(e);
        }
    }

    @Override
    public void update() {
        float hackScale = (float) ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");

        if (this.hb.hovered) {
            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }
        super.update();
    }
}
