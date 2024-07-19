extends Camera2D

var speed = 15
var zoomSpeed = Vector2(.2,.2)

var entityHandler
# Called when the node enters the scene tree for the first time.
func _ready():
	entityHandler = get_node("../EntityHandler")

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	if(Input.is_action_pressed("CameraUp")):
		position.y -= speed
	if(Input.is_action_pressed("CameraDown")):
		position.y += speed
	if(Input.is_action_pressed("CameraLeft")):
		position.x -= speed
	if(Input.is_action_pressed("CameraRight")):
		position.x += speed
	var playerShip = entityHandler.getPlayerShip()
	if(playerShip != null):
		position.x = playerShip.position.x
		position.y = playerShip.position.y
	
	
func _input(event):
	if(event.is_action_pressed("CameraZoomIn")):
		zoom += zoomSpeed
	if(event.is_action_pressed("CameraZoomOut")):
		zoom -= zoomSpeed
