const db = require('./database');
const Message = require('./Message');
const User = require('./User')

async function generateSampleData() {
    try {
        const newUser = new User({
            username: 'admin',
            fullname: 'Admin',
            password: 'admin'
        })
        await newUser.save()

        const newUser2 = new User({
            username: 'dohuutoannb',
            fullname: 'Đỗ Hữu Toàn',
            password: '123456'
        })
        await newUser2.save()

        const message = new Message({
            message: "Xin chào",
            seederId: newUser.id,
            receiverId: newUser2.id
        })
        await message.save();

    } catch (error) {
        console.log(`Lỗi: ${error}`)
    }
}



module.exports = generateSampleData;