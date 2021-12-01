const router = require('express').Router();
const authController = require('../controllers/auth.controller');

router.route('/')
    .get(authController.login)
    .post(authController.login)
module.exports = router;