package club.shitting.number2;

import com.google.android.gms.maps.model.LatLng;
/**
 * Contains information for individual bathrooms/restrooms
 * @author Calvin Nguyen
 */
public class Bathroom {

    public final static int FASTFOOD = 0;
    public final static int RESTAURANT = 1;
    public final static int STORE = 2;
    public final static int HOTEL = 3;
    public final static int SCHOOL_PARK = 4;
    public final static int GAS = 5;
    public final static int MISC = 6;

    private boolean alwaysOpen;
    private int rating;
    private int type;
    private String name;
    private LatLng location;

    private int open;
    private int close;

    /**
     * Default constructor when nothing is known
     */
    private Bathroom() {
        this.name = "DEFAULT";
        this.alwaysOpen = false;
        this.rating = 0;
        this.type = MISC;
        this.location = new LatLng(0,0);
    }

    /**
     * Constructor when name is location is known (bare minimum)
     * @param name      expects a String with the name of the location
     * @param location  expects a LatLng with the coordinates of the location
     */
    public Bathroom(String name, LatLng location) {
        this.name = name;
        this.location = location;
    }

    /**
     * Constructor when name, location, and 24Hr is known
     * @param name          expects a String with the name of the location
     * @param alwaysOpen    expects a boolean that says if the bathroom is always open or not
     * @param location      expects a LatLng with the coordinates of the location
     */
    public Bathroom(String name, boolean alwaysOpen, LatLng location) {
        this.name = name;
        this.alwaysOpen = alwaysOpen;
        this.location = location;
        this.type = MISC;
        this.rating = 0;
    }

    /**
     * Constructor when name, location, rating, and 24Hr is known
     * @param name          expects a String with the name of the location
     * @param rating        expects an int corresponding to the quality of the bathroom [0,5]
     * @param alwaysOpen    expects a boolean that says if the bathroom is always open or not
     * @param location      expects a LatLng with the coordinates of the location
     */
    public Bathroom(String name, int rating, boolean alwaysOpen, LatLng location) {
        this.name = name;
        this.alwaysOpen = alwaysOpen;
        this.rating = rating;
        this.location = location;
    }

    /**
     * Constructor when all but the rating is known
     * @param name          expects a String with the name of the location
     * @param alwaysOpen    expects an int corresponding to the quality of the bathroom [0,5]
     * @param type          expects an int corresponding to one of seven bathroom types
     * @param location      expects a LatLng with the coordinates of the location
     */
    public Bathroom(String name, boolean alwaysOpen, int type, LatLng location) {
        this(name, alwaysOpen, 0, type, location);
    }

    /**
     * Constructor when all but the 24hr is known
     * @param name          expects a String with the name of the location
     * @param rating        expects an int corresponding to the quality of the bathroom [0,5]
     * @param type          expects an int corresponding to one of seven bathroom types
     * @param location      expects a LatLng with the coordinates of the location
     */
    public Bathroom(String name, int rating, int type, LatLng location) {
        this(name, false, rating, type, location);
    }

    /**
     * Constructor when all details are known
     * @param name          expects a String with the name of the location
     * @param alwaysOpen    expects a boolean that says if the bathroom is always open or not
     * @param rating        expects an int corresponding to the quality of the bathroom [0,5]
     * @param type          expects an int corresponding to one of seven bathroom types
     * @param location      expects a LatLng with the coordinates of the location
     */
    public Bathroom(String name, Boolean alwaysOpen, int rating, int type, LatLng location) {
        this.name = name;
        this.alwaysOpen = alwaysOpen;
        this.rating = rating;
        this.type = type;
        this.location = location;
    }

    /**
     * Sets the rating of the bathroom
     * @param rating expects an int corresponding to the quality of the bathroom [0,5]
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Sets whether or not the bathroom is 24hr
     * @param alwaysOpen expects a boolean that says if the bathroom is always open or not
     */
    public void setOpen(boolean alwaysOpen) {
        this.alwaysOpen = alwaysOpen;
    }

    /**
     * Sets the type of bathroom
     * <p>
     *     See constant int variables
     * </p>
     * @param type expects an int corresponding to one of seven bathroom types
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the name of the bathroom
     * @return a String with the name of the bathroom
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the opening time of the bathroom
     * @param open expects an int in 24hr time
     */
    public void setOpen(int open) {
        this.open = open;
    }

    /**
     * Sets the closing time of the bathroom
     * @param close expects an int in 24hr time
     */
    public void setClose(int close) {
        this.close = close;
    }

    /**
     * Gets the rating of the bathroom
     * @return an int corresponding to the quality of the bathroom [0,5]
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Gets the type of the bathroom
     * <p>
     *     See constnat int variables
     * </p>
     * @return a constant int corresponding to the type of bathroom
     */
    public int getType() {
        return this.type;
    }

    /**
     * Checks to see if the bathroom is 24 hours
     * @return a boolean that says if the bathroom is 24 hours or not
     */
    public boolean isAlwaysOpen() {
        return this.alwaysOpen;
    }

    /**
     * Gets the opening time of the bathroom
     * @return an int in 24hr time
     */
    public int getOpenTime() {
        return this.open;
    }

    /**
     * Gets the closing time of the bathroom
     * @return an int in 24hr time
     */
    public int getCloseTime() {
        return this.close;
    }

    /**
     * Checks to see if the bathroom is currently open
     * @param time expects an int representing the time in 24-hour
     * @return a boolean that is true if the bathroom is currently open
     */
    public boolean isOpen(int time) {
       if (time >= open && time < close) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the coodinate location
     * @return a LatLng containing the coordinates
     */
    public LatLng getLocation() {
        return location;
    }





}
