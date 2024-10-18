package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import curatedchallenges.CuratedChallenges;
import downfall.util.TextureLoader;
import expansioncontent.HexaghostChallenge;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.BurnPower;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.FlameAffectAllEnemiesPower;
import theHexaghost.relics.CandleOfCauterizing;
import theHexaghost.relics.JarOfFuel;

public class SearingGhostflame extends AbstractGhostflame {

    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("searing.png"));
    public static Texture bruhB = TextureLoader.getTexture(HexaMod.makeUIPath("searingBright.png"));
    public static Texture bruh2 = TextureLoader.getTexture(HexaMod.makeUIPath("burn.png"));
    public int attacksPlayedThisTurn = 0;

    private final String ID = "hexamod:SearingGhostflame";
    private final String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

    private Color flameColor = new Color(178F/255F, 249F/255F, 164F/255F, 1F);
    private Color activeColor = new Color(178F/255F * 0.5F, 249F/255F * 0.5F, 164F/255F * 0.5F, 1F);

    public SearingGhostflame(float x, float y) {
        super(x, y);
        magic = 6;

        //this.textColor = new Color(.75F,1F,.75F,1F);
        this.triggersRequired = 2;

        this.effectIconXOffset = 60F;
        this.effectIconYOffset = -20F;
        this.advanceOnCardUse = true;

    }

    @Override
    public int getActiveFlamesTriggerCount() {
        return attacksPlayedThisTurn;
    }

    @Override
    public void onCharge() {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = getEffectCount();
              //  boolean isHexaghostChallengeActive = HexaghostChallenge.ID.equals(CuratedChallenges.currentChallengeId);

                if (AbstractDungeon.player.hasPower(FlameAffectAllEnemiesPower.POWER_ID)) {
                    for (int i = 0; i < AbstractDungeon.player.getPower(FlameAffectAllEnemiesPower.POWER_ID).amount; i++) {
                        applyEffectToAllEnemies(x);
                       // if (isHexaghostChallengeActive) {
                      //      applyEffectToPlayer(x);
                     //   }
                    }
                } else {
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m != null && !m.isDead && !m.isDying && !m.halfDead) {
                        applyEffectToSingleEnemy(m, x);
                     //   if (isHexaghostChallengeActive) {
                    //        applyEffectToPlayer(x);
                    //    }
                    }
                }

                if (AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)) {
                    AbstractRelic r = AbstractDungeon.player.getRelic(CandleOfCauterizing.ID);
                    r.flash();
                }
            }
        });
    }

    private void applyEffectToAllEnemies(int x) {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m != null && !m.isDead && !m.isDying && !m.halfDead) {
                att(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, x), x));
            }
        }
        att(new VFXAction(new AbstractGameEffect() {
            public void update() {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (m != null && !m.isDead && !m.isDying && !m.halfDead) {
                        AbstractDungeon.effectsQueue.add(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY));
                    }
                }
                this.isDone = true;
            }
            @Override
            public void render(SpriteBatch spriteBatch) {}
            @Override
            public void dispose() {}
        }, 0.3F));
    }

    private void applyEffectToSingleEnemy(AbstractMonster m, int x) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, x), x));
        att(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.4F));
    }

    private void applyEffectToPlayer(int x) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BurnPower(AbstractDungeon.player, x), x));
        att(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.4F));
    }

    @Override
    public void advanceTrigger(AbstractCard c) {
        if (!charged && ( c.type == AbstractCard.CardType.ATTACK || (AbstractDungeon.player.hasRelic(JarOfFuel.ID) && c.type == AbstractCard.CardType.POWER ) ) ){
            if (attacksPlayedThisTurn < 2) {
                if(AbstractDungeon.player.hasRelic(JarOfFuel.ID) && c.type == AbstractCard.CardType.POWER){
                    AbstractRelic r =  AbstractDungeon.player.getRelic(JarOfFuel.ID);
                    if(r != null){ r.flash(); }
                }
                advanceTriggerAnim();
                attacksPlayedThisTurn++;
                if (attacksPlayedThisTurn == 2) {
                    charge();
                }
            }
        }
    }

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public Texture getHelperTextureBright() {
        return bruhB;
    }

    @Override
    public Texture getHelperEffectTexture() {
        return bruh2;
    }

    @Override
    public String returnHoverHelperText() {
        int x = getEffectCount();
        return x + "";
    }

    public int getEffectCount() {
        int x = magic;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += 2 * AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        if(AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)){
            x += CandleOfCauterizing.SOULBURN_BONUS_AMOUNT;
        }
        return x;
    }

    @Override
    public void resetVariable() {
        attacksPlayedThisTurn = 0;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = DESCRIPTIONS[0];
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - attacksPlayedThisTurn);
            if (x == 1) {
                s = s + DESCRIPTIONS[1] + x + DESCRIPTIONS[2];
            } else {
                s = s + DESCRIPTIONS[1] + x + DESCRIPTIONS[3];
            }
        } else {
            s = s + DESCRIPTIONS[4];
        }
        int x = getEffectCount();
        s = s + DESCRIPTIONS[5] + x + DESCRIPTIONS[6];
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[7];
        }
        return s;

    }


    public Color getFlameColor() {
        return activeColor.cpy();
        //return Color.SKY.cpy();
    }

    public Color getActiveColor() {
        //return activeColor.cpy();
        return Color.PURPLE.cpy();
    }
}
