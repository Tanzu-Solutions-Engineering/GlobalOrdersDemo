
function toggleGenerator(holder) {
	
	if (holder.generatorStreaming) {
		$.getJSON("http://" + holder.config.generatorHost + "/stopStream",
				function(data) {
					//
				});
	} else {
		$.getJSON("http://" + holder.config.generatorHost + "/startStream",
				function(data) {
					//
				});
	}
}

function killGenerator(holder) {
	$.getJSON("http://" + holder.config.generatorHost + "/killApp", function(data) {
		//
	});
}

function killMonitor(holder) {
	$.getJSON("http://" + holder.config.monitorHost + "/killApp", function(data) {
		//
	});
}