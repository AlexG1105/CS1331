/**
 * A testing class for DoctorCS.java and RogueAI.java.
 *
 * @author 1331TAs
 * @version 1.0
 */
public class CrimeSimulator {
    public static void main(String[] args) {
        Coordinates coords = new Coordinates(13.31, 23.16);
        DoctorCS cs = new DoctorCS(new RogueAI(10, 0, 15, coords,
            new Coordinates(50.0, 25.5)), "Robert Paulson", 13310001);
        RogueAI ai = (RogueAI) cs.getAI();
        System.out.println(cs.toString());
        System.out.println(ai.toString());

        cs.saveTheDay();
        System.out.println(ai.toString());

        System.out.println(cs.getStatus());
        System.out.println(ai.getDestructed());
    }
}
