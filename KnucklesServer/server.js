var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var f = require('./f');
var io = require('./io');

app.use(bodyParser.json());

var mraa = require('mraa');

var OUT_PIN = 3;
var out = new mraa.Gpio(OUT_PIN);
out.dir(mraa.DIR_OUT);
var is_on = false;
out.write(0);
var mode = "off";

var settings = {"min_temp":23.0}

app.get('/', function (req, res) {
  res.send('Hello World!');
});

var SENSOR_COUNT = 5;

var sensors = [];
for(var i = 0; i < SENSOR_COUNT; i++){
	sensors[i] = new mraa.Aio(i);
}

function getState() {
  return {
    "sensors":readings,
    "temps":temps,
    "mode":mode,
    "is_heating": is_on,
    "settings": settings
  };
}

function manageTemp(t) {
	if(mode == "auto"){
		if(t <= settings.min_temp && !is_on){
			is_on = true;
			out.write(1);
			console.log("(auto) turned on | "+t);
		} else if(t >= settings.min_temp && is_on){
			is_on = false;
			out.write(0);
			console.log("(auto) turned off | "+t);	
		}
	}
}

app.get('/status', function (req, res) {
  res.send(getState());
});

app.post('/heat', function(req, res){
	var body = req.body;
	if(body.mode == "on"){
		mode = body.mode;
		is_on = true;
		out.write(1);	
	} else if(body.mode == "off"){
		mode = body.mode;
		is_on = false;
		out.write(0);	
	} else if(body.mode == "auto"){
		mode = body.mode;
		is_on = false;
		out.write(0);
	}
	res.send(getState());
});

app.get('/settings', function(req, res) {
	res.send(settings);
});

app.post('/settings', function (req, res) {
  var body = req.body;

    if('min_temp' in body){
      settings.min_temp = body.min_temp;
    }

  console.log(settings);
  res.send(settings);
});

var readings = [];
var temps = [];
setInterval(function(){
	for(var i = 0; i < SENSOR_COUNT; i++){
		readings[i] = sensors[i].read();
		temps[i] = f.btot(readings[i]).t_c;
	}
        manageTemp(temps[0]);
}, 100);

var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});
