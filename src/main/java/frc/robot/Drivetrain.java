// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Talon;

/**
 * 
 * 
 * There is a drivetrain class now.
 * 
 */
public class Drivetrain {
    private String m_drivetrainName;
    private Talon m_leftMotorController;
    private Talon m_rightMotorController;

    /**
     * Constructor for the drivetrain taking a name.
     * 
     * @param dName The name of the drivetrain.
     */
    public Drivetrain(String dName) {
        m_drivetrainName = dName;
        m_leftMotorController = new Talon(0);
        m_rightMotorController = new Talon(1);
    }

    /**
     * 
     * Init function to set motor inversion.
     * 
     */
    public void initDrivetrain() {

        m_leftMotorController.setInverted(false);
        m_rightMotorController.setInverted(true);

    }

    /**
     * 
     * Method that makes the drivetrain move forward/backwards.
     * @param speed Value between -1 and 1 for robot speed.
     */
    public void drivetrainMover(double speed) {

        m_leftMotorController.set(speed);
        m_rightMotorController.set(speed);

    }


}
