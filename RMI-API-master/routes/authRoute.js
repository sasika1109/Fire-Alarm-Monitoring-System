const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');

router.route('/').post(authController.checkAuth);

module.exports = router;

