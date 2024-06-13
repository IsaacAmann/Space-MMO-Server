extends Node

var socket = WebSocketPeer.new()
var sent = false

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
			print("packet: ", socket.get_packet())
	elif state == WebSocketPeer.STATE_CLOSING:
		
		pass
	elif state == WebSocketPeer.STATE_CLOSED:
		var code = socket.get_close_code()
		var reason = socket.get_close_reason()
		print("Web socket closed with code %d reason %s", code ,reason);
		set_process(false)
