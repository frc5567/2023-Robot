package frc.robot;

import frc.robot.Claw.ClawState;
import frc.robot.Shoulder.ShoulderState;

/**
 * This encapsulates the inputs for elevator and arm (possible claw later)
 */
public class CoDriveInput {
    public double m_elevatorPos;
    public double m_armPos;
    public ToggleInput m_clawPos;
    public ToggleInput m_shoulderPos;
    public double m_manualElevator;
    public RobotState m_desiredState;

    public enum ToggleInput {
        kNoInput(0),
        kToggle(-1);

        private int m_toggleInput;

        /**
         * sets the member variable to the claw value
         * @param value
         */
        ToggleInput(int value) {
            m_toggleInput = value;
        }
        public int getToggleState() {
            return m_toggleInput;
        }
    }

    /**
     * Default constructor which sets zero values
     */
    public CoDriveInput() {
        m_elevatorPos = RobotMap.ElevatorConstants.ELEVATOR_START_POS;
        m_armPos = RobotMap.ArmConstants.ARM_START_POS;
        m_manualElevator = 0;
        //TODO: might want to switch default value to closed in the future.
        m_clawPos = ToggleInput.kNoInput;
        m_shoulderPos = ToggleInput.kNoInput;
        m_desiredState = RobotState.kUnknown;
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
        m_clawPos = ToggleInput.kNoInput;
        m_shoulderPos = ToggleInput.kNoInput;
        m_desiredState = RobotState.kUnknown;
    }

    /**
     * Constructor that sets the elevator, arm, and claw positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     */
    public CoDriveInput(double elevatorPos, double armPos, ToggleInput clawPos) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_manualElevator = 0;
        m_shoulderPos = ToggleInput.kNoInput;
        m_desiredState = RobotState.kUnknown;
    }

    /**
     * Constructor that sets the elevator, arm, claw, and shoulder positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     * @param shoulderPos
     */
    public CoDriveInput(double elevatorPos, double armPos, ToggleInput clawPos, ToggleInput shoulderPos) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_shoulderPos = shoulderPos;
        m_manualElevator = 0;
        m_desiredState = RobotState.kUnknown;
    }

    /**
     * Constructor that sets the elevator, arm, claw, manual elevator speed, and shoulder positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     * @param shoulderPos
     * @param manualElevator
     */
    public CoDriveInput(double elevatorPos, double armPos, ToggleInput clawPos, ToggleInput shoulderPos, double manualElevator) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_shoulderPos = shoulderPos;
        m_manualElevator = manualElevator;
        m_desiredState = RobotState.kUnknown;
    }

    /**
     * Constructor that sets the elevator, arm, claw, manual elevator speed, and shoulder positions.
     * @param elevatorPos
     * @param armPos
     * @param clawPos
     * @param shoulderPos
     * @param manualElevator
     * @param desiredState
     */
    public CoDriveInput(double elevatorPos, double armPos, ToggleInput clawPos, ToggleInput shoulderPos, double manualElevator, RobotState desiredState) {
        m_elevatorPos = elevatorPos;
        m_armPos = armPos;
        m_clawPos = clawPos;
        m_shoulderPos = shoulderPos;
        m_manualElevator = manualElevator;
        m_desiredState = desiredState;
    }
}

