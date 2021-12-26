const User = require("../models/User");

const login = async (req, res) => {
    const userFound = await User.findOne({
        where:{
            username: req.body.username,
            password: req.body.password
        }
    })
    if(userFound != null){
        return res.status(200).json(userFound);
    }
    return res.status(404).json({message: "Login Failed"})
}

module.exports = {
    login
}