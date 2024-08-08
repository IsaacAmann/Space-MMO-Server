extends Node2D

var timeAlive
# Called when the node enters the scene tree for the first time.
func _ready():
	timeAlive = 0
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	timeAlive += delta
	if(timeAlive >= 8):
		self.queue_free()

