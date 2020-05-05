const express = require('express');
const bodyParser = require('body-parser');

// require routes
const sensorRoute = require('./routes/sensorRoute');
const authRoute = require('./routes/authRoute');
const notification = require('./routes/notification');

// defining api URL
const apiURL = '/api/';

const app = express();

// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }))

// parse application/json
app.use(bodyParser.json());

// CORS
app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, Authorization');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PATCH, DELETE, OPTIONS');

    next();
});

// combining api URL with the route to work
app.use(apiURL + 'sensors/', sensorRoute);
app.use(apiURL + 'auth/', authRoute);
app.use(apiURL + 'messages', notification);

module.exports = app;
