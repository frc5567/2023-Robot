package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Claw.ClawState;
import frc.robot.CoDriveInput.ToggleInput;
import frc.robot.Shoulder.ShoulderState;

/**
 * Encapsulates the input for the copilot controller.
 */
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

        //commented out arm code for testing elevator
        if (m_controller.getStartButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_START_POS;
            coDriveInput.m_desiredState = RobotState.kTravel;
        }
        else if (m_controller.getAButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_FLOOR_POS;
            coDriveInput.m_desiredState = RobotState.kFloorPickup;

        }
        else if (m_controller.getBButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_MID_POS;
            coDriveInput.m_desiredState = RobotState.kShelfPickup;

        }
        else if (m_controller.getXButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_HIGH_POS;
            coDriveInput.m_desiredState = RobotState.kFloorPiece;

        }
        else if (m_controller.getYButton()){
            coDriveInput.m_desiredState = RobotState.kMidPiece;
        }
        else {
           coDriveInput.m_elevatorPos = RobotMap.NO_POS_INPUT;
           double speed = m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis();
           if (Math.abs(speed) > 0.09) {
            coDriveInput.m_manualElevator = speed;
           }
           else {
            coDriveInput.m_manualElevator = 0;
           }   
        }

        // When the right bumper is pressed, toggles the claw state.
        if (m_controller.getRightBumperPressed()) {
                coDriveInput.m_clawPos = ToggleInput.kToggle;
        }

        // When the left bumper is pressed, toggles the shoulder state.
        if (m_controller.getLeftBumperPressed()) {
                coDriveInput.m_shoulderPos = ToggleInput.kToggle;
        }

        if (m_controller.getBackButton()) {
            coDriveInput.m_armPos = 30;
        }
        else {
            coDriveInput.m_armPos = 0;
        }

        return coDriveInput;
    }

}
