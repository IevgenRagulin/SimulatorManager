var isHtmlInitialized = false;
var engineFeatures = [ "rpm", "pwr", "pwp", "mp_", "egt1", "egt2", "cht1",
		"cht2", "est", "ff_", "fp_", "op_", "ot_", "n1_", "n2_", "vib", "vlt",
		"amp" ];
//if specific engineFeatures are enabled or not. On every update we check if there changes in a list of enabled engine features. If this list has changed, we reinitialize the html
var engineFeaturesState = new Object();
var thisObj;
//attach the .equals method to Array's prototype to call it on any array - we need this to be able to compare arrays
Array.prototype.equals = function (array) {
    // if the other array is a falsy value, return
    if (!array)
        return false;

    // compare lengths - can save a lot of time 
    if (this.length != array.length)
        return false;

    for (var i = 0, l=this.length; i < l; i++) {
        // Check if we have nested arrays
        if (this[i] instanceof Array && array[i] instanceof Array) {
            // recurse into the nested arrays
            if (!this[i].equals(array[i]))
                return false;       
        }           
        else if (this[i] != array[i]) { 
            // Warning - two different object instances will never be equal: {x:20} != {x:20}
            return false;   
        }           
    }       
    return true;
} 

function cz_vutbr_fit_simulatormanager_jscomponents_enginespanel_EnginesPanel() {
	window.thisObj = this;
	console.log("Creating engines panel");
	//when document 
	// we load script in such a tricky way because there is an error if we try
	// to do it normally through @Javascript annotation in EnginesPanel.java
	// as soon as script is loaded, loadVisualizationLibrary
	loadScript("https://www.google.com/jsapi", loadGoogleVisualizationLibrary);
	console.log("this.getElement 2" + this.getElement().innerHTML);
	var e = this.getElement();
}

function loadGoogleVisualizationLibrary() {
	//after google library is loaded, we init engines panel
	google.load("visualization", "1", {
		packages : [ "gauge" ],
		"callback" : initEnginesPanel
	});
};


function initEnginesPanel() {
	var e = thisObj.getElement();
	console.log("Going to init engines panel. Rpm: "+thisObj.getState().rpm);
	if (!(typeof thisObj.getState().rpm === 'undefined')) {
		initEngineHtmlAndGauges(e, thisObj.getState());
	}
	thisObj.onStateChange = function() {
		//if some of the features state has been updated, reinit html
		//if html hasn't yet been initialized, do it
		if ((!window.isHtmlInitialized)||(hasStateOfSomeFeatureChanged(thisObj.getState()))) {
			console.log("Going to init engines panel. Rpm: "+thisObj.getState().rpm);
			initEngineHtmlAndGauges(e, thisObj.getState());
		}
		if (isHtmlInitialized) {
			for (var i=0; i<engineFeatures.length; i++) {
				//we call drawGauge through timeout in order to do this asynchronyously to improve performance, not sure if it really improves performance
				setTimeout(function(){
					drawGauge(thisObj.getState(), engineFeatures[i]);
				}, 0);
			}
		}
	};
	
}

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

function hasStateOfSomeFeatureChanged(state) {
	for (var i=0; i<engineFeatures.length; i++) {
		if (!arraysIdentical(engineFeaturesState[engineFeatures[i]], state[engineFeatures[i]])) {
			console.log("state of some feature has changed YES. prev: "+engineFeaturesState[engineFeatures[i]]+" new: "+state[engineFeatures[i]]);
			return true;
		}
	}
	console.log("state of some feature has changed NO");
	return false;
}

function arraysIdentical(a, b) {
    var i = a.length;
    if (i != b.length) return false;
    while (i--) {
        if (a[i] !== b[i]) return false;
    }
    return true;
};

function initEngineHtmlAndGauges(e, state) {
	console.log("going to init engine html and gauge");
	e.innerHTML = "";
	for (var i=0; i<engineFeatures.length; i++) {
		var newFeatureState = state[engineFeatures[i]];
		engineFeaturesState[engineFeatures[i]]=newFeatureState;//update features state
		getHtmlAndInitGaugeForFeature(e, state, engineFeatures[i]);
	}
	window.isHtmlInitialized = true;
}

function drawGauge(state, featureName) {
	if ((!(typeof state[featureName] === 'undefined'))
			&& (state[featureName][0])) {
		var maxVal = state["max" + featureName][0];
		var minVal = state["min" + featureName][0];
		var range = maxVal - minVal;
		
		var redStart = maxVal - range*0.1;
		var redEnd = maxVal;
		var yellowStart = minVal;
		var yellowEnd = minVal + range*0.1;
		console.log("red start feature"+featureName+" "+redStart);
		var options = {
			width : 120,
			height : 120,
			redFrom : redStart,
			redTo : redEnd,
			yellowFrom : yellowStart,
			yellowTo : yellowEnd,
			minorTicks : 5,
			max : maxVal,
			min : minVal
		};
		var value = 0;
		if (!(typeof state[featureName + "vals"] === 'undefined')) {
			value = state[featureName + "vals"][0];
		} else {
			value = state["min" + featureName][0];
		}
		var data = google.visualization.arrayToDataTable([
				[ 'Label', 'Value' ], [ featureName, value ] ]); // name
		var gauge = new google.visualization.Gauge(document
				.getElementById('engine-gauge-' + featureName));
		gauge.options = options;
		gauge.data = data;
		//console.log("going to draw gauge for feature name"+featureName+" options.min"+options.min+"options.max"+options.max+" value"+value);
		gauge.draw(data, options);
	}
}

function getHtmlAndInitGaugeForFeature(e, state, featureName) {
	console.log("feature "+featureName+" is enabled"+state[featureName][0]);
	if (state[featureName][0]) {
		e.innerHTML = e.innerHTML + "<div id='engine-gauge-" + featureName
				+ "' style='width: 120px; height: 120px; float: left'></div>";
		drawGauge(state, featureName);
	}
}
