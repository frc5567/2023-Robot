package frc.robot;

public class AutonInput {
    public RobotState m_desiredState;
    public double m_driveTarget;
    public double m_turnTarget;
    public boolean m_autonComplete;

    public AutonInput(RobotState desiredState, double driveTarget, double turnTarget, boolean autonComplete) {
        m_desiredState = desiredState;
        m_driveTarget = driveTarget;
        m_turnTarget = turnTarget;
        m_autonComplete = autonComplete;
    }
}
