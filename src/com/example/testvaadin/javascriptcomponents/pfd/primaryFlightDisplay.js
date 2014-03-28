var sightRadius = 90;
var sightCenterRadius = 10;
var sightCenterCenterRadius = 1;

var canvasHeight = 300;
var canvasWidth = 320;

var vsCanW = 50;
var vsCanH = 340;

var compasCanvasWidth = 320;
var compasCanvasHeight = 40;

var backGrW=canvasWidth+vsCanW*2;
var backGrH=canvasHeight+compasCanvasHeight;


var compasRadius = 200;
var compassIndicatorWidth = 5;


var speedBarWidth = 40;
var speedBarHeight = 300;
var leftSpeedBarMargin = 0;
var topSpeedBarMargin = 0;
var speedIndicatorWidth = 5;

var altBarWidth = 40;
var altBarHeight = 300;
var altBarRightMargin = 0;
var altBarTopMargin = 0;

var currentSpeed = 0;
var currentAltitude = 0;
var currentPitch = 0;
var currentRoll = 0;
var currentCompass = 0;
var currentVerSpeed = 0;

var wantHaveSpeed = 0;
var wantHaveAltitude = 0;
var wantHavePitch = 0;
var wantHaveRoll = 0;
var wantHaveCompass = 0;
var wantHaveVerSpeed = 0;

var currentlyChangingRoll = false;
var currentlyChangingPitch = false;
var currentlyChangingAlt = false;
var currentlyChangingSpeed = false;
var currentlyChangingCompass = false;
var currentlyChangingVerSpeed = false;

var horizontWidth = 500;

var isItFirstLoad = true;

var currentlyRotatingHorizont = false;
var horizontHasBeenRotated = false;

var darkGray = '404040';

function com_example_testvaadin_javascriptcomponents_pfd_PrimaryFlightDisplay() {
	var e = this.getElement();
	window.currentSpeed = 0;
	window.currentAltitude = 0;
	window.currentPitch = 0;
	window.currentRoll = 0;
	window.currentYaw = 0;
	window.currentCompass = 0;
	window.currentVerSpeed = 0;
	initHtml(e);
	init();
	this.onStateChange = function() {
		console.log("got new data");
		window.wantHaveSpeed = metersPerSecondToKts(this.getState().s);
		window.wantHaveAltitude = metersToFeet(this.getState().a);
		window.wantHaveRoll = this.getState().r;
		window.wantHavePitch = this.getState().p;
		window.wantHaveHeading = this.getState().h;
		window.wantHaveVerSpeed = metersPerSecondToFeetPerMin(this.getState().vs);
		update();
	};
}

function initHtml(e) {
	console.log("init html");
	e.innerHTML = e.innerHTML+"<h1>PFD</h1>"
			+ "<div id='pfd_whole' style='width: 360px; height: 340px; padding-left: 40px; background-color: black; position:relative; margin-left: 40px;'>"
			//+ "<canvas id='background' width='400' height='340'>"
			//+ "</canvas>"
			+ "<canvas id='pfd' width='320' height='300'>"
			+ "Your browser doesn't support canvas."
			+ "</canvas>"
			+ "<canvas id='sight' width='320' height='300' style='position: absolute; margin-left: -320px'>"
			+ "</canvas>"
			+ "<canvas id='speed' width='320' height='300' style='position: absolute; margin-left: -320px'>"
			+ "</canvas>"
			+ "<canvas id='altitude' width='320' height='300' style='position: absolute; margin-left: -320px'>"
			+ "</canvas>"
			+ "<canvas id='compass' width='320' height='40' style='position: absolute; margin-top:300px;margin-left: -320px '>"
			+ "</canvas>"
			+ "<canvas id='verspeed' width='40' height='340' style='position:absolute; margin-left: 0px'>"
			+ "</canvas>" + "</div>"
			+ "<div style='margin-top: 50px; margin-left: 40px'>"
			+ "<label id='speedT'>Current speed:</label>"
			+ "<label id='speedV'></label><br/>"
			+ "<label id='altitudeT'>Current altitude:</label>"
			+ "<label id='altitudeV'></label><br/>"
			+ "<label id='rollT'>Roll:</label>"
			+ "<label id='rollV'></label><br/>"
			+ "<label id='pitchT'>Pitch:</label>"
			+ "<label id='pitchV'></label><br/>"
			+ "<label id='compassT'>Compass:</label>"
			+ "<label id='compassV'></label><br/>"
			+ "<button id='addPitch'>+10 pitch</button>"
			+ "<button id='minusPitch'>-10 pitch</button>"
			+ "<button id='addRoll'>+60 roll</button>"
			+ "<button id='minusRoll'>-60 roll</button>"
			+ "<button id='addAltitude'>+50 altitude</button>"
			+ "<button id='minusAltitude'>-50 altitude</button>"
			+ "<button id='addSpeed'>+50 speed</button>"
			+ "<button id='minusSpeed'>-50 speed</button>"
			+ "<button id='addCompass'>+180 compass</button>"
			+ "<button id='minusCompass'>-180 compass</button>" + "</div>";
}

	function metersPerSecondToKts(metersPerSecond) {
		return metersPerSecond * 1.94;
	}

	function metersToFeet(meters) {
		return meters * 3.2808;
	}

	function metersPerSecondToFeetPerMin(metersPerSecond) {
		return metersPerSecond * 196.850;
	}
	
	function init() {
		drawBackground();
		drawSpeedIndicator();
		drawHeightIndicator();
		drawSight();
		drawCompassIndicator();
		drawVerticalSpeed();
		setClickListeners();
	}




