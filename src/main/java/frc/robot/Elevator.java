package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.*;

/**
 * Elevator class encapsulating motion magic for our elevator
 */
public class Elevator {
    
    private WPI_TalonFX m_elevator;

    /**
     * Constructor for the elevator class. Sets CAN ID to the Talon from RobotMap.
     */
    public Elevator() {

        m_elevator = new WPI_TalonFX(RobotMap.ElevatorConstants.ELEVATOR_CAN_ID);

    }

    /**
     * Resets the encoder postition to zero.
     */
    private void zeroEncoders() {

        m_elevator.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.TIMEOUT_MS);

    }
    
    /**
     * Initialization method for the Elevator class.
     */
    public void init() {

        // Sets the inversion status of the elevator to false.
        m_elevator.setInverted(false);

        m_elevator.setNeutralMode(NeutralMode.Brake);

        this.zeroEncoders();

    }

    /**
     * Configure the zero slot PID for motion magic to control the motors with accurate values.
     */
    public void configPID() {
		// Stops motor controllers
		m_elevator.set(ControlMode.PercentOutput, 0);

		// Set neutral mode
		m_elevator.setNeutralMode(NeutralMode.Brake);

		// Configures sensor as quadrature encoder
		m_elevator.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.ElevatorConstants.PID_PRIMARY, RobotMap.TIMEOUT_MS);

		// Config sensor and motor direction
		m_elevator.setInverted(true);
		m_elevator.setSensorPhase(true);

		// Set status frame period for data collection where 5 is period length in ms
		m_elevator.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.TIMEOUT_MS);

		// Config neutral deadband
		m_elevator.configNeutralDeadband(RobotMap.ElevatorConstants.NEUTRAL_DEADBAND, RobotMap.TIMEOUT_MS);

		// Config peak output
		m_elevator.configPeakOutputForward(+RobotMap.ElevatorConstants.PID_PEAK_OUTPUT, RobotMap.TIMEOUT_MS);
		m_elevator.configPeakOutputReverse(-RobotMap.ElevatorConstants.PID_PEAK_OUTPUT, RobotMap.TIMEOUT_MS);

		// Motion Magic Config
		m_elevator.configMotionAcceleration(RobotMap.ElevatorConstants.ELEVATOR_ACCELERATION, RobotMap.TIMEOUT_MS);
		m_elevator.configMotionCruiseVelocity(RobotMap.ElevatorConstants.ELEVATOR_CRUISE_VELOCITY, RobotMap.TIMEOUT_MS);

		// PID Config
		m_elevator.config_kP(0, RobotMap.ElevatorConstants.ELEVATOR_GAINS.kP, RobotMap.TIMEOUT_MS);
		m_elevator.config_kI(0, RobotMap.ElevatorConstants.ELEVATOR_GAINS.kI, RobotMap.TIMEOUT_MS);
		m_elevator.config_kD(0, RobotMap.ElevatorConstants.ELEVATOR_GAINS.kD, RobotMap.TIMEOUT_MS);
		m_elevator.config_kF(0, RobotMap.ElevatorConstants.ELEVATOR_GAINS.kF, RobotMap.TIMEOUT_MS);
		m_elevator.config_IntegralZone(0, RobotMap.ElevatorConstants.ELEVATOR_GAINS.kIzone, RobotMap.TIMEOUT_MS);
		m_elevator.configClosedLoopPeakOutput(0, RobotMap.ElevatorConstants.ELEVATOR_GAINS.kPeakOutput, RobotMap.TIMEOUT_MS);
		m_elevator.configAllowableClosedloopError(0, 0, RobotMap.TIMEOUT_MS);

		// PID closed loop config
		m_elevator.configClosedLoopPeriod(0, 10, RobotMap.TIMEOUT_MS);

		// Sets profile slot for PID
		m_elevator.selectProfileSlot(0, RobotMap.ElevatorConstants.PID_PRIMARY);
	}

    /**
     * If the target distance is less than 0 it stops elevator.
     * If the distance is greater than zero it calculates the target and tells elevator to go there.
     * @param deltaInches
     */
    public void drivePID(double deltaInches) {

        double position = m_elevator.getSelectedSensorPosition();


        if(deltaInches < 0) {

            m_elevator.set(ControlMode.PercentOutput, 0.0);
            System.out.println(" Stop");

        }

        else{

            double target = (deltaInches) * (RobotMap.ElevatorConstants.TICKS_PER_REVOLUTION / RobotMap.ElevatorConstants.DRUM_CIRCUMFERENCE);
            m_elevator.set(ControlMode.MotionMagic, target);
            System.out.println("Go to [" + target + "] Position: [" + position + "]");

        }

    }
}
