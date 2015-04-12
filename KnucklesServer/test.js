var mraa = require('mraa');

var pinNumber = 3;
var analogPin = 0;

//var digitalPin = new mraa.Gpio(pinNumber);
//var analogPin = new mraa.Aio(analogPin);

var x = mraa.Pwm(pinNumber);
x.period_us(700);
x.enable(True);
var value = 0.0;
//digitalPin.dir(mraa.DIR_OUT); // vs DIR_IN

var state = 0;

setInterval(function(){
        //console.log(analogPin.read());
        //state = 1 - state;
        //digitalPin.write(state);
	x.write(value);
	value = value + 0.01;
	if(value >= 1.0){
		value = 0.0;	
	}

}, 50);

