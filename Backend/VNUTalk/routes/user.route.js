const router = require('express').Router();
const userController = require('../controllers/user.controller');

router.route('/')
    .get(userController.getAll)
    .post(userController.create)
module.exports = router;