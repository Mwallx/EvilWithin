package slimebound.patches;

import charbosses.orbs.AbstractEnemyOrb;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.characters.SlimeboundCharacter;

import java.util.ArrayList;
import java.util.Random;

@SpirePatch(clz = AbstractOrb.class, method = "setSlot",
        paramtypez = {
                int.class,
                int.class})
public class OrbPositionPatch {
    // Adjustable parameters
    private static final float BASE_X_OFFSET = 200F * Settings.scale;
    private static final float X_SPACING = 70F * Settings.scale;
    private static final float BASE_Y_OFFSET = 0F * Settings.scale;
    private static final float Y_SPACING = 60F * Settings.scale;
    private static final float ALTERNATE_COLUMN_Y_OFFSET = -30F * Settings.scale;

    public static SpireReturn<Void> Prefix(AbstractOrb abstractOrb_instance, int slotNum, int maxOrbs) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter && !(abstractOrb_instance instanceof AbstractEnemyOrb)) {
            int column = slotNum / 2;
            int row = slotNum % 2;
            boolean isAlternateColumn = column % 2 == 1;

            float xPos = AbstractDungeon.player.drawX + BASE_X_OFFSET + (column * X_SPACING);
            float yPos = AbstractDungeon.player.drawY + BASE_Y_OFFSET + (row * Y_SPACING);

            if (isAlternateColumn) {
                yPos += ALTERNATE_COLUMN_Y_OFFSET;
            }

            abstractOrb_instance.tX = xPos;
            abstractOrb_instance.tY = yPos;
            abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
            return SpireReturn.Return(null);
        } else {
            return SpireReturn.Continue();
        }
    }
}