function update() {
	if (!window.currentlyChangingPitch) {
		setPitch();
	}
	if (!window.currentlyChangingRoll) {
		setRoll();
	}
	if (!window.currentlyChangingSpeed) {
		setSpeed();
	}
	if (!window.currentlyChangingAltitude) {
		setAltitude();
	}
	if (!window.currentlyChangingCompass) {
		setCompass();
	}
	if (!window.currentlyChangingVerSpeed) {
		setVerticalSpeed();
	} else {
		console.log("COMPASS NOT SET");
	}
}

function setClickListeners() {
	setAddPitchClickListener();
	setMinusPitchClickListener();
	setAddRollClickListener();
	setMinusRollClickListener();
	setAddSpeedClickListener();
	setMinusSpeedClickListener();
	setAddAltitudeClickListener();
	setMinusAltitudeClickListener();
	setAddCompassClickListener();
	setMinusCompassClickListener();
}

function drawBackground() {
	var ctx = document.getElementById('pfd').getContext('2d');
	ctx.fillStyle = 'black';
	fillRect(ctx,0,0,window.backGrW, window.backGrH);
	ctx.fill();
}

function setAddPitchClickListener() {
	document.getElementById('addPitch').addEventListener('click', function() {
		var newPitch = (window.currentPitch + 10) % 360;
		if (!window.currentlyChangingPitch) {
			setPitch(newPitch);
		}
	});
}

function setMinusPitchClickListener() {
	document.getElementById('minusPitch').addEventListener('click', function() {
		var newPitch = (window.currentPitch - 10) % 360;
		if (newPitch < 0) {
			newPitch = 360 + newPitch;
		}
		if (!window.currentlyChangingPitch) {
			setPitch(newPitch);
		}
	});
}

function setAddRollClickListener() {
	document.getElementById('addRoll').addEventListener('click', function() {
		if (!window.currentlyChangingRoll) {
			setRoll(window.currentRoll + 60);
		}
	});
}

function setMinusRollClickListener() {
	document.getElementById('minusRoll').addEventListener('click', function() {
		if (!window.currentlyChangingRoll) {
			setRoll(window.currentRoll - 60);
		}
	});
}

function setAddAltitudeClickListener() {
	document.getElementById('addAltitude').addEventListener('click',
			function() {
				if (!window.currentlyChangingAlt) {
					setAltitude(window.currentAltitude + 50);
				}
			});
}

function setMinusAltitudeClickListener() {
	document.getElementById('minusAltitude').addEventListener('click',
			function() {
				if (!window.currentlyChangingAlt) {
					setAltitude(window.currentAltitude - 50);
				}
			});
}

function setAddSpeedClickListener() {
	document.getElementById('addSpeed').addEventListener('click', function() {
		if (!window.currentlyChangingSpeed) {
			setSpeed(window.currentSpeed + 50);
		}
	});
}

