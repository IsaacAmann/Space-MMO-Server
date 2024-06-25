extends Node

var socket = WebSocketPeer.new()

var sent = false

signal entity_update_message(message)
signal new_entity_message(message)
signal entity_delete_message(message)
signal sector_join_message(message)
signal error_message(message)



# Called when the node enters the scene tree for the first time.
func _ready():
	print("connecting")
	var error = socket.connect_to_url("ws://localhost:8080/openGameSession/Cnidarian/8VGLXBaAnP89vWC27VBj0w==");
	print(error);
	


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
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
			print("packet: ", packet)
			#Get message type
			var type = packet.decode_s8(0)
			match type:
				#player info
				1:
					print("player info")
				#sector join
				2:
					print("sector join")
				#error
				3:
					print("error")
				#join debug
				4:
					print("join debug")
				
				#entity update
				5:
					print("entity update")
				
				#new entity notification
				6: 
					print("new entity notification")
	elif state == WebSocketPeer.STATE_CLOSING:
		
		pass
	elif state == WebSocketPeer.STATE_CLOSED:
		var code = socket.get_close_code()
		var reason = socket.get_close_reason()
		print("Web socket closed with code %d reason %s", code ,reason);
		set_process(false)

func sendMessage(message: PackedByteArray):
	socket.send(message)
	pass
