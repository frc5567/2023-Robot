package frc.robot;

/**
 * This encapsulates the drive encoder positions
 */
public class DriveEncoderPos {
    public double m_leftLeaderPos;
    public double m_rightLeaderPos;

    /**
     * Default constructor which sets values to zero
     */
    public DriveEncoderPos() {
        m_leftLeaderPos = 0;
        m_rightLeaderPos = 0;
    }
    
    /**
     * Constructor that takes drive encoder positions
     * @param leftPos
     * @param rightPos
     */
    public DriveEncoderPos(double leftPos, double rightPos) {
        m_leftLeaderPos = leftPos;
        m_rightLeaderPos = rightPos;
    }
}