function setMinusSpeedClickListener() {
	document.getElementById('minusSpeed').addEventListener('click', function() {
		if (!window.currentlyChangingSpeed) {
			setSpeed(window.currentSpeed - 50);
		}
	});
}

function setAddCompassClickListener() {
	document.getElementById('addCompass').addEventListener('click', function() {
		if (!window.currentlyChangingCompass) {
			setCompass(window.currentCompass + 180);
		}
	});
}

function setMinusCompassClickListener() {
	document.getElementById('minusCompass').addEventListener('click',
			function() {
				if (!window.currentlyChangingCompass) {
					setCompass(window.currentCompass - 180);
				}
			});
}

function clearRect(ctx, x, y, w, h) {
	ctx.clearRect(x, y, w, h);
}

function drawTestLine(ctx) {
	ctx.beginPath();
	ctx.lineWidth = 15;
	ctx.strokeStyle = 'red';
	ctx.moveTo(0, 0);
	ctx.lineTo(300, 300);
	ctx.stroke();
	ctx.restore();
}

function fillRect(ctx, x, y, w, h) {
	ctx.beginPath();
	ctx.fillRect(x, y, w, h);
	ctx.fill();
}

function fillSky(ctx, x, y, w, h) {
	ctx.fillStyle = '0080ff';
	fillRect(ctx, x, y, w, h);
}

function fillGround(ctx, x, y, w, h) {
	ctx.fillStyle = '804000';
	fillRect(ctx, x, y, w, h);
}


//Help function to calculate compass, pitch, roll step based on difference between current
//value and desired value.
function calculateCompassPitchRollStep(diff, step) {
	diff = diff % 360;
	var direction = 0;
	if ((diff > step) && (diff <= 180)) {
		direction = diff*step;
	} else if ((diff > step) && (diff > 180)) {
		direction = (diff - 360)*step;
	} else if ((diff < -step) && (diff <= -180)) {
		direction = (diff + 360)*step;
	} else if ((diff < -step) && (diff > -180)) {
		direction = diff*step;
	}
	return Math.round(direction*100)/100;
}


function calculateAltitudeSpeedStep(dif) {
	if ((dif>1)||(dif<1)) {
		return dif*0.1;
	} else {
		return 0;
	}
}

function shouldWeMakeAnimationStep(dif, step) {
	return ((dif > step) || (dif < -step));
}

function setPitch() {
	// Check if we should continue animating pitch
	var difPitch = (window.wantHavePitch - window.currentPitch) % 360;
	if (shouldWeMakeAnimationStep(difPitch, 0.3)) {
		requestAnimationFrame(function() {
			setPitch();
		});
		difPitch = calculateCompassPitchRollStep(difPitch, 0.3);
		window.currentlyChangingPitch = true;
	} else {
		window.currentlyChangingPitch = false;
	}

	var ctx = document.getElementById('pfd').getContext('2d');
	var newPitch = (window.currentPitch + difPitch) % 360;
	// Transform negative numbers to 0-359 coordinates
	if (newPitch < 0) {
		newPitch = 360 + newPitch;
	}
	// Draw lines and numbers for sight
	drawLineNumbersForSight(0, newPitch);

	// ctx.save();
	drawArtificialHorizon(ctx, window.currentRoll, newPitch, window.currentYaw);
	window.currentPitch = newPitch;
	// ctx.restore();
	document.getElementById('pitchV').innerHTML = window.currentPitch;
}

function setRoll() {
	var difRoll = (window.wantHaveRoll - window.currentRoll) % 360;
	// Check if we should continue animating roll
	var ctx = document.getElementById('pfd').getContext('2d');
	if (shouldWeMakeAnimationStep(difRoll, 0.5)) {
		requestAnimationFrame(function() {
			setRoll();
		});
		difRoll = calculateCompassPitchRollStep(difRoll, 0.5);
		window.currentlyChangingRoll = true;
	} else {
		window.currentlyChangingRoll = false;
	}
	rotateCanvasByRollDegrees(ctx, -difRoll);
	drawLineNumbersForSight(difRoll, window.currentPitch);
	var newRoll = (window.currentRoll + difRoll) % 360;
	window.currentRoll = newRoll;

	drawArtificialHorizon(ctx, window.currentRoll, window.currentPitch,
			window.currentYaw);
	document.getElementById('rollV').innerHTML = window.currentRoll;
}


