const router = require('express').Router();
const messageController = require('../controllers/message.controller');
const { route } = require('./auth.route');
router.route('/')
    .get(messageController.getBySeederAndReceiver)
    .post(messageController.create)
module.exports = router;