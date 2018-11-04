/**
 *@author aguo36
 *@version 1.0
 */
public class Coordinates {
    private final double latitude;
    private final double longitude;
    /**Constructor
     *@param latitude type double
     *@param longitude type double
     *@return
     */
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    /**getter method for Latitude
     *@param
     *@return double
     */
    public double getLatitude() {
        return latitude;
    }
    /**getter method for longitude
     *@param
     *@return double
     */
    public double getLongitude() {
        return longitude;
    }
    /**equals method
     *@param other Object to be compared
     *@return boolean whether they are equal
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Coordinates)) {
            return false;
        }
        Coordinates that = (Coordinates) other;
        return (((this.latitude - that.latitude) < 0.000001)
                && ((this.longitude - that.longitude) < 0.000001))
            ? (true) : (false);
    }
    /**tostring method
     *@param
     *@return String
     */
    public String toString() {
        return ("latitude: " + String.format("%.2f", latitude)
            + ", longitude: " + String.format("%.2f", longitude));
    }
}
