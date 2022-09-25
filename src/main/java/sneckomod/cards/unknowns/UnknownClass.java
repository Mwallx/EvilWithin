package sneckomod.cards.unknowns;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import downfall.util.CardIgnore;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

import static sneckomod.SneckoMod.makeCardPath;

@CardIgnore
public class UnknownClass extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownClass");
    private static final String[] unknownClass = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    private final String TID; //Temporary ID
    private final String UP_DESC;
    public CardColor myColor;

    public UnknownClass(CardColor cardColor) {
        super(ID + cardColor.name(), makeCardPath(determineCardImg(cardColor)), CardType.SKILL, CardRarity.COMMON);
        TID = ID + cardColor.name();
        myColor = cardColor;
        name = unknownClass[0];
        originalName = unknownClass[0];
        if (CardCrawlGame.isInARun() || CardCrawlGame.loadingSave) {
            rawDescription = unknownClass[1] + getCharName(myColor)
                    + unknownClass[2];
        } else {
            rawDescription = unknownClass[1] + unknownClass[3]
                    + unknownClass[2];
        }
        UP_DESC = unknownClass[6] + rawDescription;
        if (Objects.equals(CardCrawlGame.languagePack.getCardStrings(TID).NAME, "[MISSING_TITLE]")) {
            BaseMod.loadCustomStrings(CardStrings.class, "{\"" + TID 
            + "\": {\"NAME\": \"" + name 
            + "\", \"DESCRIPTION\": \"" + rawDescription 
            + "\", \"UPGRADE_DESCRIPTION\": \"" + UPGRADE_DESCRIPTION +"\"}}");
        }
        initializeDescription();
    }

    @Override
    public void upp() {
        rawDescription = UP_DESC;
        initializeDescription();
    }

    private static String determineCardImg(CardColor myColor) {
        switch (myColor.name()) {
            case "RED":
                return "UnknownIronclad.png";
            case "BLUE":
                return "UnknownDefect.png";
            case "GREEN":
                return "UnknownSilent.png";
            case "PURPLE":
                return "UnknownWatcher.png";
            case "GUARDIAN":
                return "UnknownGuardian.png";
            case "SLIMEBOUND":
                return "UnknownSlimeBoss.png";
            case "HEXA_GHOST_PURPLE":
                return "UnknownHexaghost.png";
            case "THE_CHAMP_GRAY":
                return "UnknownChamp.png";
            case "THE_BRONZE_AUTOMATON":
                return "UnknownAutomaton.png";
            case "GREMLIN":
                return "UnknownGremlin.png";
            case "HERMIT_YELLOW":
                return "UnknownHermit.png";
            default:
                String filename = "UnknownClass" + myColor.name();
                if (Gdx.files.internal(makeCardPath(filename) + ".png").exists())
                    return filename;
                return "UnknownModded.png";
        }
    }

    private static String getCharName(CardColor myColor) {
        ArrayList<AbstractPlayer> theDudes = new ArrayList<>(CardCrawlGame.characterManager.getAllCharacters());
        for (AbstractPlayer p : theDudes) {
            if (p.getCardColor() == myColor)
                return p.getLocalizedCharacterName().replace(unknownClass[4], "");
        }
        return myColor.name();
    }

    @Override
    public AbstractCard makeCopy() {
        return new UnknownClass(myColor);
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == myColor;
    }

    @Override
    public ArrayList<String> myList() {
        for (int i = 0; i < SneckoMod.unknownClasses.size(); i++) {
            if (SneckoMod.unknownClasses.get(i).myColor == myColor) {
                return AbstractUnknownCard.unknownClassReplacements.get(i);
            }
        }
        return null;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        switch (myColor.name()) {
            case "RED":
                return SneckoMod.overBannerIronclad;
            case "GREEN":
                return SneckoMod.overBannerSilent;
            case "BLUE":
                return SneckoMod.overBannerDefect;
            case "PURPLE":
                return SneckoMod.overBannerWatcher;
            case "SLIMEBOUND":
                return SneckoMod.overBannerSlime;
            case "GUARDIAN":
                return SneckoMod.overBannerGuardian;
            case "GHOST_GREEN":
                return SneckoMod.overBannerHexa;
            case "CHAMP_GRAY":
                return SneckoMod.overBannerChamp;
            case "BRONZE_AUTOMATON":
                return SneckoMod.overBannerAuto;
            case "GREMLIN":
                return SneckoMod.overBannerGremlins;
            case "HERMIT_YELLOW":
                return SneckoMod.overBannerHermit;
            default:
                return SneckoMod.overBannerClasses.computeIfAbsent(myColor.name(), s -> {
                    String filename = "sneckomodResources/images/cardicons/overbannerIcons/class" + s + ".png";
                    if (Gdx.files.internal(filename).exists())
                        return TextureLoader.getTextureAsAtlasRegion(filename);
                    return SneckoMod.overBannerModded;
                });
        }
    }
}