function rotateCanvasByRollDegrees(ctx, roll) {
	ctx.translate(window.canvasHeight / 2, window.canvasWidth / 2);
	ctx.rotate(roll * Math.PI / 180);
	ctx.translate(-window.canvasHeight / 2, -window.canvasWidth / 2);
}

function drawArtificialHorizon(ctx, roll, pitch, yaw) {
	clearRect(ctx, 0, 0, window.window.canvasWidth, window.canvasHeight);
	drawSkyAndGround(ctx, pitch);
}

function drawSkyAndGround(ctx, newPitch) {
	// Calculating the X,Y,W,H for sky and ground
	if (newPitch >= 0) {
		var skyOnTop, grOnTop, skyBottom, grBottom, skyOnTopY, grOnTopY, skyBottomY, grBottomY;
		if (newPitch >= 180) {
			skyOnTop = newPitch * 5 - 180 * 5;
			grOnTop = 180 * 5;
			skyBottom = 360 * 5 - newPitch * 5;
			grBottom = 0;

			skyOnTopY = 0;
			grOnTopY = skyOnTop;
			skyBottomY = grOnTopY + grOnTop;
			grBottomY = skyBottomY + skyBottom;
		} else {
			skyOnTop = 0;
			grOnTop = newPitch * 5;
			skyBottom = 180 * 5;
			grBottom = 180 * 5 - newPitch * 5;

			skyOnTopY = 0;
			grOnTopY = 0;
			skyBottomY = grOnTopY + grOnTop;
			grBottomY = skyBottomY + skyBottom;
		}

		// Translate by
		ctx.translate(0, -150 * 5);
		var skyGroundLeftX = -(window.horizontWidth - window.window.canvasWidth) / 2;
		fillSky(ctx, skyGroundLeftX, skyOnTopY, window.horizontWidth, skyOnTop);
		fillGround(ctx, skyGroundLeftX, grOnTopY, window.horizontWidth, grOnTop);
		fillSky(ctx, skyGroundLeftX, skyBottomY, window.horizontWidth,
				skyBottom);
		fillGround(ctx, skyGroundLeftX, grBottomY, window.horizontWidth,
				grBottom);
		ctx.translate(0, 150 * 5);
	} else {
		console.error("Unexpected pitch value!");
	}
}


function drawLineNumberHelpFunc(ctxSight, startInt, endInt, difPitch, direction) {
	ctxSight.strokeStyle = 'white';
	ctxSight.fillStyle = 'white';
	for (var i = startInt; i <= endInt; i = i + 2.5) {
		if (((i % 5) == 0) && (i != startInt)) {
			ctxSight.fillText(i, window.window.canvasWidth / 2 - 45,
					window.canvasHeight / 2 + i * 5 * direction + 3 + difPitch
							* 5);
			ctxSight.moveTo(window.window.canvasWidth / 2 - 30,
					window.canvasHeight / 2 + i * 5 * direction + difPitch * 5);
			ctxSight.lineTo(window.window.canvasWidth / 2 + 30,
					window.canvasHeight / 2 + i * 5 * direction + difPitch * 5);
		} else if (((i % (2.5)) == 0) && (i != startInt)) {
			ctxSight.moveTo(window.window.canvasWidth / 2 - 15,
					window.canvasHeight / 2 + i * 5 * direction + difPitch * 5);
			ctxSight.lineTo(window.window.canvasWidth / 2 + 15,
					window.canvasHeight / 2 + i * 5 * direction + difPitch * 5);
		}
	}
}

function rotateSightFor180DigreesArountPoint(ctxSight, xRotationPoint,
		yRotationPoint) {
	window.currentlyRotatingHorizont = true;
	window.horizontHasBeenRotated = true;
	ctxSight.translate(xRotationPoint, yRotationPoint);
	ctxSight.rotate(180 * Math.PI / 180);
	ctxSight.translate(-xRotationPoint, -yRotationPoint);
}

