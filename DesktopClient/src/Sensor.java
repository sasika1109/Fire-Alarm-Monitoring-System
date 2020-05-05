/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sasika
 */
public class Sensor {
        public String _id;
        public String status;
        public int floorNo;
        public int roomNo;
        public int smokeLevel;
        public int co2Level;
        
        public Sensor( String _id,String status, int floorNo, int roomNo, int smokeLevel,int co2Level) {
            this._id = _id;
            this.status = status;
            this.floorNo = floorNo;
            this.roomNo = roomNo;
            this.smokeLevel = smokeLevel;
            this.co2Level = co2Level;
        }
}
