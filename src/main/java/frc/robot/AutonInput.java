package frc.robot;

import frc.robot.Claw.ClawState;

public class AutonInput {
    public RobotState m_desiredState;
    public double m_driveTarget;
    public double m_turnTarget;
    public boolean m_autonComplete;
    public ClawState m_clawState;
    public double m_delay;
    public boolean m_autoLevel;

    public AutonInput() {
        m_desiredState = RobotState.kUnknown;
        m_driveTarget = 0;
        m_turnTarget = 0;
        m_autonComplete = false;
        m_clawState = ClawState.kUnknown;
        m_delay = Double.NaN;
        m_autoLevel = false;
    }

    public AutonInput(RobotState desiredState, double driveTarget, double turnTarget, boolean autonComplete, ClawState clawState, double delay, boolean autoLevelIn) {
        m_desiredState = desiredState;
        m_driveTarget = driveTarget;
        m_turnTarget = turnTarget;
        m_autonComplete = autonComplete;
        m_clawState = clawState;
        m_delay = delay;
        m_autoLevel = autoLevelIn;
    }
}
