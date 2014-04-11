var dataaaSpeed = []; 
var initializedSpeed = false;
var seriesSpeed;
var speedCssId;
var titleSpeed = "Indicated airspeed";
com_example_testvaadin_jscomponents_jshighchart_JsHighChartSpeed = function() 
 {
     window.speedCssId = this.getState().cssid;
    this.onStateChange = function() 
    {
    	if (window.dataaaSpeed.length==0) {
    		window.dataaaSpeed = $.parseJSON(this.getState().data);
    		$(document).ready(readDataAndDrawSpeed(window.speedCssId));
    	}
    	var newPoint = this.getState().n;
    	var x = (new Date()).getTime();// current time. TODO: Instead of generating time here, pass it from server
    	window.seriesSpeed.addPoint([x,newPoint], true, false);
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
				}
			}]
		});
    }
};