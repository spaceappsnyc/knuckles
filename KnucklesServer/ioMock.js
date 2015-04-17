var self = {
	LOOP_SPEED: 100,
	SENSOR_COUNT: 5,
	OUT_COUNT: 5,
	LIGHT_COUNT: 5,
	interval: null,
	SENSOR_PINS: [0,1,2,3,4],
	HEAT_PINS: [],
	LIGHT_PINS: [],
	
	readings: [],
	temps: [],
	peltier_on: [],
	light_on: [],

	sensors: [],
	heaters: [],
	lights: [],
	start: function(callback){
		for(var i = 0; i < self.SENSOR_COUNT; i++){
		
		
		}
		self.interval = setInterval(function(){
			for(var i = 0; i < self.SENSOR_COUNT; i++){
				self.temps[i] = 128;
			}
			callback(self.temps);
		}, self.LOOP_SPEED);
	},
	
	turnAllOff: function(){
		
	},
	stop: function(){
		self.turnAllOff();
		clearInterval(self.interval);
		
	},
	setHeat: function(sensor, on){
	
		
	},
	
	setLight: function(sensor, on){
	
		
	},
	
	btot: function (b) {

		var R_INF = 0.1751507772;
		var V_SRC = 4.62;
		var B_VAL = 3950;
		var R_SHUNT = 97100;
		var R_FUDGE = 4000;


		var v_t = V_SRC*(b/1024.0);
		var r_t = ((v_t*R_SHUNT)/(V_SRC - v_t)) + R_FUDGE;
		var t = B_VAL / Math.log(r_t/R_INF);
		var t_c = t - 273.0;
		return {"b":b,"t":t,"v_t":v_t,"r_t":r_t,"t_c":t_c};
	}
};

module.exports = self;