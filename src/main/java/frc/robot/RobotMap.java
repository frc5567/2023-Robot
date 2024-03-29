package frc.robot;

public class RobotMap {

    /**
     * The timeout in milliseconds of the CTRE methods.
     */
    public static final int TIMEOUT_MS = 30;

    /**
     * CAN ID for the pigeon2.
     */
    public static final int PIGEON_CAN_ID = 21;

    /**
     * PCM (pneumatic control module)
     */
    public static final int PCM_CAN_ID = 1;
    
    /**
     * Constant for setting variables to 0 if there is not input value.
     */
    public static final double NO_INPUT = Double.NaN;

    /**
     * encoder deadband used in robot.java for transitions
     */
    public static final double ENC_DEADBAND = 10000;

    /**
     * encoder deadband used in drive straight
     */
    public static final double DRIVE_STRAIGHT_DEADBAND = 5000;

    /**
     * All drivetrain constants
     */
    public static class DrivetrainConstants {

        /**
         * CAN ID of the left leader motor controller
         */
        public static final int LEFT_LEADER_CAN_ID = 10;

        /**
         * CAN ID of the right leader motor controller
         */
        public static final int RIGHT_LEADER_CAN_ID = 11;

        /**
         * CAN ID of the left follower motor controller
         */
        public static final int LEFT_FOLLOWER_CAN_ID = 12;

        /**
         * CAN ID of the left follower motor controller
         */
        public static final int RIGHT_FOLLOWER_CAN_ID = 13;

        /**
         * Maximum angle at which the charge station is level
         */
        public static final double MAX_LEVEL_ANGLE = 2.0;

        /**
         * Max angle of charge station
         */
        public static final double MAX_ANGLE = 45.0;

        /**
         * Upper end angle for robot speed when charge station between 12 and 6 deg.
         */
        public static final double UPPER_MID_RANGE_ANGLE = 12.0;

        /**
         * Upper end angle for robot speed when charge station between 6 and 2 deg.
         */
        public static final double UPPER_LOW_RANGE_ANGLE = 6.0;

        /**
         * Auto leveling crawl drive speed
         */
        public static final double CRAWL_LEVEL_DRIVE_SPEED = 0.065;

        /**
         * Auto leveling mid drive speed
         */
        public static final double MID_LEVEL_DRIVE_SPEED = 0.075;

        /**
         * Auto leveling high drive speed
         */
        public static final double HIGH_LEVEL_DRIVE_SPEED = 0.09;

        /**
         * Low Gear Solenoid port on the PCM.
         */
        public static final int DOUBLE_SOLENOID_LOW_GEAR_PORT = 5;

        /** 
         * High gear Solenoid port on PCM.
         */
        public static final int DOUBLE_SOLENOID_HIGH_GEAR_PORT = 4;

        /**
         * Drive Straight PID constants
         */
        public final static Gains DISTANCE_GAINS = new Gains( 0.1, 0.0,  0.0, 0.0,            100,  0.70 );
        
        /**
         * Turn to Angle PID constants
         */
        public final static Gains TURNING_GAINS = new Gains( 0.5, 0.0,  4.0, 0.0,            200,  0.5 );

        public static final double NEUTRAL_DEADBAND = 0.001;

        /**
         * This is a property of the Pigeon IMU, and should not be changed.
         */
        public final static int PIDGEON_UNITS_PER_ROTATION = 8192;

        public final static int SENSOR_UNITS_PER_ROTATION = 2048;

        public final static int PID_PRIMARY = 0;
        public final static int PID_TURN = 1;

        public final static int GEAR_RATIO = 15;

        /**
         * DEADBAND for the turnToAngle.
         */
        public static final double DRIVE_ANGLE_DEADBAND = 0.8;

        /**
         * This is a counter end constant for measuring number of cycles (20 ms) in order for stable autolevel completion
         */
        public static final int AUTOLEVEL_COUNTER = 50;

        //TODO: tune and test these as well
        public static final double ONE_CYCLE_ANGLE_DEADBAND = 0.1;

        public static final double TWO_CYCLE_ANGLE_DEADBAND = 0.2;

        public static final int BALANCE_CYCLE_COUNT = 15;
	
    }

    /**
     * All pilot controller constants
     */
    public static class PilotControllerConstants {

        /**
         * USB port number of the pilot x-box controller
         */
        public static final int XBOX_CONTROLLER_USB_PORT = 0;

        /**
         * Speed index for the driver input return value array
         */
        public static final int DRIVER_INPUT_SPEED = 0;

        /**
         * Turn index for the driver input return value array
         */
        public static final int DRIVER_INPUT_TURN = 1;

        /**
         * Absolute value of the deadband range for stick input
         */
        public static final double STICK_DEADBAND = 0.09;

