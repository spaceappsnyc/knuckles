var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var f = require('./f');

app.use(bodyParser.json());

var mraa = require('mraa');

var OUT_PIN = 3;
var out = new mraa.Gpio(OUT_PIN);
out.dir(mraa.DIR_OUT);

app.get('/', function (req, res) {
  res.send('Hello World!');
});

var SENSOR_COUNT = 5;

var sensors = [];
for(var i = 0; i < SENSOR_COUNT; i++){
	sensors[i] = new mraa.Aio(i);
}

var readings = [];
var temps = [];
setInterval(function(){
	for(var i = 0; i < SENSOR_COUNT; i++){
		readings[i] = sensors[i].read();
		temps[i] = f.btot(readings[i]).t_c;
	}
}, 100);

app.get('/heat', function (req, res) {

  var data = {
    "sensors":readings,
    "temps":temps
  };

  res.send(data);
});

app.post('/heat', function (req, res) {
  var body = req.body;
  var on = 0;
  if(body.on == "1"){
    on = 1;
  }
  out.write(on);
  var resp = {"results":0, "request": body, "on":on};
  console.log(resp);
  res.send(resp);
});

var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});
