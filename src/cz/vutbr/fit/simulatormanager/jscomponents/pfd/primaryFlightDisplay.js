var sightRadius = 90;
var sightCenterRadius = 10;
var sightCenterCenterRadius = 1;

var canvasHeight = 228.8;
var canvasWidth = 240;

var sightHeight = 208.8;
var sightWidth = 240;

var vsCanW = 50;// vertical speed width
var vsCanH = 340;// vertical speed height

var compasCanvasWidth = 240;
var compasCanvasHeight = 40;
var compasTopMargin = 170;

var backGrW = canvasWidth + vsCanW * 2;
var backGrH = canvasHeight + compasCanvasHeight;

var compasRadius = 200;
var compassIndicatorWidth = 5;

var speedBarWidth = 40;
var speedBarHeight = 260;
var leftSpeedBarMargin = 2;
var topSpeedBarMargin = 0;
var speedIndicatorWidth = 5;

var altBarWidth = 40;// altitude bar width
var altBarHeight = 260;// altitude bar height
var altBarRightMargin = 0;
var altBarTopMargin = 0;

// current values on the canvas, animation increases/decreases these values step
// by step to get to wantHaveValues
var currentSpeed = 0;
var currentAltitude = 0;
var currentPitch = 0;
var currentRoll = 0;
var currentCompass = 0;
var currentVerSpeed = 0;

// latest values from AWCom
var wantHaveSpeed = 0;
var wantHaveAltitude = 0;
var wantHavePitch = 0;
var wantHaveRoll = 0;
var wantHaveCompass = 0;
var wantHaveVerSpeed = 0;

var currentlyChangingRollOrPitch = false;
var currentlyChangingAlt = false;
var currentlyChangingSpeed = false;
var currentlyChangingCompass = false;
var currentlyChangingVerSpeed = false;

var horizontWidth = 500;

var isItFirstLoad = true;

var currentlyRotatingHorizont = false;
var horizontHasBeenRotated = false;

var darkGray = '#404040';
var pfdCan;
var pfdCtx;
var initialized = false;
function cz_vutbr_fit_simulatormanager_jscomponents_pfd_PrimaryFlightDisplay() {
	// window.currentPitchShape.x=0;
	var e = this.getElement();
	resetPfd();
	initHtml(e);
	window.pfdCan = document.getElementById('pfd');
	window.pfdCtx = window.pfdCan.getContext('2d');
	init();
	update();
	this.onStateChange = function() {
		window.wantHaveSpeed = this.getState().s;
		window.wantHaveAltitude = this.getState().a;
		window.wantHaveRoll = this.getState().r;
		window.wantHavePitch = this.getState().p;
		window.wantHaveHeading = this.getState().h;
		window.wantHaveVerSpeed = this.getState().vs;
	};
	TweenLite.ticker.addEventListener("tick", update); // refresh all elements
	window.initialized = true;
}


// Set reset currentlyChangingValue, currentValue global variables to prevent
// bug when we switch between simulators
function resetPfd() {
	window.currentCompass = 0;
	window.currentAltitude = 0;
	window.currentSpeed = 0;
	window.currentRoll = 0;
	window.currentPitch = 0;
	window.currentVerSpeed = 0;
	window.currentlyChangingRollOrPitch = false;
	window.currentlyChangingAlt = false;
	window.currentlyChangingSpeed = false;
	window.currentlyChangingCompass = false;
	window.currentlyChangingVerSpeed = false;
}

