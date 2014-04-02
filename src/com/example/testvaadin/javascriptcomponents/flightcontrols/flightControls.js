/* Important:
 * This file uses functions and state data from primaryFlightDisplay.js. So this component cannot be used alone, without PrimaryFlightDisplay.
 * PFD component should be included to the view if we want to use FlightControls component.
 */

/*Plane configuration*/
var maxSpeedOnFlaps = -1; //default value. This data is get from database 

var currentRudder = 0;
var currentAileron = 0;
var currentElevator = 0;
var currentFlaps = 0;
var currentSpeedBrakes = 0;

var yokeWidth = 120;
var yokeHeight = 120;

var yokeBackWidth = 130;
var yokeBackHeight = 130;

var rudderWidth = 120;
var rudderHeight = 20;

var rudderBackWidth = 130;
var rudderBackHeight = 32;

var speedBrakesWidth = 130;
var speedBrakesHeight = 130;

var flapsWidth = 130;
var flapsHeight = 130;

var wantHaveRudder = 0;
var wantHaveAileron = 0;
var wantHaveElevator = 0;
var wantHaveFlaps = 0;
var wantHaveSpeedBrakes = 0;

var currentlyChangingRudder = false;
var currentlyChangingAileron = false;
var currentlyChangingElevator = false;
var currentlyChangingSpeedBrakes = false;
var currentlyChangingFlaps = false;

var xOffset = (window.yokeBackWidth-window.yokeWidth)/2;
var yOffset = (window.yokeBackHeight-window.yokeHeight)/2;

var xOffsetR = (window.rudderBackWidth-window.rudderWidth)/2;
var yOffsetR = (window.rudderBackHeight-window.rudderHeight)/2;


function com_example_testvaadin_javascriptcomponents_flightcontrols_FlightControls() {
	var e = this.getElement();
	console.log("INIT CONTROL YOKE");
	initYokeHtml(e);
	initYoke();
	this.onStateChange = function() {
		window.wantHaveRudder = this.getState().rd;
		window.wantHaveAileron = this.getState().ail;
		window.wantHaveElevator = this.getState().el;
		window.wantHaveFlaps = this.getState().fl;
		window.wantHaveSpeedBrakes = this.getState().sb;
		updateFlightControls();
		window.maxSpeedOnFlaps = this.getState().maxonflaps;
	};
}

function initYokeHtml(e) {
	e.innerHTML = e.innerHTML
			+ "<h3>Control Yoke</h3>"
			+ "<canvas id='controlYokeB' width='130' height='130' style='margin-bottom: 50px; margin-left:40px; background-color: black;'>"
			+ "Your browser doesn't support canvas." + "</canvas>"
			+ "<canvas id='controlRudderB' width='130' height='32' style='margin-left: -130px; background-color: black'></canvas>"
			+ "<h3 id='speedBrakesV'>Speed brakes</h3>"
			+ "<canvas id='speedBrakes' width='130' height='130' style='margin-left: 40px;'></canvas>"
			+ "<h3 id='flapsV'>Flaps</h3>"
			+ "<canvas id='flaps' width='130' height='130' style='margin-left: 40px;'></canvas>"
			+ "<div style='margin-top: 10px; margin-left: 40px'>";
}

function updateFlightControls() {
	if (!window.currentlyChangingRudder) {
		setRudder();
	}
	if (!window.currentlyChangingElevator) {
		setElevator();
	}
	if (!window.currentlyChangingAileron) {
		setAileron();
	}
	if (!window.currentlyChangingSpeedBrakes) {
		setSpeedBrakes();
	}
	if (!window.currentlyChangingFlaps) {
		setFlaps();
	}	
}

//Function for calculating aileron, rudder, elevetor, speed brakes, flaps animation step
function calculateFlightControlsAnimStep(dif, step) {
	if ((dif>step)||(dif<-step)) {
		return dif*step;
	} else {
		return 0;
	}
}

