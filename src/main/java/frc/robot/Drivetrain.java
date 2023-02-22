// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

//WHEN THE TIME COMES, ALL COMMENTS THAT HAVE "COMMENTED OUT FOR BOT(wiffle) TESTING:" MUST BE REMOVED TO LET GEAR STUFF WORK!!!

/**
 * Class encapsulates drive position, gear, and auto leveling method.
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
    //COMMENTED OUT FOR BOT(wiffle) TESTING: private Gear m_gear;

    // Pneumatic Controller for Gear box
    private DoubleSolenoid m_solenoid;



    /**
     * Constructor for the drivetrain taking a name.
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
        
        m_solenoid = new DoubleSolenoid(RobotMap.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, RobotMap.DrivetrainConstants.DOUBLE_SOLENOID_LOW_GEAR_PORT, RobotMap.DrivetrainConstants.DOUBLE_SOLENOID_HIGH_GEAR_PORT);

        m_drivetrain = new DifferentialDrive(m_leftSide, m_rightSide);

        // Instantiation of the gear and setting it to unknown.
        //COMMENTED OUT FOR BOT(wiffle) TESTING: m_gear = Gear.kUnknown;

    }

    /**
     * 
     * Init function to set motor inversion.
     * 
     */
    public void initDrivetrain() {

        //TODO: THIS IS CORRECT FOR THE WIFFLEBOT, BUT HAS TO BE CHECKED ON REAL BOT
        m_leftLeader.setInverted(false);
        m_rightLeader.setInverted(true);
        m_leftFollower.setInverted(InvertType.FollowMaster);
        m_rightFollower.setInverted(InvertType.FollowMaster);

        // Shiftgear in robot in Low Gear
        //COMMENTED OUT FOR BOT(wiffle) TESTING: shiftGear(Gear.kLowGear);

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
     * Method that makes the drivetrain move forward/backwards and turn.
     * @param driveInput 
     */
    public void arcadeDrive(DriveInput driveInput) {

        m_drivetrain.arcadeDrive(driveInput.m_speed, driveInput.m_turnSpeed);
        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);
        //this. means the instance of the class that you are currently in (Drivetrain)
        //COMMENTED OUT FOR BOT(wiffle) TESTING: this.shiftGear(driveInput.m_gear);
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
     * Boolean method to check whether thes bot is level at a given time.
     * @param currentPitch the angle of pitch that the robot is currently experiencing
     * @return true if bot is within "level" range, false otherwise
     */
    public boolean isLevel(double currentPitch) {
        if (Math.abs(currentPitch) < RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE){
            return true;
        }
        else {
            return false;
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
    
    //COMMENTED OUT FOR BOT(wiffle) TESTING: 
    /**
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
    */

    /**
     * Gets encoder positions of the drivetrain
     * @return DriveEncoderPos 
     */
    public DriveEncoderPos getEncoderPositions() {
        //TODO: THESE NEGATED POS VARS DO NOT MAKE LOGIC SENSE WITH WIFFLEBOT TESTING, BUT WORK
        double leftPos = -m_leftLeader.getSelectedSensorPosition();
        double rightPos = -m_rightLeader.getSelectedSensorPosition();
        DriveEncoderPos drivePositions = new DriveEncoderPos(leftPos, rightPos);
        return drivePositions;

    }

    /**
     * Zeros out the encoder positions of the drivetrain
     */
    public void zeroEncoders() {
        m_leftLeader.getSensorCollection().setQuadraturePosition(0, RobotMap.TIMEOUT_MS);
        m_rightLeader.getSensorCollection().setQuadraturePosition(0, RobotMap.TIMEOUT_MS);

    }

}
