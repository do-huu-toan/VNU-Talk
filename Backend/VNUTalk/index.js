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


/* Middleware  */

app.use(cookieParser());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(passport.initialize());
app.use(express.urlencoded({
    limit: '50mb'
}));


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
/* End Middleware */


/* Routes */
// index:
app.get('/', (req, res) => {
    return res.json({ message: 'index' })
})
// Đăng nhập:
app.use("/api/v1/auth", authRoute)
app.post("/api/v1/auth/facebook", passport.authenticate('facebook-token', { session: false }), (req, res) => {
    return res.send(req.user);
})


app.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
})