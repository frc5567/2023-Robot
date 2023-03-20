package frc.robot;

import frc.robot.Shoulder.ShoulderState;

/**
 * Enum to encapsulate combinations of positions for multiple mechanisms on the Robot
 */
public enum RobotState {
    /**
     * Default state for travelling
     */
    kTravel ("Travel", RobotMap.ArmConstants.ARM_START_POS, RobotMap.ElevatorConstants.ELEVATOR_MID_POS, ShoulderState.kUp),
    
    /**
     * State for doing floor pickup of gamepiece
     */
    kFloorPickup ("Floor Pickup", RobotMap.ArmConstants.ARM_FLOOR_POS, RobotMap.ElevatorConstants.ELEVATOR_LOW_POS, ShoulderState.kDown),

    /**
     * State for doing shelf pickup of gamepiece
     */
    kShelfPickup ("Shelf Pickup", RobotMap.ArmConstants.ARM_SHELF_POS, RobotMap.ElevatorConstants.ELEVATOR_SHELF_POS, ShoulderState.kDown),

    /**
     * State for placing gamepiece at floor level
     */
    kFloorPiece ("Floor Game Piece", RobotMap.ArmConstants.FLOOR_PLACE_POS, RobotMap.ElevatorConstants.ELEVATOR_MID_POS, ShoulderState.kDown),

    /**
     * State for placing gamepiece at Mid level
     */
    kMidPiece ("Mid Game Piece", RobotMap.ArmConstants.ARM_MID_POS, RobotMap.ElevatorConstants.ELEVATOR_LOW_POS, ShoulderState.kDown),

    /**
     * State for placing cone at High level
     */
    kHighCone ("High Cone", RobotMap.ArmConstants.ARM_HIGH_POS, RobotMap.ElevatorConstants.ELEVATOR_HIGH_POS, ShoulderState.kDown),

    /**
     * State for placing cube at High level
     */
    kHighCube ("HighCube", RobotMap.ArmConstants.ARM_MID_POS, RobotMap.ElevatorConstants.ELEVATOR_HIGH_POS, ShoulderState.kDown),

    /**
     * State for approaching cone at Mid level
     */
    kApproachMid ("Approach Mid", RobotMap.ArmConstants.ARM_APPROACH_POS, RobotMap.ElevatorConstants.ELEVATOR_MID_POS, ShoulderState.kDown),

    /**
     * State for approaching cone at High level
     */
    kApproachHigh ("Approach High", RobotMap.ArmConstants.ARM_APPROACH_POS, RobotMap.ElevatorConstants.ELEVATOR_HIGH_POS, ShoulderState.kDown),

    /**
     * Unknown State -- should generally not be used for actual positioning. No driver input case.
     */
    kUnknown ("Unknown", 0.0, 0.0, ShoulderState.kUnknown);

    private String CurrentState;
    private Double armTicks;
    private Double elevatorTicks;
    private ShoulderState shoulderState;

    /**
    * This is the constructor for the enum RobotState, setting the intial state. 
    */
    RobotState (String State, Double armTicks, Double elevatorTicks, ShoulderState shoulderState){
        this.CurrentState = State;
        this.armTicks = armTicks;
        this.elevatorTicks = elevatorTicks;
        this.shoulderState = shoulderState;
    }

    /**
     * Returns CurrentState as a string.
     * @return CurrentState
     */
    public String toString(){
        return this.CurrentState;
    }

    /**
     * Returns the Elevator position in Ticks
     * @return elevatorTicks
     */
    public Double getElevatorTarget() {
        return this.elevatorTicks;
    }

    /**
     * Returns the Arm position in Ticks
     * @return armTicks
     */
    public Double getArmTarget() {
        return this.armTicks;
    }

    /**
     * Returns the Shoulder state
     * @return shoulderState
     */
    public ShoulderState getShoulderState() {
        return this.shoulderState;
    }
}
