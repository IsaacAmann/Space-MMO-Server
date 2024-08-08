extends Node

var socket = WebSocketPeer.new()

var sent = false

signal entity_update_message(message)
signal new_entity_message(message)
signal entity_delete_message(message)
signal sector_join_message(message)
signal error_message(message)
signal chat_message_receive(message)

var entityHandler
var inputHandler
var affectsHandler
var chatHandler

var debugMode = false
var requestComplete = false



# Called when the node enters the scene tree for the first time.
func _ready():
	print("connecting")
	var error
	if debugMode == true:
		error = socket.connect_to_url("ws://localhost:8080/openGameSession/Cnidarian/BDdvbh3M_BZqy7_z07GxvA==")
		requestComplete = true
	else:
		var token = JavaScriptBridge.eval("sessionStorage.getItem('accessToken')")
		var url = JavaScriptBridge.eval("sessionStorage.getItem('url')")
		#var url = "https://winapimonitoring.com"
		var headers = ["Content-Type: application/json", "Authorization: Bearer " + str(token)]
		var httpRequest = HTTPRequest.new()
		add_child(httpRequest)
		var body = JSON.new().stringify({"message": "test"})
		httpRequest.request_completed.connect(self.getTokenRequestComplete)
		httpRequest.request(url + "/api/getGameToken", headers, HTTPClient.METHOD_POST, body)
		
	#var error = socket.connect_to_url("ws://localhost:8080/openGameSession/Cnidarian2/uXVlqbA27aCYXtrIdxpQJw==");
	print(error);
	entityHandler = get_node("../EntityHandler")
	inputHandler = get_node("../InputHandler")
	affectsHandler = get_node("../AffectsHandler")
	chatHandler = get_node("../ChatHandler")
	
func getTokenRequestComplete(result, response_code, headers, body):
	print(body)
	var username = JavaScriptBridge.eval("sessionStorage.getItem('username')")
	var httpUrl = JavaScriptBridge.eval("sessionStorage.getItem('url')")
	
	#var url1 = String('wss://winapimonitoring.com/openGameSession/')
	var url1 = httpUrl + "/openGameSession/"
	if(url1.find("localhost", 0) == -1):
		url1 = url1.replace("https", "wss")
	else:
		url1 = String("ws://localhost:8080/openGameSession/")
	print("line 44: " + str(url1))
	url1 = url1 + username + "/"
	var json = JSON.new()
	json.parse(body.get_string_from_utf8())
	var response = json.get_data()

	url1 = url1 + response.gameSessionToken
	
	print(response.gameSessionToken)
	print("Line 53: " + url1)
	
	socket.connect_to_url(url1)
	requestComplete = true
	pass
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	if requestComplete == true:
		socket.poll()
		var state = socket.get_ready_state()
		if state == WebSocketPeer.STATE_OPEN:
			if(sent == false):
				var value = PackedByteArray()
				value.append(3)
				socket.send(value);
				sent = true
			while socket.get_available_packet_count():
				var packet = socket.get_packet()
				#print("packet: ", packet)
				#Get message type
				var type = packet.decode_s8(0)
				match type:
					#player info
					0:
						print("player info")
					#sector join
					1:
						print("sector join")
					#error
					2:
						print("error")
					#join debug
					3:
						print("join debug")
					
					#entity update
					4:
						entityHandler.handleEntityUpdate(packet)
						#print("entity update")
					
					#new entity notification
					5: 
						entityHandler.handleNewEntity(packet)
						#print("new entity notification")
					#Player input (probably wont be needed by client
					6:
						pass
					#Laser effect
					7:
						affectsHandler.handleLaserPacket(packet)
					8:
						entityHandler.handleEntityDelete(packet)
					9:
						chatHandler.handleChatMessage(packet)
						
		elif state == WebSocketPeer.STATE_CLOSING:
			
			pass
		elif state == WebSocketPeer.STATE_CLOSED:
			var code = socket.get_close_code()
			var reason = socket.get_close_reason()
			print("Web socket closed with code %d reason %s", code ,reason);
			#set_process(false)

func sendMessage(message: PackedByteArray):
	socket.send(message)
	pass
