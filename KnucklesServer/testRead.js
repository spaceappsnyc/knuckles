var mraa = require('mraa');

var apin = 0;
var analogPin = new mraa.Aio(apin);

var R_INF = 0.1751507772;
var V_SRC = 4.62;
var B_VAL = 3950;
var R_SHUNT = 97100;
var R_FUDGE = 2000;

setInterval(function(){
        var v = analogPin.read();
	var reading = btot(v);
	console.log(reading);
}, 500);

function btot(b){
	var v_t = V_SRC*(b/1024.0);
	var r_t = ((v_t*R_SHUNT)/(V_SRC - v_t)) + R_FUDGE;
	var t = B_VAL / Math.log(r_t/R_INF);
	var t_c = t - 273.0;
        return {"b":b,"t":t,"v_t":v_t,"r_t":r_t,"t_c":t_c};

}
