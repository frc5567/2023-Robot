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
        m_driveTarget = 0;
        m_turnTarget = 0;
        m_autonComplete = false;
        m_clawState = ClawState.kUnknown;
        m_delay = Double.NaN;
        m_autoLevel = false;
    }

    /**
     * The main constructor, at least with parameters.
     * @param desiredState this is the passed in desired state to transition to; this one is dedicated for Jessica
     * @param driveTarget the target in inches to drive to; this one is dedicated for Emma
     * @param turnTarget the target angle to turn to; this one is dedicated for BrayBae
     * @param autonComplete a boolean for denoting when the actual auton path is complete; this one is dedicated for Chris
     * @param clawState the current state of the Claw; this one is dedicated for Curt
     * @param delay the delay counter in cycles; this one is dedicated for Erik
     * @param autoLevelIn the boolean for denoting whether the autoLevel is ; this one is dedicated for Jacen
     */
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
