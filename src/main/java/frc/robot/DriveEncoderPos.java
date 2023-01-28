package frc.robot;

public class DriveEncoderPos {
    public double m_leftLeaderPos;
    public double m_rightLeaderPos;

    public DriveEncoderPos() {
        m_leftLeaderPos = 0;
        m_rightLeaderPos = 0;
    }
    
    public DriveEncoderPos(double leftPos, double rightPos) {
        m_leftLeaderPos = leftPos;
        m_rightLeaderPos = rightPos;
    }
}
