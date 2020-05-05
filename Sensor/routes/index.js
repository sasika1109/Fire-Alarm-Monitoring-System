var express = require('express');
var router = express.Router();
const request = require('request');

/* GET home page. */
router.get('/', function(req, res, next) {
  // request('http://localhost:3000/api/sensors/getAllSensors', { json: true }, (err, res, body)  => {
  //   console.log(err);
  //   if (err) { console.log(err); }
  //   console.log(body);
  // })
  request('http://www.google.com', function (error, response, body) {
    if (!error && response.statusCode == 200) {
      console.log(body) // Print the google web page.
    }
  })
  console.log('working');
  res.render('index', { title: 'Express' });
});

module.exports = router;
