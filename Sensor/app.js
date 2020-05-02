var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var sensors = require('./routes/sensors');

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

sensors.allSensors(); // read all the sensors when the app gets run

/**
 * The initial plan was to run a cron job
 * but since the cron job only allows a minimum time of 1 minute
 * function with 3 second time out is used
 * This function will run ever 3 seconds and update a random sensor from the data base
 */
function myLoop() {         //  create a loop function
  setTimeout(function() {   //  call a 3s setTimeout when the loop is called
    sensors.updateSensors(); // update method
    myLoop();             //  ..  again which will trigger another
  }, 3000)
}
myLoop();


app.use('/', indexRouter);

module.exports = app;
