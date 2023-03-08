package frc.robot;

/**
 * This encapsulates the inputs for arcade drive
 */
public class DriveInput {
    public double m_speed;
    public double m_turnSpeed;
    public Gear m_gear;
    public boolean m_isAutoLeveling;

    /**
     * Default constructor which sets zero values
     */
    public DriveInput() {
        m_speed = 0;
        m_turnSpeed = 0;
        m_gear = Gear.kLowGear;
        m_isAutoLeveling = false;
    }

    /**
     * Constructor that sets the speed and turn
     * @param speed
     * @param turn
     */
    public DriveInput(double speed, double turn) {
        m_speed = speed;
        m_turnSpeed = turn;
        m_gear = Gear.kLowGear;
        m_isAutoLeveling = false;
    }

     /**
     * Constructor that sets the speed, turn, and gear
     * @param speed
     * @param turn
     * @param gear
     */
    public DriveInput(double speed, double turn, Gear gear) {
        m_speed = speed;
        m_turnSpeed = turn;
        m_gear = gear;
        m_isAutoLeveling = false;
    }

    /**
     * Constructor that sets the speed, turn, arm position, and gear
     * @param speed
     * @param turn
     * @param gear
     * @param armPos
     */
    public DriveInput(double speed, double turn, Gear gear, double armPos) {
        m_speed = speed;
        m_turnSpeed = turn;
        m_gear = gear;
        m_isAutoLeveling = false;
    }

    /**
     * Constructor that sets the speed, turn, arm position, manual arm speed, and gear
     * @param speed
     * @param turn
     * @param gear
     * @param armPos
     * @param manualArm 
     */
    public DriveInput(double speed, double turn, Gear gear, double armPos, double manualArm) {
        m_speed = speed;
        m_turnSpeed = turn;
        m_gear = gear;
        m_isAutoLeveling = false;
    }

    /**
     * Constructor that sets the speed, turn, arm position, manual arm speed, and gear
     * @param speed
     * @param turn
     * @param gear
     * @param armPos
     * @param manualArm 
     * @param desiredState
     */
    public DriveInput(double speed, double turn, Gear gear, double armPos, double manualArm, RobotState desiredState) {
        m_speed = speed;
        m_turnSpeed = turn;
        m_gear = gear;
        m_isAutoLeveling = false;
    }
}
