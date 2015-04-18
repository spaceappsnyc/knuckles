var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var io = require('./ioMock');

app.use(bodyParser.json());

app.get('/', function (req, res) {
  res.send('Hello World!');
});


app.get('/heat', function (req, res) {

  var data = {
    "sensors":[],
    "temps":[]
  };

  res.send(data);
});

app.post('/heat', function (req, res) {
  var resp = {"results":0};
  console.log(req.body);
  res.send(req.body);
});

var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;
  

  console.log('Example app listening at http://%s:%s', host, port);
  io.start(function(t){
	console.log(t);
	console.log(io.SENSOR_PINS);
	});

});
