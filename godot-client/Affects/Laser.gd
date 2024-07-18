extends Node2D

@export
var color: Color = Color.RED
@export
var startPosition: Vector2 = Vector2(0,0)
@export 
var endPosition: Vector2 = Vector2(1000,1000)
@export
var duration: float = 5.3

var timeAlive: float

# Called when the node enters the scene tree for the first time.
func _ready():
	color = Color.RED
	startPosition = Vector2(0,0)
	endPosition = Vector2(1000, 1000)
	duration = 5.3
	timeAlive = 0


func _draw():
	draw_line(startPosition, endPosition, color, 5, true)

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	timeAlive += delta
	
	#Check if affect has expired
	if timeAlive >= duration:
		self.queue_free()
	pass
