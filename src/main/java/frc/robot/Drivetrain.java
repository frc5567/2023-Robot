// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;
import com.ctre.phoenix.sensors.PigeonIMU_StatusFrame;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Class encapsulates methods pertaining to Drivetrain functionality
 */
public class Drivetrain {
    private WPI_TalonFX m_leftLeader;
    private WPI_TalonFX m_rightLeader;
    private WPI_TalonFX m_leftFollower;
    private WPI_TalonFX m_rightFollower;

    private Pigeon2 m_pidgey;

    private double[] m_angleDeltas;
    	
    /** Config Objects for motor controllers */
	TalonFXConfiguration m_leftConfig = new TalonFXConfiguration();
	TalonFXConfiguration m_rightConfig = new TalonFXConfiguration();

    // Declaring the gear so it can be used for swicthing between high and low gear
    private Gear m_gear;

    // Pneumatic Controller for Gear box
    private DoubleSolenoid m_solenoid;

    //Counter for Autolevel auton
    private int m_levelCounter;

    private int m_outputCounter;

    /**
     * Main constructor for the drivetrain class
     * @param pidgey IMU pigeon instance
     */
    public Drivetrain(Pigeon2 pidgey) {
        m_leftLeader = new WPI_TalonFX(RobotMap.DrivetrainConstants.LEFT_LEADER_CAN_ID);
        m_rightLeader = new WPI_TalonFX (RobotMap.DrivetrainConstants.RIGHT_LEADER_CAN_ID);
        m_leftFollower = new WPI_TalonFX(RobotMap.DrivetrainConstants.LEFT_FOLLOWER_CAN_ID);
        m_rightFollower = new WPI_TalonFX(RobotMap.DrivetrainConstants.RIGHT_FOLLOWER_CAN_ID);
        
        m_solenoid = new DoubleSolenoid(RobotMap.PCM_CAN_ID, PneumaticsModuleType.CTREPCM, RobotMap.DrivetrainConstants.DOUBLE_SOLENOID_LOW_GEAR_PORT, RobotMap.DrivetrainConstants.DOUBLE_SOLENOID_HIGH_GEAR_PORT);

        m_pidgey = pidgey;

        m_angleDeltas = new double[RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT];

        // Instantiation of the gear and setting it to unknown.
        m_gear = Gear.kUnknown;

        m_levelCounter = 0;
        m_outputCounter = 0;
    }

    /**
     * Init function to intialize the Drivetrain in its entirety
     */
    public void initDrivetrain() {
        /* Reset Configs */
		m_pidgey.configFactoryDefault();
        m_rightLeader.configFactoryDefault();
        m_leftLeader.configFactoryDefault();
        m_rightFollower.configFactoryDefault();
        m_leftFollower.configFactoryDefault();

        m_leftLeader.setInverted(true);
        m_rightLeader.setInverted(false);
        m_leftFollower.setInverted(InvertType.FollowMaster);
        m_rightFollower.setInverted(InvertType.FollowMaster);

        m_levelCounter = 0;

        // Shiftgear in robot in Low Gear
        this.shiftGear(Gear.kLowGear);

        this.zeroSensors();

        this.configPID();
    }