// Transform 180-360 values to (-180;-0)
function transformPitchValue(pitch) {
	if (pitch > 180) {
		pitch = pitch - 360;
	}
	return pitch;
}

function drawLineNumbersForSight(difRoll, difPitch) {
	var transformedPitchValue = transformPitchValue(difPitch);
	var ctxSight = document.getElementById('sight').getContext('2d');
	drawSight();
	rotateCanvasByRollDegrees(ctxSight, -difRoll);
	// draw numbers for degrees -90 to +90
	drawLineNumberHelpFunc(ctxSight, 0, 90, transformedPitchValue, -1);
	drawLineNumberHelpFunc(ctxSight, 0, 90, transformedPitchValue, 1);
	ctxSight.stroke();
}

function drawSight() {
	var ctx = document.getElementById('sight').getContext('2d');
	ctx.clearRect(0, -100, window.horizontWidth, window.canvasHeight + 200);
	ctx.save();
	// Draw big outer circle
	ctx.strokeStyle = 'black';
	arc(ctx, window.canvasWidth / 2, window.canvasHeight / 2,
			window.sightRadius, 0, 2 * Math.PI);
	ctx.lineWidth = 3;
	// Draw smaller inner circle
	arc(ctx, window.canvasWidth / 2, window.canvasHeight / 2,
			window.sightCenterRadius, 0, 2 * Math.PI);
	// Draw point
	arc(ctx, window.window.canvasWidth / 2, window.canvasHeight / 2,
			window.sightCenterCenterRadius, 0, 2 * Math.PI);
	ctx.restore();
}

function drawVerticalSpeedLines(ctxVS, direction) {
	ctxVS.strokeStyle = 'white';
	ctxVS.fillStyle='white';
	ctxVS.moveTo(window.vsCanW * 0.1, window.vsCanH * 0.5);
	ctxVS.lineTo(window.vsCanW * 0.8, window.vsCanH * 0.5);
	var scale = 0.28 * window.vsCanH;
	var vsiYCenter = window.vsCanH * 0.50;
	for (var i = 1; i <= 3; i++) {
		// draw 0.5
		ctxVS.moveTo(window.vsCanW * 0.4, vsiYCenter + ((i - 0.5) / 3) * scale
				* direction);
		ctxVS.lineTo(window.vsCanW * 0.50, vsiYCenter + ((i - 0.5) / 3) * scale
				* direction);
		// draw 1
		ctxVS.fillText(i, window.vsCanW*0.2, vsiYCenter + (i / 3) * scale
				* direction+3);
		ctxVS.moveTo(window.vsCanW * 0.4, vsiYCenter + (i / 3) * scale
				* direction);
		ctxVS.lineTo(window.vsCanW * 0.55, vsiYCenter + (i / 3) * scale
				* direction);
	}
	ctxVS.stroke();
}

function drawVerticalSpeed() {
	var ctxVS = document.getElementById('verspeed').getContext('2d');
	ctxVS.beginPath();
	ctxVS.clearRect(0, 0, window.vsCanW, vsCanH);
	ctxVS.beginPath();
	ctxVS.strokeStyle = darkGray;
	ctxVS.fillStyle = darkGray;
	ctxVS.moveTo(window.vsCanW * 0.10, window.vsCanH * 0.2);
	ctxVS.lineTo(window.vsCanW * 0.10, window.vsCanH * 0.35);
	// ctxVS.lineTo(window.vsCanW*0.15, window.vsCanH*0.40);
	// ctxVS.lineTo(window.vsCanW*0.15, window.vsCanH*0.60);
	ctxVS.lineTo(window.vsCanW * 0.10, window.vsCanH * 0.65);
	ctxVS.lineTo(window.vsCanW * 0.10, window.vsCanH * 0.80);
	ctxVS.lineTo(window.vsCanW * 0.50, window.vsCanH * 0.80);
	ctxVS.lineTo(window.vsCanW * 0.80, window.vsCanH * 0.68);
	ctxVS.lineTo(window.vsCanW * 0.80, window.vsCanH * 0.32);
	ctxVS.lineTo(window.vsCanW * 0.80, window.vsCanH * 0.20);
	ctxVS.lineTo(window.vsCanW * 0.80, window.vsCanH * 0.20);
	ctxVS.stroke();
	ctxVS.fill();
	ctxVS.beginPath();
	drawVerticalSpeedLines(ctxVS, 1);
	drawVerticalSpeedLines(ctxVS, -1);
}

