extends CharacterBody2D

var ExternalModule = preload("res://Ships/ExternalModule.gd")
var InternalModule = preload("res://Ships/InternalModule.gd")

var internalModules: Array[InternalModule] = []
var externalModules: Array[ExternalModule] = []

@export var entityID: int

# Called when the node enters the scene tree for the first time.
func _ready():
	#get internal modules
	
	#get external modules
	
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	position += velocity * delta


