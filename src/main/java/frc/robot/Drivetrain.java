// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
    
    // Declaring the gear so it can be used for swicthing between high and low gear
    private Gear m_gear;

    // Pneumatic Controller for Gear box
    private DoubleSolenoid m_solenoid;
    
    
    /**
     * Gear the drivetrain is currently in. Gear Unknown is the intial value before we init it. 
     */
    public enum Gear {
        kLowGear ("Low Gear"),

        kHighGear ("High Gear"),

        kUnknown ("Unknown");

        private String GearName;

       /**
        * This is the constructor for the enum Gear, setting the intial state. 
        */
        Gear (String GearName){
            this.GearName = GearName;
        }

        /**
         * Returns gearname as a string.
         * @return GearName
         */

        public String toString(){
            return this.GearName;
        }
    }
    


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

        // Instanciation of the gear and setting it to unknown.
        m_gear = Gear.kUnknown; 

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

        // Shiftgear in robot in Low Gear
        shiftGear(Gear.kLowGear);

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
        //level bot set to no speed
        if (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE) {
            level = true;
            arcadeDrive(0, 0);
        }
        //crawl speed for bot angle within 6 - 2 degrees
        else if ((Math.abs(currentPitch) > RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE) && (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.UPPER_LOW_RANGE_ANGLE)) {
            double speed = Math.copySign(RobotMap.DrivetrainConstants.CRAWL_LEVEL_DRIVE_SPEED, (-currentPitch));
            arcadeDrive(speed, 0);
        }
        //mid speed for bot angle within 12 - 6 degrees
        else if ((Math.abs(currentPitch) > RobotMap.DrivetrainConstants.UPPER_LOW_RANGE_ANGLE) && (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.UPPER_MID_RANGE_ANGLE)) {
            double speed = Math.copySign(RobotMap.DrivetrainConstants.MID_LEVEL_DRIVE_SPEED, (-currentPitch));
            arcadeDrive(speed, 0);
        }
        //high speed for bot angle within 17 - 12 degrees
        else if ((Math.abs(currentPitch) > RobotMap.DrivetrainConstants.UPPER_MID_RANGE_ANGLE) && (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.MAX_ANGLE)){
            //speed is negated: in Pigeon, actual robot ends are opposite, pitch now reflects that
            double speed = Math.copySign(RobotMap.DrivetrainConstants.HIGH_LEVEL_DRIVE_SPEED, (-currentPitch));
            arcadeDrive(speed, 0);
        }
        return level;
    }

    /**
     * String method to check whether thes bot is level at a given time.
     * @param currentPitch the angle of pitch that the robot is currently experiencing
     * @return String ending for a leveled bot, if level, otherwise String ending for not leveled bot
     */
    public String isLevel(double currentPitch) {
        if (Math.abs(currentPitch) < RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE){
            return "is level.";
        }
        else {
            return "is not level.";
        }
    }

    /**
     * Applies brake mode on drive train motor controllers. 
     */
    public void brakeMode() {
        m_leftLeader.setNeutralMode(NeutralMode.Brake);
        m_rightLeader.setNeutralMode(NeutralMode.Brake);
        m_leftFollower.setNeutralMode(NeutralMode.Brake);
        m_rightFollower.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Applies coast mode on drive train motor controllers. 
     */
    public void coastMode() {
        m_leftLeader.setNeutralMode(NeutralMode.Coast);
        m_rightLeader.setNeutralMode(NeutralMode.Coast);
        m_leftFollower.setNeutralMode(NeutralMode.Coast);
        m_rightFollower.setNeutralMode(NeutralMode.Coast);
    }

    /** shiftGear is the way we change between high and low gear (Gear.kLowGear and Gear.kHighGear)
     * @param gear
     */
    public void shiftGear(Gear gear){
        // Compare the incoming parameter to the current state and determine if it is already set to that gear. 
        if (m_gear == gear){
            return;
        }

        m_gear = gear; 

        // Solenoid causes the gear to shift. kForward(Plunger Out) and KReverse (Plunger In) are both in WPILib. 
        // Together they change the Penumatics.
        if(m_gear == Gear.kLowGear){
            m_solenoid.set(Value.kForward);
        }
        else if (m_gear == Gear.kHighGear){
            m_solenoid.set(Value.kReverse);
        }
    }




}
