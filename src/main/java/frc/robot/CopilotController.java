package frc.robot;

import frc.robot.CoDriveInput.ToggleInput;

/**
 * Class that represents the Copilot controller and methods associated with its functionality
 */
public class CopilotController {
    private GamePad m_controller;

    /**
     * Constructor that sets the port for the GamePad
     */
    public CopilotController() {
        m_controller = new GamePad(1);
    }

    /**
     * Takes copilot driver input from the controller/gamepad 
     * @return the CoDriveInput object representing Copilot input to the bot
     */
    public CoDriveInput getCoDriveInput() {
        CoDriveInput coDriveInput = new CoDriveInput();

        if (m_controller.getTravelPressed()) {
            coDriveInput.m_desiredState = RobotState.kTravel;
        }
        else if (m_controller.getFloorPickupPressed()) {
            coDriveInput.m_desiredState = RobotState.kFloorPickup;
        }
        else if (m_controller.getShelfPickupPressed()) {
            coDriveInput.m_desiredState = RobotState.kShelfPickup;
        }
        else if (m_controller.getLowPlacePressed()) {
            coDriveInput.m_desiredState = RobotState.kFloorPiece;
        }
        else if (m_controller.getMidPlacePressed()) {
            coDriveInput.m_desiredState = RobotState.kMidPiece;
        }
        else if (m_controller.getHighConePressed()) {
            coDriveInput.m_desiredState = RobotState.kHighCone;
        }
        else if (m_controller.getHighCubePressed()) {
            coDriveInput.m_desiredState = RobotState.kHighCube;
        }
        else if (m_controller.getMidApproachPressed()) {
            coDriveInput.m_desiredState = RobotState.kApproachMid;
        }
        else if (m_controller.getHighApproachPressed()) {
            coDriveInput.m_desiredState = RobotState.kApproachHigh;
        }
        else if (m_controller.getElevatorUpPressed()) {
            coDriveInput.m_manualElevator = 0.2;
        }
        else if (m_controller.getElevatorDownPressed()) {
            coDriveInput.m_manualElevator = -0.2;
        }
        else if (m_controller.getArmUpPressed()) {
            coDriveInput.m_manualArm = 0.2;
        }
        else if (m_controller.getArmDownPressed()) {
            coDriveInput.m_manualArm = -0.2;
        }
        
        // When the right bumper is pressed, toggles the claw state.
        if (m_controller.getToggleClawPressed()) {
                coDriveInput.m_clawPos = ToggleInput.kToggle;
        }

        // When the left bumper is pressed, toggles the shoulder state.
        if (m_controller.getToggleShoulderPressed()) {
                coDriveInput.m_shoulderPos = ToggleInput.kToggle;
        }

        return coDriveInput;
    }
    
}
