var mraa = require('mraa');

var pinNumber = 13;
var analogPin = 0;

var digitalPin = new mraa.Gpio(pinNumber);
var analogPin = new mraa.Aio(analogPin);

digitalPin.dir(mraa.DIR_OUT); // vs DIR_IN

var state = 0;

setInterval(function(){
        console.log(analogPin.read());
        //state = 1 - state;
        //digitalPin.write(state);
}, 1000);

