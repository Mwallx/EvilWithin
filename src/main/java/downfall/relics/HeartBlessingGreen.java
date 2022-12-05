package downfall.relics;

import basemod.abstracts.CustomRelic;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.downfallMod;

import java.util.Iterator;

public class HeartBlessingGreen extends CustomRelic {

    public static final String ID = downfallMod.makeID("HeartBlessingGreen");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/HeartBlessingGreen.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/HeartBlessing.png"));

    public HeartBlessingGreen() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        Iterator<AbstractMonster> var1 = AbstractDungeon.getMonsters().monsters.iterator();
        AbstractMonster m;
        do {
            if (!var1.hasNext()) {
                return;
            }
            m = var1.next();
        } while(m.type != AbstractMonster.EnemyType.BOSS);

        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}
