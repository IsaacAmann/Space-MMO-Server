extends Node

var socket = WebSocketPeer.new()

# Called when the node enters the scene tree for the first time.
func _ready():

	socket.connect_to_url("ws://localhost:8080/openGameSession");


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
