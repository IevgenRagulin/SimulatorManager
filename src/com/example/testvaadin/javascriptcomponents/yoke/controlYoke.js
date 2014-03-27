var currentRudder = 0;
var currentAileron = 0;
var currentElevator = 0;

var yokeWidth = 120;
var yokeHeight = 120;

var yokeBackWidth = 130;
var yokeBackHeight = 130;

var wantHaveRudder = 0;
var wantHaveAileron = 0;
var wantHaveElevator = 0;

var currentlyChangingRudder = false;
var currentlyChangingAileron = false;
var currentlyChangingElevator = false;

var xOffset = (window.yokeBackWidth-window.yokeWidth)/2;
var yOffset = (window.yokeBackHeight-window.yokeHeight)/2;

function com_example_testvaadin_javascriptcomponents_yoke_ControlYoke() {
	var e = this.getElement();
	window.currentRudder = 0;
	window.currentAileron = 0;
	window.currentElevator = 0;
	console.log("INIT CONTROL YOKE");
	initYokeHtml(e);
	initYoke();
	this.onStateChange = function() {
		window.wantHaveRudder = this.getState().rudder;
		window.wantHaveAileron = this.getState().aileron;
		window.wantHaveElevator = this.getState().elevator;
		console.log("NEW ELEVATOR" + window.wantHaveElevator);
		console.log("NEW AILERON" + window.wantHaveAileron);
		console.log("NEW RUDDER" + window.wantHaveRudder);
		updateYoke();
	};
}

function initYokeHtml(e) {
	e.innerHTML = e.innerHTML
			+ "<h1>Control Yoke</h1>"
			+ "<canvas id='controlYokeB' width='130' height='130' style='margin-left:40px; background-color: black;'>"
			+ "Your browser doesn't support canvas." + "</canvas>"
			+ "<canvas id='controlYoke' width='130' height='130' style='margin-left:-130px;'></canvas>"
			+ "<div style='margin-top: 50px; margin-left: 40px'>"
			+ "<label id='rudderV'>Current rudder:</label>"
			+ "<label id='aileronV'Current aileron:></label><br/>"
			+ "<label id='elevatorV'>Current elevator:</label></div>";
}

function updateYoke() {
	if (!window.currentlyChangingRudder) {
		setRudder();
	}
	if (!window.currentlyChangingElevator) {
		setElevator();
	}
	if (!window.currentlyChangingAileron) {
		setAileron();
	}
}

function setRudder() {
	console.log("SETTING RUDDER");
	var ctx = document.getElementById('controlYoke').getContext('2d');
}

function calculateElRudAilAnimStem(dif, step) {
	if ((dif>step)||(dif<-step)) {
		return dif*step;
	} else {
		return 0;
	}
}

function setElevator() {
	var dif = window.wantHaveElevator-window.currentElevator;
	if (shouldWeMakeAnimationStep(dif, 0.1)) {
		requestAnimationFrame(function() {
			setElevator();
		});
		dif = calculateElRudAilAnimStem(dif, 0.1);
		window.currentlyChangingElevator = true;
	} else {
		window.currentlyChangingElevator = false;
	}
	window.currentElevator = window.currentElevator+dif;
	drawAilRudElev();
}

function setAileron() {
	var dif = window.wantHaveAileron-window.currentAileron;
	if (shouldWeMakeAnimationStep(dif, 0.1)) {
		requestAnimationFrame(function() {
			setAileron();
		});
		dif = calculateElRudAilAnimStem(dif, 0.1);
		window.currentlyChangingAileron = true;
	} else {
		window.currentlyChangingAileron = false;
	}
	window.currentAileron+=dif;
	drawAilRudElev();
}

function drawAilRudElev() {
	var ctx = document.getElementById('controlYoke').getContext('2d');
	clearRect(ctx, 0, 0, window.yokeBackWidth, window.yokeBackHeight);
	ctx.fillStyle = "red";
	/*Draw aileron*/
	var y = window.yokeHeight/2+yOffset;
	var x = window.yokeWidth/2+window.currentAileron*(window.yokeWidth/2)+xOffset;
	fillArc(ctx, x, y, 5, 0, 2 * Math.PI);
	/*Draw elevator*/
	x = window.yokeWidth/2+xOffset;
	y = window.yokeHeight/2+window.currentElevator*(window.yokeHeight/2)+yOffset;
	fillArc(ctx, x, y, 5, 0, 2 * Math.PI);
}

function initYoke() {
	drawYoke();
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
	fillRect(ctx, (window.yokeWidth / 2) - 4+xOffset, yOffset, 8, window.yokeHeight);
	fillRect(ctx, xOffset, (window.yokeHeight / 2) - 4+yOffset, window.yokeWidth, 8);
}
