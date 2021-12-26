require('dotenv').config();
const express = require('express');
const app = express();
const port = process.env.PORT || 3000;
const morgan = require('morgan');
const jwt = require('jsonwebtoken');
const cookieParser = require('cookie-parser');
const http = require('http');
const server = http.createServer(app);

const init = true;

//Database
const db = require('./models/database');
const initDatabase = require('./models/GenerateSampleData');

if (init) {
  db.sync({ force: init }).then(() => {
    initDatabase();
  });
}
else {
  db.sync({ force: false })
}


app.use(morgan('combined'));

app.use(express.urlencoded({
  limit: '50mb'
}));

app.use(express.json());

app.use(express.static('public'))


server.listen(port, () => {
  console.log(`Running on port: ${port}...`)
})