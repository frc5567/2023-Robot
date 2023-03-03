package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

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
     * Sets default configuration on the motor controller, sets motor inversion (false), sets neutral mode (Brake), zeros encoders.
     */
    public void init() {
        m_arm.configFactoryDefault();
        m_arm.setInverted(false);
        m_arm.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Configure the zero slot PID for motion magic to control the motors with accurate values.
     */
    public void configPID() {
		// Stops motor controllers
		m_arm.set(ControlMode.PercentOutput, 0);

		// Configures sensor as quadrature encoder
		m_arm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, RobotMap.ArmConstants.PID_PRIMARY, RobotMap.TIMEOUT_MS);

		// Set status frame period for data collection where 5 is period length in ms
		m_arm.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.TIMEOUT_MS);

		// Config neutral deadband
		m_arm.configNeutralDeadband(RobotMap.ArmConstants.NEUTRAL_DEADBAND, RobotMap.TIMEOUT_MS);

		// Config peak output (forward and reverse)
		m_arm.configPeakOutputForward(+RobotMap.ArmConstants.PID_PEAK_OUTPUT, RobotMap.TIMEOUT_MS);
		m_arm.configPeakOutputReverse(-RobotMap.ArmConstants.PID_PEAK_OUTPUT, RobotMap.TIMEOUT_MS);

		// Motion Magic Config (acceleration and cruise velocity)
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

        //zeros the encoders
        this.zeroEncoders();
	}

    /**
     * Use motion magic to figure out how far the arm needs to move based on the target.
     * @param target
     */
    public void armPID(double target) {
        m_arm.set(ControlMode.MotionMagic, target);
    }

    /**
     * manual drive method for the arm. Takes input from the controller.
     * @param speed
     */
    public void driveArm(double speed) {
        m_arm.set(speed);
    }

    /**
     * sets arm to coast mode (used when disabled)
     */
    public void coastMode() {
		m_arm.setNeutralMode(NeutralMode.Coast);
	}

    /**
     * Gets the current position of the arm mechanism in encoder ticks
     * @return CurrentArmPosition
     */
    public Double getArmPosition() {
        return m_arm.getSelectedSensorPosition();
    }

}
