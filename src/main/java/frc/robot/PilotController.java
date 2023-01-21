package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class PilotController {
    private XboxController m_controller;

    public PilotController() {
        m_controller = new XboxController(RobotMap.PilotControllerConstants.XBOX_CONTROLLER_USB_PORT);

    }

    /**
     * Takes the driver input from the controller.
     * @return speed (0) and turn (1) values in an array
     */
    public double[] getDriverInput() {
        double[] driverInput = new double[2];

        driverInput[RobotMap.PilotControllerConstants.DRIVER_INPUT_SPEED] = (m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis());
        //Adjusting for a deadband to compensate for controller stick drift.
        driverInput[RobotMap.PilotControllerConstants.DRIVER_INPUT_TURN] = adjustForDeadband(m_controller.getLeftX());
        return driverInput;
    }

    /**
     * Return input from the controller for auto leveling.
     */
    public boolean isAutoLeveling() {
        boolean autoLeveling = m_controller.getAButton();

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
