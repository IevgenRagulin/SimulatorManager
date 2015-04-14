var gaugerpm;
var gaugepwr;
var isHtmlInitialized = false;
var engineFeatures = [ "rpm", "pwr", "pwp", "mp_", "egt1", "egt2", "cht1",
		"cht2", "est", "ff_", "fp_", "op_", "ot_", "n1_", "n2_", "vib", "vlt",
		"amp" ];
function cz_vutbr_fit_simulatormanager_jscomponents_enginespanel_EnginesPanel() {

	var e = this.getElement();
	if (!(typeof this.getState().rpm === 'undefined')) {
		initEngineHtmlAndGauges(e, this.getState());
		window.isHtmlInitialized = true;
	}

	
	this.onStateChange = function() {
		console.log("KUKU ON STATE CHANGE");
		if ((!(typeof this.getState().rpm === 'undefined'))
				&& (!window.isHtmlInitialized)) {
			console.log("KUKU ON STATE CHANGE going to init engine html"+window.isHtmlInitialized);
			window.isHtmlInitialized = true;
			initEngineHtmlAndGauges(e, this.getState());
		}
		if (isHtmlInitialized) {
			console.log("KUKU on state change. rpm gauge"+window.gaugerpm);
			//setGaugeValue("rpm", this.getState().rpm, this.getState().rpmvals);
			console.log("KUKU on state change. pwr gauge"+window.gaugepwr);
			//setGaugeValue("pwr", this.getState().pwr, this.getState().pwrvals);
		}
	};
}

function setGaugeValue(featureName, isEnabled, vals) {
	console.log("KUKU set value "+vals+" is enabled "+isEnabled);
	if ((!(typeof isEnabled === 'undefined'))&&(isEnabled[0])) {
		console.log("KUKU setGaugeValue, feature is enabled");
		window["gauge"+featureName].set(vals[0]);
	}
}

function initEngineHtmlAndGauges(e, state) {
	console.log("KUKU going to init engine html and gauges");
	if (state.rpm[0]) {
		console.log("KUKU rpm is enabled");
		e.innerHTML = e.innerHTML + getHtmlForFeatureName("rpm");
		initGaugeForFeatureName("rpm", state.minrpm, state.maxrpm);
	} 
	if (state.pwr[0]) {
		console.log("KUKU pwr is enabled");
		e.innerHTML = e.innerHTML + getHtmlForFeatureName("pwr");
		//initGaugeForFeatureName("pwr", state.minpwr, state.maxpwr);
	} 
	console.log("KUKU after initing gauges. rpm"+window.gaugerpm);
	console.log("KUKU after initing gauges. pwr"+window.gaugepwr);
	
}
// todo pass gauge object here to which the new gauge is set
function initGaugeForFeatureName(featureName, minval, maxval) {
	console.log("KUKU init gauge for feature name"+featureName);
	var opts = {
		lines : 12, // The number of lines to draw
		angle : 0.15, // The length of each line
		lineWidth : 0.15, // The line thickness
		//limitMax : true,
		pointer : {
			length : 0.9, // The radius of the inner circle
			strokeWidth : 0.035, // The rotation offset
			color : '#000000' // Fill color
		},
		limitMax : 'true', // If true, the pointer will not go past the end of
		// the gauge
		colorStart : '#6FADCF', // Colors
		colorStop : '#8FC0DA', // just experiment with them
		strokeColor : '#E0E0E0', // to see which ones work best for you
		generateGradient : false
	};
	console.log("KUKU creating gauge. Going to get element by id engine-"
			+ featureName);
	var target = document.getElementById("engine-" + featureName); // your
	//prev window["gauge"+featureName]
	//now var
	var gauge = new Gauge(target).setOptions(opts); // create sexy gauge!
	console.log("kuku minval"+minval);
	console.log("kuku maxval"+maxval);
	gauge.setTextField(document.getElementById("engine-textfield-"
			+ featureName));
	gauge.animationSpeed = 1; // set animation speed (32 is default
	gauge.minValue = minval[0];
	gauge.maxValue = maxval[0];
	gauge.set(minval[0]);
	
	
	//window["gauge"+featureName] = new Gauge(target).setOptions(opts); // create sexy gauge!
	//window["gauge"+featureName].setTextField(document.getElementById("engine-textfield-"
	//		+ featureName));
	////window["gauge"+featureName].animationSpeed = 1; // set animation speed (32 is default
	//window["gauge"+featureName].minValue = minval[0];
	//window["gauge"+featureName].maxValue = maxval[0];
	//window["gauge"+featureName].set(5);
	//console.log("KUKU new gauge "+ window["gauge"+featureName]);
	// value)
}

function getHtmlForFeatureName(featureName) {
	return "<br/><canvas id='engine-" + featureName
			+ "' width='120' height='60' background-color: white;'>"
			+ "Your browser doesn't support canvas." + "</canvas><br/>"
			+ featureName+": <div id='engine-textfield-" + featureName
			+ "' style='display:inline'></div><br/>"
}
