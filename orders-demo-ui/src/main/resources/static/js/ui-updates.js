function updateGeneratorStatus(holder) {
	
	$.getJSON("https://" + holder.config.generatorHost + "/status")
		.done(	
			function(data) {
				$("#generator-running").text(
						data.stopped ? "Stopped" : "Running");
				
				$("#generator-streaming").text(
						data.streaming ? "Active" : "Stopped");
				
				$("#generator-sent").html(
						"<b>" + data.messagesSent + "</b>");
				
				$("#generator-rmq").text(
						data.rabbitHost);

				if (data.streaming) {
					$("#generator-toggle").val("Stop");
				} else {
					$("#generator-toggle").val("Start");
				}
				
				holder.generatorStreaming = data.streaming;

			})
		.fail(
				function(jqxhr, textStatus, error) {
					$("#generator-running").text(textStatus + error);
			});
	setTimeout(updateGeneratorStatus, 1000, holder);
}


function updateMonitorStatus(holder) {
	$.getJSON("https://" + holder.config.monitorHost + "/status")
			.done(
					function(data) {
						$("#monitor-running").text("Running");
						$("#monitor-processed").html(
								"<b>" + data.messagesProcessed
										+ "</b>");
						$("#monitor-rmq").text(
								data.rabbitHost);
					})
			.fail(function(jqxhr, textStatus, error) {
				$("#monitor-running").text(textStatus + error);
			});
	setTimeout(updateMonitorStatus, 1000, holder);
}