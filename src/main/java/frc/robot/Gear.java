package frc.robot;

/**
 * Gear the drivetrain is currently in. Gear Unknown is the intial value before we init it. 
 */
public enum Gear {
    kLowGear ("Low Gear"),

    kHighGear ("High Gear"),

    kUnknown ("Unknown");

    private String GearName;

    /**
    * This is the constructor for the enum Gear, setting the intial state. 
    */
    Gear (String GearName){
        this.GearName = GearName;
    }

    /**
     * Returns gearname as a string.
     * @return GearName
     */
    public String toString(){
        return this.GearName;
    }
}
