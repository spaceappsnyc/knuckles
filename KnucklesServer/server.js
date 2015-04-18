var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var io = require('./io');

app.use(bodyParser.json());


var settings = {"min_temp":23.0}
var mode = "off"

app.get('/', function (req, res) {
  res.send('Hello World!');
});

function getState() {
  return {
    "sensors":io.readings,
    "temps":io.temps,
    "mode":mode,
    "is_heating": io.heat_on,
	"is_light": io.light_on,
    "settings": settings
  };
}

function manageTemp(tA) {
	if(mode == "auto"){
		for(var t = 0; t < tA.length; t++){
			if(tA[t] <= settings.min_temp && !io.heat_on[t]){
				io.setHeat(t, true);
				console.log("(auto) turned on | "+t);
			} else if(tA[t] >= settings.min_temp && io.heat_on[t]){
				io.setHeat(t, false);
				console.log("(auto) turned off | "+t);
			}
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

  console.log(settings);
  res.send(settings);
});


var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);
  io.start(manageTemp)

});
