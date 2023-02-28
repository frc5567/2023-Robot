package frc.robot;

import frc.robot.Claw.ClawState;
import frc.robot.Shoulder.ShoulderState;

/**
 * This encapsulates the inputs for elevator and arm (possible claw later)
 */
public class CoDriveInput {
    public double m_elevatorPos;
    public double m_armPos;
    public ClawState m_clawPos;
    public ShoulderState m_shoulderPos;
    public double m_manualElevator;

    /**
     * Default constructor which sets zero values
     */
    public CoDriveInput() {
        m_elevatorPos = RobotMap.ElevatorConstants.ELEVATOR_START_POS;
        m_armPos = RobotMap.ArmConstants.ARM_START_POS;
        m_manualElevator = 0;
        //TODO: might want to switch default value to closed in the future.
        m_clawPos = ClawState.kOpen;
        m_shoulderPos = ShoulderState.kDown;
    }  

    /**
     * Constructor that sets the elevator and arm positions.
     * @param elevatorPos
     * @param armPos
     */
    public CoDriveInput(double elevatorPos, double armPos) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_manualElevator = 0;
        m_clawPos = ClawState.kOpen;
        m_shoulderPos = ShoulderState.kDown;
    }

    /**
     * Constructor that sets the elevator, arm, and claw positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     */
    public CoDriveInput(double elevatorPos, double armPos, ClawState clawPos) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_manualElevator = 0;
        m_shoulderPos = ShoulderState.kDown;
    }

    /**
     * Constructor that sets the elevator, arm, claw, and shoulder positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     * @param shoulderPos
     */
    public CoDriveInput(double elevatorPos, double armPos, ClawState clawPos, ShoulderState shoulderPos) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_shoulderPos = shoulderPos;
        m_manualElevator = 0;
    }

    /**
     * Constructor that sets the elevator, arm, claw, manual elevator speed, and shoulder positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     * @param shoulderPos
     * @param manualElevator
     */
    public CoDriveInput(double elevatorPos, double armPos, ClawState clawPos, ShoulderState shoulderPos, double manualElevator) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_shoulderPos = shoulderPos;
        m_manualElevator = manualElevator;
    }
}