        /**
         * adjust output of turns to tone down the final output.
         */
        public static final double TURN_SCALER = 0.7;
    }

    /**
     * All auton constants for class methods and pathing
     */
    public static class AutonConstants {
        /**
         * Default value of Auton path, no object auton
         */
        public static final String DEFAULT_AUTON_PATH = "Auto Level";

        public static final String SHORT_COMMUNITY = "Short Community";

        public static final String LONG_COMMUNITY = "Long Community";

        public static final String MID_CONE_SHORT_COMMUNITY = "Mid Cone Short Community";

        public static final String MID_CUBE_SHORT_COMMUNITY = "Mid Cube Short Community";

        public static final String MID_CONE_LONG_COMMUNITY = "Mid Cone Long Community";

        public static final String MID_CUBE_LONG_COMMUNITY = "Mid Cube Long Community";

        public static final String HIGH_CONE_SHORT_COMMUNITY = "High Cone Short Community";

        public static final String HIGH_CUBE_SHORT_COMMUNITY = "High Cube Short Community";

        public static final String HIGH_CONE_LONG_COMMUNITY = "High Cone Long Community";

        public static final String HIGH_CUBE_LONG_COMMUNITY = "High Cube Long Community";

        public static final String HIGH_CUBE_COMMUNITY_BALANCE = "High Cube Community Balance";

        public static final String HIGH_CUBE_BALANCE = "High Cube Balance";

        public static final String HIGH_CONE_BALANCE = "High Cone Balance";

        //TODO: these have to be tested, false distances
        public static final double SHORT_COMMUNITY_DIST = 85;

        public static final double LONG_COMMUNITY_DIST = 145;

        //TODO: test and tune these for true values
        public static final double FIRST_CHARGING_DIST = 96.75;

        public static final double SECOND_CHARGING_DIST = 155;

        public static final double THIRD_CHARGING_DIST = 90.0;

        public static final double NO_EXIT_DIST = 78.0;

        public static final double TARGET_ANGLE = 180;

    }

    /**
     * Encapsulates constants for the elevator class.
     */
    public static class ElevatorConstants {

        public static final int ELEVATOR_CAN_ID = 17;

        public static final int PID_PRIMARY = 0;
        public static final double NEUTRAL_DEADBAND = 0.001;
        public static final double PID_PEAK_OUTPUT = 1.0;
        public static final int ELEVATOR_ACCELERATION = 50000;
        public static final int ELEVATOR_CRUISE_VELOCITY = 50000;

        public static final Gains ELEVATOR_GAINS = new Gains(0.4, 0.0, 0.0, 0.0, 100, 1.0);

        public static final double DRUM_CIRCUMFERENCE = 8.1875;
        public static final int TICKS_PER_REVOLUTION = 2048;

        public static final double ELEVATOR_LOW_POS = -252000;
        public static final double ELEVATOR_MID_POS = 0;
        public static final double ELEVATOR_HIGH_POS = 561250;
        public static final double ELEVATOR_SHELF_POS = -695000;
    }

    /**
     * Encapsulates constants for the arm class.
     */
    public static class ArmConstants {

        public static final int ARM_CAN_ID = 15;

        public static final int PID_PRIMARY = 0;
        public static final double NEUTRAL_DEADBAND = 0.001;
        public static final double PID_PEAK_OUTPUT = 1.0;

        public static final int ARM_ACCELERATION = 35000;
        public static final int ARM_CRUISE_VELOCITY = 45000;

        public static final Gains ARM_GAINS = new Gains(0.4, 0.0, 0.0, 0.0, 100, 1.0);

        public static final double DRUM_CIRCUMFERENCE = 8.1875;
        public static final int TICKS_PER_REVOLUTION = 2048;

        public static final double ARM_START_POS = 0.0;
        public static final double ARM_APPROACH_POS = 405000;
        public static final double ARM_HIGH_POS = 444700;
        public static final double ARM_MID_POS = 487000;
        public static final double FLOOR_PLACE_POS = 692800;
        public static final double ARM_SHELF_POS = 707000;
        public static final double ARM_FLOOR_POS = 768000;
    }

    /**
     * Encapsulates constants for the copilot controller class.
     */
    public static class CopilotConstants {
    }

    /**
     * Encapsulates the constants for the claw class
     */
    public static class ClawConstants {
        public static final int DOUBLESOLENOID_OPEN_PORT = 2;
        public static final int DOUBLESOLENOID_CLOSE_PORT = 3;
    }

    /**
     * Encapsulates the constants for the shoulder class
     */
    public static class ShoulderConstants {
        public static final int DOUBLESOLENOID_UP_PORT = 0;
        public static final int DOUBLESOLENOID_DOWN_PORT = 1;
    }
    
}