function initHtml(e) {
	console.log("init htmll");
	e.innerHTML = e.innerHTML
			+ "<div id='pfd_whole' style='width: 410px; height: 340px; padding-left: 20px; background-color: black; position:relative;'>"
			// + "<canvas id='background' width='400' height='340'>"
			// + "</canvas>"
			+ "<canvas id='pfd' width='240' height='228.8' style='margin-top: 55.6px; margin-left: 50px; position: absolute; border-radius: 15px;'>"
			+ "Your browser doesn't support canvas."
			+ "</canvas>"
			+ "<canvas id='sight' width='240' height='228.8' style='position: absolute; margin-left: 50px; margin-top: 55.6px;'>"
			+ "</canvas>"
			+ "<canvas id='bankAngleLines' width='240' height='228.8' style='position: absolute; margin-left: 50px; margin-top: 55.6px;'>"
			+ "</canvas>"
			+ "<canvas id='speed' width='240' height='260' style='position: absolute; margin-top: 40px;'>"
			+ "</canvas>"
			+ "<canvas id='altitude' width='260' height='260' style='position: absolute; margin-left: 100px; margin-top: 40px;'>"
			+ "</canvas>"
			+ "<canvas id='compass' width='240' height='40' style='position: absolute; margin-top:300px;margin-left: 50px '>"
			+ "</canvas>"
			+ "<canvas id='compassIndicator' width='240' height='40' style='position: absolute; margin-top:300px;margin-left: 50px '>"
			+ "</canvas>"
			+ "<canvas id='verspeed' width='40' height='340' style='position:absolute; margin-left: 350px'>"
			+ "</canvas>" + "</div>";
}

function init() {
	drawSight();
	drawCompassIndicator();
	drawVerticalSpeed();
	drawBankAngleLines();
}

function update() {
	// This is the heart of animation. We use TweenLite library to tween the
	// values (i.e. go smoothly from 0 to 100)
	TweenLite.to(window, 1, {
		currentPitch : window.wantHavePitch,
		currentSpeed : window.wantHaveSpeed,
		currentAltitude : window.wantHaveAltitude,
		currentVerSpeed : window.wantHaveVerSpeed
	});
	// we only do the update if no other update is done at the moment to avoid
	// eternal loops
	setRollAndPitch();
	setSpeed();
	setAltitude();
	setCompass();
	setVerticalSpeed();
}

function clearRect(ctx, x, y, w, h) {
	ctx.clearRect(x, y, w, h);
}

function fillRect(ctx, x, y, w, h) {
	ctx.beginPath();
	ctx.fillRect(x, y, w, h);
	ctx.fill();
}

function fillSky(ctx, x, y, w, h) {
	ctx.fillStyle = '#0080ff';
	fillRect(ctx, x, y, w, h);
}

function fillGround(ctx, x, y, w, h) {
	ctx.fillStyle = '#804000';
	fillRect(ctx, x, y, w, h);
}

// Help function to calculate compass, pitch, roll step based on difference
// between current
// value and desired value.
function calculateCompassPitchRollStep(diff, step) {
	diff = diff % 360;
	var direction = 0;
	if ((diff > step) && (diff <= 180)) {
		direction = diff * step;
	} else if ((diff > step) && (diff > 180)) {
		direction = (diff - 360) * step;
	} else if ((diff < -step) && (diff <= -180)) {
		direction = (diff + 360) * step;
	} else if ((diff < -step) && (diff > -180)) {
		direction = diff * step;
	}
	return direction;
}

/**
 * we only redraw if difference between current and want have > 0
 */
function shouldRedraw(dif, step) {
	return ((dif > step) || (dif < -step) || window.initialized);
}

function setRollAndPitch() {
	// update PITCH values
	// Check if we should continue animating pitch
	var difPitch = (window.wantHavePitch - window.currentPitch) % 360;
	var difRoll = (window.wantHaveRoll - window.currentRoll) % 360;
	// DRAW everything
	if (shouldRedraw(difRoll, 0.5)
			|| shouldRedraw(difPitch, 0.5)) {
		// rotate canvas back to initial position. It's important to rotate
		// back, o that the lines for sight are drawn when rotation angle is 0
		rotateCanvasByRollDegrees(window.pfdCan, window.currentRoll);
		drawLineNumbersForSight(difRoll, window.currentPitch);
		window.currentRoll = (window.currentRoll + difRoll) % 360;
		// rotate canvas to a new position
		rotateCanvasByRollDegrees(window.pfdCan, -window.currentRoll);
		drawArtificialHorizon(window.currentRoll, window.currentPitch,
				window.currentYaw);
	}
}

function rotateCanvasByRollDegrees(can, roll) {
	var ctx = can.getContext('2d');
	ctx.translate(can.width / 2, can.height / 2);
	ctx.rotate(roll * Math.PI / 180);
	ctx.translate(-can.width / 2, -can.height / 2);
}

