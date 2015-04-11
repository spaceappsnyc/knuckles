var express = require('express');
var app = express();
var mraa = require('mraa');

app.get('/', function (req, res) {
  res.send('Hello World!');
});

var SENSOR_COUNT = 5;

var sensors = [];
for(var i = 0; i < SENSOR_COUNT; i++){
	sensors[i] = new mraa.Aio(i);
}

var readings = [];
setInterval(function(){
	for(var i = 0; i < SENSOR_COUNT; i++){
		readings[i] = sensors[i].read();	
	}
}, 100);

app.get('/heat', function (req, res) {

  var data = {
    "sensors":readings,
    "temps":[]
  };

  res.send(data);
});

app.post('/heat', function (req, res) {
  var resp = {"results":0};
  console.log(req);
  res.send(resp);
});

var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});
