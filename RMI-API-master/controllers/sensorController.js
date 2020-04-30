const Sensor = require("./../models/Sensor");

// get all sensors
exports.getAllSensors = async (req, res) => {
  // find all sensors form the database and assign it to a variable
  const sensors = await Sensor.find();

  // send response along with the sensors array
  res.status(200).json({
    messagae: "Success",
    sensors,
  });
};

// get single user
exports.getSensor = async (req, res) => {
  // find a particular sensor by passing the sensor ID through URL parameter and assign the user to a variable
  const sensor = await Sensor.findById(req.params.id);

  // send response along with the sensor
  res.status(201).json({
    messagae: "Success",
    sensor
  });
};

// add a sensor
exports.addSensor = async (req, res) => {
  const sensor = new Sensor(req.body);

  // save to database
  await sensor.save();

  // send response along with the added user
  res.status(201).json({
    message: "Success",
    sensor,
  });
};

// update a sensor
exports.updateSensor = async (req, res) => {
  // update sensor and save to database
  const updatedSensor = await Sensor.findByIdAndUpdate(req.params.id, req.body, {
    runValidators: true,
    new: true,
  });

  // send response along with the added sensor
  res.status(200).json({
    messagae: "Success",
    updatedSensor,
  });
};

exports.putSensor = async (req, res) => {
  // update sensor and save to database
  const updatedSensor = await Sensor.findByIdAndUpdate(req.params.id, req.body, {
    runValidators: true,
    new: true,
  });

  // send response along with the added sensor
  res.status(200).json({
    messagae: "Success",
    updatedSensor,
  });
}

// delete a sensor
exports.deleteSensor = async (req, res) => {
  // find user using the sensor ID passed through URL parameter and delete sensor
  await Sensor.findByIdAndDelete(req.params.id);

  // send response
  res.status(200).json({
    messagae: "Success",
    data: null,
  });
};
