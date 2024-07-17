extends CharacterBody2D

var ExternalModule = preload("res://Ships/ExternalModule.gd")
var InternalModule = preload("res://Ships/InternalModule.gd")

var internalModules: Array[InternalModule] = []
var externalModules: Array[ExternalModule] = []

var angularVelocity: float = 0


@export var entityID: int
@export var spriteRotation: float
@export var nameLabel: String

var sprite
var externalModuleContainer
var label

# Called when the node enters the scene tree for the first time.
func _ready():
	
	externalModuleContainer = get_node("./externalModuleContainer")
	#get internal modules
	sprite = get_node("./Sprite2D")
	#get external modules
	
	#Create name label
	print(nameLabel)
	label = Label.new()
	label.text = nameLabel
	add_child(label)
	
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	
	position += velocity * delta
	#sprite.rotation = spriteRotation
	#sprite.rotation += angularVelocity * delta
	#rotation = spriteRotation
	rotation += angularVelocity * delta
	#Modify external Modules
	var moduleContainer = get_node("./externalModuleContainer")
	var modules = moduleContainer.get_children()
	for i in range(modules.size()):
		modules[i].rotation = 0
	label.rotation = rotation * -1



