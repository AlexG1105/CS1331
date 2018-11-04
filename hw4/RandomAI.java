import java.util.Random;
/**
 *@author aguo36
 *@version 1.0
 */
public class RandomAI extends AI {
    private static final Random randomizer = new Random();
    /**Constructor
     *@param cannonTarget type Coordinates
     *@param secretHQ type Coordinates
     *@return
     */
    public RandomAI(Coordinates cannonTarget, Coordinates secretHQ) {
        super(cannonTarget, secretHQ);
    }
    /** returns whether one should swap
     *@param
     *@return boolean
     */
    public boolean shouldSwapCannonTarget() {
        return (randomizer.nextInt(100) < 50);
    }
    /** returns whether one should destruct
     *@param
     *@return boolean
     */
    public boolean shouldSelfDestruct() {
        return (randomizer.nextInt(100) < 50);
    }
}
