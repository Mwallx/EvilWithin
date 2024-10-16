package theHexaghost;

import basemod.AutoAdd;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.Loader;

import java.lang.reflect.Method;

public class CuratedChallengesHandler {
    private static final String MOD_ID = "CuratedChallenges";
    private static boolean isLoaded = false;
    private static Method registerChallengeMethod = null;
    private static Class<?> challengeDefinitionClass = null;

    static {
        if (Loader.isModLoaded(MOD_ID)) {
            try {
                Class<?> apiClass = Class.forName("curatedchallenges.util.CuratedChallengesAPI");
                challengeDefinitionClass = Class.forName("curatedchallenges.interfaces.ChallengeDefinition");
                registerChallengeMethod = apiClass.getMethod("registerChallenge", challengeDefinitionClass);
                isLoaded = true;
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                // Silent fail - mod is not loaded or incompatible
            }
        }
    }

    public static boolean isCuratedChallengesLoaded() {
        return isLoaded;
    }

    public static void registerChallenge(Object challenge) {
        if (isLoaded && registerChallengeMethod != null && challengeDefinitionClass != null) {
            try {
                if (challengeDefinitionClass.isInstance(challenge)) {
                    registerChallengeMethod.invoke(null, challenge);
                }
            } catch (Exception e) {
                // Silent fail - registration failed
            }
        }
    }
}