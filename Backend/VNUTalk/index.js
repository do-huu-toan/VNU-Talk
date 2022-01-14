require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT || 3000;
const morgan = require('morgan');
const passport = require('passport')
const bodyParser = require('body-parser');
//const FacebookStrategy = require('passport-facebook').Strategy;
const FacebookTokenStrategy = require('passport-facebook-token');
const cookieParser = require('cookie-parser');
const authRoute = require('./routes/auth.route');
const accountRoute = require('./routes/account.route');
const userRoute = require('./routes/user.route');
const messageRoute = require('./routes/message.route');
// Config Database:
const db = require('./models/database.js');
const generateData = require('./models/generateSampleData');
//init socketio
const http = require('http');
const server = http.createServer(app);
const io = require('socket.io')(server, {
    cors: {
        origin: "*"
    }
});

const { ExpressPeerServer } = require('peer');
const peerServer = ExpressPeerServer(server, {
    path: '/call'
  });


db.sync({ force: false });

/* Middleware  */
app.use(cookieParser());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(passport.initialize());
app.use(express.urlencoded({
    limit: '50mb'
}));

app.use('/peerjs', peerServer);


passport.use(new FacebookTokenStrategy({
    clientID: "884581712214957",
    clientSecret: "f0bca9217a055435e32029fe6d65aac7",
    fbGraphVersion: 'v3.0'
},
    function (accessToken, refreshToken, profile, done) {
        try {
            console.log('accessToken ', accessToken)
            console.log('refreshToken ', refreshToken)
            console.log('profile ', profile)
            done(null, profile);
        }
        catch (error) {
            console.log('error ', error)
            done(error, false);
        }
    }
));
app.use(morgan('combined'));
app.use(passport.initialize());
/* CORS */
var cors = require('cors')
var corsOptions = {
    origin: '*',
    optionsSuccessStatus: 200 // some legacy browsers (IE11, various SmartTVs) choke on 204
}
app.use(cors(corsOptions))

/* End Middleware */


/* Routes */
// index:
app.get('/', (req, res) => {
    return res.status(200).json({ message: 'Toàn' })
})
// Đăng nhập:
app.use("/api/v1/account", accountRoute);
//User:
app.use('/api/v1/user', userRoute);
// Message
app.use('/api/v1/message', messageRoute)
// OAUTH2 Facebook
app.use("/api/v1/auth", authRoute)
app.post("/api/v1/auth/facebook", passport.authenticate('facebook-token', { session: false }), (req, res) => {
    return res.send(req.user);
})

// SocketIO:
var listSocket = [];
io.on("connection", (socket) => {
    // Lấy userId:
    let _userId = socket.handshake.query.userId;
    /* Tìm xem có userId này chưa */
    let isAvailable = false;
    for (let i = 0; i < listSocket.length; i++) {
        if (_userId == listSocket[i].userId) {
            listSocket[i].socketId = socket.id;
            isAvailable = true;
        }
    }
    if (isAvailable == false) {
        listSocket.push({
            userId: _userId,
            socketId: socket.id
        });
    }

    /* ---------------------------*/
    console.log(listSocket);
    // Nếu gửi tin nhắn
    socket.on("sendMessage", data => {
        let receiverId = data.receiverId;
        let receiverItem = listSocket.find(element => element.userId == receiverId)
        console.log(data);
        if (receiverItem) {
            io.to(receiverItem.socketId).emit('message',
                {
                    message: data.message,
                    seederId: socket.handshake.query.userId,
                });
        }


    })
    socket.on("disconnect", () => {
        console.log(socket.id + " disconnect");
        listSocket = listSocket.filter(element => element.socketId != socket.id);
        //console.log(listSocket);

    })
})

server.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
})