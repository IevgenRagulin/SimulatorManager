var dataaaAltitude = []; 
var initializedAltitude = false;
var seriesAltitude;
var altitudeCssId;
var titleAlt = "Altitude";

com_example_testvaadin_jscomponents_jshighchart_JsHighChartAltitude = function() 
 { 
    window.altitudeCssId = this.getState().cssid;
	window.dataaaAltitude = $.parseJSON(this.getState().data);
	$(document).ready(readDataAndDrawAltitude(window.altitudeCssId));

    this.onStateChange = function() 
    {
    	if (window.dataaaAltitude.length==0) {
    		window.dataaaAltitude = $.parseJSON(this.getState().data);
    		$(document).ready(readDataAndDrawAltitude(window.altitudeCssId));
    	}
    	var newPoint = this.getState().n;
    	var x = (new Date()).getTime();// current time. TODO: Instead of generating time here, pass it from server
    	window.seriesAltitude.addPoint([x,newPoint], true, false);
    };
    
    function readDataAndDrawAltitude(cssid)
    {
		$('#'+cssid).highcharts('StockChart', {
			chart : {
				events : {
					load : function() {
						// set up the updating of the chart each second
						if (!window.initializedAltitude) {
							window.initializedAltitude = true;
							window.seriesAltitude = this.series[0];
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
				text : window.titleAlt
			},
			
			series : [{
				name : window.titleAlt,
				data : window.dataaaAltitude,
				tooltip: {
					valueDecimals: 2
				}
			}]
		});
    }
};