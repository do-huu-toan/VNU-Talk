const db = require('./database.js');
const generateData = require('./generateSampleData');

db.sync({ force: true }).then(() => {
    generateData();
});
