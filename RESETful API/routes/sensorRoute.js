const express = require('express');
const router = express.Router();

// require controller
const sensorController = require('../controllers/sensorController');

router.route('/').get(sensorController.getAllSensors);      // route to GET ALL SENSORS
router.route('/:id').get(sensorController.getSensor);       // route to GET SENSOR
router.route('/').post(sensorController.addSensor);         // route to ADD SENSOR
router.route('/:id').patch(sensorController.updateSensor);  // route to EDIT SENSOR
router.route('/:id').delete(sensorController.deleteSensor); // route to DELETE SENSOR
router.route('/:id').put(sensorController.putSensor);       // route to PUT SENSOR

module.exports = router;