package frc.robot;

public class RobotMap {

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
        public static final double MAX_ANGLE = 15.0;
        /**
         * Upper end angle for robot speed when charge station between 8 and 4 deg.
         */
        public static final double UPPER_MID_RANGE_ANGLE = 8.0;
        /**
         * Upper end angle for robot speed when charge station between 4 and 2 deg.
         */
        public static final double UPPER_LOW_RANGE_ANGLE = 4.0;

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
    
}
