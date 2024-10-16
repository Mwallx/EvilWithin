package expansioncontent;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import curatedchallenges.interfaces.ChallengeDefinition;
import curatedchallenges.interfaces.WinCondition;
import curatedchallenges.winconditions.CompleteActWinCondition;
import theHexaghost.TheHexaghost;
import theHexaghost.relics.SpiritBrand;

import java.util.ArrayList;
import java.util.List;

public class Clashtastic implements ChallengeDefinition {
    public static final String ID = "CLASHTASTIC";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Clashtastic!";
    }

    @Override
    public AbstractPlayer.PlayerClass getCharacterClass() {
        return TheHexaghost.Enums.THE_SPIRIT;
    }

    @Override
    public ArrayList<AbstractCard> getStartingDeck() {
        ArrayList<AbstractCard> deck = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            deck.add(new Clash());
        }

        return deck;
    }

    @Override
    public ArrayList<AbstractRelic> getStartingRelics() {
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        relics.add(RelicLibrary.getRelic(SpiritBrand.ID).makeCopy());
        return relics;
    }

    @Override
    public String getSpecialRules() {
        return "Whenever you play Clash, enter #yDivinity.";

    }

    @Override
    public String getWinConditions() {
        return "Complete Act 3.";
    }

    @Override
    public List<WinCondition> getWinConditionLogic() {
        List<WinCondition> conditions = new ArrayList<>();
        conditions.add(new CompleteActWinCondition(3));
        return conditions;
    }
}