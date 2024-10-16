package expansioncontent;

import collector.CollectorChar;
import collector.CollectorMod;
import collector.cards.DarkLordForm;
import collector.cards.Defend;
import collector.cards.Strike;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.VelvetChoker;
import curatedchallenges.interfaces.ChallengeDefinition;
import curatedchallenges.interfaces.WinCondition;
import curatedchallenges.winconditions.CompleteActWinCondition;
import theHexaghost.TheHexaghost;
import theHexaghost.relics.SpiritBrand;

import java.util.ArrayList;
import java.util.List;

public class MemeChallenges implements ChallengeDefinition {
    public static final String ID = "MEME_CHALLENGES";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Meme Challenges Are Valid";
    }

    @Override
    public AbstractPlayer.PlayerClass getCharacterClass() {
        return CollectorChar.Enums.THE_COLLECTOR;
    }

    @Override
    public ArrayList<AbstractCard> getStartingDeck() {
        ArrayList<AbstractCard> deck = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            deck.add(new Strike());
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new Defend());
        }

        for (int i = 0; i < 6; i++) {
            deck.add(new DarkLordForm());
        }

        return deck;
    }

    @Override
    public ArrayList<AbstractRelic> getStartingRelics() {
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        relics.add(RelicLibrary.getRelic(VelvetChoker.ID).makeCopy());
        return relics;
    }

    @Override
    public String getSpecialRules() {
        return "you know what? fuck you *unslays your spire*";

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