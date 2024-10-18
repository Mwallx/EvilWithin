package downfall.patches.ui.campfire;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import downfall.downfallMod;
import downfall.patches.EvilModeCharacterSelect;
import downfall.ui.campfire.BustKeyOption;
import downfall.util.TextureLoader;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;

public class AddBustKeyButtonPatches {
    public static Texture brokenKeyCheck(Texture tex) {
        if (tex.equals(ImageMaster.RUBY_KEY) && KeyFields.bustedRuby.get(AbstractDungeon.player)) {
            return TextureLoader.getTexture(downfallMod.assetPath("images/ui/key_red.png"));
        }
        if (tex.equals(ImageMaster.EMERALD_KEY) && KeyFields.bustedEmerald.get(AbstractDungeon.player)) {
            return TextureLoader.getTexture(downfallMod.assetPath("images/ui/key_green.png"));
        }
        if (tex.equals(ImageMaster.SAPPHIRE_KEY) && KeyFields.bustedSapphire.get(AbstractDungeon.player)) {
            return TextureLoader.getTexture(downfallMod.assetPath("images/ui/key_blue.png"));
        }

        return tex;
    }

    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class KeyFields {
        public static SpireField<Boolean> bustedRuby = new SpireField<>(() -> false);
        public static SpireField<Boolean> bustedEmerald = new SpireField<>(() -> false);
        public static SpireField<Boolean> bustedSapphire = new SpireField<>(() -> false);
    }

    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddKeys {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
            if (EvilModeCharacterSelect.evilMode) {
                if (Settings.hasRubyKey && !KeyFields.bustedRuby.get(AbstractDungeon.player)) {
                    ___buttons.add(new BustKeyOption(BustKeyOption.Keys.RUBY));
                }
                if (Settings.hasEmeraldKey && !KeyFields.bustedEmerald.get(AbstractDungeon.player)) {
                    ___buttons.add(new BustKeyOption(BustKeyOption.Keys.EMERALD));
                }
                if (Settings.hasSapphireKey && !KeyFields.bustedSapphire.get(AbstractDungeon.player)) {
                    ___buttons.add(new BustKeyOption(BustKeyOption.Keys.SAPPHIRE));
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "renderName")
    public static class BrokenKeyRender {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(SpriteBatch.class.getName()) && m.getMethodName().equals("draw")) {
                        m.replace("{" +
                                "$proceed(" + AddBustKeyButtonPatches.class.getName() + ".brokenKeyCheck($1), $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14, $15, $16);" +
                                "}");
                    }
                }
            };
        }
    }

}