const login = (req, res) =>  {
    return res.status(200).json({message: "Post User Login"})
}

module.exports = {
    login
}