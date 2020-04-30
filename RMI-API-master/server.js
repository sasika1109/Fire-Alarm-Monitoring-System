const mongoose = require('mongoose');
const config = require('./config/database');
const app = require('./app');

// Connecting to MongoDB
mongoose.connect(config.database, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useCreateIndex: true,
    useFindAndModify: false
});

let db = mongoose.connection;

db.once('open', () => {
    console.log('Connected to MongoDB');
});

db.once('error', err => {
    console.log('ERROR : ' + err);
});

//Connecting to server
app.listen(config.port, () => {
    console.log(`App is listening to ${config.port}`);
});