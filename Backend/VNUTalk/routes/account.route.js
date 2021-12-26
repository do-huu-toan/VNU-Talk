const router = require('express').Router();

const accountController = require('../controllers/account.controller');

router.route('/login')
    .post(accountController.login)

module.exports = router;