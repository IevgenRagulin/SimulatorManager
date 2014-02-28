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
var currentPitch = 80;
var currentRoll = 0;
var currentYaw = 0;

var currentlyChangingRoll = false;
var currentlyChangingPitch = false;
var currentlyChangingAlt = false;
var currentlyChangingSpeed = false;

var horizontWidth = 500;

var isItFirstLoad = true;

var currentlyRotatingHorizont = false;
var horizontHasBeenRotated = false;

function com_example_testvaadin_javascriptcomponents_PrimaryFlightDisplay (){
	var e = this.getElement();

	this.onStateChange = function() {
		console.log("PFD STATE CHANGED");
		if (window.isItFirstLoad) {
			console.log("INIT HTML AND EVERYTHING");
			initHtml();
			init();
			window.isItFirstLoad = false;
		}
		;
		update(this.getState().speed, this.getState().altitude,
				this.getState().roll, this.getState().pitch,
				this.getState().yaw);
	};
	
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
}

	function update(speed, altitude, roll, pitch, yaw) {
		console.log("NEW ROLL" + roll);
		console.log("curent roll"+window.currentRoll);
		if (!window.currentlyChangingPitch) {
			setPitch(pitch);
			console.log("PITCH HAS BEEN CHANGED"+pitch);
		}
		if (!window.currentlyChangingRoll) {
			var ctx = document.getElementById('pfd').getContext('2d');
			//rotatePfdByRollDegrees(ctx, currentRoll);
			setRoll(roll);
		}
		if (!window.currentlyChangingSpeed) {
			setSpeed(speed);
		}
		if (!window.currentlyChangingAltitude) {
			setAltitude(altitude);
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
	}

	function init() {
		drawSpeedIndicator();
		drawHeightIndicator();
		drawSight();
		setClickListeners();
	}

	function setAddPitchClickListener() {
		document.getElementById('addPitch').addEventListener('click',
				function() {
					var newPitch = (window.currentPitch + 10) % 360;
					if (!window.currentlyChangingPitch) {
						setPitch(newPitch);
					}
				});
	}

	function setMinusPitchClickListener() {
		document.getElementById('minusPitch').addEventListener('click',
				function() {
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
		document.getElementById('addRoll').addEventListener('click',
				function() {
					console.log(window.currentlyChangingRoll);
					if (!window.currentlyChangingRoll) {
						setRoll(window.currentRoll + 60);
					}
				});
	}

	function setMinusRollClickListener() {
		document.getElementById('minusRoll').addEventListener('click',
				function() {
					if (!window.currentlyChangingRoll) {
						console.log("CURRENT ROLL IN ANON FUNCTION"+window.currentRoll);
						setRoll(window.currentRoll - 60);
					}
				});
	}

	function setAddAltitudeClickListener() {
		document.getElementById('addAltitude').addEventListener('click',
				function() {
					console.log("TRYING TO PLUS ALTITUDE");
					if (!window.currentlyChangingAlt) {
						setAltitude(window.currentAltitude + 50);
					}
				});
	}

	function setMinusAltitudeClickListener() {
		document.getElementById('minusAltitude').addEventListener('click',
				function() {
					console.log("TRYING TO MINUES ALTITUDE");
					if (!window.currentlyChangingAlt) {
						setAltitude(window.currentAltitude - 50);
					}
				});
	}

	function setAddSpeedClickListener() {
		document.getElementById('addSpeed').addEventListener('click',
				function() {
					if (!window.currentlyChangingSpeed) {
						setSpeed(window.currentSpeed + 50);
					}
				});
	}

	function setMinusSpeedClickListener() {
		document.getElementById('minusSpeed').addEventListener('click',
				function() {
					if (!window.currentlyChangingSpeed) {
						setSpeed(window.currentSpeed - 50);
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
		ctx.fillStyle = '0080ff';
		fillRect(ctx, x, y, w, h);
	}

	function fillGround(ctx, x, y, w, h) {
		ctx.fillStyle = '804000';
		fillRect(ctx, x, y, w, h);
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
		return ((difPitch > 0.3)||(difPitch < -0.3));
	}

	function setPitch(pitch) {
		// Check if we should continue animating pitch
		var difPitch = pitch - window.currentPitch;
		if (shouldWeChangePitch(difPitch)) {
			requestAnimationFrame(function() {
				setPitch(pitch);
			});
			difPitch = 0.3 * calculateDirection(pitch, window.currentPitch);
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
		drawLineNumbersForSight(newPitch);
		
		//ctx.save();
		drawArtificialHorizon(ctx, window.currentRoll, newPitch, window.currentYaw);
		window.currentPitch = newPitch;
		//ctx.restore();
		document.getElementById('pitchV').innerHTML = window.currentPitch;
	}
	
	function setRoll(roll) {
		var difRoll = roll - window.currentRoll;
		// Check if we should continue animating roll
		var ctx = document.getElementById('pfd').getContext('2d');
		if (shouldWeRoll(difRoll)) {
			requestAnimationFrame(function() {
				setRoll(roll);
			});
			difRoll = 0.5 * calculateDirection(roll, window.currentRoll);
			window.currentlyChangingRoll = true;
		} else {
			window.currentlyChangingRoll = false;
		}
		
		
		rotatePfdByRollDegrees(ctx, difRoll);
		difRoll = window.currentRoll + difRoll;
		window.currentRoll = difRoll;
		
		//ctx.save();
		drawArtificialHorizon(ctx, window.currentRoll, window.currentPitch, window.currentYaw);
		//ctx.restore();
		document.getElementById('rollV').innerHTML = window.currentRoll;
	}

	function shouldWeRoll(difRoll) {
		//console.log("SHOULD WE CHANGEROLL. Diff roll: "+difRoll+" Current roll: "+currentRoll);
		return ((difRoll > 0.2) || (difRoll < -0.2));
	}
	
	


	function rotatePfdByRollDegrees(ctx, roll) {
		ctx.translate(window.canvasHeight / 2, window.window.canvasWidth / 2);
		ctx.rotate(roll * Math.PI / 180);
		ctx.translate(-window.canvasHeight / 2, -window.window.canvasWidth / 2);
	}

	function drawArtificialHorizon(ctx, roll, pitch, yaw) {
		//console.log('draw artificial horizon called');
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
			fillSky(ctx, skyGroundLeftX, skyBottomY, window.horizontWidth, skyBottom);
			fillGround(ctx, skyGroundLeftX, grBottomY, window.horizontWidth, grBottom);
			ctx.translate(0, 150*5);
		} else {
			console.error("Unexpected pitch value!");
		}
	}

	function cropLinesNumberOutsideSight(ctxSight) {
		ctxSight.beginPath();
		ctxSight.arc(window.window.canvasWidth / 2, window.canvasHeight / 2, window.sightRadius + 1, 0,
				2 * Math.PI, true);
		ctxSight.clip();
	}

	function drawLineNumberHelpFunc(ctxSight, startInt, endInt, difPitch, direction) {
		for (var i = startInt; i <= endInt; i = i + 2.5) {
			if (((i % 5) == 0) && (i != startInt)) {
				ctxSight.fillText(i, window.window.canvasWidth / 2 - 45, window.canvasHeight / 2 + i * 5
						* direction + 3 + difPitch * 5);
				ctxSight.moveTo(window.window.canvasWidth / 2 - 30, window.canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
				ctxSight.lineTo(window.window.canvasWidth / 2 + 30, window.canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
			} else if (((i % (2.5)) == 0) && (i != startInt)) {
				ctxSight.moveTo(window.window.canvasWidth / 2 - 15, window.canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
				ctxSight.lineTo(window.window.canvasWidth / 2 + 15, window.canvasHeight / 2 + i * 5
						* direction + difPitch * 5);
			}
		}
	}
	
	function rotateSightFor180DigreesArountPoint(ctxSight, xRotationPoint, yRotationPoint) {
		window.currentlyRotatingHorizont = true;
		window.horizontHasBeenRotated = true;
		ctxSight.translate(x, y);
		ctxSight.rotate(roll * Math.PI / 180);
		//ctxSight.translate(-x, -window.window.canvasWidth / 2);
	}

	function drawLineNumbersForSight(difPitch) {
		var ctxSight = document.getElementById('sight').getContext('2d');
		// Transform 180-360 values to (-180;-0)
		if (difPitch > 180) {
			difPitch = difPitch - 360;
		}
		if ((difPitch>90)&&(!window.horizontHasBeenRotated)&&(!window.currentlyRotatingHorizont)) {
			console.log ("SHOULD ROTATE"+difPitch);
			var x = window.canvasWidth/2;
			var y = 90*5;
			rotateSightFor180DigreesArountPoint(ctxSight, x, y);
		} else {
			console.log("SHOULDNT ROTATE"+difPitch);
		}
		
		cropLinesNumberOutsideSight(ctxSight);
		ctxSight.clearRect(0, 0, window.canvasHeight, window.window.canvasWidth);
		drawSight();
		ctxSight.save();
		ctxSight.beginPath();
		ctxSight.strokeStyle = 'white';
		ctxSight.fillStyle = 'white';
		// draw numbers for degrees 0-180
		console.log("DRAW NUMBER FOR SIGHT");
		drawLineNumberHelpFunc(ctxSight, 0, 90, difPitch, -1);
		// draw numbers for degrees 180-360
		//drawLineNumberHelpFunc(ctxSight, 180, 360, difPitch, -1);
		// draw numbers for degrees 0 - (-180)
		drawLineNumberHelpFunc(ctxSight, 0, 90, difPitch, 1);
		// draw numbers for degrees -180-(-360)
		//drawLineNumberHelpFunc(ctxSight, 180, 360, difPitch, 1);
		ctxSight.stroke();
		ctxSight.restore();
	}

	function setSpeed(speed) {
		console.log('set speed called');
		var ctxSpeed = document.getElementById('speed').getContext('2d');
		var difSpeed = speed - window.currentSpeed;
		var difSpeedStep = calculateAltitudeSpeedStep(difSpeed);
		if (difSpeed > 0) {
			window.currentlyChangingSpeed = true;
			requestAnimationFrame(function() {
				setSpeed(speed);
			});
		} else if (difSpeed < 0) {
			window.currentlyChangingSpeed = true;
			requestAnimationFrame(function() {
				setSpeed(speed);
			});
		} else {
			window.currentlyChangingSpeed = false;
		}
		var newSpeed = window.currentSpeed + difSpeedStep;
		window.currentSpeed = newSpeed;

		clearRect(ctxSpeed, window.leftSpeedBarMargin, window.topSpeedBarMargin, window.speedBarWidth,
				window.speedBarHeight);
		ctxSpeed.save();
		ctxSpeed.fillStyle = 'black';
		fillRect(ctxSpeed, window.leftSpeedBarMargin, window.topSpeedBarMargin, window.speedBarWidth,
				window.speedBarHeight);

		// Speed marks and numbers
		ctxSpeed.translate(window.leftSpeedBarMargin, window.topSpeedBarMargin + window.speedBarHeight);
		ctxSpeed.beginPath();
		ctxSpeed.lineWidth = 2;
		ctxSpeed.strokeStyle = 'white';
		ctxSpeed.fillStyle = 'white';
		ctxSpeed.font = '8pt Calibri';

		// draw lines with numbers
		for (var i = 0; i < 1000; i += 20) {
			ctxSpeed.moveTo(20, -i * 2 - 3 - window.speedBarHeight / 2 + newSpeed * 2);
			ctxSpeed.lineTo(30, -i * 2 - 3 - window.speedBarHeight / 2 + newSpeed * 2);
			ctxSpeed.fillText(i, 0, -i * 2 - window.speedBarHeight / 2 + newSpeed * 2);
			// draw lines in between the numbers
			ctxSpeed.moveTo(25, -i * 2 - 3 - window.speedBarHeight / 2 + newSpeed * 2 - 20);
			ctxSpeed.lineTo(30, -i * 2 - 3 - window.speedBarHeight / 2 + newSpeed * 2 - 20);
		}
		ctxSpeed.stroke();
		ctxSpeed.restore();
		document.getElementById('speedV').innerHTML = window.currentSpeed;

	}

	function calculateAltitudeSpeedStep(dif) {
		var difAltitudeStep;
		if (dif > 0) {
			if (dif > 50) {
				difAltitudeStep = 10;
			} else {
				difAltitudeStep = 1;
			}
		} else if (dif < 0) {
			if (dif < -50) {
				difAltitudeStep = -10;
			} else {
				difAltitudeStep = -1;
			}
		} else {
			difAltitudeStep = 0;
		}
		return difAltitudeStep;
	}

	function setAltitude(altitude) {
		console.log('set alt called');
		console.log("current alt"+window.currentAltitude+" new alt"+altitude);

		var ctxAltitude = document.getElementById('altitude').getContext('2d');
		var difAltitude = altitude - window.currentAltitude;
		var difAltitudeStep = calculateAltitudeSpeedStep(difAltitude);
		if (difAltitude > 0) {
			requestAnimationFrame(function() {
				window.currentlyChangingAlt = true;
				setAltitude(altitude);
			});
		} else if (difAltitude < 0) {
			window.currentlyChangingAlt = true;
			requestAnimationFrame(function() {
				setAltitude(altitude);
			});
		} else {
			window.currentlyChangingAlt = false;
		}

		newAltitude = window.currentAltitude + difAltitudeStep;
		window.currentAltitude = newAltitude;

		clearRect(ctxAltitude, window.window.canvasWidth - window.altBarRightMargin - window.altBarWidth,
				window.altBarTopMargin, window.altBarWidth, window.altBarHeight);
		ctxAltitude.save();
		ctxAltitude.fillStyle = 'black';
		fillRect(ctxAltitude, window.window.canvasWidth - window.altBarRightMargin - window.altBarWidth,
				window.altBarTopMargin, window.altBarWidth, window.altBarHeight);

		// Speed marks and numbers
		ctxAltitude.translate(window.window.canvasWidth - window.altBarRightMargin - window.altBarWidth,
				window.altBarTopMargin + window.altBarHeight);
		ctxAltitude.beginPath();
		ctxAltitude.lineWidth = 2;
		ctxAltitude.strokeStyle = 'white';
		ctxAltitude.fillStyle = 'white';
		ctxAltitude.font = '8pt Calibri';

		// draw lines with numbers
		for (var i = 0; i < 10000; i += 20) {
			ctxAltitude.moveTo(0, -i * 2 - 3 - window.altBarHeight / 2 + newAltitude * 2);
			ctxAltitude.lineTo(10, -i * 2 - 3 - window.altBarHeight / 2 + newAltitude * 2);
			ctxAltitude.fillText(i, 10, -i * 2 - window.altBarHeight / 2 + newAltitude * 2);
			// draw lines in between the numbers
			ctxAltitude.moveTo(0, -i * 2 - 3 - window.altBarHeight / 2 + newAltitude * 2 - 20);
			ctxAltitude.lineTo(5, -i * 2 - 3 - window.altBarHeight / 2 + newAltitude * 2 - 20);
		}
		ctxAltitude.stroke();
		ctxAltitude.restore();
		document.getElementById('altitudeV').innerHTML = window.currentAltitude;
	}

	function drawSpeedIndicator() {
		// Draw speed indicator
		var ctx = document.getElementById('speed').getContext('2d');
		ctx.save();
		ctx.strokeStyle = 'black';
		ctx.fillStyle = 'black';
		ctx.beginPath();
		ctx.moveTo(window.speedBarWidth + window.leftSpeedBarMargin, window.canvasHeight / 2 - 3);
		ctx.lineTo(window.speedBarWidth + window.leftSpeedBarMargin + window.speedIndicatorWidth,
				window.canvasHeight / 2 - 8);
		ctx.lineTo(window.speedBarWidth + window.leftSpeedBarMargin + window.speedIndicatorWidth,
				window.canvasHeight / 2);
		ctx.lineTo(window.speedBarWidth + window.leftSpeedBarMargin, window.canvasHeight / 2 - 3);
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
		ctx.moveTo(window.window.canvasWidth - window.altBarWidth - window.altBarRightMargin,
				window.canvasHeight / 2 - 3);
		ctx.lineTo(window.window.canvasWidth - window.altBarWidth - window.altBarRightMargin
				- window.speedIndicatorWidth, window.canvasHeight / 2 - 8);
		ctx.lineTo(window.window.canvasWidth - window.altBarWidth - window.altBarRightMargin
				- window.speedIndicatorWidth, window.canvasHeight / 2);
		ctx.lineTo(window.window.canvasWidth - window.altBarWidth - window.altBarRightMargin,
				window.canvasHeight / 2 - 3);
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
		ctx.arc(window.window.canvasWidth / 2, window.canvasHeight / 2, window.sightRadius + 1, 0,
				2 * Math.PI, true);
		ctx.clip();
		// Draw big outer circle
		ctx.strokeStyle = 'black';
		arc(ctx, window.window.canvasWidth / 2, window.canvasHeight / 2, window.sightRadius, 0, 2 * Math.PI);
		ctx.lineWidth = 3;
		// Draw smaller inner circle
		arc(ctx, window.window.canvasWidth / 2, window.canvasHeight / 2, window.sightCenterRadius, 0,
				2 * Math.PI);
		// Draw point
		arc(ctx, window.window.canvasWidth / 2, window.canvasHeight / 2, window.sightCenterCenterRadius, 0,
				2 * Math.PI);
		ctx.restore();
	}
	;

	function drawAltitude() {
		var ctx = document.getElementById('altitude').getContext('2d');
		ctx.save();
		ctx.rect(window.window.canvasWidth - window.altBarWidth - window.altBarRightMargin,
				window.altBarTopMargin, window.altBarWidth, window.altBarHeight);
		ctx.fill();
		ctx.restore();
	}
