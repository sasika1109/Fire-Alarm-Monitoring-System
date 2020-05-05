const express = require('express');
const router= express.Router();
const notification = require('../controllers/notificationController');

router.route('/email').post(notification.email);
router.route('/text').post(notification.text);

module.exports = router;
