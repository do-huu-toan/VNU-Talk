const User = require("../models/User");

const getAll = async (req, res) => {
    try {
        const listUser = await User.findAll();
        return res.status(200).json(listUser);
    } catch (error) {
        return res.status(400).json(error);
    }
}
const create = async (req, res) => {
    try{
        const newUser = new User({
            fullname: req.body.fullname,
            password: req.body.password,
            username: req.body.username
        })
        return res.status(201).json(await newUser.save());
    }
    catch(error){
        return res.status(500).json(error)
    }
}
module.exports = {
    getAll,
    create
}