function drawArtificialHorizon(roll, pitch, yaw) {
	clearRect(window.pfdCtx, 0, 0, window.canvasWidth, window.canvasHeight);
	drawSkyAndGround(window.pfdCtx, pitch);
}

// TODO: Simplify this code
function drawSkyAndGround(ctx, newPitch) {
	// Calculating the X,Y,W,H for sky and ground
	// All values are multiplied by 5. One pitch value = 5 pixels.
	var skyOnTop, grOnTop, skyBottom, grBottom, skyOnTopY, grOnTopY, skyBottomY, grBottomY;
	// if current pitch is smaller than 0
	if (newPitch <= 0) {
		newPitch = 360 + newPitch; // for easier computation Transform negative
		// numbers to 0-359 coordinates
		skyOnTop = newPitch * 5 - 180 * 5;
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
	var translateBy = -(180 - window.canvasHeight / 10) * 5;
	ctx.translate(0, translateBy);
	var skyGroundLeftX = -(window.horizontWidth - window.window.canvasWidth) / 2;
	fillSky(ctx, skyGroundLeftX, skyOnTopY, window.horizontWidth, skyOnTop);
	fillGround(ctx, skyGroundLeftX, grOnTopY, window.horizontWidth, grOnTop);
	fillSky(ctx, skyGroundLeftX, skyBottomY, window.horizontWidth, skyBottom);
	fillGround(ctx, skyGroundLeftX, grBottomY, window.horizontWidth, grBottom);
	ctx.translate(0, -translateBy);
	// Draw lines on top of artificial horizon to indicate the bank angle.

}

function drawBankAngleLines() {
	var can = document.getElementById('bankAngleLines');
	var ctx = can.getContext('2d');
	ctx.strokeStyle = "white";
	ctx.fillStyle = "white";
	ctx.lineWidth = 1;
	// draw bank lines at the left
	drawBankLineAtRoll(can, 60, 0.03, 0);
	drawBankLineAtRoll(can, 45, 0.03, 0);
	drawBankLineAtRoll(can, 30, 0.03, 0);
	drawBankLineAtRoll(can, 15, 0.03, 0);
	// draw bank lines at the right
	drawBankLineAtRoll(can, -60, 0.03, 0);
	drawBankLineAtRoll(can, -45, 0.03, 0);
	drawBankLineAtRoll(can, -30, 0.03, 0);
	drawBankLineAtRoll(can, -15, 0.03, 0);
	// Draw bank angle triangle
	ctx.moveTo(window.canvasWidth / 2 - 3, 0);
	ctx.lineTo(window.canvasWidth / 2 + 3, 0);
	ctx.lineTo(window.canvasWidth / 2, 6);
	ctx.lineTo(window.canvasWidth / 2 - 3, 0);
	ctx.stroke();
	ctx.fill();

}

/**
 * Note: doesn't do stroke to improve performance, stroke should be done by
 * caller
 */
function drawBankLineAtRoll(can, roll, startPos, endPos) {
	var ctx = can.getContext('2d');
	ctx.save();
	rotateCanvasByRollDegrees(can, roll);
	ctx.moveTo(can.width / 2, can.height * startPos);
	ctx.lineTo(can.width / 2, can.height * endPos);
	ctx.restore();
}

function drawLineNumberHelpFunc(ctxSight, startInt, endInt, difPitch, direction) {
	ctxSight.strokeStyle = 'white';
	ctxSight.fillStyle = 'white';
	for (var i = startInt; i <= endInt; i = i + 2.5) {
		// Don't draw the line number and line if it is not visible
		// Reason: 1. performance. 2. So that it doesn't overlap with bank angle
		// lines
		if (isLineNumberVisible(i * direction, difPitch)) {
			if (((i % 5) == 0) && (i != startInt)) {
				ctxSight.fillText(i, window.canvasWidth / 2 - 45,
						window.canvasHeight / 2 + i * 5 * direction + 3
								+ difPitch * 5);
				ctxSight.moveTo(window.canvasWidth / 2 - 30,
						window.canvasHeight / 2 + i * 5 * direction + difPitch
								* 5);
				ctxSight.lineTo(window.canvasWidth / 2 + 30,
						window.canvasHeight / 2 + i * 5 * direction + difPitch
								* 5);
			} else if (((i % (2.5)) == 0) && (i != startInt)) {
				ctxSight.moveTo(window.canvasWidth / 2 - 15,
						window.canvasHeight / 2 + i * 5 * direction + difPitch
								* 5);
				ctxSight.lineTo(window.canvasWidth / 2 + 15,
						window.canvasHeight / 2 + i * 5 * direction + difPitch
								* 5);
			}
		}
	}
}

function isLineNumberVisible(lineNumberVal, pitchVal) {
	var visibleNumOfLines = window.canvasHeight / 10 + 4;// we make it a
	// little bigger in
	// the bottom, so
	// that lines don't
	// dissaper too
	// early
	var visibleNumOfLinesTop = visibleNumOfLines; // we make the visible a bit
	// smaller so that it
	// doesn't overlap with bank
	// angle lines
	if ((lineNumberVal >= 0) && (pitchVal <= 0)) {
		return ((Math.abs(Math.abs(lineNumberVal) - Math.abs(pitchVal))) < visibleNumOfLines);
	} else if ((lineNumberVal >= 0) && (pitchVal >= 0)) {
		return ((Math.abs(lineNumberVal + pitchVal)) < visibleNumOfLines);
	} else if ((lineNumberVal <= 0) && (pitchVal <= 0)) {
		return ((Math.abs(lineNumberVal + pitchVal)) < visibleNumOfLinesTop);
	} else if ((lineNumberVal <= 0) && (pitchVal >= 0)) {
		return ((Math.abs(Math.abs(lineNumberVal) - pitchVal)) < visibleNumOfLinesTop);
	}
}

/**
 * Transform 180-360 values to (-180;-0)
 */
function transformPitchValue(pitch) {
	if (pitch > 180) {
		pitch = pitch - 360;
	}
	return pitch;
}

/**
 * draw line numbers indicating pitch angle
 */
function drawLineNumbersForSight(difRoll, difPitch) {
	var transformedPitchValue = transformPitchValue(difPitch);
	var canSight = document.getElementById('sight');
	var ctxSight = canSight.getContext('2d');
	drawSight();
	// rotate sight canvas to initial position
	rotateCanvasByRollDegrees(canSight, window.currentRoll);
	var newRoll = (window.currentRoll + difRoll) % 360;
	// rotate line number sight canvas to new position. Explanation: we make two
	// rotations instead of one to decrease the error created by approximating
	rotateCanvasByRollDegrees(canSight, -newRoll);
	// draw numbers for degrees -90 to +90
	drawLineNumberHelpFunc(ctxSight, 0, 90, transformedPitchValue, -1);
	drawLineNumberHelpFunc(ctxSight, 0, 90, transformedPitchValue, 1);
	drawBankIndicator(canSight, ctxSight);
	ctxSight.stroke();
}

/**
 * as far as i remember, this draws this little triangle on the top to indicate
 * performance note: doesn't stroke at the end to improve performance.
 */
function drawBankIndicator(can, ctx) {
	ctx.moveTo(can.width / 2, can.height * 0.04);
	ctx.lineTo(can.width / 2 - 4, can.height * 0.04 + 8);
	ctx.lineTo(can.width / 2 + 4, can.height * 0.04 + 8);
	ctx.lineTo(can.width / 2, can.height * 0.04);
}

/**
 * draws the circle in the midle of the pfd
 */
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
	ctx.stroke();
	ctx.restore();
}

