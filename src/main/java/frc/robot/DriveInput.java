package frc.robot;

/**
 * This encapsulates the inputs for arcade drive
 */
public class DriveInput {
    public double m_speed;
    public double m_turnSpeed;
    public Gear m_gear;
    public boolean m_isAutoLeveling;
    public double m_angle;

    /**
     * Default constructor which sets default values
     */
    public DriveInput() {
        m_speed = 0;
        m_turnSpeed = 0;
        m_gear = Gear.kLowGear;
        m_isAutoLeveling = false;
        m_angle = RobotMap.NO_INPUT;
    }
}
