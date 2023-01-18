package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class PilotController {
    private XboxController m_controller;

    public PilotController() {
        m_controller = new XboxController(0);

    }

    /**
     * Takes the driver imput from the controller.
     * @return speed (0) and turn (1) values in an array
     */
    public double[] getDriverImput() {
        double[] driverImput = new double[2];

        driverImput[0] = (m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis());
        driverImput[1] = m_controller.getLeftX();
        return driverImput;
    }
}
