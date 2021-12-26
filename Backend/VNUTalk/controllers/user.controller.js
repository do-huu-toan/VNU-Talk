const User = require("../models/User");

const getAll = async (req, res) => {
    try {
        const listUser = await User.findAll();
        return res.status(200).json(listUser);
    } catch (error) {
        return res.status(400).json(error);
    }
}
module.exports = {
    getAll
}