function calculateVsYPosition(verticalSpeed) {
	var verticalSpeedYPos = 0;
	var scale = (0.28 * window.vsCanH)/1000;
	if (verticalSpeed > 3000) {
		verticalSpeedYPos = window.vsCanH * 0.22;
	} else if (verticalSpeed < -3000) {
		verticalSpeedYPos = window.vsCanH * 0.78;
	} else {
		verticalSpeedYPos = window.vsCanH * 0.5-(verticalSpeed/3) * scale;
	}
	return verticalSpeedYPos;
}

function calculateVerSpeedStep(difVerSpeed) {
	return difVerSpeed*0.01;
}

function setVerticalSpeed() {
	var difVerSpeed = window.wantHaveVerSpeed - window.currentVerSpeed;
	var difVerSpeedStep = calculateVerSpeedStep(difVerSpeed);
    if (shouldWeMakeAnimationStep(difVerSpeed, 10)) {
    	window.currentlyChangingVerSpeed = true;
		requestAnimationFrame(function() {
			setVerticalSpeed();
		});
    } else {
    	window.currentlyChangingVerSpeed = false;
    }
	window.currentVerSpeed += difVerSpeedStep;
	var speedYPos = calculateVsYPosition(window.currentVerSpeed);
	var ctxVS = document.getElementById('verspeed').getContext('2d');
	drawVerticalSpeed();
	ctxVS.beginPath();
	ctxVS.strokeStyle = 'white';
	ctxVS.lineWidth = 2;
	ctxVS.moveTo(window.vsCanW, window.vsCanH/2);
	ctxVS.lineTo(window.vsCanW*0.5, speedYPos);
	ctxVS.font = '10pt Calibri';
	ctxVS.fillText(Math.round(window.currentVerSpeed), window.vsCanW*0.05, window.vsCanH*0.16);
	ctxVS.stroke();
}

function setSpeed() {
	var ctxSpeed = document.getElementById('speed').getContext('2d');
	var difSpeed = window.wantHaveSpeed - window.currentSpeed;
	var difSpeedStep = calculateAltitudeSpeedStep(difSpeed);
	if (shouldWeMakeAnimationStep(difSpeed, 1.0)) {
		window.currentlyChangingSpeed = true;
		requestAnimationFrame(function() {
			setSpeed();
		});
	} else {
		window.currentlyChangingSpeed = false;
	}
	window.currentSpeed+= difSpeedStep;;

	clearRect(ctxSpeed, window.leftSpeedBarMargin, window.topSpeedBarMargin,
			window.speedBarWidth, window.speedBarHeight);
	ctxSpeed.save();
	ctxSpeed.fillStyle = window.darkGray;
	fillRect(ctxSpeed, window.leftSpeedBarMargin, window.topSpeedBarMargin,
			window.speedBarWidth, window.speedBarHeight);

	// Speed marks and numbers
	ctxSpeed.translate(window.leftSpeedBarMargin, window.topSpeedBarMargin
			+ window.speedBarHeight);
	ctxSpeed.beginPath();
	ctxSpeed.lineWidth = 2;
	ctxSpeed.strokeStyle = 'white';
	ctxSpeed.fillStyle = 'white';
	ctxSpeed.font = '8pt Calibri';

	// draw lines with numbers
	for (var i = 0; i < 1000; i += 20) {
		ctxSpeed.moveTo(window.speedBarWidth - 10, -i * 2 - 3
				- window.speedBarHeight / 2 + window.currentSpeed * 2);
		ctxSpeed.lineTo(window.speedBarWidth, -i * 2 - 3
				- window.speedBarHeight / 2 + window.currentSpeed * 2);
		ctxSpeed.fillText(i, 0, -i * 2 - window.speedBarHeight / 2 + window.currentSpeed
				* 2);
		// draw lines in between the numbers
		ctxSpeed.moveTo(window.speedBarWidth - 5, -i * 2 - 3
				- window.speedBarHeight / 2 + window.currentSpeed * 2 - 20);
		ctxSpeed.lineTo(window.speedBarWidth, -i * 2 - 3
				- window.speedBarHeight / 2 + window.currentSpeed * 2 - 20);
	}
	ctxSpeed.stroke();
	ctxSpeed.restore();
	document.getElementById('speedV').innerHTML = window.currentSpeed;

}


