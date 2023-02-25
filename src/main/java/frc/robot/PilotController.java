package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Encapsulates the pilot controller inputs
 */
public class PilotController {
    private XboxController m_controller;

    /**
     * Constructor for the pilot controller. Instantiates the xbox controller.
     */
    public PilotController() {
        m_controller = new XboxController(RobotMap.PilotControllerConstants.XBOX_CONTROLLER_USB_PORT);

    }

    /**
     * Takes the driver input from the controller.
     * @return speed (0) and turn (1) values in an array
     */
    public DriveInput getDriverInput() {
        DriveInput driverInput = new DriveInput();

        if (m_controller.getStartButton()) {
            driverInput.m_armPosition = RobotMap.CopilotConstants.ARM_START_POS;
        }
        else if (m_controller.getAButton()) {
            driverInput.m_armPosition = RobotMap.CopilotConstants.ARM_FLOOR_POS;
        }
        else if (m_controller.getBButton()) {
            driverInput.m_armPosition = RobotMap.CopilotConstants.ARM_MID_POS;
        }
        else if (m_controller.getXButton()) {
            driverInput.m_armPosition = RobotMap.CopilotConstants.ARM_HIGH_POS;
        }
        else if (m_controller.getYButton()) {
            driverInput.m_armPosition = RobotMap.CopilotConstants.ARM_APPROACH_POS;
        }
        else {
            driverInput.m_armPosition = RobotMap.NO_POS_INPUT;
            double speed = m_controller.getRightY();
           if (Math.abs(speed) > 0.09) {
            driverInput.m_manualArm = speed;
           }
           else {
            driverInput.m_manualArm = 0;
           }  
        }

        driverInput.m_speed = (m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis());

        //Adjusting for a deadband to compensate for controller stick drift.
        driverInput.m_turnSpeed = adjustForDeadband(m_controller.getLeftX());

        driverInput.m_gear = getPilotGear();
        driverInput.m_isAutoLeveling = this.isAutoLeveling();
        return driverInput;
    }

    /** 
     * Get driver gear change.
     * @return Gear indicates what the pilot is telling us to do. Unknown indicates no gear change.
     */ 
    public Gear getPilotGear(){
        Gear returnGear = Gear.kUnknown;

        // When x button is pressed, the robot switches to high gear.
        if(m_controller.getLeftBumper()){
            returnGear = Gear.kHighGear;
        } 
        // When y button is pressed the robot switches to low gear. 
        else if(m_controller.getRightBumper()){
            returnGear = Gear.kLowGear; 
        }
        return returnGear;
    }
    
    /**
     * Return input from the controller for auto leveling.
     */
    public boolean isAutoLeveling() {
        boolean autoLeveling = m_controller.getBackButton();

        return autoLeveling;
    }

    /**
     * Deadband method for stick.
     * @param stickInput takes a value from -1 to 1.
     * @return input value adjusted for deadband. It is a double with a value between -1 and 1.
     */
    private double adjustForDeadband(double stickInput) {
        double retVal = 0;
        double deadband = RobotMap.PilotControllerConstants.STICK_DEADBAND;
    
        //take absolute value for simplification.
        double absStickInput = Math.abs(stickInput);

        //if the stick input is outside the deadband, adjust.
        if (!(absStickInput < deadband)) {
            retVal = (absStickInput / (1.0 - deadband));
            retVal = Math.copySign(retVal, stickInput);
        }

        return retVal;
    }
}
