package frc.robot;

public class AutonInput {
    public RobotState m_desiredState;
    public double m_driveTarget;
    public double m_turnTarget;
    public boolean m_autonComplete;

    public AutonInput() {
        m_desiredState = RobotState.kUnknown;
        m_driveTarget = 0;
        m_turnTarget = 0;
        m_autonComplete = false;
    }

    public AutonInput(RobotState desiredState, double driveTarget, double turnTarget, boolean autonComplete) {
        m_desiredState = desiredState;
        m_driveTarget = driveTarget;
        m_turnTarget = turnTarget;
        m_autonComplete = autonComplete;
    }
}
