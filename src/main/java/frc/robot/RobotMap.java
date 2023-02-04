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
        public static final double MAX_ANGLE = 17.0;
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
        public static final double CRAWL_LEVEL_DRIVE_SPEED = 0.3;
        /**
         * Auto leveling mid drive speed
         */
        public static final double MID_LEVEL_DRIVE_SPEED = 0.4;
        /**
         * Auto leveling high drive speed
         */
        public static final double HIGH_LEVEL_DRIVE_SPEED = 0.5;
        /**
        * Low Gear Solenoid port on the PCM.
         *
         * TODO: Find Ports that low gear and high gear connect to.
         */
        public static final int DOUBLESOLENOID_LOW_GEAR_PORT = 6;

        /** 
         * High gear Solenoid port on PCM.
         */
         public static final int DOUBLESOLENOID_HIGH_GEAR_PORT = 7;

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
    }

    /**
     * All robot shuffleboard constants
     */
    public static class RobotShuffleboardConstants {

        /**
         * Default value of Auton path, no object auton
         */
        public static final double DEFAULT_AUTON_PATH = 0;
    }

    /**
     * Encapsulates constants for the elevator class.
     */
    public static class ElevatorConstants {

        public static final int ELEVATOR_CAN_ID = 17;

        public static final int PID_PRIMARY = 0;
        public static final double NEUTRAL_DEADBAND = 0.001;
        public static final double PID_PEAK_OUTPUT = 1.0;
        public static final int ELEVATOR_ACCELERATION = 2500;
        public static final int ELEVATOR_CRUISE_VELOCITY = 3500;

        public static final Gains ELEVATOR_GAINS = new Gains(0.4, 0.0, 0.0, 0.0, 100, 1.0);

        public static final double DRUM_CIRCUMFERENCE = 8.1875;
        public static final int TICKS_PER_REVOLUTION = 2048;

        public static final double START_POSITION = 0.0;
        public static final double LOW_POSITION = 250.0;
        public static final double MID_POSITION = 500.0;
        public static final double HIGH_POSITION = 750.0;
    
    }

    /**
     * Encapsulates constants for the elevator class.
     *TODO: double check all constants and TEST
     */
    public static class ArmConstants {

        public static final int ARM_CAN_ID = 15;

        public static final int PID_PRIMARY = 0;
        public static final double NEUTRAL_DEADBAND = 0.001;
        public static final double PID_PEAK_OUTPUT = 1.0;
        public static final int ARM_ACCELERATION = 2000;
        public static final int ARM_CRUISE_VELOCITY = 2000;

        public static final Gains ARM_GAINS = new Gains(0.4, 0.0, 0.0, 0.0, 100, 1.0);

        public static final double DRUM_CIRCUMFERENCE = 8.1875;
        public static final int TICKS_PER_REVOLUTION = 2048;

        public static final double START_POSITION = 0.0;
        public static final double LOW_POSITION = 10.0;
        public static final double MID_POSITION = 20.0;
        public static final double HIGH_POSITION = 30.0;
        
    }
    
}