function setAltitude() {
	// console.log('set alt called');
	var ctxAltitude = document.getElementById('altitude').getContext('2d');
	var difAltitude = window.wantHaveAltitude - window.currentAltitude;
	var difAltitudeStep = calculateAltitudeSpeedStep(difAltitude);
	if (shouldWeMakeAnimationStep(difAltitude, 1)) {
		requestAnimationFrame(function() {
			window.currentlyChangingAlt = true;
			setAltitude();
		});
	} else {
		window.currentlyChangingAlt = false;
	}

	newAltitude = window.currentAltitude + difAltitudeStep;
	window.currentAltitude = newAltitude;

	clearRect(ctxAltitude, window.window.canvasWidth - window.altBarRightMargin
			- window.altBarWidth, window.altBarTopMargin, window.altBarWidth,
			window.altBarHeight);
	ctxAltitude.save();
	ctxAltitude.fillStyle = window.darkGray;
	fillRect(ctxAltitude, window.window.canvasWidth - window.altBarRightMargin
			- window.altBarWidth, window.altBarTopMargin, window.altBarWidth,
			window.altBarHeight);

	// Speed marks and numbers
	ctxAltitude.translate(window.window.canvasWidth - window.altBarRightMargin
			- window.altBarWidth, window.altBarTopMargin + window.altBarHeight);
	ctxAltitude.beginPath();
	ctxAltitude.lineWidth = 2;
	ctxAltitude.strokeStyle = 'white';
	ctxAltitude.fillStyle = 'white';
	ctxAltitude.font = '8pt Calibri';

	// draw lines with numbers
	for (var i = 0; i < 50000; i += 200) {
		ctxAltitude.moveTo(0, -(i * 2)/10 - 3 - window.altBarHeight / 2
				+ (newAltitude * 2)/10);
		ctxAltitude.lineTo(10, -(i * 2)/10 - 3 - window.altBarHeight / 2
				+ (newAltitude * 2)/10);
		ctxAltitude.fillText(i, 10, -(i * 2)/10 - window.altBarHeight / 2
				+ (newAltitude * 2)/10);
		// draw lines in between the numbers
		ctxAltitude.moveTo(0, -(i * 2)/10 - 3 - window.altBarHeight / 2
				+ (newAltitude * 2)/10 - 20);
		ctxAltitude.lineTo(5, -(i * 2)/10 - 3 - window.altBarHeight / 2
				+ (newAltitude * 2)/10 - 20);
	}
	ctxAltitude.stroke();
	ctxAltitude.restore();
	document.getElementById('altitudeV').innerHTML = window.currentAltitude;
}

// Help function for rotating compass
function rotateCompassCanvasByDegrees(compassCanvas, degrees) {
	compassCanvas.translate(window.compasCanvasWidth / 2,
			window.compasCanvasHeight + 170);
	compassCanvas.rotate(degrees * Math.PI / 180);
	compassCanvas.translate(-window.compasCanvasWidth / 2,
			-window.compasCanvasHeight - 170);
}


// Sets compass value in small iterations. Animates it also with
// requestAnimationFrame
function setCompassValue(ctxCompass, compass) {
	var difCompass = (compass - window.currentCompass)%360;
	var compassStep = calculateCompassPitchRollStep(difCompass, 0.05);
	if ((difCompass > 0.005) || (difCompass < -0.005)) {
		requestAnimationFrame(function() {
			window.currentlyChangingCompass = true;
			// window.currentCompass = window.currentCompass % 360;
			setCompass();
		});
	} else {
		window.currentlyChangingCompass = false;
	}
	window.currentCompass = Math.round((window.currentCompass+compassStep)%360*100.00)/100.00;
	if (window.currentCompass<0) {
		window.currentCompass+=360;
	}
	//console.log("setting compass value"+compassStep);
	rotateCompassCanvasByDegrees(ctxCompass, -compassStep);
	document.getElementById('compassV').innerHTML = window.currentCompass;
}

