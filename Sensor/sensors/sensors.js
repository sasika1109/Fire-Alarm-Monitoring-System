request = require('request');
let allSensors = [];

module.exports.getSensors = async function () {
  await request('http://localhost:3000/api/sensors/', function (error, response, body) {
    let ids = [];
    if (!error && response.statusCode == 200) {
      allSensors = (JSON.parse(body)).sensors;
      allSensors.forEach(data => {
        ids.push(data._id);
      })
      return 'ids';
    }
  })
};

module.exports.data = 'data';
