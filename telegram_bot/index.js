const express = require('express');
const bodyParser = require('body-parser')
const morgan = require('morgan');
const app = express();

app.use(bodyParser.json());
app.use(morgan('short'));

//setup port
app.set('port', (process.env.PORT || 5000));

const repository = require('./repository/repository');
//init routes
const timeleaksRoute = require('./route/timeleaks');
app.post('/timeleaks', timeleaksRoute.post(repository));
app.options('/timeleaks', timeleaksRoute.options());


//start server
app.listen(app.get('port'), function() {
  console.log('Node app is running on port', app.get('port'));
});