/**
 * 
 */

var cast = window.cast || {};

// Anonymous namespace

(function() {
	'use strict';
	
	Things.PROTOCOL = 'urn:x-cast:kobe.cast.things';
	
	function NewGame(){
		this.castReceiverManager_ = cast.receiver.CastReceiverManager.getInstance();
		this.castMessageBus_ = this.castReceiverManager_.getCastMessageBus(Things.PROTOCOL,
				cast.receiver.cast.CastMessageBus.MessageType.STRING);
		this.castMessageBus_.onMessage = this.onMessage.bind(this);
		this.castReceiverManager_.onSenderConnected = this.onSenderConnected.bind(this);
		this.castReceiverManager_.onSenderDisconnected = this.onSenderDisconnected.bind(this);
		
		this.castReceiverManager_.start();
	}
	
	
	/**
	 * Object model for the game. should track events and data that can be passed 
	 * between players
	 */
	ThingsGame.prototype = {
			
			/**
			 * sender connected event
			 * @param {event} event the sender connected
			 * update scoreboard ui with sender info
			 */
			onSenderConnected: function(event){
				this.castReceiverManager_.getSenders().length;
			},
			
			/**
			 * sender disconnect event
			 * @param {event} event the sender disconnected
			 * store disconnected sender's data incase of reconnection?
			 * terminate if last sender
			 */
			onSenderDisconnected: function(event){
				if(this.castReceiverManager_.getSenders() == 0 &&
						event.reason == cast.receiver.system.DisconnectReason.REQUESTED_BY_SENDER){
					window.close();
				}
			},
			
			/**
			 * Message received event; main communication avenue for senders
			 * parse message and determine which functions to call
			 */
			onMessage: function(event){
				var message = event.data;
				var senderId = event.senderId;
				if(message.command == 'update'){
					this.updateUI(senderId, message);
				} else if(message.command == 'join'){
					this.onJoin(senderId, message);
				}
			},
			
			/**
			 * onJoin; command that player joined was received
			 * @param {string} senderId
			 * @param {string} message "name:Sebastian"
			 */
			onJoin: function(senderId, message){
				//parse player name and update scoreboard
				var playername = message.split(":")[1];
				var nameDisplay = document.getElementById("playerNames");
				var scoreDisplay = document.getElementById("playerScores");
				
				var newplayer = document.createElement('td');
				newplayer.setAttribute('id', senderId + "_name");
				newplayer.innerHTML = playername;
				nameDisplay.appendChild(newplayer);
				
				var newscore = document.createElement('td');
				newscore.setAttribute('id', senderId + "_score");
				newscore.innerHTML = "0";
				scoreDisplay.appendChild(newscore);
				
			}
	}
	
	
})();

