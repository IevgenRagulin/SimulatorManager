var dataaaAltitude = [];
var initializedAltitude = false;
var seriesAltitude;
var altitudeCssId;
var titleAlt = "Altitude";
var altitudeCompomentObj;
com_example_testvaadin_jscomponents_jshighchart_JsHighChartAltitude = function() {
	//to make object accessible from outside of the function; Used to make RPC calls to the server
	window.altitudeCompomentObj = this;
	//set css id of the component
	window.altitudeCssId = this.getState().cssid;
	window.dataaaAltitude = $.parseJSON(this.getState().data);
	console.log("data"+this.getState().data);
	console.log("data"+window.dataaaAltitude);
	$(document).ready(readDataAndDrawAltitude(window.altitudeCssId));
	//Handle state change on server
	this.onStateChange = function() {
		var newPoint = this.getState().n;
		console.log("data state changed"+newPoint);
		if (newPoint != -100) {//-100 means that newPoint variable was not initialized
			var x = (new Date()).getTime();// current time. TODO: Instead of
			// generating time here, pass it from
			// server
			window.seriesAltitude.addPoint([ x, newPoint ], true, false);
		} else {
			//window.seriesAltitude.show();
			console.log("series data: "+window.seriesAltitude.data);
			//window.seriesAltitude.addPoint([ 0, 0], true, false);
		}
	};

	// Default implementation of the click handler
	 //this.click = function () {
	 //alert("Error: Must implement click() method");
	 //};
	// Pass user interaction to the server-side
   // this.onClick
	//var self = this; //can't use self inside of a function
    //this.click = function() {
    //    self.onClick(mycomponent.getValue());
    //};
};



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
	console.log("DIF CLOSEST ID" + closestId);
	return closestId;
}

function readDataAndDrawAltitude(cssid) {
	console.log("read data and draw altitude");
	$('#' + cssid).highcharts(
			'StockChart',
			{
				chart : {
					events : {
						load : function() {
							if (!window.initializedAltitude) {
								window.initializedAltitude = true;
								window.seriesAltitude = this.series[0];
								console.log("this.series[0].data"+this.series[0].data);
							}

						},
						click : function(event) {
							var id = findIdClosestToClick(event.xAxis[0].value);
							altitudeCompomentObj.onClick(id);
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
