package frc.robot;

import frc.robot.CoDriveInput.ToggleInput;

/**
 * Encapsulates the input for the copilot controller.
 */
public class CopilotController {
    private GamePad m_controller;

    /**
     * Constructor that sets the port for the Xbox controller(will be gamepad eventually).
     */
    public CopilotController() {
        m_controller = new GamePad(1);
    }

    /**
     * Takes copilot driver input from the controller/gamepad 
     * @return the position of the arm/elevator
     */
    public CoDriveInput getCoDriveInput() {
        CoDriveInput coDriveInput = new CoDriveInput();

        //commented out arm code for testing elevator
        if (m_controller.getTravelPressed()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_START_POS;
            coDriveInput.m_desiredState = RobotState.kTravel;
        }
        else if (m_controller.getFloorPickupPressed()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_FLOOR_POS;
            coDriveInput.m_desiredState = RobotState.kFloorPickup;

        }
        else if (m_controller.getShelfPickupPressed()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_MID_POS;
            coDriveInput.m_desiredState = RobotState.kShelfPickup;

        }
        else if (m_controller.getLowPlacePressed()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_HIGH_POS;
            coDriveInput.m_desiredState = RobotState.kFloorPiece;

        }
        else if (m_controller.getMidPlacePressed()){
            coDriveInput.m_desiredState = RobotState.kMidPiece;
        }
        else if (m_controller.getHighConePressed()){
            coDriveInput.m_desiredState = RobotState.kHighCone;
        }
        else if (m_controller.getHighCubePressed()){
            coDriveInput.m_desiredState = RobotState.kHighCube;
        }
        else if (m_controller.getMidApproachPressed()){
            coDriveInput.m_desiredState = RobotState.kApproachMid;
        }
        else if (m_controller.getHighApproachPressed()){
            coDriveInput.m_desiredState = RobotState.kApproachHigh;
        }
        else if (m_controller.getElevatorUpPressed()){
            coDriveInput.m_manualElevator = 0.2;
        }
        else if (m_controller.getElevatorDownPressed()){
            coDriveInput.m_manualElevator = -0.2;
        }
        else if (m_controller.getArmUpPressed()){
            coDriveInput.m_manualArm = 0.2;
        }
        else if (m_controller.getArmDownPressed()){
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
