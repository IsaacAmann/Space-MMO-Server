extends CharacterBody2D

var ExternalModule = preload("res://Ships/ExternalModule.gd")
var InternalModule = preload("res://Ships/InternalModule.gd")

var internalModules: Array[InternalModule] = []
var externalModules: Array[ExternalModule] = []

var angularVelocity: float = 0

@export var entityID: int
@export var spriteRotation: float
@export var nameLabel: String
@export var lerpPosition: Vector2

var sprite
var externalModuleContainer
var label
var lerpModifier = 2.0

# Define minimum and maximum scales
var min_scale: float = 0.2
var max_scale: float = 1.0

# Called when the node enters the scene tree for the first time.
func _ready():
	externalModuleContainer = get_node("./externalModuleContainer")
	# Get internal modules
	sprite = get_node("./Sprite2D")
	# Get external modules

	# Find and scale fire nodes
	var fire1 = get_node("fire1")
	var fire2 = get_node("fire2")
	var fire3 = get_node("fire3")
	
	# Create name label
	print(nameLabel)
	label = Label.new()
	label.text = nameLabel
	add_child(label)

# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	position += velocity * delta
	rotation += angularVelocity * delta

	#Attempt to correct to server position
	position = position.lerp(lerpPosition, delta * lerpModifier)
	
	# Calculate velocity magnitude
	var velocity_magnitude = velocity.length()

	# Map velocity magnitude to scale range
	var scale = clamp((velocity_magnitude / 1000) * (max_scale - min_scale) + min_scale, min_scale, max_scale)

	# Apply scale to fire nodes
	var fire1 = get_node("fire1")
	var fire2 = get_node("fire2")
	var fire3 = get_node("fire3")
	
	fire1.scale.x = scale
	fire2.scale.x = scale
	fire3.scale.x = scale

	# Modify external Modules
	var moduleContainer = get_node("./externalModuleContainer")
	var modules = moduleContainer.get_children()
	for i in range(modules.size()):
		modules[i].rotation = 0
	label.rotation = rotation * -1
