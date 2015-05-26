var isHtmlInitialized = false;
var engineFeatures = [ "rpm", "pwr", "pwp", "mp_", "et1", "et2", "ct1", "ct2",
		"est", "ff_", "fp_", "op_", "ot_", "n1_", "n2_", "vib", "vlt", "amp" ];
var fuelTanks = [ "lfu", "cfu", "rfu" ]
// if specific engineFeatures are enabled or not. On every update we check if
// there changes in a list of enabled engine features. If this list has changed,
// we reinitialize the html
var engineFeaturesState = new Object();
// same for fuel tanks
var fuelTanksState = new Object();
var thisObj;

/**
 * This is called once on creating EnginesPanel() java object, entering the page
 * with enginesPanel
 */
function cz_vutbr_fit_simulatormanager_jscomponents_enginesfuelpanel_EnginesAndFuelPanel() {
	window.thisObj = this;
	// when document
	// we load script in such a tricky way because there is an error if we try
	// to do it normally through @Javascript annotation in EnginesPanel.java.
	// As soon as script is loaded, loadVisualizationLibrary
	loadScript("https://www.google.com/jsapi", loadGoogleVisualizationLibrary);
	var e = this.getElement();
}

/**
 * Load google visualisation library. After loading it, we init engines panel
 */
function loadGoogleVisualizationLibrary() {
	// console.log("kuku load visuzliation library");
	google.load("visualization", "1", {
		packages : [ "gauge" ],
		"callback" : initEnginesPanel
	});
};

/**
 * This is called once, by on finishing executing
 * loadGoogleVisualizationLibrary() method. This initialized everything, sets
 * onStateChangeListener
 */
function initEnginesPanel() {
	if (!(typeof thisObj.getState().rpm === 'undefined')) {
		initAllHtmlAndGauges(thisObj.getElement(), thisObj.getState());
	}
	thisObj.onStateChange = function() {
		// if some of the features state has been updated, reinit html
		// if html hasn't yet been initialized, do it
		// console.log("kuku state changed");
		if ((!window.isHtmlInitialized)
				|| (hasStateOfSomeFeatureChanged(thisObj.getState()))) {
			// console.log("kuku going to init html. Has state of some feature
			// changed"+hasStateOfSomeFeatureChanged(thisObj.getState()));
			initAllHtmlAndGauges(thisObj.getElement(), thisObj.getState());
		}
		if (isHtmlInitialized) {
			// iterate over fuel tanks
			for (var i = 0; i < fuelTanks.length; i++) {
				//setTimeoutToDrawFuelGauge(i);
			}
			// iterate over engines. the number of engines is the same as the
			// size of array rpm
			for (var i = 0; i < thisObj.getState().rpm.length; i++) {
				for (var j = 0; j < engineFeatures.length; j++) {
					// we call drawEngineGauge through timeout in order to do
					// this
					// asynchroniously to improve performance, not sure if it
					// really improves performance
					// also, we need to create a new instance of variable i
					// because
					// javascript is so javascript:
					// http://stackoverflow.com/questions/5226285/settimeout-in-a-for-loop-and-pass-i-as-value
					setTimeoutToDrawEngineGauge(i, j);
				}
			}
		}
	};
}

/**
 * Draw gauge on engineId for featureId asynchroniously
 */
function setTimeoutToDrawEngineGauge(engineId, featureId) {
	setTimeout(function() {
		drawEngineGauge(engineId, engineFeatures[featureId]);
	}, 0);
}

/**
 * Draw gauge for fuel tank asynchroniously
 * 
 * @param engineId
 * @param featureId
 */
function setTimeoutToDrawFuelGauge(tankId) {
	setTimeout(function() {
		drawFuelTankGauge(fuelTanks[tankId]);
	}, 0);
}

/**
 * Load script from url, attach it to head of html. On finishing, call the
 * callback
 * 
 * @param url
 * @param callback
 */
function loadScript(url, callback) {
	// Adding the script tag to the head as suggested before
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = url;
	// Then bind the event to the callback function.
	// There are several events for cross browser compatibility.
	script.onreadystatechange = callback;
	script.onload = callback;
	// Fire the loading
	head.appendChild(script);
}

/**
 * Return true if the state (enabled/disabled rpm, changed min/max pwr etc.) has
 * changed
 */
function hasStateOfSomeFeatureChanged(state) {
	for (var i = 0; i < engineFeatures.length; i++) {
		if (!arraysIdentical(engineFeaturesState[engineFeatures[i]],
				state[engineFeatures[i]])) {
			// console.log("state of some feature has changed YES. prev: "
			// + engineFeaturesState[engineFeatures[i]] + " new: "
			// + state[engineFeatures[i]]);
			return true;
		}
	}
	//console.log("state of some feature has changed NO");
	return false;
}

/**
 * Return true if arrays a and b are equal
 * 
 * @param a
 * @param b
 * @returns {Boolean}
 */
function arraysIdentical(a, b) {
	var i = a.length;
	if (i != b.length)
		return false;
	while (i--) {
		if (a[i] !== b[i])
			return false;
	}
	return true;
};