    /**
     * Method that makes the drivetrain move forward/backwards and turn.
     * @param speed Value between -1 and 1 for robot speed.
     * @param turn Value between -1 and 1 for turning
     */
    public void arcadeDrive(double speed, double turn) {
        m_leftLeader.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -turn);
        m_rightLeader.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, turn);
        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);
    }

    /**
     * Method that makes the drivetrain move forward/backwards and turn and allows for shifting gears.
     * @param driveInput the Pilot DriveInput object
     */
    public void arcadeDrive(DriveInput driveInput) {
        m_leftLeader.set(ControlMode.PercentOutput, driveInput.m_speed, DemandType.ArbitraryFeedForward, -driveInput.m_turnSpeed);
        m_rightLeader.set(ControlMode.PercentOutput, driveInput.m_speed, DemandType.ArbitraryFeedForward, driveInput.m_turnSpeed);
        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);
        this.shiftGear(driveInput.m_gear);

        if ((m_outputCounter++) % 100 == 0) {
            //used to see if motor is overheating during competition
            System.out.print("Speed [" + driveInput.m_speed + "] LT [" + -driveInput.m_turnSpeed + "] RT[" + driveInput.m_turnSpeed + "]");
            double outputLL = m_leftLeader.getMotorOutputPercent();
            double outputRL = m_rightLeader.getMotorOutputPercent();
            double outputLF = m_leftFollower.getMotorOutputPercent();
            double outputRF = m_rightFollower.getMotorOutputPercent();
            System.out.print("Output LL [" + outputLL + "] RL [" + outputRL + "] LF [" + outputLF + "] RF [" + outputRF + "]");
            double tempLL = m_leftLeader.getTemperature();
            double tempRL = m_rightLeader.getTemperature();
            double tempLF = m_leftFollower.getTemperature();
            double tempRF = m_rightFollower.getTemperature();
            System.out.println("Temp LL [" + tempLL + "] RL [" + tempRL + "] LF [" + tempLF + "] RF [" + tempRF + "]");
        }
    }

    /**
     * Decides what direction to drive and with how much power based on the pitch while on the charging station.
     * @param currentPitch the angle of pitch that the robot is currently experiencing
     * @return true if level and stabilized, false if not
     */
    public boolean autoLevel(double currentPitch) {
        boolean level = false;
        String didthething = "";
        double speed = 0.0;

        //level bot set to no speed
        if (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE) {
            if (m_levelCounter < RobotMap.DrivetrainConstants.AUTOLEVEL_COUNTER) {
                m_levelCounter++;
            }
            else {
                level = true;
                m_levelCounter = 0;
            }
            arcadeDrive(0, 0);
        }
        //crawl speed for bot angle within 6 - 2 degrees
        else if ((Math.abs(currentPitch) > RobotMap.DrivetrainConstants.MAX_LEVEL_ANGLE) && (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.UPPER_LOW_RANGE_ANGLE)) {
            //speed = Math.copySign(RobotMap.DrivetrainConstants.CRAWL_LEVEL_DRIVE_SPEED, (-currentPitch));
            speed = 0.0;

            if (Math.abs(currentPitch) > RobotMap.DrivetrainConstants.ONE_CYCLE_ANGLE_DEADBAND) {
                int count = 0;
                for (int i = 0; i < RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT; i++) {
                    double deadband = (RobotMap.DrivetrainConstants.ONE_CYCLE_ANGLE_DEADBAND * (i+1));
                    if (Math.abs(currentPitch) < (m_angleDeltas[i] - deadband)) {
                        count++;
                    }
                }
                if (count > (RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT * 0.66)) {
                    //speed = Math.copySign(RobotMap.DrivetrainConstants.CRAWL_LEVEL_DRIVE_SPEED, (currentPitch));
                    speed = 0.0;
                    didthething = "yass";
                }
            }

            arcadeDrive(speed, 0);
            m_levelCounter = 0;
        }        
        //mid speed for bot angle within 12 - 6 degrees
        else if ((Math.abs(currentPitch) > RobotMap.DrivetrainConstants.UPPER_LOW_RANGE_ANGLE) && (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.UPPER_MID_RANGE_ANGLE)) {
            speed = Math.copySign(RobotMap.DrivetrainConstants.MID_LEVEL_DRIVE_SPEED, (-currentPitch));

            if (Math.abs(currentPitch) > RobotMap.DrivetrainConstants.ONE_CYCLE_ANGLE_DEADBAND) {
                int count = 0;
                for (int i = 0; i < RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT; i++) {
                    double deadband = (RobotMap.DrivetrainConstants.ONE_CYCLE_ANGLE_DEADBAND * (i+1));
                    if (Math.abs(currentPitch) < (m_angleDeltas[i] - deadband)) {
                        count++;
                    }
                }
                if (count > (RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT * 0.66)) {
                    speed = Math.copySign(RobotMap.DrivetrainConstants.CRAWL_LEVEL_DRIVE_SPEED, (currentPitch));
                    didthething = "yass";
                }
            }
            arcadeDrive(speed, 0);
            m_levelCounter = 0;
        }
        //high speed for bot angle within 17 - 12 degrees
        else if ((Math.abs(currentPitch) > RobotMap.DrivetrainConstants.UPPER_MID_RANGE_ANGLE) && (Math.abs(currentPitch) <= RobotMap.DrivetrainConstants.MAX_ANGLE)){
            //speed is negated: in Pigeon, actual robot ends are opposite, pitch now reflects that
            speed = Math.copySign(RobotMap.DrivetrainConstants.HIGH_LEVEL_DRIVE_SPEED, (-currentPitch));

            if (Math.abs(currentPitch) > RobotMap.DrivetrainConstants.ONE_CYCLE_ANGLE_DEADBAND) {
                int count = 0;
                for (int i = 0; i < RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT; i++) {
                    double deadband = (RobotMap.DrivetrainConstants.ONE_CYCLE_ANGLE_DEADBAND * (i+1));
                    if (Math.abs(currentPitch) < (m_angleDeltas[i] - deadband)) {
                        count++;
                    }
                }
                if (count > (RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT * 0.66)) {
                    speed = Math.copySign(RobotMap.DrivetrainConstants.CRAWL_LEVEL_DRIVE_SPEED, (currentPitch));
                    didthething = "yass";
                }
            }
            arcadeDrive(speed, 0);
            m_levelCounter = 0;
        }

        for (int i = (RobotMap.DrivetrainConstants.BALANCE_CYCLE_COUNT - 1); i > 0; i--) {
            m_angleDeltas[i] = m_angleDeltas[i-1];            
        }

        m_angleDeltas[0] = Math.abs(currentPitch); 
        
        m_angleDeltas[1] = m_angleDeltas[0];
        m_angleDeltas[0] = Math.abs(currentPitch);        
        //System.out.println("OutputCount [" + m_outputCounter++ + "] Current pitch: [" + currentPitch + "] speed [" + speed + "] didthething [" + didthething + "]");
        return level;
    }

    /**
     * Boolean method to check whether the bot is level at a given time.
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
     * Applies brake mode on Drivetrain motor controllers. 
     */
    public void brakeMode() {
        m_leftLeader.setNeutralMode(NeutralMode.Brake);
        m_rightLeader.setNeutralMode(NeutralMode.Brake);
        m_leftFollower.setNeutralMode(NeutralMode.Brake);
        m_rightFollower.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Applies coast mode on Drivetrain motor controllers. 
     */
    public void coastMode() {
        m_leftLeader.setNeutralMode(NeutralMode.Coast);
        m_rightLeader.setNeutralMode(NeutralMode.Coast);
        m_leftFollower.setNeutralMode(NeutralMode.Coast);
        m_rightFollower.setNeutralMode(NeutralMode.Coast);
    }

     /** 
     * shiftGear is the way we change between high and low gear (Gear.kLowGear and Gear.kHighGear)
     * @param gear the desired gear to be used
     */
    public void shiftGear(Gear gear){
        // Compare the incoming parameter to the current state and determine if it is already set to that gear. 
        if (m_gear == gear){
            return;
        }

        m_gear = gear; 

        // Solenoid causes the gear to shift. kForward(Plunger Out) and KReverse (Plunger In) are both in WPILib. 
        // Together they change the pneumatics.
        if(m_gear == Gear.kLowGear){
            m_solenoid.set(Value.kForward);
        }
        else if (m_gear == Gear.kHighGear){
            m_solenoid.set(Value.kReverse);
        }
    }
    

    /**
     * Gets encoder positions of the drivetrain
     * @return the updated encoder positions
     */
    public DriveEncoderPos getEncoderPositions() {
        double leftPos = m_leftLeader.getSelectedSensorPosition();
        double rightPos = m_rightLeader.getSelectedSensorPosition();
        DriveEncoderPos drivePositions = new DriveEncoderPos(leftPos, rightPos);
        return drivePositions;
    }

    /**
     * Zeros out the encoder positions and IMU
     */
    public void zeroSensors() {
        m_leftLeader.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.TIMEOUT_MS);
        m_rightLeader.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.TIMEOUT_MS);
		m_pidgey.setYaw(0, RobotMap.TIMEOUT_MS);
		m_pidgey.setAccumZAngle(0, RobotMap.TIMEOUT_MS);
    }
	
	/**
     *  Method used to zero integrated sensors and reset their positions when initializing Motion Magic
     */
	public void zeroDistance(){
        m_leftLeader.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.TIMEOUT_MS);
        m_rightLeader.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.TIMEOUT_MS);
	}
	
    /**
     * Drive straight forward (or backward for negative distance) a set number of inches
     * @param distance a double passed for setting what magnitude of distance the robot must travel in inches
     * @return a boolean designating whether the target has been reached
     */
    public boolean driveStraight(double distance) {
        boolean reachedTarget = false;
        // 6" wheels means 18.85" per rotation
        double rotations = distance / 18.85;
        double target_sensorUnits = (RobotMap.DrivetrainConstants.SENSOR_UNITS_PER_ROTATION * rotations) * RobotMap.DrivetrainConstants.GEAR_RATIO;
        double target_turn = 0.0; // don't turn

        if((m_outputCounter % 100) == 0) {
            System.out.println("driveStraight [" + target_sensorUnits + "] Current Position [" + getEncoderPositions().m_rightLeaderPos + "]");
            System.out.println("output [" + m_rightLeader.getMotorOutputPercent() + "] velocity [" + m_rightLeader.getSelectedSensorVelocity() + "]");
        }

        /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
        m_rightLeader.set(ControlMode.MotionMagic, target_sensorUnits, DemandType.AuxPID, target_turn);
        m_leftLeader.follow(m_rightLeader, FollowerType.AuxOutput1);
        m_rightFollower.follow(m_rightLeader);
        m_leftFollower.follow(m_leftLeader);

        if ((Math.abs(m_rightLeader.getSelectedSensorPosition() - target_sensorUnits) < RobotMap.DRIVE_STRAIGHT_DEADBAND) && m_rightLeader.getSelectedSensorVelocity() < 100) {
            reachedTarget = true;
        }

        return reachedTarget;
    }

    /**
     * Method for turning to a target angle relative to robot starting position
     * @param angle Target angle to turn to - 0 is straight ahead at start, left is negative, right is positive
     * @return a boolean designating whether the target has been reached
     */
    public boolean turnToAngle(double angle) {
        boolean reachedTarget = false;
        // 6" wheels means 18.85" per rotation
        double currentYaw = this.m_pidgey.getYaw();
        double target_turn = angle;
        //double current_RLsensorUnits = getEncoderPositions().m_rightLeaderPos;
        //double current_LLsensorUnits = getEncoderPositions().m_leftLeaderPos;

        //System.out.println("turmToAngle RL[" + current_RLsensorUnits + "] LL[" + current_LLsensorUnits + "] Current Position [" + getEncoderPositions().m_rightLeaderPos + "]");
        //System.out.println("Current Yaw [" + currentYaw + "] Target Turn [" + target_turn + "]");

        if (target_turn < (currentYaw - (RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 10))) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, 0.3);
            m_leftLeader.set(ControlMode.PercentOutput, -0.3);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);
        }
        else if (target_turn < (currentYaw - (RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 5))) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, 0.1);
            m_leftLeader.set(ControlMode.PercentOutput, -0.1);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);
        }
        else if (target_turn < (currentYaw - (RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 3))) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, 0.04);
            m_leftLeader.set(ControlMode.PercentOutput, -0.04);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);
        }
        else if (target_turn < (currentYaw - (RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND))) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, 0.03);
            m_leftLeader.set(ControlMode.PercentOutput, -0.03);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);
        }        
        else if (target_turn > (currentYaw + (RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 10))) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, -0.3);
            m_leftLeader.set(ControlMode.PercentOutput, 0.3);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);    
        }
        else if (target_turn > (currentYaw + RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 5)) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, -0.);
            m_leftLeader.set(ControlMode.PercentOutput, 0.1);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);    
        }  
        else if (target_turn > (currentYaw + RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 3)) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, -0.04);
            m_leftLeader.set(ControlMode.PercentOutput, 0.04);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);    
        }
        else if (target_turn > (currentYaw + RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND)) {
            /* Configured for MotionMagic on Quad Encoders' Sum and Auxiliary PID on Pigeon */
            m_rightLeader.set(ControlMode.PercentOutput, -0.03);
            m_leftLeader.set(ControlMode.PercentOutput, 0.03);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader);    
        }
        else {
            m_rightLeader.set(ControlMode.PercentOutput, 0.0);
            m_leftLeader.set(ControlMode.PercentOutput, 0.0);
            m_rightFollower.follow(m_rightLeader);
            m_leftFollower.follow(m_leftLeader); 
        }

        if ((Math.abs(this.m_pidgey.getYaw() - angle) < (RobotMap.DrivetrainConstants.DRIVE_ANGLE_DEADBAND * 2)) && m_rightLeader.getSelectedSensorVelocity() < 100) {
            System.out.println("TurnToAngle completed!!!!!!!!");
            reachedTarget = true;
        }

        return reachedTarget;
    }

    /**
     * Sets up the PID configuration for drive straight (or turn)
     */
    public void configPID() {

        /* Configure the left Talon's selected sensor as integrated sensor */
		m_leftConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor; //Local Feedback Source

		/* Configure the Remote (Left) Talon's selected sensor as a remote sensor for the right Talon */
		m_rightConfig.remoteFilter0.remoteSensorDeviceID = m_leftLeader.getDeviceID(); //Device ID of Remote Source
		m_rightConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.TalonFX_SelectedSensor; //Remote Source Type
		
		/* Now that the Left sensor can be used by the master Talon,
		 * set up the Left (Aux) and Right (Master) distance into a single
		 * Robot distance as the Master's Selected Sensor 0. */
        //TalonFXInvertType _leftInvert = m_leftLeader.getInverted() ? TalonFXInvertType.Clockwise : TalonFXInvertType.CounterClockwise;
        TalonFXInvertType _rightInvert = m_rightLeader.getInverted() ? TalonFXInvertType.Clockwise : TalonFXInvertType.CounterClockwise; 
		setRobotDistanceConfigs(_rightInvert, m_rightConfig);

		/* FPID for Distance */
		m_rightConfig.slot0.kF = RobotMap.DrivetrainConstants.DISTANCE_GAINS.kF;
		m_rightConfig.slot0.kP = RobotMap.DrivetrainConstants.DISTANCE_GAINS.kP;
		m_rightConfig.slot0.kI = RobotMap.DrivetrainConstants.DISTANCE_GAINS.kI;
		m_rightConfig.slot0.kD = RobotMap.DrivetrainConstants.DISTANCE_GAINS.kD;
		m_rightConfig.slot0.integralZone = RobotMap.DrivetrainConstants.DISTANCE_GAINS.kIzone;
		m_rightConfig.slot0.closedLoopPeakOutput = RobotMap.DrivetrainConstants.DISTANCE_GAINS.kPeakOutput;

        m_rightConfig.motionCurveStrength = 4;

		/** Heading Configs */
		m_rightConfig.remoteFilter1.remoteSensorDeviceID = m_pidgey.getDeviceID();    //Pigeon Device ID
		m_rightConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw; //This is for a Pigeon over CAN
		m_rightConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor1; //Set as the Aux Sensor
		m_rightConfig.auxiliaryPID.selectedFeedbackCoefficient = 3600.0 / RobotMap.DrivetrainConstants.PIDGEON_UNITS_PER_ROTATION; //Convert Yaw to tenths of a degree

		/* false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1
		 *   This is typical when the master is the right Talon FX and using Pigeon
		 * 
		 * true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
		 *   This is typical when the master is the left Talon FX and using Pigeon
		 */
		m_rightConfig.auxPIDPolarity = true;

		/* FPID for Heading */
		m_rightConfig.slot1.kF = RobotMap.DrivetrainConstants.TURNING_GAINS.kF;
		m_rightConfig.slot1.kP = RobotMap.DrivetrainConstants.TURNING_GAINS.kP;
		m_rightConfig.slot1.kI = RobotMap.DrivetrainConstants.TURNING_GAINS.kI;
		m_rightConfig.slot1.kD = RobotMap.DrivetrainConstants.TURNING_GAINS.kD;
		m_rightConfig.slot1.integralZone = RobotMap.DrivetrainConstants.TURNING_GAINS.kIzone;
		m_rightConfig.slot1.closedLoopPeakOutput = RobotMap.DrivetrainConstants.TURNING_GAINS.kPeakOutput;


		/* Config the neutral deadband. */
		m_leftConfig.neutralDeadband = RobotMap.DrivetrainConstants.NEUTRAL_DEADBAND;
		m_rightConfig.neutralDeadband = RobotMap.DrivetrainConstants.NEUTRAL_DEADBAND;


		/**
		 * 1ms per loop.  PID loop can be slowed down if need be.
		 * For example,
		 * - if sensor updates are too slow
		 * - sensor deltas are very small per update, so derivative error never gets large enough to be useful.
		 * - sensor movement is very slow causing the derivative error to be near zero.
		 */
		int closedLoopTimeMs = 1;
		m_rightLeader.configClosedLoopPeriod(0, closedLoopTimeMs, RobotMap.TIMEOUT_MS);
		m_rightLeader.configClosedLoopPeriod(1, closedLoopTimeMs, RobotMap.TIMEOUT_MS);

		/* Motion Magic Configs */
		m_rightConfig.motionAcceleration = 9000; //(distance units per 100 ms) per second
		m_rightConfig.motionCruiseVelocity = 12000; //distance units per 100 ms

		/* APPLY the config settings */
		m_leftLeader.configAllSettings(m_leftConfig);
		m_rightLeader.configAllSettings(m_rightConfig);

		/* Set status frame periods to ensure we don't have stale data */
		/* These aren't configs (they're not persistant) so we can set these after the configs.  */
		m_rightLeader.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, RobotMap.TIMEOUT_MS);
		m_rightLeader.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, RobotMap.TIMEOUT_MS);
		m_rightLeader.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, RobotMap.TIMEOUT_MS);
		m_rightLeader.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10, RobotMap.TIMEOUT_MS);
		m_leftLeader.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.TIMEOUT_MS);
		m_pidgey.setStatusFramePeriod(PigeonIMU_StatusFrame.CondStatus_9_SixDeg_YPR , 5, RobotMap.TIMEOUT_MS);

        /* Determine which slot affects which PID */
        m_rightLeader.selectProfileSlot(0, RobotMap.DrivetrainConstants.PID_PRIMARY);
        m_rightLeader.selectProfileSlot(1, RobotMap.DrivetrainConstants.PID_TURN);

    }

    /** 
	 * Determines if SensorSum or SensorDiff should be used 
	 * for combining left/right sensors into Robot Distance.  
	 * 
	 * Assumes Aux Position is set as Remote Sensor 0.  
	 * 
	 * configAllSettings must still be called on the master config
	 * after this function modifies the config values. 
	 * 
	 * @param masterInvertType Invert of the Master Talon
	 * @param masterConfig Configuration object to fill
	 */
    void setRobotDistanceConfigs(TalonFXInvertType masterInvertType, TalonFXConfiguration masterConfig){
		/**
		 * Determine if we need a Sum or Difference.
		 * 
		 * The auxiliary Talon FX will always be positive
		 * in the forward direction because it's a selected sensor
		 * over the CAN bus.
		 * 
		 * The master's native integrated sensor may not always be positive when forward because
		 * sensor phase is only applied to *Selected Sensors*, not native
		 * sensor sources.  And we need the native to be combined with the 
		 * aux (other side's) distance into a single robot distance.
		 */

		/* THIS FUNCTION should not need to be modified. 
		   This setup will work regardless of whether the master
		   is on the Right or Left side since it only deals with
		   distance magnitude.  */

		/* Check if we're inverted */
		if (masterInvertType == TalonFXInvertType.Clockwise){
			/* 
				If master is inverted, that means the integrated sensor
				will be negative in the forward direction.

				If master is inverted, the final sum/diff result will also be inverted.
				This is how Talon FX corrects the sensor phase when inverting 
				the motor direction.  This inversion applies to the *Selected Sensor*,
				not the native value.

				Will a sensor sum or difference give us a positive total magnitude?

				Remember the Master is one side of your drivetrain distance and 
				Auxiliary is the other side's distance.

					Phase | Term 0   |   Term 1  | Result
				Sum:  -1 *((-)Master + (+)Aux   )| NOT OK, will cancel each other out
				Diff: -1 *((-)Master - (+)Aux   )| OK - This is what we want, magnitude will be correct and positive.
				Diff: -1 *((+)Aux    - (-)Master)| NOT OK, magnitude will be correct but negative
			*/

			masterConfig.diff0Term = FeedbackDevice.IntegratedSensor; //Local Integrated Sensor
			masterConfig.diff1Term = FeedbackDevice.RemoteSensor0;   //Aux Selected Sensor
			masterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference; //Diff0 - Diff1
		} else {
			/* Master is not inverted, both sides are positive so we can sum them. */
			masterConfig.sum0Term = FeedbackDevice.RemoteSensor0;    //Aux Selected Sensor
			masterConfig.sum1Term = FeedbackDevice.IntegratedSensor; //Local IntegratedSensor
			masterConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorSum; //Sum0 + Sum1
		}

		/* Since the Distance is the sum of the two sides, divide by 2 so the total isn't double
		   the real-world value */
		masterConfig.primaryPID.selectedFeedbackCoefficient = 0.5;
	}    
}
