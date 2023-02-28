package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Claw.ClawState;
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
            coDriveInput.m_elevatorPos = RobotMap.ElevatorConstants.ELEVATOR_START_POS;
        }
        else if (m_controller.getAButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_FLOOR_POS;
            coDriveInput.m_elevatorPos = RobotMap.ElevatorConstants.ELEVATOR_FLOOR_POS;

        }
        else if (m_controller.getBButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_MID_POS;
            coDriveInput.m_elevatorPos = RobotMap.ElevatorConstants.ELEVATOR_MID_POS;

        }
        else if (m_controller.getXButton()) {
            //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_HIGH_POS;
            coDriveInput.m_elevatorPos = RobotMap.ElevatorConstants.ELEVATOR_HIGH_POS;

        }
        // else if (m_controller.getYButton()){
        //     //coDriveInput.m_armPos = RobotMap.CopilotConstants.ARM_APPROACH_POS;
        // }
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
        if (m_controller.getRightBumper()) {
            if (coDriveInput.m_clawPos == ClawState.kOpen) {
                coDriveInput.m_clawPos = ClawState.kClosed;
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
            else {
                coDriveInput.m_shoulderPos = ShoulderState.kDown;
            }
        }

        return coDriveInput;
    }

}
