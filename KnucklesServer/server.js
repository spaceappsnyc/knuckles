var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var io = require('./io');

app.use(bodyParser.json());


var settings = {"min_temp": 23.0, "max_temp": 24.0}
var mode = "off"
var operation = "cool"

app.get('/', function (req, res) {
  res.send('Hello World!');
});

function getState() {
  return {
    "sensors":io.readings,
    "temps":io.temps,
    "mode":mode,
    "operation": operation,
    "is_heating": io.heat_on,
	"is_light": io.light_on,
    "settings": settings
  };
}

function manageTemp(tA) {
	if(mode == "auto"){
		for(var t = 0; t < tA.length; t++){
			if(((operation == "heat" && tA[t] <= settings.min_temp) || 
				(operation == "cool" && tA[t] >= settings.max_temp)) && !io.heat_on[t]){
				io.setHeat(t, true);
				console.log("(auto) turned on | "+t);
			} else if(
				((operation == "heat" && tA[t] >= settings.min_temp) || 
				(operation == "cool" && tA[t] <= settings.max_temp)) && io.heat_on[t]){
				io.setHeat(t, false);
				console.log("(auto) turned off | "+t);
			}
		}
	}
}

app.get('/status', function (req, res) {
	var status = getState();
	console.log(status);
  res.send(status);
});

app.post('/heat', function(req, res){
	var body = req.body;
	if(body.mode == "on"){
		mode = body.mode;
		for(var i = 0; i < io.OUT_COUNT; i++){
			io.setHeat(i, true);
		}
	} else if(body.mode == "off"){
		mode = body.mode;
		for(var i = 0; i < io.OUT_COUNT; i++){
			io.setHeat(i, false);
		}
	} else if(body.mode == "auto"){
		mode = body.mode;
		for(var i = 0; i < io.OUT_COUNT; i++){
			io.setHeat(i, false);
		}
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
    if('max_temp' in body){
    	settings.max_temp = body.max_temp;
    }
    if('operation' in body){
    	if(body.operation == "heat" || body.operation == "cool"){
    		settings.operation = body.operation;
    	}
    }

  console.log(settings);
  res.send(settings);
});


var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
  io.start(manageTemp)

});
