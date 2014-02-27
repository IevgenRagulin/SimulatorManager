var sightRadius = 90;
var sightCenterRadius = 10;
var sightCenterCenterRadius = 1;

var canvasHeight = 300;
var canvasWidth = 300;

var speedBarWidth = 30;
var speedBarHeight = 300;
var leftSpeedBarMargin = 0;
var topSpeedBarMargin = 0;
var speedIndicatorWidth = 5;

var altBarWidth = 30;
var altBarHeight = 300;
var altBarRightMargin = 0;
var altBarTopMargin = 0;

var currentSpeed = 0;
var currentAltitude = 0;
var currentPitch = 0;
var currentRoll = 0;
var currentYaw = 0;

var currentlyChangingRoll = false;
var currentlyChangingPitch = false;
var currentlyChangingAlt = false;
var currentlyChangingSpeed = false;

var horizontWidth = 300;

var isItFirstLoad = true;

com_example_testvaadin_javascriptcomponents_PrimaryFlightDisplay = function() {
	var e = this.getElement();

	this.onStateChange = function() {
		console.log("PFD STATE CHANGED");
		if (isItFirstLoad) {
			init();
			ifItFirstLoad = false;
		}
		;
		update(this.getState().speed, this.getState().altitude,
				this.getState().roll, this.getState().pitch,
				this.getState().yaw);
	};

	function update(speed, altitude, roll, pitch, yaw) {
		console.log("NEW ROLL" + roll);

		//setPitch(pitch);
		if (!currentlyChangingRoll) {
			setRoll(roll);
		}
		setSpeed(speed);
		setAltitude(altitude);
	}

	function initHtml() {
		e.innerHTML = "<h1>PFD</h1>"
				+ "<div style='position:relative; margin-left: 40px;'>"
				+ "<canvas id='pfd' width='300' height='300' style='border:1px solid #000000; position: absolute;'>"
				+ "Your browser doesn't support canvas."
				+ "</canvas>"
				+ "<canvas id='sight' width='300' height='300' style='border:1px solid #000000; position: absolute;'>"
				+ "Your browser doesn't support canvas."
				+ "</canvas>"
				+ "<canvas id='speed' width='300' height='300' style='border:1px solid #000000; position: absolute;'>"
				+ "Your browser doesn't support canvas."
				+ "</canvas>"
				+ "<canvas id='altitude' width='300' height='300' style='border:1px solid #000000; position: absolute;'>"
				+ "Your browser doesn't support canvas." + "</canvas>"
				+ "</div>"
				+ "<div style='margin-top: 350px; margin-left: 40px;'>"
				+ "<label id='speedT'>Current speed:</label>"
				+ "<label id='speedV'></label><br/>"
				+ "<label id='altitudeT'>Current altitude:</label>"
				+ "<label id='altitudeV'></label><br/>"
				+ "<label id='rollT'>Roll:</label>"
				+ "<label id='rollV'></label><br/>"
				+ "<label id='pitchT'>Pitch:</label>"
				+ "<label id='pitchV'></label><br/>"
				+ "<button id='addPitch'>+10 pitch</button>"
				+ "<button id='minusPitch'>-10 pitch</button>"
				+ "<button id='addRoll'>+60 roll</button>"
				+ "<button id='minusRoll'>-60 roll</button>"
				+ "<button id='addAltitude'>+50 altitude</button>"
				+ "<button id='minusAltitude'>-50 altitude</button>"
				+ "<button id='addSpeed'>+50 speed</button>"
				+ "<button id='minusSpeed'>-50 speed</button>" + "</div>";
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
	}

	function init() {
		initHtml();
		drawSpeedIndicator();
		drawHeightIndicator();
		drawSight();
		setClickListeners();
	}

	function setAddPitchClickListener() {
		document.getElementById('addPitch').addEventListener('click',
				function() {
					var newPitch = (currentPitch + 10) % 360;
					if (!currentlyChangingPitch) {
						setPitch(newPitch);
					}
				});
	}

	function setMinusPitchClickListener() {
		document.getElementById('minusPitch').addEventListener('click',
				function() {
					var newPitch = (currentPitch - 10) % 360;
					if (newPitch < 0) {
						newPitch = 360 + newPitch;
					}
					if (!currentlyChangingPitch) {
						setPitch(newPitch);
					}
				});
	}

	function setAddRollClickListener() {
		document.getElementById('addRoll').addEventListener('click',
				function() {
					console.log(currentlyChangingRoll);
					if (!currentlyChangingRoll) {
						setRoll(currentRoll + 60);
					}
				});
	}

	function setMinusRollClickListener() {
		document.getElementById('minusRoll').addEventListener('click',
				function() {
					if (!currentlyChangingRoll) {
						setRoll(currentRoll - 60);
					}
				});
	}

	function setAddAltitudeClickListener() {
		document.getElementById('addAltitude').addEventListener('click',
				function() {
					console.log("TRYING TO PLUS ALTITUDE");
					if (!currentlyChangingAlt) {
						setAltitude(currentAltitude + 50);
					}
				});
	}

	function setMinusAltitudeClickListener() {
		document.getElementById('minusAltitude').addEventListener('click',
				function() {
					console.log("TRYING TO MINUES ALTITUDE");
					if (!currentlyChangingAlt) {
						setAltitude(currentAltitude - 50);
					}
				});
	}

	function setAddSpeedClickListener() {
		document.getElementById('addSpeed').addEventListener('click',
				function() {
					if (!currentlyChangingSpeed) {
						setSpeed(currentSpeed + 50);
					}
				});
	}

	function setMinusSpeedClickListener() {
		document.getElementById('minusSpeed').addEventListener('click',
				function() {
					if (!currentlyChangingSpeed) {
						setSpeed(currentSpeed - 50);
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
		ctx.stroke();
	}

	function fillSky(ctx, x, y, w, h) {
		ctx.save();
		ctx.fillStyle = '0080ff';
		fillRect(ctx, x, y, w, h);
		ctx.restore();
	}

	function fillGround(ctx, x, y, w, h) {
		ctx.save();
		ctx.fillStyle = '804000';
		fillRect(ctx, x, y, w, h);
		ctx.restore();
	}

	function calculateDirection(newValue, currentValue) {
		var diff = newValue - currentValue;
		var direction = 0;
		if ((diff > 0.2) && (diff <= 180)) {
			direction = 1;
		} else if ((diff > 0.2) && (diff > 180)) {
			direction = -1;
		} else if ((diff < -0.2) && (diff <= -180)) {
			direction = 1;
		} else if ((diff < -0.2) && (diff > -180)) {
			direction = -1;
		}
		return direction;
	}

	function shouldWeChangePitch(difPitch) {
		console.log("SHOULD WE CHANGEPITCH" + difPitch);
		return difPitch != 0;
	}

	function setPitch(pitch) {
		var ctx = document.getElementById('pfd').getContext('2d');
		console.log("set pitch called");
		// Check if we should continue animating pitch
		var difPitch = pitch - currentPitch;
		if (shouldWeChangePitch(difPitch)) {
			currentlyChangingPitch = true;
			difPitch = 1 * calculateDirection(pitch, currentPitch);
			requestAnimationFrame(function() {
				setPitch(pitch);
			});
		} else {
			currentlyChangingPitch = false;
		}
		difPitch = (currentPitch + difPitch) % 360;
		// Draw lines and numbers for sight
		drawLineNumbersForSight(difPitch);
		// Transform negative numbers to 0-359 coordinates

		if (difPitch < 0) {
			difPitch = 360 + difPitch;
		}
		currentPitch = difPitch;
		drawArtificialHorizon(ctx, currentRoll, currentPitch, currentYaw);
		document.getElementById('pitchV').innerHTML = currentPitch;
	}

	function shouldWeRoll(difRoll) {
		//console.log("SHOULD WE CHANGEROLL. Diff roll: "+difRoll+" Current roll: "+currentRoll);
		return ((difRoll > 0.2) || (difRoll < -0.2));
	}

	function setRoll(roll) {
		var ctx = document.getElementById('pfd').getContext('2d');
		var difRoll = roll - currentRoll;
		if (shouldWeRoll(difRoll)) {
			currentlyChangingRoll = true;
			difRoll = 0.5 * calculateDirection(roll, currentRoll);
			requestAnimationFrame(function() {
				setRoll(roll);
			});
		} else {
			console.log("no, we shouldn't change roll");
			currentlyChangingRoll = false;
		}

		rotatePfdByRollDegrees(ctx, difRoll);
		ctx.save();
		difRoll = currentRoll + difRoll;
		currentRoll = difRoll;
		//console.log("NEW CURRENT ROLL after rotation"+currentRoll);

		drawArtificialHorizon(ctx, currentRoll, currentPitch, currentYaw);
		ctx.restore();
		document.getElementById('rollV').innerHTML = currentRoll;
	}

	function rotatePfdByRollDegrees(ctx, roll) {
		ctx.translate(canvasHeight / 2, canvasWidth / 2);
		ctx.rotate(roll * Math.PI / 180);
		ctx.translate(-canvasHeight / 2, -canvasWidth / 2);
	}

	function drawArtificialHorizon(ctx, roll, pitch, yaw) {
		//console.log('draw artificial horizon called');
		clearRect(ctx, 0, 0, canvasWidth, canvasHeight);
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
			var skyGroundLeftX = -(horizontWidth - canvasWidth) / 2;
			fillSky(ctx, skyGroundLeftX, skyOnTopY, horizontWidth, skyOnTop);
			fillGround(ctx, skyGroundLeftX, grOnTopY, horizontWidth, grOnTop);
			fillSky(ctx, skyGroundLeftX, skyBottomY, horizontWidth, skyBottom);
			fillGround(ctx, skyGroundLeftX, grBottomY, horizontWidth, grBottom);
		} else {
			console.error("Unexpected pitch value!");
		}
	}

	function cropLinesNumberOutsideSight() {
		var ctx = document.getElementById('sight').getContext('2d');
		ctx.beginPath();
		ctx.arc(canvasWidth / 2, canvasHeight / 2, sightRadius + 1, 0,
				2 * Math.PI, true);
		ctx.clip();
	}

	function drawLineNumberHelpFunc(startInt, endInt, difPitch, direction) {
		var ctx = document.getElementById('sight').getContext('2d');
		for (var i = startInt; i <= endInt; i = i + 2.5) {
			if (((i % 5) == 0) && (i != startInt)) {
				ctx.fillText(i, canvasWidth / 2 - 45, canvasHeight / 2 + i * 5
						* direction + 3 + difPitch * 5);
				ctx.moveTo(canvasWidth / 2 - 30, canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
				ctx.lineTo(canvasWidth / 2 + 30, canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
			} else if (((i % (2.5)) == 0) && (i != startInt)) {
				ctx.moveTo(canvasWidth / 2 - 15, canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
				ctx.lineTo(canvasWidth / 2 + 15, canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
			}
		}
	}

	function drawLineNumbersForSight(difPitch) {
		var ctx = document.getElementById('sight').getContext('2d');
		// Transform 180-360 values to -180-0
		if (difPitch > 180) {
			difPitch = difPitch - 360;
		}
		cropLinesNumberOutsideSight();
		ctx.clearRect(0, 0, canvasHeight, canvasWidth);
		drawSight();
		ctx.save();
		ctx.beginPath();
		ctx.strokeStyle = 'white';
		ctx.fillStyle = 'white';
		// draw numbers for degrees 0-180
		drawLineNumberHelpFunc(0, 180, difPitch, -1);
		// draw numbers for degrees 180-360
		drawLineNumberHelpFunc(180, 360, difPitch, -1);
		// draw numbers for degrees 0 - (-180)
		drawLineNumberHelpFunc(0, 180, difPitch, 1);
		// draw numbers for degrees -180-(-360)
		drawLineNumberHelpFunc(180, 360, difPitch, 1);
		ctx.stroke();
		ctx.restore();
	}

	function setSpeed(speed) {
		console.log('set speed called');
		var ctx = document.getElementById('speed').getContext('2d');
		var difSpeed = speed - currentSpeed;
		var difSpeedStep = calculateAltitudeSpeedStep(difSpeed);
		if (difSpeed > 0) {
			currentlyChangingSpeed = true;
			requestAnimationFrame(function() {
				setSpeed(speed);
			});
		} else if (difSpeed < 0) {
			currentlyChangingSpeed = true;
			requestAnimationFrame(function() {
				setSpeed(speed);
			});
		} else {
			currentlyChangingSpeed = false;
		}
		var newSpeed = currentSpeed + difSpeedStep;
		currentSpeed = newSpeed;

		clearRect(ctx, leftSpeedBarMargin, topSpeedBarMargin, speedBarWidth,
				speedBarHeight);
		ctx.save();
		ctx.fillStyle = 'black';
		fillRect(ctx, leftSpeedBarMargin, topSpeedBarMargin, speedBarWidth,
				speedBarHeight);

		// Speed marks and numbers
		ctx.translate(leftSpeedBarMargin, topSpeedBarMargin + speedBarHeight);
		ctx.beginPath();
		ctx.lineWidth = 2;
		ctx.strokeStyle = 'white';
		ctx.fillStyle = 'white';
		ctx.font = '8pt Calibri';

		// draw lines with numbers
		for (var i = 0; i < 1000; i += 20) {
			ctx.moveTo(20, -i * 2 - 3 - speedBarHeight / 2 + newSpeed * 2);
			ctx.lineTo(30, -i * 2 - 3 - speedBarHeight / 2 + newSpeed * 2);
			ctx.fillText(i, 0, -i * 2 - speedBarHeight / 2 + newSpeed * 2);
			// draw lines in between the numbers
			ctx.moveTo(25, -i * 2 - 3 - speedBarHeight / 2 + newSpeed * 2 - 20);
			ctx.lineTo(30, -i * 2 - 3 - speedBarHeight / 2 + newSpeed * 2 - 20);
		}
		ctx.stroke();
		ctx.restore();
		document.getElementById('speedV').innerHTML = currentSpeed;

	}

	function calculateAltitudeSpeedStep(dif) {
		var difAltitudeStep;
		if (dif > 0) {
			if (dif > 50) {
				difAltitudeStep = 10;
			} else {
				difAltitudeStep = 2;
			}
		} else if (dif < 0) {
			if (dif < -50) {
				difAltitudeStep = -10;
			} else {
				difAltitudeStep = -2;
			}
		} else {
			difAltitudeStep = 0;
		}
		return difAltitudeStep;
	}

	function setAltitude(altitude) {
		console.log('set alt called');

		var ctx = document.getElementById('altitude').getContext('2d');
		var difAltitude = altitude - currentAltitude;
		var difAltitudeStep = calculateAltitudeSpeedStep(difAltitude);
		if (difAltitude > 0) {
			requestAnimationFrame(function() {
				currentlyChangingAlt = true;
				setAltitude(altitude);
			});
		} else if (difAltitude < 0) {
			currentlyChangingAlt = true;
			requestAnimationFrame(function() {
				setAltitude(altitude);
			});
		} else {
			currentlyChangingAlt = false;
		}

		newAltitude = currentAltitude + difAltitudeStep;
		currentAltitude = newAltitude;

		clearRect(ctx, canvasWidth - altBarRightMargin - altBarWidth,
				altBarTopMargin, altBarWidth, altBarHeight);
		ctx.save();
		ctx.fillStyle = 'black';
		fillRect(ctx, canvasWidth - altBarRightMargin - altBarWidth,
				altBarTopMargin, altBarWidth, altBarHeight);

		// Speed marks and numbers
		ctx.translate(canvasWidth - altBarRightMargin - altBarWidth,
				altBarTopMargin + altBarHeight);
		ctx.beginPath();
		ctx.lineWidth = 2;
		ctx.strokeStyle = 'white';
		ctx.fillStyle = 'white';
		ctx.font = '8pt Calibri';

		// draw lines with numbers
		for (var i = 0; i < 10000; i += 20) {
			ctx.moveTo(0, -i * 2 - 3 - altBarHeight / 2 + newAltitude * 2);
			ctx.lineTo(10, -i * 2 - 3 - altBarHeight / 2 + newAltitude * 2);
			ctx.fillText(i, 10, -i * 2 - altBarHeight / 2 + newAltitude * 2);
			// draw lines in between the numbers
			ctx.moveTo(0, -i * 2 - 3 - altBarHeight / 2 + newAltitude * 2 - 20);
			ctx.lineTo(5, -i * 2 - 3 - altBarHeight / 2 + newAltitude * 2 - 20);
		}
		ctx.stroke();
		ctx.restore();
		document.getElementById('altitudeV').innerHTML = currentAltitude;
	}

	function drawSpeedIndicator() {
		// Draw speed indicator
		var ctx = document.getElementById('speed').getContext('2d');
		ctx.save();
		ctx.strokeStyle = 'black';
		ctx.fillStyle = 'black';
		ctx.beginPath();
		ctx.moveTo(speedBarWidth + leftSpeedBarMargin, canvasHeight / 2 - 3);
		ctx.lineTo(speedBarWidth + leftSpeedBarMargin + speedIndicatorWidth,
				canvasHeight / 2 - 8);
		ctx.lineTo(speedBarWidth + leftSpeedBarMargin + speedIndicatorWidth,
				canvasHeight / 2);
		ctx.lineTo(speedBarWidth + leftSpeedBarMargin, canvasHeight / 2 - 3);
		ctx.stroke();
		ctx.fill();
		ctx.restore();
	}

	function drawHeightIndicator() {
		// Draw speed indicator
		var ctx = document.getElementById('speed').getContext('2d');
		ctx.save();
		ctx.strokeStyle = 'black';
		ctx.fillStyle = 'black';
		ctx.beginPath();
		ctx.moveTo(canvasWidth - altBarWidth - altBarRightMargin,
				canvasHeight / 2 - 3);
		ctx.lineTo(canvasWidth - altBarWidth - altBarRightMargin
				- speedIndicatorWidth, canvasHeight / 2 - 8);
		ctx.lineTo(canvasWidth - altBarWidth - altBarRightMargin
				- speedIndicatorWidth, canvasHeight / 2);
		ctx.lineTo(canvasWidth - altBarWidth - altBarRightMargin,
				canvasHeight / 2 - 3);
		ctx.stroke();
		ctx.fill();
		ctx.restore();
	}

	function arc(ctx, x, y, r, startAngle, finishAngle) {
		ctx.beginPath();
		ctx.arc(x, y, r, startAngle, finishAngle);
		ctx.stroke();
	}
	;

	function drawSight() {
		var ctx = document.getElementById('sight').getContext('2d');
		ctx.save();
		// Clip everything what's outside the circle
		ctx.beginPath();
		ctx.arc(canvasWidth / 2, canvasHeight / 2, sightRadius + 1, 0,
				2 * Math.PI, true);
		ctx.clip();
		// Draw big outer circle
		ctx.strokeStyle = 'black';
		arc(ctx, canvasWidth / 2, canvasHeight / 2, sightRadius, 0, 2 * Math.PI);
		ctx.lineWidth = 3;
		// Draw smaller inner circle
		arc(ctx, canvasWidth / 2, canvasHeight / 2, sightCenterRadius, 0,
				2 * Math.PI);
		// Draw point
		arc(ctx, canvasWidth / 2, canvasHeight / 2, sightCenterCenterRadius, 0,
				2 * Math.PI);
		ctx.restore();
	}
	;

	function drawAltitude() {
		var ctx = document.getElementById('altitude').getContext('2d');
		ctx.save();
		ctx.rect(canvasWidth - altBarWidth - altBarRightMargin,
				altBarTopMargin, altBarWidth, altBarHeight);
		ctx.fill();
		ctx.restore();
	}
};