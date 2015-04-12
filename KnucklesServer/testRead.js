var mraa = require('mraa');

var apin = 0;
var analogPin = new mraa.Aio(apin);

var R_INF = 0.1751507772;
var V_SRC = 5.0;
var B_VAL = 3950;

setInterval(function(){
        var v = analogPin.read();
	var reading = btot(v);
	console.log(reading);
}, 500);

function btot(b){
	var v_t = V_SRC*(b*1024.0);
	var r_t = (v_t*100000)/(V_SRC - v_t);
	var t = B_VAL / Math.log(r_t/R_INF);
	return {"b":b,"t":t,"v_t":v_t,"r_t":r_t};
}