function setRudder() {
	var dif = window.wantHaveRudder-window.currentRudder;
	if (shouldWeMakeAnimationStep(dif, 0.05)) {
		requestAnimationFrame(function() {
			setRudder();
		});
		dif = calculateFlightControlsAnimStep(dif, 0.05);
		window.currentlyChangingRudder = true;
	} else {
		window.currentlyChangingRudder= false;
	}
	window.currentRudder = window.currentRudder+dif;
	drawAilRudElev();
}



function setElevator() {
	var dif = window.wantHaveElevator-window.currentElevator;
	//check if we should continue animating
	if (shouldWeMakeAnimationStep(dif, 0.05)) {
		requestAnimationFrame(function() {
			setElevator();
		});
		dif = calculateFlightControlsAnimStep(dif, 0.05);
		window.currentlyChangingElevator = true;
	} else {
		window.currentlyChangingElevator = false;
	}
	window.currentElevator = window.currentElevator+dif;
	drawAilRudElev();
}

function setAileron() {
	var dif = window.wantHaveAileron-window.currentAileron;
	//check if we should continue animating
	if (shouldWeMakeAnimationStep(dif, 0.05)) {
		requestAnimationFrame(function() {
			setAileron();
		});
		dif = calculateFlightControlsAnimStep(dif, 0.05);
		window.currentlyChangingAileron = true;
	} else {
		window.currentlyChangingAileron = false;
	}
	window.currentAileron+=dif;
	drawAilRudElev();
}

function drawAilRudElev() {
	var ctx = document.getElementById('controlYokeB').getContext('2d');
	clearRect(ctx, 0, 0, window.yokeBackWidth, window.yokeBackHeight);
	drawYoke();
	ctx.fillStyle = "red";
	ctx.strokeStyle = "red";
	/*Draw aileron*/
	var y = window.yokeHeight/2+yOffset;
	var x = window.yokeWidth/2+window.currentAileron*(window.yokeWidth/2)+xOffset;
	fillArc(ctx, x, y, 5, 0, 2 * Math.PI);
	/*Draw elevator*/
	x = window.yokeWidth/2+xOffset;
	y = window.yokeHeight/2+window.currentElevator*(window.yokeHeight/2)+yOffset;
	fillArc(ctx, x, y, 5, 0, 2 * Math.PI);
	/*Draw rudder*/
	drawRudderInd();
}

function drawRudderInd() {
	var ctxRud = document.getElementById('controlRudderB').getContext('2d');
	clearRect(ctxRud, 0, 0, window.rudderBackWidth, window.rudderBackHeight);
	drawRudder();
	ctxRud.fillStyle = "red";
	ctxRud.strokeStyle = "red";
	/*Draw rudder*/
	var y = window.rudderHeight/2+window.yOffsetR;
	var x = window.rudderWidth/2+window.currentRudder*(window.rudderWidth/2)+window.xOffsetR;
	fillArc(ctxRud, x, y, 5, 0, 2 * Math.PI);
}

function setSpeedBrakes() {
	var dif = window.wantHaveSpeedBrakes-window.currentSpeedBrakes;
	//check if we should continue animating
	console.log("SET SPEED BRAKES"+dif);
	if (shouldWeMakeAnimationStep(dif, 0.05)) {
		requestAnimationFrame(function() {
			setSpeedBrakes();
		});
		dif = calculateFlightControlsAnimStep(dif, 0.05);
		window.currentlyChangingSpeedBrakes = true;
	} else {
		window.currentlyChangingSpeedBrakes = false;
	}
	window.currentSpeedBrakes=Math.round((window.currentSpeedBrakes+dif)*1000)/1000;
	document.getElementById('speedBrakesV').innerHTML = "Speed brakes "+window.currentSpeedBrakes;
	drawSpeedBrakesInd();
}

function setFlaps() {
	var dif = window.wantHaveFlaps-window.currentFlaps;
	//check if we should continue animating
	console.log("SET FLAPS"+dif);
	if (shouldWeMakeAnimationStep(dif, 0.05)) {
		requestAnimationFrame(function() {
			setFlaps();
		});
		dif = calculateFlightControlsAnimStep(dif, 0.05);
		window.currentlyChangingFlaps = true;
	} else {
		window.currentlyChangingFlaps = false;
	}
	window.currentFlaps=Math.round((window.currentFlaps+dif)*1000)/1000;
	document.getElementById('flapsV').innerHTML = "Flaps "+window.currentFlaps;
	drawFlapsInd();
}