function drawVerticalSpeedLines(ctxVS, direction) {
	ctxVS.strokeStyle = 'white';
	ctxVS.fillStyle = 'white';
	ctxVS.moveTo(window.vsCanW * 0.3, window.vsCanH * 0.5);
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
		ctxVS.fillText(i, window.vsCanW * 0.2, vsiYCenter + (i / 3) * scale
				* direction + 3);
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
	var coord = [ window.vsCanW * 0.10, window.vsCanH * 0.2,// top left corner
	window.vsCanW * 0.10, window.vsCanH * 0.40, window.vsCanW * 0.30,
			window.vsCanH * 0.45, window.vsCanW * 0.30, window.vsCanH * 0.55,
			window.vsCanW * 0.10, window.vsCanH * 0.60, window.vsCanW * 0.10,
			window.vsCanH * 0.80,// bottom left corner
			window.vsCanW * 0.50, window.vsCanH * 0.80,// bottom right corner
			window.vsCanW * 0.80, window.vsCanH * 0.68, window.vsCanW * 0.80,
			window.vsCanH * 0.32, window.vsCanW * 0.50, window.vsCanH * 0.20 ];// top
	// right
	// corner
	drawPolygone(ctxVS, coord);
	ctxVS.stroke();
	ctxVS.fill();
	ctxVS.beginPath();
	drawVerticalSpeedLines(ctxVS, 1);
	drawVerticalSpeedLines(ctxVS, -1);
}

