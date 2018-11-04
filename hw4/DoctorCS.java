/*
Analysis Questions:
1. This is not good style because later you
are forced to cast the ai object to RogueAI. If
you didn't cast it, it should work theoretically
but the compiler would point out an error since
the methods aren't given explicitly in the AI class.
2. There is a way to circumvent the use of
this by adding the methods getFirewallProtection()
and lowerFirewall() while making them abstract
in the AI superclass. Simply, we would just have to
define the methods in the RandomAI class while making
lowerFirewall() empty and getFirewallProtection() return
-1. This would prevent the use of instanceof because
both rogueAI and randomoAI have the methods, and prevent
casting because the methods are defined as
abstract in the abstract superclass.
 */
/**
 *@author aguo36
 *@version 1.0
 */
public class DoctorCS {
    private AI ai;
    private final String secretIdentity;
    private final int jlaid;
    private boolean safe;
    /**Constructor
     *@param ai type AI
     *@param secretIdentity type String
     *@param jlaid type int
     *@return
     */
    public DoctorCS(AI ai, String secretIdentity, int jlaid) {
        this.ai = ai;
        this.secretIdentity = secretIdentity;
        this.jlaid = jlaid;
        safe = false;
    }
    /**saves the day
     *@param
     *@return
     */
    public void saveTheDay() {
        if (ai instanceof RogueAI) {
            while (((RogueAI) ai).getFirewallProtection() > 0) {
                ((RogueAI) ai).lowerFirewall();
            }
        }
        safe = ai.swapCannonTarget(ai.getSecretHQ());
    }
    /**gets the battle status
     *@param
     *@return String
     */
    public String getStatus() {
        if (safe) {
            return "Doctor CS has saved the day!";
        } else if (!safe && ai.getDestructed()) {
            return "Dr. Chipotle has succeded in his plan...";
        } else {
            return "Georgia Tech is still in danger!";
        }
    }
    /**tostring method
     *@param
     *@return String
     */
    public String toString() {
        return "" + secretIdentity + " aka Doctor CS with"
            + " JLAID: " + jlaid;
    }
    /**getter method for ai
     *@param
     *@return AI
     */
    public AI getAI() {
        return ai;
    }
    /**getter method for jlaid
     *@param
     *@return int
     */
    public int getJlaid() {
        return jlaid;
    }
}
