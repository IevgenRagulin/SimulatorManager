var dataaaAltitude = [];
var initializedAltitude = false;
var seriesAltitude;
var altitudeCssId;
var titleAlt = "Altitude";
var altitudeCompomentObj;
cz_vutbr_fit_simulatormanager_jscomponents_jshighchart_JsHighChartAltitude = function() {
	//to make object accessible from outside of the function; Used to make RPC calls to the server
	window.altitudeCompomentObj = this;
	window.initializedAltitude = false;
	//set css id of the component
	window.altitudeCssId = this.getState().cssid;
	window.dataaaAltitude = $.parseJSON(this.getState().data);
	$(document).ready(readDataAndDrawAltitude(window.altitudeCssId));
	
	//Handle state change on server
	this.onStateChange = function() {
		var newPoint = this.getState().n;
		//-100 means that newPoint variable was not initialized
		// we add points to graph only if simulation is not paused. currentPaused variable is in flightControls.js
		if ((newPoint != -100)&&(!window.currentPaused)) {
			var x = (new Date()).getTime();// current time. TODO: Instead of
			// generating time here, pass it from
			// server
			window.seriesAltitude.addPoint([ x, newPoint ], true, false);
		} else {
			window.seriesAltitude.show();
		}
	};
};


//get the id of a point which is the closest to click
function findIdClosestToClick(time) {
	var closestId = window.dataaaAltitude[0].id;
	var closestI = 0;
	for (var i = 0; i < window.dataaaAltitude.length; i++) {
		if (Math.abs(window.dataaaAltitude[i].x - time) < Math
				.abs(window.dataaaAltitude[closestI].x - time)) {
			closestId = window.dataaaAltitude[i].id;
			closestI = i;
		}
	}
	return closestId;
}

function getTimeCorrespondingToId(id) {
	for (var i = 0; i < window.dataaaAltitude.length; i++) {
		if (window.dataaaAltitude[i].id == id) {
			return window.dataaaAltitude[i].x;
		}
	}
}

function readDataAndDrawAltitude(cssid) {
	Highcharts.setOptions({
		global : {
			timezoneOffset: -120
		}
	});
	$('#' + cssid).highcharts(
			'StockChart',
			{
				chart : {
					events : {
						load : function() {
							if (!window.initializedAltitude) {
								window.initializedAltitude = true;
								window.seriesAltitude = this.series[0];
							}

						},
						//when the user clicks on a graph, we determine the id closest to click and send it to the backend (see JsHighChart constructor)
						click : function(event) {
							var id = findIdClosestToClick(event.xAxis[0].value);
							altitudeCompomentObj.onClick(id, getTimeCorrespondingToId(id));
						}
					}
				},

				rangeSelector : {
					buttons : [ {
						count : 1,
						type : 'minute',
						text : '1M'
					}, {
						count : 5,
						type : 'minute',
						text : '5M'
					}, {
						count : 10,
						type : 'minute',
						text : '10M'
					}, {
						count : 30,
						type : 'minute',
						text : '30M'
					}, {
						type : 'all',
						text : 'All'
					} ],
					inputEnabled : false,
					selected : 0
				},

				title : {
					text : window.titleAlt
				},

				series : [ {
					name : window.titleAlt,
					data : window.dataaaAltitude,
					tooltip : {
						valueDecimals : 2
					},
					animation: false,
					turboThreshold: 0//if we don't set it, graph stops working when there are more then 1000 points
				} ],
				
				
			});
}
