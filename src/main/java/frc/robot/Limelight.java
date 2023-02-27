package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Limelight class that encapsulates the code for determining location target relative to the bot. 
 */
public class Limelight {

    /**
     * Declares the Pipeline objects for the limelight Class
     */
    public Pipeline m_pipeline;
    public Pipeline m_AptagPipe;
    public Pipeline m_RetroTapePipe; 

    /**
     * Creates an enum to store the pipeline ID's(0 for retro tape and 1 for april tags) and the methond to get its ID.
     */
    public enum Pipeline{
        kRetroTapePipeID(0),
        kApTagPipeID(1);

        int m_pipelineID;
        //Intakes the pipelineID and sets it to the member variable 
        private Pipeline(int pipelineID){
            m_pipelineID = pipelineID;
        }
        /**
         * @return the member varuble for the Pipeline. 
         */
        public int getID(){
            return m_pipelineID;
        }
    }
 
    //declares the network table for the limelight
   NetworkTable m_limelightTable; 

    //Sets the x angle offset so we can calculate the distance from the limelight to the reflective tape.  
   double m_xAngleOffset;

    //Percent of the reflective tape the screen takes up so we can calculate the distance. 
   double m_areaOfScreen;

   
   /**
    * Constructor that gets the values of the x angle offset and the target area.
    */
   public Limelight() {
        // Creates the Network Table that stores the values for the Limelight and allows those values to be easily changed for testing
        m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

        m_xAngleOffset = m_limelightTable.getEntry("tx").getDouble(0.0);
        m_areaOfScreen = m_limelightTable.getEntry("ta").getDouble(0.0);
   }

   /**
    * Sets the default pipeline to the RetroTape pipeline
    */
   public void init() {
    setPipeline(Pipeline.kRetroTapePipeID);
   }

   /**
    * Method that gets the values of the x angle offset and the target area and sets them to the member variables for each.
    */
   public void periodic() {
    // Updates the values for tx and ta in the limelight table
    m_xAngleOffset = m_limelightTable.getEntry("tx").getDouble(0.0);
    m_areaOfScreen = m_limelightTable.getEntry("ta").getDouble(0.0);

    //TODO: After shuffleboard is finished, uncomment this code.
    /** 
     * Puts the x angle offset on the shuffleboard.
     * SmartDashboard.putNumber("LimelightX Offset", m_xAngleOffset);
     * Puts the percentage of screen on the shuffleboard.
     * SmartDashboard.putNumber("LimelightArea Percentage", m_areaOfScreen);
     */
   }

    /**
     * Tells how far the target is from left to right.
     * @return the angular value in the x direction of the target(-29.8 to 29.8)
     */
   public double xOffset() {
    return(m_xAngleOffset);
   }
   
    /**
     * Determines the percent of the screen the target takes up.
     * @return the area of the screen taken up by target
     */
   public double areaOfScreen() {
    return(m_areaOfScreen);
   }

    /**
     * Switches the pipelines in the Network Table. 
     */
   public static void setPipeline(Pipeline pipeline){
        NetworkTableInstance.getDefault().getEntry("pipeline").setNumber(pipeline.getID());
    }

}


