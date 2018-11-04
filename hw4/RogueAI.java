/**
 *@author aguo36
 *@version 1.0
 */
public class RogueAI extends AI {
    private int firewallProtection;
    private int alertLevel;
    private final int maxAlert;
    /**Constructor
     *@param firewallProtection type int
     *@param alertLevel type int
     *@param maxAlert type int
     *@param cannonTarget type Coordinates
     *@param secretHQ type Coordinates
     *@return
     */
    public RogueAI(int firewallProtection, int alertLevel,
                   int maxAlert, Coordinates cannonTarget,
                   Coordinates secretHQ) {
        super(cannonTarget, secretHQ);
        this.firewallProtection = firewallProtection;
        this.alertLevel = alertLevel;
        this.maxAlert = maxAlert;
    }
    /**Constructor
     *@param firewallProtection type int
     *@param maxAlert type int
     *@param cannonTarget type Coordinates
     *@param secretHQ type Coordinates
     *@return
     */
    public RogueAI(int firewallProtection, int maxAlert,
                   Coordinates cannonTarget,
                   Coordinates secretHQ) {
        this(firewallProtection, 0, maxAlert,
             cannonTarget, secretHQ);
    }
    /**Constructor
     *@param firewallProtection type int
     *@param cannonTarget type Coordinates
     *@param secretHQ type Coordinates
     *@return
     */
    public RogueAI(int firewallProtection,
                   Coordinates cannonTarget,
                   Coordinates secretHQ) {
        this(firewallProtection, 0, 10,
             cannonTarget, secretHQ);
    }
    /**Lowers Firewall
     *@param
     *@return
     */
    public void lowerFirewall() {
        firewallProtection -= 2;
        alertLevel++;
    }
    /**returns if firewallProtection is leq 0
     *@param
     *@return boolean
     */
    public boolean shouldSwapCannonTarget() {
        return (firewallProtection <= 0);
    }
    /**returns if alertlevel geq maxalert
     *@param
     *@return boolean
     */
    public boolean shouldSelfDestruct() {
        return (alertLevel >= maxAlert);
    }
    /**getter method for firewallProtection
     *@param
     *@return int
     */
    public int getFirewallProtection() {
        return firewallProtection;
    }
    /**getter method for alertLevel
     *@param
     *@return int
     */
    public int getAlertLevel() {
        return alertLevel;
    }
    /**getter method for maxAlert
     *@param
     *@return int
     */
    public int getMaxAlert() {
        return maxAlert;
    }
    /**toString method
     *@param
     *@return String
     */
    public String toString() {
        return ("Dr. Chipotle's guacamole cannon is currently "
                + "pointed at " + getCannonTarget() + ", and is at alert"
                + " level " + getAlertLevel() + " with firewall"
                + " protection " + getFirewallProtection() + ".");
    }
}