/**
 * Init html for all gauges on all engines, and for fuel tanks
 * 
 * @param e
 * @param state
 */
function initAllHtmlAndGauges(e, state) {
	e.innerHTML = "";
	initFuelTanksHtmlAndGauges(e, state);
	// iterate over engines. the number of engines is the same as the length of
	// array rpm
	for (var i = 0; i < state.rpm.length; i++) {
		// iterate engine html for all engines
		initEngineHtmlAndGauges(e, state, i);
	}
	// make the div collapsable so that a user can hide it if desired
	$(".collapsable").accordion({
		header : "h3",
		collapsible : true
	});
	window.isHtmlInitialized = true;
}

/**
 * Init html for all gauges on this engine
 * 
 * @param e
 * @param state
 * @param engineId
 */
function initEngineHtmlAndGauges(e, state, engineId) {
	// console.log("kuku going to init engine html and gauge. engid" +
	// engineId);
	var engineHtml = "<div class='collapsable'><h3>Engine " + engineId
			+ "</h3><div>";
	for (var i = 0; i < engineFeatures.length; i++) {
		var newFeatureState = state[engineFeatures[i]];
		// update features state
		engineFeaturesState[engineFeatures[i]] = newFeatureState;
		engineHtml = engineHtml
				+ getHtmlForEngineFeature(state, engineFeatures[i], engineId);
	}
	engineHtml = engineHtml + "</div></div>";
	e.innerHTML = e.innerHTML + engineHtml;

	for (var i = 0; i < engineFeatures.length; i++) {
		drawEngineGauge(engineId, engineFeatures[i]);
	}
}

function initFuelTanksHtmlAndGauges(e, state) {
	var tanksHtml = "<div class='collapsable'><h3>Fuel tanks</h3><div>";
	for (var i = 0; i < fuelTanks.length; i++) {
		var newTankState = state[fuelTanks[i]];
		// update tank state
		fuelTanksState[fuelTanks[i]] = newTankState;
		tanksHtml = tanksHtml + getHtmlForFuelTank(state, fuelTanks[i]);
	}
	tanksHtml = tanksHtml + "</div></div>";
	e.innerHTML = e.innerHTML + tanksHtml;
	for (var i = 0; i < fuelTanks.length; i++) {
		drawFuelTankGauge(fuelTanks[i]);
	}
}

/**
 * 
 * @param greenFrom_
 *            starting from this value the gauge is green
 * @param greenTo_
 *            ending with this value the gauge is green. Must be > than
 *            greenFrom_
 * @param redFrom_
 *            starting from this value the gauge is red
 * @param redTo_
 *            ending with this value the gauge is red. Must be > than redFrom_
 * @param minVal
 *            minimum value of gauge
 * @param maxVal
 *            maximum value of gauge
 * @returns gauge options used for building the graph
 */
function buildGaugeOptions(greenFrom_, greenTo_, redFrom_, redTo_, minVal,
		maxVal) {
	var options = {
		width : 120,
		height : 120,
		redFrom : redFrom_,
		redTo : redTo_,
		greenFrom : greenFrom_,
		greenTo : greenTo_,
		animation : false,
		minorTicks : 5,
		max : maxVal,
		min : minVal
	};
	return options;
}

/**
 * Check if featureName on engineId is enabled for this engine model
 */
function isEngineFeatureEnabled(featureName, engineId) {
	var state = thisObj.getState();
	return ((!(typeof state[featureName + "vals"] === 'undefined')) && (state[featureName][engineId]));
}

/**
 * Check if fuel feature is enabled for this simulator model
 */
function isFuelFeatureEnabled(featureName) {
	var state = thisObj.getState();
	return ((!(typeof state[featureName + "vals"] === 'undefined')) && (state[featureName]));
}

/**
 * Is value for featureName and engineId is coming through AWCom
 */
function isEngineFeatureAWComValueAvailable(featureName, engineId) {
	var state = thisObj.getState();
	// do we have values from AWCom on the UI
	// is AWCom sending values for engine with engineId
	return (!(typeof state[featureName + "vals"] === 'undefined'))
			&& (state[featureName + "vals"].length > engineId);
}

/**
 * Is fuel value for featureName coming through AWCom
 */
function isFuelFeatureAWComValueAvailable(featureName) {
	var state = thisObj.getState();
	// if we have values from AWCom on the UI
	return (!(typeof state[featureName + "vals"] === 'undefined'));
}

/**
 * Get minValue for featureName and engine id from engine model
 * 
 * @returns
 */
function getEngineFeatureMinValue(featureName, engineId) {
	var state = thisObj.getState();
	return state["min" + featureName][engineId];
}

/**
 * Get lowValue for featureName and engine id from engine model
 * 
 * @returns
 */
function getEngineFeatureLowValue(featureName, engineId) {
	var state = thisObj.getState();
	return state["low" + featureName][engineId];
}

/**
 * Get highValue for featureName and engine id from engine model
 * 
 * @returns
 */
