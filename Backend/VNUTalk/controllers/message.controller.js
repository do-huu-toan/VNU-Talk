const Message = require("../models/Message");
const User = require("../models/User");
const { Op } = require("sequelize");
const getBySeederAndReceiver = async (req, res) => {
    const _seederId = req.query.seederId;
    const _receiverId = req.query.receiverId;
    if (!_seederId && !_receiverId) {
        return res.status(404).json({ message: "Chưa truyền seederId và receiverId" })
    }
    try {
        const listMessage = await Message.findAll({
            where: {
                [Op.or]: [
                    {
                        seederId: _seederId,
                        receiverId: _receiverId
                    },
                    {
                        seederId: _receiverId,
                        receiverId: _seederId
                    }
                ],
            },
            order: [
                ['createdAt', 'ASC'],
            ]
        })
        return res.status(200).json(listMessage);
    }
    catch (err) {
        return res.status(500).json({ message: `Có lỗi xảy ra: ${err}` });
    }
}

const create = async (req, res) => {
    const seederId = req.body.seederId
    const receiverId = req.body.receiverId;
    const message = req.body.message;
    if (seederId && receiverId) {
        const newMessage = new Message({
            seederId: seederId,
            receiverId: receiverId,
            message: message
        })
        try {
            const result = await newMessage.save();
            return res.status(201).json(result);
        } catch (error) {
            return res.status(500).json({ message: "Create Message Error" })
        }
    }
    else {
        return res.status(404).json({ message: "Input not valid" });
    }
}
module.exports = {
    getBySeederAndReceiver,
    create
}