function calculateVsYPosition(verticalSpeed) {
	var verticalSpeedYPos = 0;
	var scale = (0.28 * window.vsCanH) / 1000;
	// if>3000, set to max
	if (verticalSpeed > 3000) {
		verticalSpeedYPos = window.vsCanH * 0.22;
		// if<3000, set to min
	} else if (verticalSpeed < -3000) {
		verticalSpeedYPos = window.vsCanH * 0.78;
	} else {
		// looking a this code year after I've written it.. looks like we take
		// the vertical speed and scale it down by 1000, without caring what
		// units AWCom is actually sending
		verticalSpeedYPos = window.vsCanH * 0.5 - (verticalSpeed / 3) * scale;
	}
	return verticalSpeedYPos;
}

function setVerticalSpeed() {
	var difVerSpeed = window.wantHaveVerSpeed - window.currentVerSpeed;
	if (shouldRedraw(difVerSpeed, 1)) {
		var speedYPos = calculateVsYPosition(window.currentVerSpeed);
		var ctxVS = document.getElementById('verspeed').getContext('2d');
		drawVerticalSpeed();
		ctxVS.beginPath();
		ctxVS.strokeStyle = 'white';
		ctxVS.lineWidth = 2;
		ctxVS.moveTo(window.vsCanW, window.vsCanH / 2);
		ctxVS.lineTo(window.vsCanW * 0.5, speedYPos);
		ctxVS.font = '10pt Calibri';
		ctxVS.fillText(Math.round(window.currentVerSpeed),
				window.vsCanW * 0.05, window.vsCanH * 0.16);
		ctxVS.stroke();
	}
}

/**
 * Increase currentSpeed by a small value to be closer to wantHaveSpeed
 */
function setSpeed() {
	var difSpeed = window.wantHaveSpeed - window.currentSpeed;
	if (shouldRedraw(difSpeed, 1)) {
		drawSpeed();
	}
}

function drawSpeed() {
	var ctxSpeed = document.getElementById('speed').getContext('2d');
	drawSpeedBar(ctxSpeed);
	drawSpeedLinesWithNumbers(ctxSpeed);
	drawSpeedIndicator(ctxSpeed);
	writeSpeedOnSpeedIndicator(ctxSpeed);
}

function writeSpeedOnSpeedIndicator(ctxSpeed) {
	var currentSpeedInt = Math.round(window.currentSpeed);
	ctxSpeed.font = '14pt Calibri';
	ctxSpeed.fillStyle = 'white';
	// Write speed on speed indicator
	ctxSpeed.fillText(currentSpeedInt, window.leftSpeedBarMargin + 1,
			window.speedBarHeight / 2 + 3);
}

function drawSpeedBar(ctxSpeed) {
	clearRect(ctxSpeed, window.leftSpeedBarMargin, window.topSpeedBarMargin,
			window.speedBarWidth, window.speedBarHeight);
	ctxSpeed.save();
	ctxSpeed.fillStyle = window.darkGray;
	fillRect(ctxSpeed, window.leftSpeedBarMargin, window.topSpeedBarMargin,
			window.speedBarWidth, window.speedBarHeight);
}

function drawSpeedLinesWithNumbers(ctxSpeed) {
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
		ctxSpeed.fillText(i, 2, -i * 2 - window.speedBarHeight / 2
				+ window.currentSpeed * 2);
		// draw lines in between the numbers
		ctxSpeed.moveTo(window.speedBarWidth - 5, -i * 2 - 3
				- window.speedBarHeight / 2 + window.currentSpeed * 2 - 20);
		ctxSpeed.lineTo(window.speedBarWidth, -i * 2 - 3
				- window.speedBarHeight / 2 + window.currentSpeed * 2 - 20);
	}
	ctxSpeed.stroke();
	ctxSpeed.restore();
}

