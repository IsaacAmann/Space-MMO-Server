extends Camera2D

var speed = 15
var zoomSpeed = Vector2(0.2, 0.2)
var target_position = Vector2.ZERO
var zoomLevel = 0

var entityHandler

# Called when the node enters the scene tree for the first time.
func _ready():
	entityHandler = get_node("../EntityHandler")
	target_position = position

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	if Input.is_action_pressed("CameraUp"):
		target_position.y -= speed * delta
	if Input.is_action_pressed("CameraDown"):
		target_position.y += speed * delta
	if Input.is_action_pressed("CameraLeft"):
		target_position.x -= speed * delta
	if Input.is_action_pressed("CameraRight"):
		target_position.x += speed * delta

	var playerShip = entityHandler.getPlayerShip()
	if playerShip != null:
		target_position = playerShip.position

	# Directly set camera position to target position
	position = target_position

func _input(event):
	if event.is_action_pressed("CameraZoomIn") && zoomLevel < 6:
		zoom += zoomSpeed
		zoomLevel += 1
	if event.is_action_pressed("CameraZoomOut") && zoomLevel > -1:
		zoom -= zoomSpeed
		zoomLevel -= 1
