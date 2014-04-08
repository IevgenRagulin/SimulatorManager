var dataaa = []; 
com_example_testvaadin_jscomponents_jshighchart_JsHighChart = function() 
 {
     var element = $(this.getElement());
         // getData
     var title = this.getState().title;
     window.dataaa = $.parseJSON(this.getState().data);
     var units = this.getState().units;
    
    $(document).ready(readDataAndDraw());
    
    this.onStateChange = function() 
    {
        $(document).ready(readDataAndDraw());
    };
    
    function readDataAndDraw()
    {
        var id = document.getElementById("myJSComponent");
        // double check if we really found the right div
                //if (id == null) return;
        //if(id.id != "myJSComponent") return;
        $.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=aapl-c.json&callback=?', function(dataa) {
        	console.log("dataaa"+window.dataaa[0]);
        	console.log("dataaar"+dataa[0]);
        	console.log("dataa"+dataa);

		$('#myJSComponent').highcharts('StockChart', {
			

			rangeSelector : {
				selected : 1,
				inputEnabled: $('#container').width() > 480
			},

			title : {
				text : 'AAPL Stock Price'
			},
			
			series : [{
				name : 'AAPL',
				data : window.dataaa,
				tooltip: {
					valueDecimals: 2
				}
			}]
		});
    });
    }
};