function initYoke() {
	drawYoke();
	drawRudder();
	drawSpeedBrakes();
	drawFlaps();
}

function shouldMakeYokeAnimStep(dif, step) {
	return ((dif>step)||(dif<-step));
}

function drawYoke() {
	var ctx = document.getElementById('controlYokeB').getContext('2d');
	ctx.beginPath();
	ctx.fillStyle = "white";
	// fill rect, clear rect, arc functions is in primaryFlightDisplay.js file
	clearRect(ctx, 0, 0, ctx.width, ctx.height);
	fillRect(ctx, (window.yokeWidth / 2) - 4+window.xOffset, window.yOffset, 8, window.yokeHeight);
	fillRect(ctx, window.xOffset, (window.yokeHeight / 2) - 4+window.yOffset, window.yokeWidth, 8);
}

function drawRudder() {
	var ctx = document.getElementById('controlRudderB').getContext('2d');
	ctx.beginPath();
	ctx.fillStyle = "white";
	// fill rect, clear rect, arc functions are in primaryFlightDisplay.js file
	clearRect(ctx, 0, 0, ctx.width, ctx.height);
	fillRect(ctx, xOffsetR, (window.rudderHeight / 2) - 4+yOffsetR, window.rudderWidth, 8);
}

function drawSpeedBrakesInd() {
	var ctx = document.getElementById('speedBrakes').getContext('2d');
	clearRect(ctx, 0, 0, window.speedBrakesWidth, window.speedBrakesHeight);
	drawSpeedBrakes();
	ctx.fillStyle = "black";
	ctx.strokeStyle = "black";
	ctx.lineWidth=4;
	//Transfer speed brakes value 0-1 to 0-45 degrees. 
	var speedBrakesInDeg = 45*window.currentSpeedBrakes;
	/*Draw speed brakes*/
	var y = window.speedBrakesWidth*Math.sin((Math.PI / 180)*speedBrakesInDeg);
	var x =  window.speedBrakesWidth*Math.cos((Math.PI / 180)*speedBrakesInDeg);
	ctx.moveTo(0,0);
	ctx.lineTo(x,y);
	ctx.stroke();
}

function drawFlapsInd() {
	var ctx = document.getElementById('flaps').getContext('2d');
	clearRect(ctx, 0, 0, window.flapsWidth, window.flapsHeight);
	drawFlaps();
	ctx.strokeStyle = "black";
	if ((window.currentSpeed>window.maxSpeedOnFlaps)&&(window.currentFlaps>0.1)) {
		ctx.strokeStyle="red";
		console.log("set flaps color red");
	}
	console.log(window.currentSpeed>window.maxSpeedOnFlaps);
	ctx.lineWidth=4;
	/*Draw speed brakes*/
	var y = window.flapsWidth*Math.sin((Math.PI / 180)*window.currentFlaps);
	var x =  window.flapsWidth*Math.cos((Math.PI / 180)*window.currentFlaps);
	ctx.moveTo(0,0);
	ctx.lineTo(x,y);
	ctx.stroke();
}

function drawSpeedBrakes() {
	var ctx = document.getElementById('speedBrakes').getContext('2d');
	ctx.beginPath();
	ctx.fillStyle = "black";
	// fill rect, clear rect functions are in primaryFlightDisplay.js file
	clearRect(ctx, 0, 0, window.speedBrakesWidth, window.speedBrakesHeight);
	fillRect(ctx, 0, 0, window.speedBrakesWidth, 2);
}

function drawFlaps() {
	var ctx = document.getElementById('flaps').getContext('2d');
	ctx.beginPath();
	ctx.fillStyle = "black";
	// fill rect, clear rect functions are in primaryFlightDisplay.js file
	clearRect(ctx, 0, 0, window.speedBrakesWidth, window.speedBrakesHeight);
	fillRect(ctx, 0, 0, window.speedBrakesWidth, 2);
}
