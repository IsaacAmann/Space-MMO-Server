extends Node2D


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func handlerInventoryPakcet(packet: PackedByteArray):
	var currentIndex = 1
	
	var inventoryID = packet.decode_s32(currentIndex)
	currentIndex += 4
	
	var jsonString = packet.slice(currentIndex, packet.size() - currentIndex)
	jsonString = Marshalls.base64_to_raw(jsonString.get_string_from_ascii()).get_string_from_ascii()
	
	var json = JSON.new()
	var error = json.parse(jsonString)
	if error == OK:
		var data = json.data
	else:
		print("JSON error: ", json.get_error_message(), " in ", jsonString, " at line ", json.get_error_line())
	

	
	
	
