var mraa = require('mraa');
var f = require('./f');

var apin = 0;
var analogPin = new mraa.Aio(apin);

setInterval(function(){
        var v = analogPin.read();
	var reading = f.btot(v);
	console.log(reading);
}, 500);

