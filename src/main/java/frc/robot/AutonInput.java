package frc.robot;

import frc.robot.Claw.ClawState;

/**
 * Auton input to grab passed states and other related variables needed for Auton functionality
 */
public class AutonInput {
    public RobotState m_desiredState;
    public double m_driveTarget;
    public double m_turnTarget;
    public boolean m_autonComplete;
    public ClawState m_clawState;
    public double m_delay;
    public boolean m_autoLevel;

    /**
     * The default constructor.
     */
    public AutonInput() {
        m_desiredState = RobotState.kUnknown;
        m_driveTarget = RobotMap.NO_INPUT;
        m_turnTarget = RobotMap.NO_INPUT;
        m_autonComplete = false;
        m_clawState = ClawState.kUnknown;
        m_delay = RobotMap.NO_INPUT;
        m_autoLevel = false;
    }
}
