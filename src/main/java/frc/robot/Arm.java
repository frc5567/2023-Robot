package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.*;

/**
 * Arm class encapsulating motion magic for our arm on top of the elevator.
 */
public class Arm {
 
    private WPI_TalonFX m_arm;

    /**
     * Constructor for arm class. Sets CAN ID for arm motor controller
     */
    public Arm() {
        m_arm = new WPI_TalonFX(RobotMap.ArmConstants.ARM_CAN_ID);
    }

    /**
     * Method used to reset the encoder on the arm to 0.
     */
    private void zeroEncoders() {
        m_arm.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.TIMEOUT_MS);
    }

    /**
     * Intitializtion method for the Arm class
     */
    public void init() {
        //sets the inversion status to false
        m_arm.setInverted(false);
        
        m_arm.setNeutralMode(NeutralMode.Brake);

        this.zeroEncoders();
    }

    /**
     * Configure the zero slot PID for motion magic to control the motors with accurate values.
     */
    public void configPID() {
		// Stops motor controllers
		m_arm.set(ControlMode.PercentOutput, 0);

		// Configures sensor as quadrature encoder
		m_arm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.ArmConstants.PID_PRIMARY, RobotMap.TIMEOUT_MS);

		// Config sensor and motor direction
        //TODO: when testing check to see which way the motor is positive/if we need to invert it
		m_arm.setInverted(true);
		m_arm.setSensorPhase(true);

		// Set status frame period for data collection where 5 is period length in ms
		m_arm.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.TIMEOUT_MS);

		// Config neutral deadband
		m_arm.configNeutralDeadband(RobotMap.ArmConstants.NEUTRAL_DEADBAND, RobotMap.TIMEOUT_MS);

		// Config peak output
		m_arm.configPeakOutputForward(+RobotMap.ArmConstants.PID_PEAK_OUTPUT, RobotMap.TIMEOUT_MS);
		m_arm.configPeakOutputReverse(-RobotMap.ArmConstants.PID_PEAK_OUTPUT, RobotMap.TIMEOUT_MS);

		// Motion Magic Config
		m_arm.configMotionAcceleration(RobotMap.ArmConstants.ARM_ACCELERATION, RobotMap.TIMEOUT_MS);
		m_arm.configMotionCruiseVelocity(RobotMap.ArmConstants.ARM_CRUISE_VELOCITY, RobotMap.TIMEOUT_MS);

		// PID Config
		m_arm.config_kP(0, RobotMap.ArmConstants.ARM_GAINS.kP, RobotMap.TIMEOUT_MS);
		m_arm.config_kI(0, RobotMap.ArmConstants.ARM_GAINS.kI, RobotMap.TIMEOUT_MS);
		m_arm.config_kD(0, RobotMap.ArmConstants.ARM_GAINS.kD, RobotMap.TIMEOUT_MS);
		m_arm.config_kF(0, RobotMap.ArmConstants.ARM_GAINS.kF, RobotMap.TIMEOUT_MS);
		m_arm.config_IntegralZone(0, RobotMap.ArmConstants.ARM_GAINS.kIzone, RobotMap.TIMEOUT_MS);
		m_arm.configClosedLoopPeakOutput(0, RobotMap.ArmConstants.ARM_GAINS.kPeakOutput, RobotMap.TIMEOUT_MS);
		m_arm.configAllowableClosedloopError(0, 0, RobotMap.TIMEOUT_MS);

		// PID closed loop config
		m_arm.configClosedLoopPeriod(0, 10, RobotMap.TIMEOUT_MS);

		// Sets profile slot for PID
		m_arm.selectProfileSlot(0, RobotMap.ArmConstants.PID_PRIMARY);
	}

    /**
     * If the arm is at the target, stops the arm. Otherwise, figures out how far the arm needs to move and uses motion magic to get there.
     * @param deltaInches
     */
    public void armPID(double deltaInches) {
        double armPosition = m_arm.getSelectedSensorPosition();

        if(deltaInches < 0) {

            m_arm.set(ControlMode.PercentOutput, 0.0);
            System.out.println(" Stop (arm)");

        }

        else{

            double target = (deltaInches) * (RobotMap.ArmConstants.TICKS_PER_REVOLUTION / RobotMap.ArmConstants.DRUM_CIRCUMFERENCE);
            m_arm.set(ControlMode.MotionMagic, target);
            System.out.println("Go to [" + target + "] Arm Position: [" + armPosition + "]");

        }

    }
    
}
