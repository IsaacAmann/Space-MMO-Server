extends CharacterBody2D

var ExternalModule = preload("res://Ships/ExternalModule.gd")
var InternalModule = preload("res://Ships/InternalModule.gd")

var internalModules: Array[InternalModule] = []
var externalModules: Array[ExternalModule] = []

var angularVelocity: float = 0


@export var entityID: int
@export var spriteRotation: float
var sprite

# Called when the node enters the scene tree for the first time.
func _ready():
	#get internal modules
	sprite = get_node("./Sprite2D")
	#get external modules
	
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	
	position += velocity * delta
	sprite.rotation = spriteRotation
	sprite.rotation += angularVelocity * delta
	#rotation = spriteRotation
	#rotation += angularVelocity * delta



