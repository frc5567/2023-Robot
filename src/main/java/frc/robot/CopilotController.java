package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Claw.ClawState;
import frc.robot.Shoulder.ShoulderState;

public class CopilotController {
    private XboxController m_controller;

    /**
     * Constructor that sets the port for the Xbox controller(will be gamepad eventually).
     */
    public CopilotController() {
        m_controller = new XboxController(1);
    }

    /**
     * Takes copilot driver input from the controller/gamepad 
     * @return the position of the arm/elevator
     */
    public CoDriveInput getCoDriveInput() {
        CoDriveInput coDriveInput = new CoDriveInput();

        if (m_controller.getAButton()) {
            coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_START_POS;
            coDriveInput.m_elevatorPos = RobotMap.CopilotConstants.ELEVATOR_START_POS;
        }
        else if (m_controller.getXButton()) {
            coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_LOW_POS;
            coDriveInput.m_elevatorPos = RobotMap.CopilotConstants.ELEVATOR_LOW_POS;

        }
        else if (m_controller.getBButton()) {
            coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_MID_POS;
            coDriveInput.m_elevatorPos = RobotMap.CopilotConstants.ELEVATOR_MID_POS;

        }
        else if (m_controller.getYButton()) {
            coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_HIGH_POS;
            coDriveInput.m_elevatorPos = RobotMap.CopilotConstants.ELEVATOR_HIGH_POS;

        }
        else {
            coDriveInput.m_armPos = -1;
            coDriveInput.m_elevatorPos = -1;
        }
        // When the right bumper is pressed, toggles the claw state.
        if (m_controller.getRightBumper()) {
            if (coDriveInput.m_clawPos == ClawState.kOpen) {
                coDriveInput.m_clawPos = ClawState.kClosed;
            }
            else if (coDriveInput.m_clawPos == ClawState.kClosed) {
                coDriveInput.m_clawPos = ClawState.kOpen;
            }
            else {
                coDriveInput.m_clawPos = ClawState.kOpen;
            }
        }
        // When the left bumper is pressed, toggles the shoulder state.
        if (m_controller.getLeftBumper()) {

            if (coDriveInput.m_shoulderPos == ShoulderState.kDown) {
                coDriveInput.m_shoulderPos = ShoulderState.kUp;
            }
            else if (coDriveInput.m_shoulderPos == ShoulderState.kUp) {
                coDriveInput.m_shoulderPos = ShoulderState.kDown;
            }
            else {
                //TODO check starting state
                coDriveInput.m_shoulderPos = ShoulderState.kDown;
            }
        }

        return coDriveInput;
    }

}
