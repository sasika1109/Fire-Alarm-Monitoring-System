//databse and port configurations
require('dotenv').config({ path: 'variables.env' });

module.exports = {
    database: `mongodb+srv://${process.env.DATABASE_USER}:${process.env.DATABASE_PASSWORD}@cluster0-i7eo5.mongodb.net/rmi-ds?retryWrites=true&w=majority`,
    port: process.env.PORT,
    secret: process.env.SECRET,
}