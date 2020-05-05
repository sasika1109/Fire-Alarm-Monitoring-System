let request = require('request');
let allSensors = [];
require("dotenv").config();

module.exports.allSensors = () => new Promise((resolve, reject) => { // this method gets all sensors from the sensors rest api
  request(process.env.URL + 'sensors/', function (error, response, body) { // http get request with request package
    if (!error && response.statusCode == 200) { // if the response is true
      allSensors = (JSON.parse(body)).sensors; // get all the sensor details
      resolve(true);
    } else {
      resolve(false);
    }
  })
});

module.exports.updateSensors = () => {
  const sensor = allSensors[Math.floor((Math.random() * allSensors.length))]; // select a random sensor using Math.random method
  if (sensor) { // check if a sensor is selected
    sensor.smokeLevel = Math.floor(Math.random() * 10 + 1); // update the smoke level using the math function to be between 1 & 10
    sensor.CO2Level = Math.floor((Math.random() * 10) + 1); // update the CO2 level using the math function to be between 1 & 10
    console.log(sensor);
    request.put(process.env.URL + 'sensors/' +sensor._id, // http put request using the request package
      {form: sensor} // payload
    )
  }
}


// module.exports = router;
