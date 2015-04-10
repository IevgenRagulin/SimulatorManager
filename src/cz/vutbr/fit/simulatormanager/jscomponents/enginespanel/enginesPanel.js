
function cz_vutbr_fit_simulatormanager_jscomponents_enginespanel_EnginesPanel() {
	console.log("KUKU panel"+this.getState().rpm);
	console.log("KUKU panel"+this.getState().minrpm);
	this.onStateChange = function() {
		console.log("KUKU panel"+this.getState().rpm);
		console.log("KUKU panel"+this.getState().minrpm);
	}
}