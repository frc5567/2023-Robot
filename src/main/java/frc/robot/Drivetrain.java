// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

/**
 * 
 * 
 * There is a drivetrain class now.
 * 
 */
public class Drivetrain {
    private String m_drivetrainName;
    private WPI_TalonSRX m_leftLeader;
    private WPI_TalonSRX m_rightLeader;
    private WPI_VictorSPX m_leftFollower;
    private WPI_VictorSPX m_rightFollower;
    private DifferentialDrive m_drivetrain;
    private MotorControllerGroup m_leftSide;
    private MotorControllerGroup m_rightSide;


    /**
     * Constructor for the drivetrain taking a name.
     * 
     * @param dName The name of the drivetrain.
     */
    public Drivetrain(String dName) {
        m_drivetrainName = dName;
        m_leftLeader = new WPI_TalonSRX(0);
        m_rightLeader = new WPI_TalonSRX (1);
        m_leftFollower = new WPI_VictorSPX(0);
        m_rightFollower = new WPI_VictorSPX(1);
        m_leftSide = new MotorControllerGroup(m_leftLeader, m_leftFollower);
        m_rightSide = new MotorControllerGroup(m_rightLeader,m_rightFollower);

        m_drivetrain = new DifferentialDrive(m_leftSide, m_rightSide);

    }

    /**
     * 
     * Init function to set motor inversion.
     * 
     */
    public void initDrivetrain() {

        m_leftLeader.setInverted(false);
        m_rightLeader.setInverted(true);
        m_leftFollower.setInverted(InvertType.FollowMaster);
        m_rightFollower.setInverted(InvertType.FollowMaster);

    }

    /**
     * 
     * Method that makes the drivetrain move forward/backwards and turn.
     * @param speed Value between -1 and 1 for robot speed.
     * @param turn Value between -1 and 1 for turning
     */
    public void arcadeDrive(double speed, double turn) {

        m_drivetrain.arcadeDrive(speed, turn);
        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);


    }


}
