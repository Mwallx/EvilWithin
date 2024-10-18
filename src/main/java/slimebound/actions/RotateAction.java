package slimebound.actions;


import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import slimebound.orbs.SpawnedSlime;

public class RotateAction extends AbstractGameAction {
    private boolean rotateSpecificSlime;
    private Class<? extends SpawnedSlime> slimeClass;

    public RotateAction() {
        this.rotateSpecificSlime = false;
    }

    public RotateAction(Class<? extends SpawnedSlime> slimeClass) {
        this.rotateSpecificSlime = true;
        this.slimeClass = slimeClass;
    }


    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.orbs.isEmpty() || p.maxOrbs == 0) {
            this.isDone = true;
            return;
        }

        // Log the frontmost orb ID before rotation
        logFrontmostOrbId(p, "Before rotation");

        if (rotateSpecificSlime) {
            rotateSpecificSlime(p);
        } else {
            rotateAllOrbs(p);
        }

        // Log the frontmost orb ID after rotation
        logFrontmostOrbId(p, "After rotation");

        this.isDone = true;
    }

    private void rotateAllOrbs(AbstractPlayer p) {
        int lastNonEmptyIndex = p.orbs.size() - 1;
        while (lastNonEmptyIndex >= 0 && p.orbs.get(lastNonEmptyIndex) instanceof EmptyOrbSlot) {
            lastNonEmptyIndex--;
        }

        if (lastNonEmptyIndex < 0) {
            // All orbs are empty, nothing to rotate
            return;
        }

        AbstractOrb orbToMove = p.orbs.get(lastNonEmptyIndex);
        p.orbs.remove(lastNonEmptyIndex);

        int firstNonEmptyIndex = 0;
        while (firstNonEmptyIndex < p.orbs.size() && p.orbs.get(firstNonEmptyIndex) instanceof EmptyOrbSlot) {
            firstNonEmptyIndex++;
        }

        p.orbs.add(firstNonEmptyIndex, orbToMove);

        for (int i = 0; i < p.orbs.size(); i++) {
            p.orbs.get(i).setSlot(i, p.maxOrbs);
        }
    }

    private void rotateSpecificSlime(AbstractPlayer p) {
        int slimeIndex = -1;
        int frontmostOccupiedSlot = -1;

        // Find the index of the specific slime type and the frontmost occupied slot
        for (int i = p.orbs.size() - 1; i >= 0; i--) {
            if (!(p.orbs.get(i) instanceof EmptyOrbSlot)) {
                if (frontmostOccupiedSlot == -1) {
                    frontmostOccupiedSlot = i;
                }
                if (p.orbs.get(i).getClass() == slimeClass) {
                    slimeIndex = i;
                    break;
                }
            }
        }

        // If the slime is not present or already at the frontmost occupied slot, do nothing
        if (slimeIndex == -1 || slimeIndex == frontmostOccupiedSlot) {
            return;
        }

        // Remove the slime from its current position
        AbstractOrb slimeOrb = p.orbs.remove(slimeIndex);

        // Add the slime to the frontmost occupied slot
        p.orbs.add(frontmostOccupiedSlot, slimeOrb);

        // Adjust the positions of all orbs
        for (int i = 0; i < p.orbs.size(); i++) {
            p.orbs.get(i).setSlot(i, p.maxOrbs);
        }
    }
    private void logFrontmostOrbId(AbstractPlayer p, String phase) {
        for (int i = p.orbs.size() - 1; i >= 0; i--) {
            if (!(p.orbs.get(i) instanceof EmptyOrbSlot)) {
                BaseMod.logger.info(phase + ": Frontmost orb ID is " + p.orbs.get(i).ID + " at index " + i);
                return;
            }
        }
        BaseMod.logger.info(phase + ": No non-empty orbs found");
    }
}
