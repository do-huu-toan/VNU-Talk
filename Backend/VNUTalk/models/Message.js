const { Sequelize, DataTypes } = require('sequelize');
const db = require('./database');
const User = require('./User');

const Message = db.define('Message', {
  id: {
    type: DataTypes.UUID,
    defaultValue: DataTypes.UUIDV4,
    primaryKey: true,
    unique: true,
    allowNull: false
  },
  message: {
    type: DataTypes.STRING
  },
  seederId:{
    type: DataTypes.UUID,
    allowNull: false
  },
  receiverId:{
    type: DataTypes.UUID,
    allowNull: false
  }
});

module.exports = Message;