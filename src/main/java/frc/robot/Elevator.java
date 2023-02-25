package frc.robot;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.*;

/**
 * Elevator class encapsulating motion magic for our elevator
 */
public class Elevator {
    
    private WPI_TalonSRX m_elevator;

    /**
     * Constructor for the elevator class. Sets CAN ID to the Talon from RobotMap.
     */
    public Elevator() {

        m_elevator = new WPI_TalonSRX(RobotMap.ElevatorConstants.ELEVATOR_CAN_ID);

    }

    /**
     * Resets the encoder postition to zero.
     */
    private void zeroEncoders() {

        m_elevator.setSelectedSensorPosition(0.0, RobotMap.ElevatorConstants.PID_PRIMARY, RobotMap.TIMEOUT_MS);

    }
    
    /**
     * Initialization method for the Elevator class.
     * sets motor controller inversion to false, sets the neutral mode to brake, and zeros encoder. 
     */
    public void init() {
        // Sets the inversion status of the elevator to false.
        m_elevator.setInverted(true);
        m_elevator.setSensorPhase(true);
        m_elevator.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Configure the zero slot PID for motion magic to control the motors with accurate values.
     */
    public void configPID() {
		// Stops motor controllers
		m_elevator.set(ControlMode.PercentOutput, 0);

		// Configures sensor as quadrature encoder
		m_elevator.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.ElevatorConstants.PID_PRIMARY, RobotMap.TIMEOUT_MS);

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

        this.zeroEncoders();
	}

    /**
     * If the target distance is less than 0 it stops elevator.
     * If the distance is greater than zero it calculates the target and tells elevator to go there.
     * @param target
     */
    public void drivePID(double target) {
        double position = m_elevator.getSelectedSensorPosition();

        if(target <= -10000000) {
            m_elevator.set(ControlMode.PercentOutput, 0.0);
            //System.out.println(" Stop");
        }

        else{
            m_elevator.set(ControlMode.MotionMagic, target);
            System.out.println("Go to [" + target + "] Position: [" + position + "]");
        }
    }

    public void drive(double speed) {
        m_elevator.set(speed);
        double enc = m_elevator.getSelectedSensorPosition(RobotMap.ElevatorConstants.PID_PRIMARY);
        System.out.println("[" + speed + "][" + enc + "]");
    }

    public void coastMode(){
        m_elevator.setNeutralMode(NeutralMode.Coast);
    }
}