function setAltitude() {
	var difAltitude = window.wantHaveAltitude - window.currentAltitude;
	if (shouldRedraw(difAltitude, 1)) {
		var ctxAltitude = document.getElementById('altitude').getContext('2d');
		clearRect(ctxAltitude, window.canvasWidth - window.altBarRightMargin
				- window.altBarWidth, window.altBarTopMargin,
				window.altBarWidth, window.altBarHeight);
		ctxAltitude.save();
		ctxAltitude.fillStyle = window.darkGray;
		fillRect(ctxAltitude, window.canvasWidth - window.altBarRightMargin
				- window.altBarWidth, window.altBarTopMargin,
				window.altBarWidth, window.altBarHeight);
		drawAltitudeLinesNumber(ctxAltitude);
		drawAltitudeIndicator(ctxAltitude);
		writeAltOnAltIndicator(ctxAltitude);
	}
}

function writeAltOnAltIndicator(ctxAlt) {
	var currentAltInt = Math.round(window.currentAltitude);
	ctxAlt.font = '14pt Calibri';
	ctxAlt.fillStyle = 'white';
	// Write speed on speed indicator
	ctxAlt.fillText(currentAltInt, window.canvasWidth
			- window.altBarRightMargin - window.altBarWidth + 10,
			window.altBarHeight / 2 + 3);
}

function drawAltitudeLinesNumber(ctxAltitude) {
	// Speed marks and numbers
	ctxAltitude.translate(window.canvasWidth - window.altBarRightMargin
			- window.altBarWidth, window.altBarTopMargin + window.altBarHeight);
	ctxAltitude.beginPath();
	ctxAltitude.lineWidth = 2;
	ctxAltitude.strokeStyle = 'white';
	ctxAltitude.fillStyle = 'white';
	ctxAltitude.font = '8pt Calibri';

	// draw lines with numbers
	for (var i = 0; i < 50000; i += 200) {
		ctxAltitude.moveTo(0, -(i * 2) / 10 - 3 - window.altBarHeight / 2
				+ (window.currentAltitude * 2) / 10);
		ctxAltitude.lineTo(10, -(i * 2) / 10 - 3 - window.altBarHeight / 2
				+ (window.currentAltitude * 2) / 10);
		ctxAltitude.fillText(i, 10, -(i * 2) / 10 - window.altBarHeight / 2
				+ (window.currentAltitude * 2) / 10);
		// draw lines in between the numbers
		ctxAltitude.moveTo(0, -(i * 2) / 10 - 3 - window.altBarHeight / 2
				+ (window.currentAltitude * 2) / 10 - 20);
		ctxAltitude.lineTo(5, -(i * 2) / 10 - 3 - window.altBarHeight / 2
				+ (window.currentAltitude * 2) / 10 - 20);
	}
	ctxAltitude.stroke();
	ctxAltitude.restore();
}

// Help function for rotating compass
function rotateCompassCanvasByDegrees(compassCanvas, degrees) {
	compassCanvas.translate(window.compasCanvasWidth / 2,
			window.compasCanvasHeight + window.compasTopMargin);
	compassCanvas.rotate(degrees * Math.PI / 180);
	compassCanvas.translate(-window.compasCanvasWidth / 2,
			-window.compasCanvasHeight - window.compasTopMargin);
}

// Sets compass value in small iterations.
function setCompassValue(ctxCompass, compass) {
	var difCompass = (compass - window.currentCompass) % 360;
	var compassStep = calculateCompassPitchRollStep(difCompass, 0.05);
	// rotate compass back to initial pos
	rotateCompassCanvasByDegrees(ctxCompass, window.currentCompass);
	// set compass value to new position
	window.currentCompass = (window.currentCompass + compassStep) % 360;
	if (window.currentCompass < 0) {
		window.currentCompass += 360;
	}
	// rotate compass to new pos
	rotateCompassCanvasByDegrees(ctxCompass, -window.currentCompass);
}
function setCompass() {
	var ctxCompass = document.getElementById('compass').getContext('2d');
	setCompassValue(ctxCompass, window.wantHaveHeading);
	drawCompass();
}

