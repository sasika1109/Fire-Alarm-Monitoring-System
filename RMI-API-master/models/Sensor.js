const mongoose = require('mongoose');

const sensorSchema = mongoose.Schema({
    floorNo: {
        type: Number,
        required: [true, 'A sensor must have a floor number']
    },
    roomNo: {
        type: Number,
        required: [true, 'A sensor must have a room number']
    },
    status: {
        type: String,
        default: 'ACTIVE',
        required: [true, 'A sensor must have a status']
    },
    smokeLevel: {
        type: Number,
        default: 0
    },
    CO2Level: {
        type: Number,
        default: 0
    },
});

module.exports = mongoose.model('Sensor', sensorSchema);