package frc.robot;

/**
 * This encapsulates the inputs for elevator and arm (possible claw later)
 */
public class CoDriveInput {
    public double m_elevatorPos;
    public double m_armPos;

    /**
     * Default constructor which sets zero values
     */
    public CoDriveInput() {
        m_elevatorPos = 0;
        m_armPos = 0;
    }

    /**
     * Constructor that sets the speed and turn
     * @param elevatorPos
     * @param armPos
     */
    public CoDriveInput(double elevatorPos, double armPos) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
    }
}

