extends Node2D

var laser = preload("res://Affects/Laser.tscn")
# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	
	pass
	
func createLaserAffect(startPosition: Vector2, endPosition: Vector2, duration: float, color: Color):
	var instance = laser.instantiate()
	get_tree().root.add_child(instance)
	instance.color = color
	instance.startPosition = startPosition
	instance.endPosition = Vector2(endPosition)
	instance.duration = duration

	pass

func handleLaserPacket(packet):
	var startX: float
	var startY: float
	
	var endX: float
	var endY: float
	
	var duration: float
	
	var red: float
	var green: float
	var blue: float
	
	var currentIndex = 1

	startX = packet.decode_float(currentIndex)
	currentIndex += 4
	
	startY = packet.decode_float(currentIndex)
	currentIndex += 4
	
	endX = packet.decode_float(currentIndex)
	currentIndex += 4
	
	endY = packet.decode_float(currentIndex)
	currentIndex += 4
	
	duration = packet.decode_float(currentIndex)
	currentIndex += 4
	
	red = packet.decode_float(currentIndex)
	currentIndex += 4
	
	green = packet.decode_float(currentIndex)
	currentIndex += 4
	
	blue = packet.decode_float(currentIndex)
	currentIndex += 4
	
	createLaserAffect(Vector2(startX, startY), Vector2(endX, endY), duration, Color(red, green, blue))

