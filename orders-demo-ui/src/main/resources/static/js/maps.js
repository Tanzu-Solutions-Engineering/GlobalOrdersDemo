
function initialiseMap(holder) {
	//Create map instance
	chart = am4core.create("chartdiv", am4maps.MapChart);
	holder.chart = chart;

	//Set map definition
	chart.geodata = am4geodata_worldHigh;

	//Set projection
	//chart.projection = new am4maps.projections.AlbersUsa();

	polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());
	polygonSeries.useGeodata = true;
	holder.polygonSeries = polygonSeries;

	//Configure series
	var polygonTemplate = polygonSeries.mapPolygons.template;
	polygonTemplate.tooltipText = "{name}";
	polygonTemplate.fill = am4core.color("#74B266");

	// Create hover state and set alternative fill color
	var hs = polygonTemplate.states.create("hover");
	hs.properties.fill = am4core.color("#367B25");

	polygonSeries.heatRules.push({
		"property" : "fill",
		"target" : polygonSeries.mapPolygons.template,
		"min" : am4core.color("#073F07"),
		"max" : am4core.color("#FF0000")
	});

	var heatLegend = chart.createChild(am4maps.HeatLegend);
	heatLegend.series = polygonSeries;
	//heatLegend.width = am4core.percent(100);
	heatLegend.orientation = "vertical";
	heatLegend.paddingTop = 30;

	polygonSeries.mapPolygons.template.events.on("over", function(ev) {
		if (!isNaN(ev.target.dataItem.value)) {
			heatLegend.valueAxis.showTooltipAt(ev.target.dataItem.value)
		} else {
			heatLegend.valueAxis.hideTooltip();
		}
	});

	polygonSeries.mapPolygons.template.events.on("out", function(ev) {
		heatLegend.valueAxis.hideTooltip();
	});
}

function updateHistogram(holder) {
	
	$.getJSON("/getHeatMap").done(function(data) {

		// Populate the data map if needed to speed up the updates
		if (holder.dataMap == false) {
			holder.dataMap = [];
			for (var j = 0; j < holder.polygonSeries.data.length; j++) {
				holder.dataMap[holder.polygonSeries.data[j].id] = j;
			}
		}

		// Clear the old values
		for (var i = 0; i < holder.polygonSeries.data.length; i++) {
			holder.polygonSeries.data[i].value = 0;
		}

		var n = data.items.length;
		for (var i = 0; i < n; i++) {
			// Find the corresponding entry in the polygonSeries

			var j = holder.dataMap[data.items[i].state.toUpperCase()];
			if (j >= 0) {
				holder.polygonSeries.data[j].value = data.items[i].value;
			}

		}
		//refresh the chart
		holder.chart.invalidateRawData();
	}).fail(function(jqxhr, textStatus, error) {

	});
	setTimeout(updateHistogram, 1000, holder);

};