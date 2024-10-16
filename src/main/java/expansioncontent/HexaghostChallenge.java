package expansioncontent;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.shrines.FountainOfCurseRemoval;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import curatedchallenges.interfaces.ChallengeDefinition;
import curatedchallenges.interfaces.WinCondition;
import curatedchallenges.winconditions.CompleteActWinCondition;
import curatedchallenges.winconditions.RemoveAllCardsWinCondition;
import theHexaghost.TheHexaghost;
import theHexaghost.cards.*;
import theHexaghost.cards.Float;
import theHexaghost.relics.SpiritBrand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HexaghostChallenge implements ChallengeDefinition {
    public static final String ID = "HEXAGHOST_CHALLENGE";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return "Et Tu";
    }

    @Override
    public AbstractPlayer.PlayerClass getCharacterClass() {
        return TheHexaghost.Enums.THE_SPIRIT;
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
        deck.add(new Sear());
        deck.add(new Float());
        deck.add(new SkipABeat());

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
        return "Ghostflame effects affect enemies AND yourself.";
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

    @Override
    public List<Class<? extends AbstractEvent>> getEventsToRemove() {
        return Arrays.asList(
                FountainOfCurseRemoval.class
        );
    }
}