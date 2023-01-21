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
        m_leftLeader = new WPI_TalonSRX(RobotMap.DrivetrainConstants.LEFT_LEADER_CAN_ID);
        m_rightLeader = new WPI_TalonSRX (RobotMap.DrivetrainConstants.RIGHT_LEADER_CAN_ID);
        m_leftFollower = new WPI_VictorSPX(RobotMap.DrivetrainConstants.LEFT_FOLLOWER_CAN_ID);
        m_rightFollower = new WPI_VictorSPX(RobotMap.DrivetrainConstants.RIGHT_FOLLOWER_CAN_ID);
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
     * Method that makes the drivetrain move forward/backwards and turn.
     * @param speed Value between -1 and 1 for robot speed.
     * @param turn Value between -1 and 1 for turning
     */
    public void arcadeDrive(double speed, double turn) {

        m_drivetrain.arcadeDrive(speed, turn);
        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);
    }

    /**
     * Decides what direction to drive and with how much power based on the pitch while on the charging station.
     * @param currentPitch the angle of pitch that the robot is currently experiencing
     * @return true if level, false if not
     */
    public boolean autoLevel(double currentPitch) {
        
        boolean level = false;
        if (Math.abs(currentPitch) < RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE) {
            level = true;
            arcadeDrive(0, 0);
        }
        else {
            double speed = Math.copySign(RobotMap.DrivetrainConstants.LEVEL_DRIVE_SPEED, (-currentPitch));
            arcadeDrive(speed, 0);
        }
        return level;
    }

}
