package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.SlimeSpawnAction;
import slimebound.orbs.AttackSlime;
import slimebound.orbs.PoisonSlime;
import slimebound.orbs.ShieldSlime;
import slimebound.orbs.SlimingSlime;
import slimebound.powers.PotencyPower;
import sneckomod.SneckoMod;
import sneckomod.relics.SneckoSoul;

import java.util.ArrayList;

public class AbsorbEndCombatUpgraded extends CustomRelic {
    public static final String ID = "Slimebound:AbsorbEndCombatUpgraded";
    public static final String IMG_PATH = "relics/heartofgooblack.png";
    public static final String OUTLINE_IMG_PATH = "relics/heartofgooOutline.png";
    private static final int MAX_SLIMES_PER_COMBAT = 3;

    public AbsorbEndCombatUpgraded() {
        super(ID, new Texture(slimebound.SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(slimebound.SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
   //     grayscale = false;
        counter = MAX_SLIMES_PER_COMBAT;
    }

    @Override
    public void onTrigger() {
        int bonus = 0;
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if (counter > 0) {
            AbstractDungeon.actionManager.addToBottom(new SlimeSpawnAction(new ShieldSlime(), false, true, 0, bonus));
            counter--;
        }
        addToBot(new CommandAction());
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(AbsorbEndCombat.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(AbsorbEndCombat.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(AbsorbEndCombat.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AbsorbEndCombatUpgraded();
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new AbsorbEndCombat().name;
        StringBuilder sb = new StringBuilder();
        if(Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT){
            sb.append("[#").append(SlimeboundMod.placeholderColor.toString()).append("]").append(name).append("[]");
        } else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(SlimeboundMod.placeholderColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(SlimeboundMod.placeholderColor.toString()).append("]");
        }
        return DESCRIPTIONS[0] + sb.toString() + DESCRIPTIONS[1];
    }
}