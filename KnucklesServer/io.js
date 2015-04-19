var self = {
	mraa: require('mraa'),
	LOOP_SPEED: 500,
	SENSOR_COUNT: 5,
	OUT_COUNT: 5,
	LIGHT_COUNT: 0,
	HEAT_PWM: false,
	SENSOR_PINS: [0,1,2,3,4],
	HEAT_PINS: [3,4,5,6,7],//[0,1,2,3,4],
	LIGHT_PINS: [5,6,7,8,9],
	
	interval: null,
	
	readings: [],
	temps: [],
	heat_on: [],
	light_on: [],

	sensors: [],
	heaters: [],
	lights: [],
	start: function(callback){
		for(var i = 0; i < self.SENSOR_COUNT; i++){
			self.sensors[i] = new self.mraa.Aio(self.SENSOR_PINS[i]);
		}
		for(var i = 0; i < self.OUT_COUNT; i++){
			var out = new self.mraa.Gpio(self.HEAT_PINS[i]);
			out.dir(self.mraa.DIR_OUT);
			self.heaters[i] = out;
			self.heat_on[i] = false;
		}
		for(var i = 0; i < self.LIGHT_COUNT; i++){
			var out = new self.mraa.Gpio(self.LIGHT_PINS[i]);
			out.dir(self.mraa.DIR_OUT);
			self.lights[i] = out;
			self.light_on[i] = false;
		}
		self.interval = setInterval(function(){
			for(var i = 0; i < self.SENSOR_COUNT; i++){
				var reading = self.sensors[i].read();
				self.readings[i] = reading;
				self.temps[i] = self.btot(reading).t_c;
			}
			callback(self.temps);
		}, self.LOOP_SPEED);
	},
	
	turnAllOff: function(){
		for(var i = 0; i < self.OUT_COUNT; i++){
			self.heaters[i].write(0);
		}
		for(var i = 0; i < self.LIGHT_COUNT; i++){
			self.lights[i].write(0);
		}
	},
	stop: function(){
		self.turnAllOff();
		clearInterval(self.interval);
		
	},
	
	setHeat: function(outNum, on){
		self.heaters[outNum].write(self.btoi(on));
		self.heat_on[outNum] = on;
		
	},
	
	setLight: function(outNum, on){
		self.lights[outNum].write(self.btoi(on));
		self.light_on[outNum] = on;
	},
	
	btot: function (b) {

		var R_NO = 10000;
		var T_NO = 298;
		var B_VAL = 3977;

		var R_INF = R_NO*Math.exp(-B_VAL/T_NO)
		var V_SRC = 4.62;
		var R_SHUNT = 9810;
		var R_FUDGE = 0700;


		var v_t = V_SRC*(b/1024.0);
		var r_t = ((v_t*R_SHUNT)/(V_SRC - v_t)) + R_FUDGE;
		var t = B_VAL / Math.log(r_t/R_INF);
		var t_c = t - 273.0;
		return {"b":b,"t":t,"v_t":v_t,"r_t":r_t,"t_c":t_c};
	},
	
	btoi: function(b){
		if(b){
			return 1;
		} else {
			return 0;
		}
	}
	
};

module.exports = self;