/**
 *@author aguo36
 *@version 1.0
 */
public abstract class AI {
    private boolean destructed;
    private Coordinates cannonTarget;
    private Coordinates secretHQ;
    /**Constructor
     *@param cannonTarget type Coordinates
     *@param secretHQ type Coordinates
     *@return
     */
    public AI(Coordinates cannonTarget, Coordinates secretHQ) {
        this.cannonTarget = cannonTarget;
        this.secretHQ = secretHQ;
    }
    /**Swaps cannon targets
     *@param newCoords new target location
     *@return boolean
     */
    public boolean swapCannonTarget(Coordinates newCoords) {
        if ((!destructed) && !(newCoords.equals(cannonTarget))) {
            if (shouldSwapCannonTarget()) {
                cannonTarget = newCoords;
                return true;
            } else if (shouldSelfDestruct()) {
                selfDestruct();
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**abstract
     *@param
     *@return boolean
     */
    public abstract boolean shouldSwapCannonTarget();
    /**self destructs
     *@param
     *@return
     */
    public void selfDestruct() {
        destructed = true;
    }
    /**abstract
     *@param
     *@return boolean
     */
    public abstract boolean shouldSelfDestruct();
    /**getter method for destructed
     *@param
     *@return boolean
     */
    public boolean getDestructed() {
        return destructed;
    }
    /**getter method for cannonTarget
     *@param
     *@return Coordinates
     */
    public Coordinates getCannonTarget() {
        return cannonTarget;
    }
    /**getter method for secretHQ
     *@param
     *@return Coordinates
     */
    public Coordinates getSecretHQ() {
        return secretHQ;
    }
    /**tostring method
     *@param
     *@return String
     */
    public String toString() {
        return "Dr. Chipotle's guacamole cannon is currently pointed at "
            + getCannonTarget() + ".";
    }
}