function drawCompass() {
	var canCompass = document.getElementById('compass');
	var ctxCompass = canCompass.getContext('2d');
	clearRect(ctxCompass, 0, 10, canCompass.width * 5, canCompass.height * 5);
	ctxCompass.strokeStyle = window.darkGray;
	ctxCompass.fillStyle = window.darkGray;
	var y = window.compasCanvasHeight + window.compasTopMargin;
	arc(ctxCompass, window.compasCanvasWidth / 2, y, window.compasRadius, 0,
			2 * Math.PI);
	ctxCompass.fill();
	ctxCompass.fillStyle = 'white';
	ctxCompass.font = '33 pt Calibri';
	for (var i = 0; i < 36; i++) {
		ctxCompass.save();
		rotateCompassCanvasByDegrees(ctxCompass, 10 * i);
		ctxCompass.fillText(i, window.compasCanvasWidth / 2, 20);
		ctxCompass.restore();
	}
}

// Draws a rectangular box for speed and current speed displayed in this box
function drawSpeedIndicator(ctxSpeed) {
	// Draw speed indicator
	ctxSpeed.strokeStyle = 'white';
	ctxSpeed.fillStyle = 'black';
	var coords = [ window.speedBarWidth + window.leftSpeedBarMargin - 5,
			window.speedBarHeight / 2 - 3,
			window.speedBarWidth + window.leftSpeedBarMargin - 8,
			window.speedBarHeight / 2 - 6,
			window.speedBarWidth + window.leftSpeedBarMargin - 8,
			window.speedBarHeight / 2 - 20, window.leftSpeedBarMargin - 2,
			window.speedBarHeight / 2 - 20, window.leftSpeedBarMargin - 2,
			window.speedBarHeight / 2 + 16,
			window.speedBarWidth + window.leftSpeedBarMargin - 8,
			window.speedBarHeight / 2 + 16,
			window.speedBarWidth + window.leftSpeedBarMargin - 8,
			window.speedBarHeight / 2 ];
	drawPolygone(ctxSpeed, coords);

}

// Draws this little triangle which points to current altitude
function drawAltitudeIndicator(ctxAlt) {
	ctxAlt.strokeStyle = 'white';
	ctxAlt.fillStyle = 'black';
	// setting vertices coordinates of a triangle x1,y1,x2,y2,x3,y3
	var coords = [
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin,
			window.altBarHeight / 2 - 3,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					- window.speedIndicatorWidth,
			window.altBarHeight / 2 - 8,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					- window.speedIndicatorWidth, window.altBarHeight / 2 ];
	var coords = [
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					+ 5,
			window.altBarHeight / 2 - 3,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					+ 8,
			window.altBarHeight / 2 - 6,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					+ 8,
			window.altBarHeight / 2 - 20,
			window.canvasWidth - window.altBarRightMargin + 20,
			window.altBarHeight / 2 - 20,
			window.canvasWidth - window.altBarRightMargin + 20,
			window.altBarHeight / 2 + 16,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					+ 8,
			window.altBarHeight / 2 + 16,
			window.canvasWidth - window.altBarWidth - window.altBarRightMargin
					+ 8, window.altBarHeight / 2 ];
	drawPolygone(ctxAlt, coords);
}

// Draws this little triangle which points to current compass
function drawCompassIndicator() {
	var compassCtx = document.getElementById('compassIndicator').getContext(
			'2d');
	compassCtx.strokeStyle = 'white';
	compassCtx.fillStyle = 'white';
	// setting vertices coordinates of a triangle x1,y1,x2,y2,x3,y3
	var coords = [ window.compasCanvasWidth / 2 + 3, 7,
			window.compasCanvasWidth / 2 - window.compassIndicatorWidth + 3,
			7 - window.compassIndicatorWidth,
			window.compasCanvasWidth / 2 + window.compassIndicatorWidth + 3,
			7 - window.compassIndicatorWidth ];
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
	ctx.stroke();
}

/**
 * Note: stroke/fill should be called by caller to improve performance
 */
function arc(ctx, x, y, r, startAngle, finishAngle) {
	ctx.beginPath();
	ctx.arc(x, y, r, startAngle, finishAngle);
	ctx.stroke();
};

