const Message = require("../models/Message");
const User = require("../models/User");

const getBySeederAndReceiver = async (req, res) => {
    const _seederId = req.query.seederId;
    const _receiverId = req.query.receiverId;
    if(!_seederId && !_receiverId){
        return res.status(404).json({message: "Chưa truyền seederId và receiverId"})
    }
    try{
        const listMessage = await Message.findAll({
            where:{
                seederId: _seederId,
                receiverId: _receiverId
            }
        })
        return res.status(200).json(listMessage);
    }
    catch(err){
        return res.status(500).json({message: "Có lỗi xảy ra"});
    }
  
}
module.exports = {
    getBySeederAndReceiver
}