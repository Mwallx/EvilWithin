package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.relics.SneckoEye;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

public class SuperSneckoEye extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SuperSneckoEye");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SuperSneckoEye.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SuperSneckoEye.png"));
    public boolean activated = false;
    private boolean added_hand_size = false;
    public SuperSneckoEye() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        activated = false;
        beginLongPulse();
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(SneckoEye.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(SneckoEye.ID)) {
                    AbstractDungeon.player.relics.get(i).onUnequip();
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (!activated)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    if (card.cost == 3 && !activated) {
                        stopPulse();
                        flash();
                        activated = true;
                        card.cost = 0;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                        card.freeToPlayOnce = false;
                    }
                }
            });
    }

    public void onEquip() {
        if(!added_hand_size) {
            AbstractDungeon.player.masterHandSize += 2;
            added_hand_size = true;
        }
    }

    public void onUnequip() {
        AbstractDungeon.player.masterHandSize -= 2;
    }

    public void atPreBattle() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ConfusionPower(AbstractDungeon.player)));
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SneckoEye.ID);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
