extends Node2D

@export var webSocketHandler: Node2D

@export var entityDictionary: Dictionary

var adminOrb = preload("res://Ships/Debug/AdminOrb/AdminOrb.tscn")

# Called when the node enters the scene tree for the first time.
func _ready():
	webSocketHandler.entity_update_message.connect(handleEntityUpdate)
	webSocketHandler.new_entity_message.connect(handleNewEntity)
	webSocketHandler.entity_delete_message.connect(handleEntityDelete)
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func handleNewEntity(message: PackedByteArray):
	#parse message
	
	#Create entity object
	pass
	
func handleEntityDelete(message: PackedByteArray):
	#parse message
	
	#get reference from dictionary
	
	#remove from tree and from dictionary
	pass

func handleEntityUpdate(message: PackedByteArray):
	pass
