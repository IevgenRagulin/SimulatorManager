var dataaaSpeed = []; 
var initializedSpeed = false;
var seriesSpeed;
var speedCssId;
var titleSpeed = "Indicated airspeed";
var speedCompomentObj;
com_example_testvaadin_jscomponents_jshighchart_JsHighChartSpeed = function() 
 {
	//to make object accessible from outside of the function; Used to make RPC calls to the server
	window.speedCompomentObj = this;
	window.initializedSpeed = false;
	//set css id of the component
    window.speedCssId = this.getState().cssid;
	window.dataaaSpeed = $.parseJSON(this.getState().data);
	$(document).ready(readDataAndDrawSpeed(window.speedCssId));
	
    this.onStateChange = function() 
    {
    	var newPoint = this.getState().n;
    	if (newPoint != -100) {//-100 means that newPoint variable was not initialized
			var x = (new Date()).getTime();// current time. TODO: Instead of
											// generating time here, pass it from
											// server
			window.seriesSpeed.addPoint([ x, newPoint ], true, false);
		} 
    };
    
    function readDataAndDrawSpeed(cssid)
    {
		$('#'+cssid).highcharts('StockChart', {
			chart : {
				events : {
					load : function() {
						// set up the updating of the chart each second
						if (!window.initializedSpeed) {
							window.initializedSpeed = true;
							window.seriesSpeed = this.series[0];
						};
					},
					
					click : function(event) {
						//Function findIdClosestToClick is in js_highchart_altitude.js file
						var id = findIdClosestToClick(event.xAxis[0].value);
						speedCompomentObj.onClick(id, getTimeCorrespondingToId(id));
					}
				}
			},

			rangeSelector: {
				buttons: [{
					count: 1,
					type: 'minute',
					text: '1M'
				}, {
					count: 5,
					type: 'minute',
					text: '5M'
				}, {
					count: 10,
					type: 'minute',
					text: '10M'
				}, {
					count: 30,
					type: 'minute',
					text: '30M'
				},
				{
					type: 'all',
					text: 'All'
				}],
				inputEnabled: false,
				selected: 0
			},

			title : {
				text : window.titleSpeed
			},
			
			series : [{
				name : window.titleSpeed,
				data : window.dataaaSpeed,
				tooltip: {
					valueDecimals: 2
				},
				animation: false,
				turboThreshold: 0//if we don't set it, graph stops working when there are more then 1000 points
			}]
		});
    }
};