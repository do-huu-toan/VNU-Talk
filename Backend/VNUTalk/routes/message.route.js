const router = require('express').Router();
const messageController = require('../controllers/message.controller')
router.route('/')
    .get(messageController.getBySeederAndReceiver)
module.exports = router;