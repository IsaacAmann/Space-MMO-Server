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
	var currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	startX = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	startY = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	endX = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	endY = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	duration = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	red = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	green = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = packet.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	blue = currentSlice.decode_float(0)
	currentIndex += 4
	
	createLaserAffect(Vector2(startX, startY), Vector2(endX, endY), duration, Color(red, green, blue))

