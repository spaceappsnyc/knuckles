var express = require('express');
var app = express();
var mraa = require('mraa');

app.get('/', function (req, res) {
  res.send('Hello World!');
});

var sensors = [
  new mraa.Aio(0),  new mraa.Aio(1),
  new mraa.Aio(2),  new mraa.Aio(3)
];

var readings = [];

setInterval(function(){
	for(i = 0; i < 4; i++){
		readings[i] = sensors[i].read();	
	}
}, 100);

app.get('/heat', function (req, res) {

  var data = {
    "sensors":readings
  };

  res.send(data);
});

var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});
