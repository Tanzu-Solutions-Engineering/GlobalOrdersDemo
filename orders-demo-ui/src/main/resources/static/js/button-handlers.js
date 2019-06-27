
function toggleGenerator(holder) {
	
	if (holder.generatorStreaming) {
		$.getJSON("https://" + holder.config.generatorHost + "/stopStream",
				function(data) {
					//
				});
	} else {
		$.getJSON("https://" + holder.config.generatorHost + "/startStream",
				function(data) {
					//
				});
	}
}

function killGenerator(holder) {
	$.getJSON("https://" + holder.config.generatorHost + "/killApp", function(data) {
		//
	});
}

function killMonitor(holder) {
	$.getJSON("https://" + holder.config.monitorHost + "/killApp", function(data) {
		//
	});
}