function setCompass() {
	var ctxCompass = document.getElementById('compass').getContext('2d');
	setCompassValue(ctxCompass, window.wantHaveHeading);
	drawCompass();
}

function drawCompass() {
		var ctxCompass = document.getElementById('compass').getContext('2d');
		clearRect(ctxCompass, 0, 0, ctxCompass.width, ctxCompass.height);
		ctxCompass.strokeStyle = window.darkGray;
		ctxCompass.fillStyle = window.darkGray;
		arc(ctxCompass, window.compasCanvasWidth / 2,
				window.compasCanvasHeight + 170, window.compasRadius, 0,
				2 * Math.PI);
		ctxCompass.fill();
		ctxCompass.fillStyle = 'white';
		ctxCompass.font = '33 pt Calibri';
		for (var i = 0; i < 36; i++) {
			ctxCompass.fillText(i, window.compasCanvasWidth / 2,
					window.compasCanvasHeight - 20);
			rotateCompassCanvasByDegrees(ctxCompass, 10);
		}
}

// Draws this little triangle which points to current speed
function drawSpeedIndicator() {
	// Draw speed indicator
	var ctxSpeed = document.getElementById('speed').getContext('2d');
	ctxSpeed.strokeStyle = 'black';
	ctxSpeed.fillStyle = 'black';
	// setting vertices coordinates of a triangle x1,y1,x2,y2,x3,y3
	var coords = [
			window.speedBarWidth + window.leftSpeedBarMargin,
			window.canvasHeight / 2 - 3,
			window.speedBarWidth + window.leftSpeedBarMargin
					+ window.speedIndicatorWidth,
			window.canvasHeight / 2 - 8,
			window.speedBarWidth + window.leftSpeedBarMargin
					+ window.speedIndicatorWidth, window.canvasHeight / 2 ];
	drawPolygone(ctxSpeed, coords);
}

// Draws this little triangle which points to current altitude
function drawHeightIndicator() {
	// Draw speed indicator
	var ctxAlt = document.getElementById('altitude').getContext('2d');
	ctxAlt.strokeStyle = 'black';
	ctxAlt.fillStyle = 'black';
	// setting vertices coordinates of a triangle x1,y1,x2,y2,x3,y3
	var coords = [
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin,
			window.canvasHeight / 2 - 3,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					- window.speedIndicatorWidth,
			window.canvasHeight / 2 - 8,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					- window.speedIndicatorWidth, window.canvasHeight / 2 ];
	drawPolygone(ctxAlt, coords);
}

// Draws this little triangle which points to current compass
function drawCompassIndicator() {
	var compassCtx = document.getElementById('compass').getContext('2d');
	compassCtx.storkeStyle = 'white';
	compassCtx.fillStyle = 'white';
	// setting vertices coordinates of a triangle x1,y1,x2,y2,x3,y3
	var coords = [ window.compasCanvasWidth / 2 + 3, 10,
			window.compasCanvasWidth / 2 - window.compassIndicatorWidth + 3,
			5 - window.compassIndicatorWidth,
			window.compasCanvasWidth / 2 + window.compassIndicatorWidth + 3,
			5 - window.compassIndicatorWidth ];
	drawPolygone(compassCtx, coords);
}

// coords - [x1,y1,x2,y2,x3,y3...]
function drawPolygone(ctx, poly) {
	ctx.beginPath();
	ctx.moveTo(poly[0], poly[1]);
	for (var item = 2; item < poly.length - 1; item += 2) {
		ctx.lineTo(poly[item], poly[item + 1]);
	}
	ctx.closePath();
	ctx.fill();
}

function fillArc(ctx, x, y, r, startAngle, finishAngle) {
	arc(ctx, x, y, r, startAngle, finishAngle);
	ctx.fill();
}

// Help function for drawing arc
function arc(ctx, x, y, r, startAngle, finishAngle) {
	ctx.beginPath();
	ctx.arc(x, y, r, startAngle, finishAngle);
	ctx.stroke();
};