function getEngineFeatureHighValue(featureName, engineId) {
	var state = thisObj.getState();
	return state["high" + featureName][engineId];
}

/**
 * Get maxValue for featureName and engine id from engine model
 * 
 * @returns
 */
function getEngineFeatureMaxValue(featureName, engineId) {
	var state = thisObj.getState();
	return state["max" + featureName][engineId];
}

/**
 * Get minValue for featureName from simulator model
 */
function getFuelFeatureMinValue(featureName) {
	var state = thisObj.getState();
	return state["min" + featureName];
}

/**
 * Get lowValue for featureName from simulator model
 */
function getFuelFeatureLowValue(featureName) {
	var state = thisObj.getState();
	return state["low" + featureName];
}

/**
 * Get highValue for featureName from simulator model
 */
function getFuelFeatureHighValue(featureName) {
	var state = thisObj.getState();
	return state["high" + featureName];
}

/**
 * Get maxValue for featureName from simulator model
 */
function getFuelFeatureMaxValue(featureName) {
	var state = thisObj.getState();
	return state["max" + featureName];
}

/**
 * Based on input, create a gauge object
 */
function buildGauge(featureName, value, elementId, options) {
	var data = google.visualization.arrayToDataTable([ [ 'Label', 'Value' ],
			[ featureName, value ] ]);
	// console.log("building gauge for "+elementId);
	var gauge = new google.visualization.Gauge(document
			.getElementById(elementId));
	gauge.options = options;
	gauge.data = data;
	return gauge;
}

/**
 * Draw gauge for specified featureName for specified engine. To improve: maybe,
 * we could store google.visualization.Gauge and not recreate all the options
 * everytime, and just set the value. However, it didn't work for me due to some
 * reason
 * 
 * @param engineId
 * @param featureName
 */
function drawEngineGauge(engineId, featureName) {
	var state = thisObj.getState();
	//console.log("KUKU draw gauge called. is enabled" + state[featureName]
	//		+ "	 featurename" + featureName + " engineid" + engineId);
	if (isEngineFeatureEnabled(featureName, engineId)) {
		var maxVal = getEngineFeatureMaxValue(featureName, engineId);
		var lowVal = getEngineFeatureLowValue(featureName, engineId);
		var highVal = getEngineFeatureHighValue(featureName, engineId);
		var minVal = getEngineFeatureMinValue(featureName, engineId);
		var options = buildGaugeOptions(lowVal, highVal, highVal, maxVal,
				minVal, maxVal);
		var value = 0;
		if (isEngineFeatureAWComValueAvailable(featureName, engineId)) {
			// the numbers are usually quite big, so we can round them to
			// nearest int
			value = Math.round(state[featureName + "vals"][engineId]);
		} else {
			// if we don't have value from AWCom, set the value to "-2" - error
			// code indicating that there is no value from AWCom for this engine
			value = -2;
		}
		var gauge = buildGauge(featureName, value, 'engine' + engineId
				+ '-gauge-' + featureName);
		gauge.draw(gauge.data, options);
	}
}

/**
 * Draw gauge for specified featureName
 * 
 * @param featureName -
 *            fuel tank name: cfu, lfu, rfu
 */
function drawFuelTankGauge(featureName) {
	var state = thisObj.getState();
	//draw gauge only if it's enabled 
	if (isFuelFeatureEnabled(featureName)) {
		var maxVal = getFuelFeatureMaxValue(featureName);
		var lowVal = getFuelFeatureLowValue(featureName);
		var highVal = getFuelFeatureHighValue(featureName);
		var minVal = getFuelFeatureMinValue(featureName);

		var options = buildGaugeOptions(lowVal, highVal, minVal, lowVal,
				minVal, maxVal);
		var value = 0;

		if (isFuelFeatureAWComValueAvailable(featureName)) {
			value = Math.round(state[featureName + "vals"]);
		} else {
			// if we don't have value from AWCom, set the value to "-2" - error
			// code indicating that there is no value from AWCom for this fuel
			// tank
			value = -2;
		}
		var gauge = buildGauge(featureName, value, 'tank-gauge-' + featureName);
		gauge.draw(gauge.data, options);
	}
}

/**
 * Build div for gauge for specified feature name, for specified engineId. If
 * feature is not enabled on this engine, return empty string
 */
function getHtmlForEngineFeature(state, featureName, engineId) {
	//console.log("getHtmlForEngineFeature() - " + featureName + " engineId "
	//		+ engineId + " state:" + state[featureName][engineId]);
	if (state[featureName][engineId]) {
		return "<div id='engine" + engineId + "-gauge-" + featureName
				+ "' style='width: 120px; height: 120px; float: left'></div>";
	}
	return "";
}

/**
 * Build div for gauge for specified tanks. If tanks is not enabled, return
 * empty string
 */
function getHtmlForFuelTank(state, tankName) {
	//console.log("getHtmlForFuelTank() - " + tankName + " state:"
	//		+ state[tankName]);
	if (state[tankName]) {
		return "<div id='tank-gauge-" + tankName
				+ "' style='width: 120px; height: 120px; float: left'></div>";
	}
	return "";
}
